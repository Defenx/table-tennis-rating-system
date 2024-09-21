package service.validation.validator;

import enums.Error;
import service.validation.chain.BaseValidator;

public class SpecialCharacterValidator extends BaseValidator {

    private final int requiredCount;
    private final String specialCharacters;

    public SpecialCharacterValidator(int requiredCount, int priority) {
        super(priority);
        this.requiredCount = requiredCount;
        this.specialCharacters = "!@#$%^&*()";
    }

    @Override
    public String validate(String value) {

        var specialCharacterCount = value.chars()
                .filter(ch -> specialCharacters.indexOf(ch) >= 0)
                .count();

        if (specialCharacterCount < requiredCount) {
            return Error.SPECIAL_CHARACTERS_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return "";
    }
}
