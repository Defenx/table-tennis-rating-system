package services.login;

import dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис для выполнения операций входа пользователя.
 * Реализует интерфейс {@link LoginService} и обеспечивает функциональность для проверки существования пользователя
 * на основе имени пользователя и пароля.
 */
@AllArgsConstructor
public class UserLoginService implements LoginService {
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Мок-метод для получения списка всех пользователей.
     * Используется только для демонстрационных целей и тестирования.
     *
     * @return список всех пользователей
     */
    private List<UserDto> mockedFindAllUsers() {
        return List.of(
                UserDto.builder().id(UUID.randomUUID()).email("user1@example.com").password(passwordEncoder.encode("password1")).build(),
                UserDto.builder().id(UUID.randomUUID()).email("user2@example.com").password(passwordEncoder.encode("password2")).build()
        );
    }

    /**
     * Мок-метод для получения пользователей по адресу электронной почты.
     * Используется только для демонстрационных целей и тестирования.
     *
     * @param email адрес электронной почты
     * @return список пользователей с указанным адресом электронной почты
     */
    private List<UserDto> mockedFindUsersByEmail(String email) {
        return mockedFindAllUsers().stream().filter(user -> user.getEmail().equals(email)).toList();
    }

    /**
     * Проверяет, существует ли пользователь с указанным адресом электронной почты и паролем.
     *
     * @param email    адрес электронной почты пользователя
     * @param password пароль пользователя
     * @return объект {@link Optional}, содержащий {@link UserDto}, если пользователь найден и пароль совпадает,
     *         или пустой объект, если пользователь не найден или пароль неверен
     */
    @Override
    public Optional<UserDto> getExistedUser(String email, String password) {
        return mockedFindUsersByEmail(email).stream()
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .findFirst();
    }
}
