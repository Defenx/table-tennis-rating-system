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
                Route.REGISTRATION, Map.of(
                        "firstname", List.of(
                                new EmptinessValidator()),

                        "surname", List.of(
                                new EmptinessValidator()),

                        "email", List.of(
                                new EmptinessValidator(),
                                new EmailPatternValidator(),
                                new EmailRepeatValidator(userDao)),

                        "password", List.of(
                                new EmptinessValidator(),
                                new LengthValidator(8),
                                new SpecialCharacterValidator(2))
                )
        );
    }

    public Map<String, List<Validator>> getValidationsByContextPath(String contextPath) {
        return routesToValidationMap.getOrDefault(contextPath, Collections.emptyMap());
    }
}
