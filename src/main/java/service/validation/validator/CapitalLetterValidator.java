package service.validation.validator;

import java.util.Collections;
import java.util.List;

public class CapitalLetterValidator implements Validator {

    private final List<String> errorMessages;

    public CapitalLetterValidator() {
        this.errorMessages = List.of(
                "Вводимые данные должны начинаться с заглавной буквы"
        );
    }

    @Override
    public List<String> validate(String value) {

        if (value == null || !Character.isUpperCase(value.charAt(0))) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
