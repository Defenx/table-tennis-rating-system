package service.validation;

import dao.UserDao;
import service.validation.validator.*;
import servlet.Route;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ValidationRegistry {
    private final Map<String, Map<String, List<Validator>>> routesToValidationMap;

    public ValidationRegistry(UserDao userDao) {
        routesToValidationMap = Map.of(
                Route.TEST_REGISTRATION_PAGE.getUri(), Map.of(
                        "password", List.of(
                                new EmptinessValidator(),
                                new LengthValidator(16),
                                new SpecialCharacterValidator(2)),

                        "email", List.of(
                                new EmptinessValidator(),
                                new EmailPatternValidator(),
                                new EmailRepeatValidator(userDao))
                )
        );
    }

    public Map<String, List<Validator>> getValidationsByContextPath(String contextPath) {
        return routesToValidationMap.getOrDefault(contextPath, Collections.emptyMap());
    }
}
