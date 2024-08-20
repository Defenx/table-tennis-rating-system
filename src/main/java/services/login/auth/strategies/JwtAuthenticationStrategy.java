package services.login.auth.strategies;

import config.AppConfig;
import dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import services.login.LoginService;
import services.login.auth.JwtTokenService;

import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class JwtAuthenticationStrategy implements AuthenticationStrategy {
    private final LoginService loginService;
    private final JwtTokenService jwtTokenService;

    @Override
    public Optional<UserDto> authenticate(String username, String password) {
        return loginService.getExistedUser(username, password);
    }

    @Override
    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse resp, UserDto user) throws IOException {
        String token = jwtTokenService.generateToken(user.getUsername());
        resp.setHeader("Authorization", "Bearer " + token);
        resp.sendRedirect(req.getContextPath() + AppConfig.getHomePath());
    }

    @Override
    public void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException, jakarta.servlet.ServletException {
        req.setAttribute(AppConfig.getErrorMessage(), AppConfig.getInvalidCredentials());
        req.getRequestDispatcher(AppConfig.getLoginPath()).forward(req, resp);
    }
}
