package service.validation.validator;

import java.util.Collections;
import java.util.List;

public class SpaceSymbolsValidator implements Validator {

    private final List<String> errorMessages;

    public SpaceSymbolsValidator() {
        this.errorMessages = List.of(
                "Поле не должно содержать пробельных символов"
        );
    }

    @Override
    public List<String> validate(String value) {
        if (value == null || value.contains(" ")) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
