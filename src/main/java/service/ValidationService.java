package service;

import jakarta.servlet.http.HttpServletRequest;
import service.strategy.ValidationFactory;
import service.validator.FieldValidator;

import java.util.ArrayList;
import java.util.List;

public class ValidationService {
    public static final String IS_VALID_REQUEST = "isValidRequest";
    private final ValidationFactory validationFactory;

    public ValidationService(ValidationFactory validationFactory) {
        this.validationFactory = validationFactory;
    }

    public HttpServletRequest validate(HttpServletRequest request) {
        var contextPath = request.getRequestURI();
        var validations = validationFactory.getValidationsByContextPath(contextPath);
        var isValidRequest = true;

        // "/registration" need to check fields: name, surname, password, email and etc
        for (String fieldName : validations.keySet()) {

            String value = request.getParameter(fieldName);
            List<FieldValidator> errorMap = validations.get(fieldName);
            List<String> poop = new ArrayList<>();
            for (FieldValidator fieldValidator:errorMap) {
                poop.addAll(fieldValidator.validate(value));
            }
            isValidRequest = isValidRequest && poop.isEmpty();
            request.setAttribute(createAttributeName(fieldName), poop);
        }

        request.setAttribute(IS_VALID_REQUEST, isValidRequest);

        return request;
    }

    private String createAttributeName(String fieldName) {
        return fieldName + "ValidationErrors";
    }
}
