package service.login;

import dto.Credentials;
import jakarta.servlet.http.HttpServletRequest;

public class CredentialsExtractor {

    public Credentials extract(HttpServletRequest request) {
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        return new Credentials(username, password);
    }
}
