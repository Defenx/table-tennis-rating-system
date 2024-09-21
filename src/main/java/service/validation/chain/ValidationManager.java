package service.validation.chain;

import lombok.Setter;
import service.validation.validator.Validator;

import java.util.Comparator;
import java.util.List;

@Setter
public class ValidationManager {

    private List<BaseValidator> validators;

    public String runValidation(String value) {
        Validator executeValidator = bind();

        return executeValidator.validate(value);
    }

    private Validator bind() {
        validators = sortingByPriority();

        for (int i = 1; i <= validators.size() - 1; i++) {
            validators.get(i - 1).setNext(validators.get(i));
        }

        return validators.get(0);
    }

    private List<BaseValidator> sortingByPriority() {
        return validators
                .stream()
                .sorted(Comparator.comparingInt(BaseValidator::getPriority))
                .toList();
    }
}
