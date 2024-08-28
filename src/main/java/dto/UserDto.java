package dto;

import enums.Role;

import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        Integer rating,
        Role role
) {
}
