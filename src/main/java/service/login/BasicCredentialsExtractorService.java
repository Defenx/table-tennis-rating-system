package service.login;

import dto.Credentials;
import jakarta.servlet.http.HttpServletRequest;
import service.Service;


public class BasicCredentialsExtractorService implements CredentialsExtractor, Service {

    @Override
    public Credentials extract(HttpServletRequest request) {
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        return new Credentials(username, password);
    }
}
