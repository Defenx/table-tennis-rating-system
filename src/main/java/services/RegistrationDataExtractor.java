package services;

import dto.RegistrationDto;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The RegistrationDataExtractor class provides a method to extract registration data from an HTTP request.
 */


public class RegistrationDataExtractor {

    /**
     * Extracts registration data from an HTTP request and creates a RegistrationDto object.
     *
     * @param req the HTTP request containing the registration data
     * @return a RegistrationDto object with the extracted data
     */
    public static RegistrationDto extract(HttpServletRequest req){
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        return new RegistrationDto(email, password, firstname, lastname);
    }
}
