package model.helper;

import dto.RegistrationDto;
import jakarta.servlet.http.HttpServletRequest;

public class RegistrationDataExtractor {
    public static RegistrationDto extract(HttpServletRequest req){
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        return new RegistrationDto(email, password, firstname, lastname);
    }
}
