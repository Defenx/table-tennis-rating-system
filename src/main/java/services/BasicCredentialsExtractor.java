package services;

import dto.Credentials;
import jakarta.servlet.http.HttpServletRequest;

public class BasicCredentialsExtractor implements CredentialsExtractor {
    @Override
    public Credentials extract(HttpServletRequest request) {
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        return new Credentials(username, password);
    }
}