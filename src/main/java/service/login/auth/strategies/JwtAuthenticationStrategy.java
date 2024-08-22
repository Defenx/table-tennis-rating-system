package service.login.auth.strategies;

import config.AppConfig;
import dto.UserDto;
import enums.ConfigSection;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import service.UserService;
import service.login.auth.JwtTokenService;

import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class JwtAuthenticationStrategy implements AuthenticationStrategy {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ERROR_ATTRIBUTE = AppConfig.getConfigValue(ConfigSection.ERRORS, "errorMessage");
    private static final String INVALID_CREDENTIALS_MESSAGE = AppConfig.getConfigValue(ConfigSection.ERRORS, "invalidCredentials");
    private static final String HOME_PATH = AppConfig.getConfigValue(ConfigSection.PATHS, "home");
    private static final String LOGIN_PATH = AppConfig.getConfigValue(ConfigSection.PATHS, "login");
    private static final String USER_DTO_SESSION_ATTRIBUTE = "userDto";

    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    @Override
    public Optional<UserDto> authenticate(String username, String password) {
        return Optional.empty();
//        return userService.getExistedUser(username, password);
    }

    @Override
    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse resp, UserDto user) throws IOException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        String token = jwtTokenService.generateToken(user.firstName());
        resp.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + token);

        HttpSession session = req.getSession();
        session.setAttribute(USER_DTO_SESSION_ATTRIBUTE, user);

        resp.sendRedirect(req.getContextPath() + HOME_PATH);
    }

    @Override
    public void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        req.setAttribute(ERROR_ATTRIBUTE, INVALID_CREDENTIALS_MESSAGE);
        req.getRequestDispatcher(LOGIN_PATH).forward(req, resp);
    }
}