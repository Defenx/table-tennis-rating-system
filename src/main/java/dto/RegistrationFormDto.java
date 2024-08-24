package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The RegistrationFormDto class represents the data for user registration.
 */
@Data
@Builder
@AllArgsConstructor
public class RegistrationFormDto {
    private String email;
    private String password;
    private String firstname;
    private String surname;
}
