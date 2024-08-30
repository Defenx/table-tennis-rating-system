package service.validation;

import dao.UserDao;
import enums.ExtensionName;
import service.validation.validator.*;
import constant.RouteConstants;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ValidationRegistry {
    private final Map<String, Map<String, List<Validator>>> routesToValidationMap;

    public ValidationRegistry(UserDao userDao) {
        routesToValidationMap = Map.of(
                RouteConstants.REGISTRATION, Map.of(
                        "firstname", List.of(
                                new EmptinessValidator(),
                                new LanguageValidator(),
                                new CapitalLetterValidator()
                        ),

                        "surname", List.of(
                                new EmptinessValidator(),
                                new LanguageValidator(),
                                new CapitalLetterValidator()
                        ),

                        "email", List.of(
                                new EmptinessValidator(),
                                new EmailPatternValidator(),
                                new EmailRepeatValidator(userDao)
                        ),

                        "password", List.of(
                                new EmptinessValidator(),
                                new MinLengthValidator(5),
                                new MaxLengthValidator(16),
                                new SpecialCharacterValidator(1),
                                new SpaceSymbolsValidator()
                        )
                ),
                RouteConstants.LOGIN, Map.of(
                        "email", List.of(
                                new EmptinessValidator()
                        ),
                        "password", List.of(
                                new EmptinessValidator()
                        )

                ),
                RouteConstants.ADMIN_TOURNAMENT_CREATE, Map.of(
                        ExtensionName.IS_RATING.name(), List.of(
                                new EmptinessValidator()
                        ),
                        ExtensionName.TRAINING_SETS.name(), List.of(
                                new EmptinessValidator()
                        ),
                        ExtensionName.PLAYOFF_SETS.name(), List.of(
                                new EmptinessValidator()
                        )

                )
        );
    }

    public Map<String, List<Validator>> getValidationsByContextPath(String contextPath) {
        return routesToValidationMap.getOrDefault(contextPath, Collections.emptyMap());
    }
}
