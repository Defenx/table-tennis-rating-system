package service.validation.validator;

import java.util.Collections;
import java.util.List;

public class SpecialCharacterValidator implements Validator {
    private final int requiredCount;
    private final String specialCharacters;
    private final List<String> errorMessages;

    public SpecialCharacterValidator(int requiredCount) {
        this.requiredCount = requiredCount;
        this.specialCharacters = "!@#$%^&*()";
        this.errorMessages = List.of(
                "Поле должно содержать как минимум %d %s (%s)".formatted(
                        requiredCount,
                        (requiredCount > 1 ? "специальных символов" : "специальный символ"),
                        specialCharacters)
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
