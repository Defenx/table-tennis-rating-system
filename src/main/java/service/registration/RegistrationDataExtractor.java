package service.registration;

import dto.RegistrationFormDto;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The RegistrationDataExtractor class provides a method to extract registration data from an HTTP request.
 */


public class RegistrationDataExtractor {

    /**
     * Extracts registration data from an HTTP request and creates a RegistrationFormDto object.
     *
     * @param req the HTTP request containing the registration data
     * @return a RegistrationFormDto object with the extracted data
     */
    public static RegistrationFormDto extract(HttpServletRequest req){
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("surname");
        return new RegistrationFormDto(email, password, firstname, lastname);
    }
}
