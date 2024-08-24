package service.login;

import dto.Credentials;
import jakarta.servlet.http.HttpServletRequest;

public interface CredentialsExtractor {

    Credentials extract(HttpServletRequest request);
}
