package service.login;


import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import service.UserService;

import java.io.IOException;
import java.util.Optional;

import constant.SessionAttributes;

/**
 * The type User authentication service.
 */
@RequiredArgsConstructor
public class UserAuthenticationService {
    private static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private static final String INVALID_CREDENTIALS_MESSAGE = "invalidCredentials";

    private final UserService userService;

    /**
     * Authenticate optional.
     *
     * @param username the username
     * @param password the password
     * @return the optional
     */
    public Optional<User> authenticate(String username, String password) {
        return userService.getExistedUser(username, password);
    }

    /**
     * Sets session attributes.
     *
     * @param req  the req
     * @param resp the resp
     * @param user the user
     * @throws IOException the io exception
     */
    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        HttpSession session = req.getSession();
        session.setAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE, user);
        resp.sendRedirect(req.getContextPath() + RouteConstants.HOME);
    }

    /**
     * Handle authentication failure.
     *
     * @param req  the req
     * @param resp the resp
     * @throws IOException      the io exception
     * @throws ServletException the servlet exception
     */
    public void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        req.setAttribute(ERROR_MESSAGE_ATTRIBUTE, INVALID_CREDENTIALS_MESSAGE);
        req.getRequestDispatcher(Route.LOGIN.getJspPath()).forward(req, resp);
    }
}
