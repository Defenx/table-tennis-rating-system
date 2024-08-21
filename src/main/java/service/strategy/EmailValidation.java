package service.strategy;

import service.validator.FieldValidator;

import java.util.List;

public class EmailValidation extends AbstractValidation {
    public EmailValidation(String fieldName, List<FieldValidator> validations) {
        super(fieldName, validations);
    }

    // TODO: override the default validate method, add check if email already exist in the db
}
