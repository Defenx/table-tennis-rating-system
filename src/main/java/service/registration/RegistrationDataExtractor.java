package service.registration;

import dto.RegistrationFormDto;
import jakarta.servlet.http.HttpServletRequest;

public class RegistrationDataExtractor {

    public static RegistrationFormDto extract(HttpServletRequest req){
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("surname");
        return new RegistrationFormDto(email, password, firstname, lastname);
    }
}
