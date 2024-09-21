package service.validation.validator;

import enums.Error;
import service.validation.chain.BaseValidator;

public class NegativeValueValidator extends BaseValidator {

    public NegativeValueValidator(int priority) {
        super(priority);
    }

    @Override
    public String validate(String value) {
        int count = Integer.parseInt(value);

        if (count < 0) {
            return Error.NEGATIVE_VALUE_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return "";
    }

}
