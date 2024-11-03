package kboparai.controllers;


import kboparai.beans.Contact;
import kboparai.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ContactController {

    @Autowired
    @Lazy
    private DatabaseAccess da;

    private ModelAndView mv;

    @GetMapping
    public ModelAndView getIndex (Model model) {
        mv = new ModelAndView("index", "contact", new Contact());
        return mv;
    }

    @GetMapping("login")
    public ModelAndView getLogin (Model model) {
        mv = new ModelAndView("login", "contact", new Contact());
        return mv;
    }

    @GetMapping("register")
    public ModelAndView getRegister (Model model) {
        mv = new ModelAndView("register", "contact", new Contact());
        return mv;
    }

    @GetMapping("permission_denied")
    public ModelAndView getDenied (Model model) {
        mv = new ModelAndView("error/permission_denied", "contact", new Contact());
        return mv;
    }

    @PostMapping("register")
    public ModelAndView postRegister (@RequestParam String username,
                                      @RequestParam String password,
//                                      @RequestParam String role,
                                      @RequestParam (required = false) String admin,
                                      @RequestParam (required = false) String member,
                                      @RequestParam (required = false) String guest) {
        da.addUser(username, password);
        Long userId = da.findUserAccount(username).getUserId();
        if (admin != null) {
            da.addRole(userId, 1L);
        }
        if (member != null) {
            da.addRole(userId, 2L);
        }
        if (guest != null) {
            da.addRole(userId, 3L);
        }
        mv = new ModelAndView("redirect:", "contact", new Contact());
        return mv;
    }


    @GetMapping("secure/addContact")
    public ModelAndView addContact (Model model, Authentication authentication) {
        String email = authentication.getName();
        List<String> roleList= new ArrayList<String>();
        for (GrantedAuthority ga: authentication.getAuthorities()) {
            roleList.add(ga.getAuthority());
        }
        mv = new ModelAndView("secure/addContact", "contactList", da.listAll());
        mv.addObject("contact", new Contact());
        return mv;
    }

    @PostMapping("secure/addContact")
    public ModelAndView addContact2 (Model model, @ModelAttribute Contact contact, Authentication authentication) {
        String email = authentication.getName();
        List<String> roleList= new ArrayList<String>();
        for (GrantedAuthority ga: authentication.getAuthorities()) {
            roleList.add(ga.getAuthority());
        }
        da.addContact(contact);
        mv = new ModelAndView("secure/addContact", "contactList", da.listAll());
        mv.addObject("contact", new Contact());
        return mv;
    }

    @GetMapping("secure/listContacts")
    public ModelAndView listContacts (Model model, Authentication authentication) {
        String email = authentication.getName();
        List<String> roleList= new ArrayList<String>();
        for (GrantedAuthority ga: authentication.getAuthorities()) {
            roleList.add(ga.getAuthority());
        }
        mv = new ModelAndView("secure/listContacts", "contactList", da.listAll());
        mv.addObject("contact", new Contact());
        return mv;
    }

    @GetMapping("secure/deleteContact/{id}")
    public ModelAndView deleteContact (Model model, @PathVariable Long id) {
        da.deleteContact(id);
        mv = new ModelAndView ("redirect:/secure/listContacts");
        return mv;
    }

    @GetMapping("secure/editContact/{id}")
    public ModelAndView editContact (Model model, @PathVariable Long id) {
        mv = new ModelAndView ("secure/listContacts", "contact", da.listContactByID(id).get(0));
        mv.addObject("contactList", da.listAll());
        return mv;
    }

    @PostMapping("secure/editContact")
    public ModelAndView editContact2 (Model model, @ModelAttribute Contact contact) {
        da.updateContact(contact);
        mv = new ModelAndView("redirect:/secure/listContacts", "contact", new Contact());
        return mv;
    }

    @PutMapping(value = "replaceContacts", consumes = "application/json")
    public String replaceContacts (@RequestBody List<Contact> contactList) {
        da.replaceAll(contactList);
        return "Contacts replaced.";
    }
}
