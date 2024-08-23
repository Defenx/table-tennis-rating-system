package dto;

/**
 * The RegistrationDto class represents the data for user registration.
 */
public record RegistrationDto(
    String email,
    String password,
    String firstname,
    String surname
) {}
