package services;

import config.AppConfig;
import dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class UserAuthManager implements UserAuthenticationService {
    private LoginService loginService;

    @Override
    public Optional<UserDto> authenticate(String username, String password) {
        return loginService.getExistedUser(username, password);
    }

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

    @Override
    public void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        req.setAttribute(AppConfig.getErrorMessage(), AppConfig.getInvalidCredentials());
        req.getRequestDispatcher(AppConfig.getLoginPath()).forward(req, resp);
    }
}
