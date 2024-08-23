package dto;

import enums.Role;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        Integer rating,
        Role role
) {}