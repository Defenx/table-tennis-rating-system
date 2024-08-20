package dto;

import enums.Role;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Класс передачи данных (DTO) для пользователя.
 */
@Data
@Builder
public class UserDto {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Integer rating;
    private Role role;
}
