package service.login;


import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import service.UserService;

import java.io.IOException;
import java.util.Optional;


@RequiredArgsConstructor
public class UserAuthenticationService {
    private static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private static final String INVALID_CREDENTIALS_MESSAGE = "invalidCredentials";
    private static final String HOME_PATH =  "/";
    private static final String LOGIN_PAGE = "/login.jsp";
    private static final String USER_SESSION_ATTRIBUTE = "User";

    private final UserService userService;


    public Optional<User> authenticate(String username, String password) {
        return userService.getExistedUser(username, password);
    }


    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        HttpSession session = req.getSession();
        session.setAttribute(USER_SESSION_ATTRIBUTE, user);
        resp.sendRedirect(req.getContextPath() + HOME_PATH);
    }


    public void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        req.setAttribute(ERROR_MESSAGE_ATTRIBUTE, INVALID_CREDENTIALS_MESSAGE);
        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
    }
}
