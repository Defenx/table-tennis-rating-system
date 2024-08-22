package service.login.auth.strategies;

import dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;


public interface AuthenticationStrategy {

    Optional<UserDto> authenticate(String username, String password);


    void setSessionAttributes(HttpServletRequest req, HttpServletResponse response, UserDto user) throws IOException;

    void handleAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException;
}
