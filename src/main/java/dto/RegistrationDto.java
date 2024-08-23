package dto;

import enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The RegistrationDto class represents the data for user registration.
 */
public record RegistrationDto(
    String email,
    String password,
    String firstname,
    String surname
) {}
