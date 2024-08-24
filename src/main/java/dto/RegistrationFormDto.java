package dto;

/**
 * The RegistrationFormDto class represents the data for user registration.
 */
public record RegistrationFormDto(
        String email,
        String password,
        String firstname,
        String surname
) {}
