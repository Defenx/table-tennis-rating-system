package services.login.auth.strategies;

import config.AppConfig;
import dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import services.login.LoginService;
import services.login.auth.JwtTokenService;

import java.io.IOException;
import java.util.Optional;

/**
 * Реализация стратегии аутентификации с использованием JWT (JSON Web Tokens).
 * Обеспечивает аутентификацию пользователей, генерацию JWT токенов и обработку ошибок аутентификации.
 */
@AllArgsConstructor
public class JwtAuthenticationStrategy implements AuthenticationStrategy {
    private final LoginService loginService;
    private final JwtTokenService jwtTokenService;

    /**
     * Выполняет аутентификацию пользователя по имени пользователя и паролю.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return объект {@link Optional}, содержащий {@link UserDto}, если аутентификация успешна, или пустой объект, если аутентификация не удалась
     */
    @Override
    public Optional<UserDto> authenticate(String username, String password) {
        return loginService.getExistedUser(username, password);
    }

    /**
     * Устанавливает атрибуты сессии для аутентифицированного пользователя и перенаправляет на домашнюю страницу.
     * Генерирует JWT токен и устанавливает его в заголовок Authorization ответа.
     *
     * @param req  HTTP-запрос
     * @param resp HTTP-ответ
     * @param user объект {@link UserDto}, представляющий аутентифицированного пользователя
     * @throws IOException если возникает ошибка ввода-вывода
     */
    @Override
    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse resp, UserDto user) throws IOException {
        String token = jwtTokenService.generateToken(user.getUsername());
        resp.setHeader("Authorization", "Bearer " + token);
        resp.sendRedirect(req.getContextPath() + AppConfig.getHomePath());
    }

    /**
     * Обрабатывает ошибку аутентификации, устанавливая сообщение об ошибке и перенаправляя на страницу входа.
     *
     * @param req  HTTP-запрос
     * @param resp HTTP-ответ
     * @throws IOException      если возникает ошибка ввода-вывода
     * @throws jakarta.servlet.ServletException если возникает ошибка сервлета
     */
    @Override
    public void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        req.setAttribute(AppConfig.getErrorMessage(), AppConfig.getInvalidCredentials());
        req.getRequestDispatcher(AppConfig.getLoginPath()).forward(req, resp);
    }
}