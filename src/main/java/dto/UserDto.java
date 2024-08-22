package dto;

import enums.Role;

import java.util.UUID;

/**
 * The type User dto.
 */
public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        Integer rating,
        Role role
) {}