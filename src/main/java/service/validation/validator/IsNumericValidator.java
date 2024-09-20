package service.validation.validator;

import service.utils.NumberTypeChecker;

import java.util.Collections;
import java.util.List;

public class IsNumericValidator implements Validator {

    private final List<String> errorMessages;

    public IsNumericValidator() {
        this.errorMessages = List.of(
                "Значение этого поля должно быть числом"
        );
    }

    @Override
    public List<String> validate(String value) {

        if (value == null || !NumberTypeChecker.isNumber(value)) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
