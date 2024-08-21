package service.validation.validator;

import java.util.Collections;
import java.util.List;

public class LengthValidator implements Validator {
    private final int minLength;
    private final List<String> errorMessages;

    public LengthValidator(int minLength) {
        this.minLength = minLength;
        this.errorMessages = List.of(
                "The field must be at least " + minLength + " characters long"
        );
    }

    @Override
    public List<String> validate(String value) {
        if (value == null || value.length() < minLength) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
