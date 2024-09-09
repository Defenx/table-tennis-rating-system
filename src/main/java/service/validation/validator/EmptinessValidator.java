package service.validation.validator;

import java.util.Collections;
import java.util.List;

public class EmptinessValidator implements Validator {
    private final List<String> errorMessages;

    public EmptinessValidator() {
        this.errorMessages = List.of(
                "Поле не может быть пустым"
        );
    }

    @Override
    public List<String> validate(String value) {
        if (value == null || value.isEmpty()) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
