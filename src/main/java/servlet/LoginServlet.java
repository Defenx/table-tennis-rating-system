package servlet;

import config.AppConfig;
import dto.Credentials;
import dto.UserDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.CredentialsExtractor;
import services.UserAuthenticationService;

import java.io.IOException;
import java.util.Optional;

/**
 * Сервлет для обработки запросов на вход в систему.
 * Реализует методы для отображения страницы входа и обработки отправленных данных для аутентификации.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserAuthenticationService userAuthenticationService;
    private CredentialsExtractor credentialsExtractor;

    /**
     * Инициализирует сервлет, получая необходимые сервисы из контекста сервлета.
     *
     * @param config объект {@link ServletConfig} с конфигурацией сервлета
     * @throws ServletException если не удается инициализировать сервлет
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userAuthenticationService = (UserAuthenticationService) config.getServletContext().getAttribute(AppConfig.getUserAuthServiceAttribute());
        credentialsExtractor = (CredentialsExtractor) config.getServletContext().getAttribute(AppConfig.getCredentialsExtractorAttribute());
        if (userAuthenticationService == null || credentialsExtractor == null) {
            throw new ServletException(AppConfig.getServiceNotInitializedMessage());
        }
    }

    /**
     * Обрабатывает GET-запросы, перенаправляя на страницу входа.
     *
     * @param req  HTTP-запрос
     * @param resp HTTP-ответ
     * @throws ServletException если возникает ошибка обработки запроса
     * @throws IOException      если возникает ошибка ввода-вывода
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(AppConfig.getLoginPath()).forward(req, resp);
    }

    /**
     * Обрабатывает POST-запросы, выполняя аутентификацию пользователя и управляя сессией.
     *
     * @param req  HTTP-запрос
     * @param resp HTTP-ответ
     * @throws ServletException если возникает ошибка обработки запроса
     * @throws IOException      если возникает ошибка ввода-вывода
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Credentials credentials = credentialsExtractor.extract(req);

            // Проверка на наличие имени пользователя и пароля
            if (credentials.username() == null || credentials.password() == null || credentials.username().isEmpty() || credentials.password().isEmpty()) {
                userAuthenticationService.handleAuthenticationFailure(req, resp);
                return;
            }

            // Аутентификация пользователя
            Optional<UserDto> existedUser = userAuthenticationService.authenticate(credentials.username(), credentials.password());
            if (existedUser.isPresent()) {
                // Установка атрибутов сессии и перенаправление на домашнюю страницу
                userAuthenticationService.setSessionAttributes(req, resp, existedUser.get());
            } else {
                // Обработка ошибки аутентификации
                userAuthenticationService.handleAuthenticationFailure(req, resp);
            }
        } catch (Exception e) {
            // Обработка и перенаправление на страницу входа с сообщением об ошибке
            req.setAttribute(AppConfig.getUserAuthServiceAttribute(), AppConfig.getAuthenticationFailedMessage());
            req.getRequestDispatcher(AppConfig.getLoginPath()).forward(req, resp);
        }
    }
}
