package service.validator;

import java.util.Collections;
import java.util.List;

public class SpecialCharacterValidator implements FieldValidator {
    private final int requiredCount;
    private final String specialCharacters;
    private final List<String> errorMessages;

    public SpecialCharacterValidator(int requiredCount) {
        this.requiredCount = requiredCount;
        this.specialCharacters = "!@#$%^&*()";
        this.errorMessages = List.of(
                "Field must contain at least %d special character(s) (%s)".formatted(requiredCount, specialCharacters)
        );
    }

    @Override
    public List<String> validate(String value) {
        if (value == null) {
            return errorMessages;
        }

        var specialCharacterCount = value.chars()
                .filter(ch -> specialCharacters.indexOf(ch) >= 0)
                .count();

        if (specialCharacterCount < requiredCount) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
