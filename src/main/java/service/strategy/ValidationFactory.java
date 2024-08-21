package service.strategy;

import service.validator.*;
import servlet.Route;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ValidationFactory {
    private final Map<String, List<AbstractValidation>> routesToValidationMap;

    public ValidationFactory() {
        this.routesToValidationMap = Map.of(
                Route.TEST_REGISTRATION_PAGE.getUri(), List.of(
                        new PasswordValidation("password", createPasswordValidations()),
                        new EmailValidation("email", createEmailValidations())
                )
        );
    }

    public List<AbstractValidation> getValidationsByContextPath(String contextPath) {
        return routesToValidationMap.getOrDefault(contextPath, Collections.emptyList());
    }

    private List<FieldValidator> createPasswordValidations() {
        return List.of(
                new EmptinessValidator(),
                new LengthValidator(16),
                new SpecialCharacterValidator(2)
        );
    }

    private List<FieldValidator> createEmailValidations() {
        return List.of(
                new EmptinessValidator(),
                new EmailValidator()
        );
    }
}
