package service.validation.validator;

import enums.Error;
import service.validation.chain.BaseValidator;

public class EmptinessValidator extends BaseValidator {

    public EmptinessValidator(int priority) {
        super(priority);
    }

    @Override
    public String validate(String value) {

        if (value == null || value.isEmpty()) {
            return Error.EMPTINESS_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return "";
    }
}
