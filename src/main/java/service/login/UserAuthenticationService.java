package service.login;

import constant.RouteConstants;
import entity.User;
import enums.Route;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import service.UserService;

import java.io.IOException;
import java.util.Optional;

import constant.SessionAttributes;

@RequiredArgsConstructor
public class UserAuthenticationService {
    private static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private static final String INVALID_CREDENTIALS_MESSAGE = "invalidCredentials";

    private final UserService userService;

    public Optional<User> authenticate(String username, String password) {
        return userService.getExistedUser(username, password);
    }

    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        HttpSession session = req.getSession();
        session.setAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE, user);
        resp.sendRedirect(req.getContextPath() + RouteConstants.HOME);
    }

    public void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        req.setAttribute(ERROR_MESSAGE_ATTRIBUTE, INVALID_CREDENTIALS_MESSAGE);
        req.getRequestDispatcher(Route.LOGIN.getJspPath()).forward(req, resp);
    }
}