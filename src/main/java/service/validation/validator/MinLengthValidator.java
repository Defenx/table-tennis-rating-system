package service.validation.validator;

import enums.Error;
import service.validation.chain.BaseValidator;

public class MinLengthValidator extends BaseValidator {

    private final int minLength;

    public MinLengthValidator(int minLength, int priority) {
        super(priority);
        this.minLength = minLength;
    }

    @Override
    public String validate(String value) {

        if (value.length() < minLength) {
            return Error.MIN_LENGTH_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return "";
    }
}
