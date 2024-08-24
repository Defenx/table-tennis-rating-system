package dto;

import enums.Role;

import java.util.UUID;

public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer rating;
    private Role role;
}