package service.validation.validator;

import java.util.Collections;
import java.util.List;

public class MaxLengthValidator implements Validator {

    private final List<String> errorMessages;
    private final int maxLength;

    public MaxLengthValidator(int maxLength) {
        this.maxLength = maxLength;
        this.errorMessages = List.of(
                "Поле должно быть длиной не менее " + maxLength + " символов"
        );
    }

    @Override
    public List<String> validate(String value) {
        if (value == null || value.length() > maxLength) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
