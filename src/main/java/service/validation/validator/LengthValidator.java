package service.validation.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LengthValidator implements Validator {

    private final int minLength;

    private final int maxLength;

    private final String minErrorMessage;

    private final String maxErrorMessage;

    private final List<String> errorMessages;

    public LengthValidator(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.minErrorMessage = "Поле должно быть длиной не менее " + minLength + " символов";
        this.maxErrorMessage = "Поле должно быть длиной не более " + maxLength + " символов";
        this.errorMessages = new ArrayList<>();
    }

    @Override
    public List<String> validate(String value) {
        if (value == null || value.length() < minLength) {
            errorMessages.add(minErrorMessage);

            return errorMessages;
        }

        if (value.length() > maxLength) {
            errorMessages.add(maxErrorMessage);

            return errorMessages;
        }

        return Collections.emptyList();
    }
}
