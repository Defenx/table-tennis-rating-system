package services;

import dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

/**
 * Интерфейс для сервиса аутентификации пользователя.
 * Обеспечивает методы для аутентификации, установки атрибутов сессии и обработки ошибок аутентификации.
 */
public interface UserAuthenticationService {
    /**
     * Выполняет аутентификацию пользователя по имени пользователя и паролю.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return объект {@link Optional}, содержащий {@link UserDto}, если аутентификация успешна, или пустой объект, если аутентификация не удалась
     */
    Optional<UserDto> authenticate(String username, String password);

    /**
     * Устанавливает атрибуты сессии для пользователя.
     *
     * @param req      HTTP-запрос
     * @param response HTTP-ответ
     * @param user     объект {@link UserDto}, представляющий аутентифицированного пользователя
     * @throws IOException если возникает ошибка ввода-вывода
     */
    void setSessionAttributes(HttpServletRequest req, HttpServletResponse response, UserDto user) throws IOException;

    /**
     * Обрабатывает ошибку аутентификации.
     *
     * @param req  HTTP-запрос
     * @param resp HTTP-ответ
     * @throws IOException      если возникает ошибка ввода-вывода
     * @throws ServletException если возникает ошибка сервлета
     */
    void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException;
}
