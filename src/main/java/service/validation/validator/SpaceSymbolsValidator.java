package service.validation.validator;

import enums.Error;
import service.validation.chain.BaseValidator;

public class SpaceSymbolsValidator extends BaseValidator {

    public SpaceSymbolsValidator(int priority) {
        super(priority);
    }

    @Override
    public String validate(String value) {

        if (value.contains(" ")) {
            return Error.SPACE_SYMBOLS_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return "";
    }
}
