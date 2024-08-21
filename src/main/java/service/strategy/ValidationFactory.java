package service.strategy;

import service.validator.*;
import servlet.Route;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ValidationFactory {
    private final Map<String, Map<String, List<FieldValidator>>> routesToValidationMap;

    public ValidationFactory() {
        routesToValidationMap = Map.of(
                Route.TEST_REGISTRATION_PAGE.getUri(),Map.of(
                        "password",List.of(
                                new EmptinessValidator(),
                                new LengthValidator(16),
                                new SpecialCharacterValidator(2)),

                        "email", List.of(
                                new EmptinessValidator(),
                                new EmailValidator())
                )
        );
    }

    public Map<String, List<FieldValidator>> getValidationsByContextPath(String contextPath) {
        return routesToValidationMap.getOrDefault(contextPath, Collections.emptyMap());
    }


}
