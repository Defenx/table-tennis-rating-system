package services.login.auth;

import dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import services.login.auth.strategies.AuthenticationStrategy;

import java.io.IOException;
import java.util.Optional;

/**
 * Сервис аутентификации пользователя, использующий стратегию аутентификации.
 */
@AllArgsConstructor
public class UserAuthenticationService {

    private final AuthenticationStrategy authenticationStrategy;

    /**
     * Выполняет аутентификацию пользователя по имени пользователя и паролю.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return объект {@link Optional}, содержащий {@link UserDto}, если аутентификация успешна, или пустой объект, если аутентификация не удалась
     */
    public Optional<UserDto> authenticate(String username, String password) {
        return authenticationStrategy.authenticate(username, password);
    }

    /**
     * Устанавливает атрибуты сессии для пользователя.
     *
     * @param req      HTTP-запрос
     * @param response HTTP-ответ
     * @param user     объект {@link UserDto}, представляющий аутентифицированного пользователя
     * @throws IOException если возникает ошибка ввода-вывода
     */
    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse response, UserDto user) throws IOException {
        authenticationStrategy.setSessionAttributes(req, response, user);
    }

    /**
     * Обрабатывает ошибку аутентификации.
     *
     * @param req  HTTP-запрос
     * @param resp HTTP-ответ
     * @throws IOException      если возникает ошибка ввода-вывода
     * @throws ServletException если возникает ошибка сервлета
     */
    public void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        authenticationStrategy.handleAuthenticationFailure(req, resp);
    }
}