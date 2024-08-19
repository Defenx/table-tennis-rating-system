package services;

import config.AppConfig;
import dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Optional;

/**
 * Менеджер аутентификации пользователя.
 * Реализует интерфейс {@link UserAuthenticationService} и управляет процессами аутентификации, установкой атрибутов сессии и обработкой ошибок аутентификации.
 */
@AllArgsConstructor
public class UserAuthManager implements UserAuthenticationService {
    private final LoginService loginService;

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
     *
     * @param req      HTTP-запрос
     * @param resp     HTTP-ответ
     * @param user     объект {@link UserDto}, представляющий аутентифицированного пользователя
     * @throws IOException если возникает ошибка ввода-вывода
     */
    @Override
    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse resp, UserDto user) throws IOException {
        HttpSession session = req.getSession();
        session.setAttribute(AppConfig.getUserIdAttribute(), user.getId());
        session.setAttribute(AppConfig.getFirstNameAttribute(), user.getFirstName());
        session.setAttribute(AppConfig.getLastNameAttribute(), user.getLastName());
        session.setAttribute(AppConfig.getRatingAttribute(), user.getRating());
        session.setAttribute(AppConfig.getUserRoleAttribute(), user.getRole());
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
