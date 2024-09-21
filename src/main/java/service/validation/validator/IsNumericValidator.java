package service.validation.validator;

import enums.Error;
import service.utils.NumberTypeChecker;
import service.validation.chain.BaseValidator;

public class IsNumericValidator extends BaseValidator {

    public IsNumericValidator(int priority) {
        super(priority);
    }

    @Override
    public String validate(String value) {

        if (!NumberTypeChecker.isNumber(value)) {
            return Error.IS_NUMERIC_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return "";
    }
}
