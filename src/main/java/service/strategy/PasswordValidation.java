package service.strategy;

import service.validator.FieldValidator;

import java.util.List;

public class PasswordValidation extends AbstractValidation  {
    public PasswordValidation(String fieldName, List<FieldValidator> validations) {
        super(fieldName, validations);
    }
}
