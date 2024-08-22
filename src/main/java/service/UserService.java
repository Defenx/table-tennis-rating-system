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
                UserDto.builder().id(UUID.randomUUID()).build(),
                UserDto.builder().id(UUID.randomUUID()).build()
        );
    }

    private List<UserDto> mockedFindUsersByEmail(String email) {
        return mockedFindAllUsers().stream().filter(user -> user.lastName().equals(email)).toList();
    }


    public Optional<UserDto> getExistedUser(String email, String password) {
        return mockedFindUsersByEmail(email).stream()
                .findFirst();
    }


}
