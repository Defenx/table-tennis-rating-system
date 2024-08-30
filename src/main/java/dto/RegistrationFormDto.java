package dto;

public record RegistrationFormDto(
        String email,
        String password,
        String firstname,
        String surname
) {
}
