package service.login;

import dto.Credentials;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The interface Credentials extractor.
 */
public interface CredentialsExtractor {

    /**
     * Extract credentials.
     *
     * @param request the request
     * @return the credentials
     */
    Credentials extract(HttpServletRequest request);
}
