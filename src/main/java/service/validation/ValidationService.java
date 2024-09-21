package service.validation;

import jakarta.servlet.http.HttpServletRequest;
import service.validation.chain.ValidationManager;


public class ValidationService {
    public static final String IS_VALID_REQUEST = "isValidRequest";
    private final ValidationRegistry validationRegistry;
    private final ValidationManager validationManager;
    private final String validationFieldSuffix;

    public ValidationService(ValidationRegistry validationRegistry, ValidationManager validationManager) {
        this.validationRegistry = validationRegistry;
        this.validationFieldSuffix = "ValidationErrors";
        this.validationManager = validationManager;
    }

    public HttpServletRequest validate(HttpServletRequest request) {

        var contextPath = request.getRequestURI();
        var fieldToValidatorsMap = validationRegistry.getValidationsByContextPath(contextPath);
        var isValidRequest = true;

        for (var entry : fieldToValidatorsMap.entrySet()) {
            var fieldName = entry.getKey();
            validationManager.setValidators(entry.getValue());
            var fieldValue = request.getParameter(fieldName);
            var errorMessage = validationManager.runValidation(fieldValue);
            isValidRequest = isValidRequest && errorMessage.isEmpty();
            request.setAttribute(createAttributeName(fieldName), errorMessage);
        }

        request.setAttribute(IS_VALID_REQUEST, isValidRequest);

        return request;
    }

    private String createAttributeName(String fieldName) {
        return fieldName + validationFieldSuffix;
    }
}
