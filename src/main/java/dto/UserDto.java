package dto;

import enums.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Integer rating;
    private Role role;
}
