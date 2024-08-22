package service.validation;

import jakarta.servlet.http.HttpServletRequest;
import service.validation.validator.Validator;

import java.util.List;

public class ValidationService {
    public static final String IS_VALID_REQUEST = "isValidRequest";
    private final ValidationRegistry validationRegistry;
    private final String validationFieldSuffix;

    public ValidationService(ValidationRegistry validationRegistry) {
        this.validationRegistry = validationRegistry;
        this.validationFieldSuffix = "ValidationErrors";
    }

    public HttpServletRequest validate(HttpServletRequest request) {
        var contextPath = request.getRequestURI();
        var fieldToValidatorsMap = validationRegistry.getValidationsByContextPath(contextPath);
        var isValidRequest = true;

        for (var entry : fieldToValidatorsMap.entrySet()) {
            var fieldName = entry.getKey();
            var validators = entry.getValue();
            var fieldValue = request.getParameter(fieldName);
            var errorMessages = validateValue(fieldValue, validators);
            isValidRequest = isValidRequest && errorMessages.isEmpty();
            request.setAttribute(createAttributeName(fieldName), errorMessages);
        }

        request.setAttribute(IS_VALID_REQUEST, isValidRequest);

        return request;
    }

    private List<String> validateValue(String fieldValue, List<Validator> validators) {
        return validators.stream()
                .flatMap(validator -> validator.validate(fieldValue).stream())
                .toList();
    }

    private String createAttributeName(String fieldName) {
        return fieldName + validationFieldSuffix;
    }
}
