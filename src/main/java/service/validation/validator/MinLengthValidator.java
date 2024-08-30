package service.validation.validator;

import java.util.Collections;
import java.util.List;

public class MinLengthValidator implements Validator {

    private final List<String> errorMessages;
    private final int minLength;

    public MinLengthValidator(int minLength) {
        this.minLength = minLength;
        this.errorMessages = List.of(
                "Поле должно быть длиной не менее " + minLength + " символов"
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
