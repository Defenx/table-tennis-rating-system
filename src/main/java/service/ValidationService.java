package service;

import jakarta.servlet.http.HttpServletRequest;
import service.strategy.ValidationFactory;

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
        for (var validation : validations) {
            var fieldName = validation.getFieldName();
            var value = request.getParameter(fieldName);
            var errorMap = validation.validate(value);
            isValidRequest = isValidRequest && errorMap.get(fieldName).isEmpty();
            request.setAttribute(createAttributeName(fieldName), errorMap.get(fieldName));
        }

        request.setAttribute(IS_VALID_REQUEST, isValidRequest);

        return request;
    }

    private String createAttributeName(String fieldName) {
        return fieldName + "ValidationErrors";
    }
}
