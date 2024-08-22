package service;

import dto.UserDto;
import enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;

    private List<UserDto> mockedFindAllUsers() {
        return List.of(
                new UserDto(UUID.randomUUID(), "John", "Doe", 5, Role.USER),
                new UserDto(UUID.randomUUID(), "Jane", "Doe", 4, Role.USER)
        );
    }

    private List<UserDto> mockedFindUsersByEmail(String email) {
        return mockedFindAllUsers().stream()
                .toList();
    }

    public Optional<UserDto> getExistedUser(String email, String password) {
        return mockedFindUsersByEmail(email).stream()
                .findFirst();
    }
}