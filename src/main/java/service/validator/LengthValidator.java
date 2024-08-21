package service.validator;

import java.util.Collections;
import java.util.List;

public class LengthValidator implements FieldValidator {
    private final int minLength;
    private final List<String> errorMessages;

    public LengthValidator(int minLength) {
        this.minLength = minLength;
        this.errorMessages = List.of(
                "Password must be at least " + minLength + " characters long"
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
