package service.validation.validator;

import service.utils.NumberTypeChecker;

import java.util.Collections;
import java.util.List;

public class CountOfParticipantValidator implements Validator {

    private final List<String> errorMessages;

    public CountOfParticipantValidator() {
        this.errorMessages = List.of(
                "Число участников должно быть четным"
        );
    }

    @Override
    public List<String> validate(String value) {
        if (value == null || !NumberTypeChecker.isNumber(value)) {
            return errorMessages;
        }

        int count = Integer.parseInt(value);

        if (count % 2 != 0) {
            return errorMessages;
        }

        return Collections.emptyList();
    }

}
