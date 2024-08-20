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
import services.login.CredentialsExtractor;
import services.login.auth.JwtTokenService;
import services.login.auth.strategies.AuthenticationStrategy;

import java.io.IOException;
import java.util.Optional;

/**
 * Сервлет для обработки запросов на вход в систему.
 * Реализует методы для отображения страницы входа и обработки отправленных данных для аутентификации.
 * Поддерживает аутентификацию с использованием JWT токенов.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private AuthenticationStrategy authenticationStrategy;
    private CredentialsExtractor credentialsExtractor;
    private JwtTokenService jwtTokenService;

    /**
     * Инициализирует сервлет, получая необходимые сервисы из контекста сервлета.
     *
     * @param config объект {@link ServletConfig} с конфигурацией сервлета
     * @throws ServletException если не удается инициализировать сервлет из-за отсутствия необходимых сервисов
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        authenticationStrategy = (AuthenticationStrategy) config.getServletContext()
                .getAttribute(AppConfig.getUserAuthServiceAttribute());
        credentialsExtractor = (CredentialsExtractor) config.getServletContext()
                .getAttribute(AppConfig.getCredentialsExtractorAttribute());
        jwtTokenService = (JwtTokenService) config.getServletContext()
                .getAttribute(AppConfig.getJwtTokenServiceAttribute());
        if (authenticationStrategy == null || credentialsExtractor == null || jwtTokenService == null) {
            throw new ServletException(AppConfig.getServiceNotInitializedMessage());
        }
    }

    /**
     * Обрабатывает GET-запросы, перенаправляя на страницу входа.
     * Если пользователь уже аутентифицирован с использованием JWT токена, перенаправляет на домашнюю страницу.
     *
     * @param req  HTTP-запрос
     * @param resp HTTP-ответ
     * @throws ServletException если возникает ошибка обработки запроса
     * @throws IOException      если возникает ошибка ввода-вывода
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenService.extractUsername(token);
            if (username != null && jwtTokenService.validateToken(token, username)) {
                resp.sendRedirect(req.getContextPath() + AppConfig.getHomePath());
                return;
            }
        }
        req.getRequestDispatcher(AppConfig.getLoginPath()).forward(req, resp);
    }

    /**
     * Обрабатывает POST-запросы, выполняя аутентификацию пользователя и управляя сессией.
     * Если аутентификация успешна, устанавливает атрибуты сессии и перенаправляет на домашнюю страницу.
     * В случае неудачной аутентификации обрабатывает ошибку аутентификации.
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

            if (credentials.username() == null
                    || credentials.password() == null
                    || credentials.username().isEmpty()
                    || credentials.password().isEmpty()) {
                authenticationStrategy.handleAuthenticationFailure(req, resp);
                return;
            }

            // Аутентификация пользователя
            Optional<UserDto> existedUser = authenticationStrategy
                    .authenticate(credentials.username(), credentials.password());
            if (existedUser.isPresent()) {
                authenticationStrategy.setSessionAttributes(req, resp, existedUser.get());
            } else {
                authenticationStrategy.handleAuthenticationFailure(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute(AppConfig.getUserAuthServiceAttribute(), AppConfig.getAuthenticationFailedMessage());
            req.getRequestDispatcher(AppConfig.getLoginPath()).forward(req, resp);
        }
    }
}