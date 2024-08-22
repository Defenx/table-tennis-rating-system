package service;

import dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class UserService implements Service{
    private final BCryptPasswordEncoder passwordEncoder;

    private List<UserDto> mockedFindAllUsers() {
        return List.of(
                UserDto.builder().id(UUID.randomUUID()).email("user1@example.com").password(passwordEncoder.encode("password1")).build(),
                UserDto.builder().id(UUID.randomUUID()).email("user2@example.com").password(passwordEncoder.encode("password2")).build()
        );
    }

    private List<UserDto> mockedFindUsersByEmail(String email) {
        return mockedFindAllUsers().stream().filter(user -> user.getEmail().equals(email)).toList();
    }


    public Optional<UserDto> getExistedUser(String email, String password) {
        return mockedFindUsersByEmail(email).stream()
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .findFirst();
    }


}
