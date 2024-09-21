package service.validation.validator;

import enums.Error;
import service.validation.chain.BaseValidator;

public class MaxLengthValidator extends BaseValidator {

    private final int maxLength;

    public MaxLengthValidator(int maxLength, int priority) {
        super(priority);
        this.maxLength = maxLength;
    }

    @Override
    public String validate(String value) {

        if (value.length() > maxLength) {
            return Error.MAX_LENGTH_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return "";
    }
}
