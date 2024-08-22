package servlet;

import config.AppConfig;
import dto.Credentials;
import dto.UserDto;
import enums.ConfigSection;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.login.CredentialsExtractor;
import service.login.auth.JwtTokenService;
import service.login.auth.UserAuthenticationService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserAuthenticationService userAuthenticationService;
    private CredentialsExtractor credentialsExtractor;
    private JwtTokenService jwtTokenService;

    private static final String USER_AUTH_SERVICE_ATTRIBUTE = AppConfig.getConfigValue(ConfigSection.ATTRIBUTES, "userAuthService");
    private static final String CREDENTIALS_EXTRACTOR_ATTRIBUTE = AppConfig.getConfigValue(ConfigSection.ATTRIBUTES, "credentialsExtractor");
    private static final String JWT_TOKEN_SERVICE_ATTRIBUTE = AppConfig.getConfigValue(ConfigSection.ATTRIBUTES, "jwtTokenService");
    private static final String SERVICE_NOT_INITIALIZED_MESSAGE = AppConfig.getConfigValue(ConfigSection.ERRORS, "serviceNotInitialized");
    private static final String AUTHENTICATION_FAILED_MESSAGE = AppConfig.getConfigValue(ConfigSection.ERRORS, "authenticationFailed");
    private static final String HOME_PATH = AppConfig.getConfigValue(ConfigSection.PATHS, "home");
    private static final String LOGIN_PATH = AppConfig.getConfigValue(ConfigSection.PATHS, "login");

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userAuthenticationService = (UserAuthenticationService) config.getServletContext().getAttribute(USER_AUTH_SERVICE_ATTRIBUTE);
        credentialsExtractor = (CredentialsExtractor) config.getServletContext().getAttribute(CREDENTIALS_EXTRACTOR_ATTRIBUTE);
        jwtTokenService = (JwtTokenService) config.getServletContext().getAttribute(JWT_TOKEN_SERVICE_ATTRIBUTE);
        if (userAuthenticationService == null || credentialsExtractor == null || jwtTokenService == null) {
            throw new ServletException(SERVICE_NOT_INITIALIZED_MESSAGE);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenService.extractUsername(token);
            if (username != null && jwtTokenService.validateToken(token, username)) {
                resp.sendRedirect(req.getContextPath() + HOME_PATH);
                return;
            }
        }
        req.getRequestDispatcher(LOGIN_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Credentials credentials = credentialsExtractor.extract(req);

            if (credentials.username() == null
                    || credentials.password() == null
                    || credentials.username().isEmpty()
                    || credentials.password().isEmpty()) {
                userAuthenticationService.handleAuthenticationFailure(req, resp);
                return;
            }

            // Аутентификация пользователя
            Optional<UserDto> existedUser = userAuthenticationService.authenticate(credentials.username(), credentials.password());
            if (existedUser.isPresent()) {
                userAuthenticationService.setSessionAttributes(req, resp, existedUser.get());
            } else {
                userAuthenticationService.handleAuthenticationFailure(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute(USER_AUTH_SERVICE_ATTRIBUTE, AUTHENTICATION_FAILED_MESSAGE);
            req.getRequestDispatcher(LOGIN_PATH).forward(req, resp);
        }
    }
}