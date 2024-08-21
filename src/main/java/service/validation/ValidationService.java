package service.validation;

import jakarta.servlet.http.HttpServletRequest;
import service.validation.validator.Validator;

import java.util.ArrayList;

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

        for (String fieldName : validations.keySet()) {
            var value = request.getParameter(fieldName);
            var errorMap = validations.get(fieldName);
            var errors = new ArrayList<String>();
            for (Validator validator : errorMap) {
                errors.addAll(validator.validate(value));
            }
            isValidRequest = isValidRequest && errors.isEmpty();
            request.setAttribute(createAttributeName(fieldName), errors);
        }

        request.setAttribute(IS_VALID_REQUEST, isValidRequest);

        return request;
    }

    private String createAttributeName(String fieldName) {
        return fieldName + "ValidationErrors";
    }
}
