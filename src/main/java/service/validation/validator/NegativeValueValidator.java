package service.validation.validator;

import service.utils.NumberTypeChecker;

import java.util.Collections;
import java.util.List;

public class NegativeValueValidator implements Validator {

    private final List<String> errorMessages;

    public NegativeValueValidator() {
        this.errorMessages = List.of(
                "Значение этого поля не может быть отрицательным"
        );
    }

    @Override
    public List<String> validate(String value) {
        if (value == null || !NumberTypeChecker.isNumber(value)) {
            return errorMessages;
        }

        int count = Integer.parseInt(value);

        if (count < 0) {
            return errorMessages;
        }

        return Collections.emptyList();
    }

}
