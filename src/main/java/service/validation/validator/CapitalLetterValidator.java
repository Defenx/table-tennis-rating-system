package service.validation.validator;

import enums.Error;
import service.validation.chain.BaseValidator;

public class CapitalLetterValidator extends BaseValidator {

    public CapitalLetterValidator(int priority) {
        super(priority);
    }

    @Override
    public String validate(String value) {

        if (value.isBlank() || !Character.isUpperCase(value.charAt(0))) {
            return Error.CAPITAL_LETTER_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return "";
    }
}
