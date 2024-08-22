package service.login.auth;

import dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import service.Service;
import service.login.auth.strategies.AuthenticationStrategy;

import java.io.IOException;
import java.util.Optional;


@AllArgsConstructor
public class UserAuthenticationService implements Service {

    private final AuthenticationStrategy authenticationStrategy;


    public Optional<UserDto> authenticate(String username, String password) {
        return authenticationStrategy.authenticate(username, password);
    }


    public void setSessionAttributes(HttpServletRequest req, HttpServletResponse response, UserDto user) throws IOException {
        authenticationStrategy.setSessionAttributes(req, response, user);
    }


    public void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        authenticationStrategy.handleAuthenticationFailure(req, resp);
    }
}