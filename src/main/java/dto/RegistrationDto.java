package dto;

import enums.Role;
import lombok.Data;

/**
 * The RegistrationDto class represents the data for user registration.
 */

@Data
public class RegistrationDto {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private Role role;
    private int rating;

    public RegistrationDto(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = Role.USER;
        this.rating = 1000;
    }
}
