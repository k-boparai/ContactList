package kboparai.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private int id;
    private String name;
    private Long phone;
    private String address;
    private String email;
    private String role;

    private String[] roles = {"Admin", "Member", "Guest"};
}
