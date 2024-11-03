package kboparai.database;


import kboparai.beans.Contact;
import kboparai.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseAccess {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findUserAccount (String email) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "SELECT * FROM sec_user where email = :email";
        parameters.addValue("email", email);
        ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,
                new BeanPropertyRowMapper<User>(User.class));
        if (users.size()>0) {
            return users.get(0);
        }
        else {
            return null;
        }
    }

    public List<String> getRolesById (Long userId) {
        ArrayList<String> roles = new ArrayList<String>();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "select user_role.userId, sec_role.roleName "
                + "FROM user_role, sec_role "
                + "WHERE user_role.roleId = sec_role.roleId "
                + "AND userId = :userId";
        parameters.addValue("userId", userId);
        List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
        for (Map<String, Object> row: rows) {
            roles.add((String)row.get("roleName"));
        }
        return roles;
    }

    public void addUser (String email, String password) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "INSERT INTO sec_user "
                + "(email, encryptedPassword, enabled) "
                + "VALUES (:email, :encryptedPassword, 1)";
        parameters.addValue("email", email);
        parameters.addValue("encryptedPassword", passwordEncoder.encode(password));
        jdbc.update(query, parameters);
    }

    public void addRole (Long userId, Long roleId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "INSERT INTO user_role (userId, roleId) "
                + "VALUES (:userId, :roleId)";
        parameters.addValue("userId", userId);
        parameters.addValue("roleId", roleId);
        jdbc.update(query, parameters);
    }

    public Long addContact (Contact contact) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO contact (name, phone, address, email, role) VALUES "
                + "(:name, :phone, :address, :email, :role)";
        parameters.addValue("name", contact.getName());
        parameters.addValue("phone", contact.getPhone());
        parameters.addValue("address", contact.getAddress());
        parameters.addValue("email", contact.getEmail());
        parameters.addValue("role", contact.getRole());
        jdbc.update(query, parameters, keyHolder);
        return (Long) keyHolder.getKey();
    }

    public List<Contact> listAll () {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "SELECT * FROM contact";
        return jdbc.query(query, parameters, new BeanPropertyRowMapper<Contact>(Contact.class));
    }

    public List<Contact> listContactByID (Long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "SELECT * FROM contact WHERE id = :id";
        parameters.addValue("id", id);
        return jdbc.query(query, parameters, new BeanPropertyRowMapper<Contact>(Contact.class));
    }

    public void deleteContact (Long id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "DELETE FROM contact WHERE id = :id";
        parameters.addValue("id", id);
        jdbc.update(query, parameters);
    }

    public void updateContact (Contact contact) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "UPDATE contact SET name = :name, phone = :phone, address = :address, "
                + "email = :email, role = :role WHERE id = :id";
        parameters.addValue("name", contact.getName());
        parameters.addValue("phone", contact.getPhone());
        parameters.addValue("address", contact.getAddress());
        parameters.addValue("email", contact.getEmail());
        parameters.addValue("role", contact.getRole());
        parameters.addValue("id", contact.getId());
        jdbc.update(query, parameters);
    }

    public void replaceAll (List<Contact> contactList) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "DELETE FROM contact";
        jdbc.update(query, parameters);

        for (Contact c : contactList) {
            addContact(c);

        }
    }
}
