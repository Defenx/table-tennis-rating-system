package service.login;


import dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import service.UserService;

import java.io.IOException;
import java.util.Optional;


@AllArgsConstructor
public class UserAuthenticationService {
    private static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private static final String INVALID_CREDENTIALS_MESSAGE = "invalidCredentials";
    private static final String HOME_PATH =  "/";
    private static final String LOGIN_PATH = "/login";
    private static final String USER_DTO_SESSION_ATTRIBUTE = "userDto";

    private final UserService userService;


    public Optional<UserDto> authenticate(String username, String password) {
        return userService.getExistedUser(username, password);
    }


    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse resp, UserDto user) throws IOException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        HttpSession session = req.getSession();
        session.setAttribute(USER_DTO_SESSION_ATTRIBUTE, user);
        resp.sendRedirect(req.getContextPath() + HOME_PATH);
    }


    public void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        req.setAttribute(ERROR_MESSAGE_ATTRIBUTE, INVALID_CREDENTIALS_MESSAGE);
        req.getRequestDispatcher(LOGIN_PATH).forward(req, resp);
    }
}