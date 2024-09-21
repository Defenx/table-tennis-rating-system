package service.validation.validator;

import enums.Error;
import service.validation.chain.BaseValidator;

public class EmailPatternValidator extends BaseValidator {
    private final String emailPattern;

    public EmailPatternValidator(int priority) {
        super(priority);
        this.emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
    }

    @Override
    public String validate(String value) {

        if (!value.matches(emailPattern)) {
            return Error.EMAIL_PATTERN_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return "";
    }
}
