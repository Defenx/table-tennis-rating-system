package services;

import dto.Credentials;
import jakarta.servlet.http.HttpServletRequest;

public interface CredentialsExtractor {
    Credentials extract(HttpServletRequest request);
}