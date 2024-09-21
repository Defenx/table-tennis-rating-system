package service.validation.chain;

import lombok.Getter;
import lombok.Setter;
import service.validation.validator.Validator;

@Getter
@Setter
public abstract class BaseValidator implements Validator {

    protected BaseValidator(int priority) {
        this.priority = priority;
    }

    private Validator next;

    private int priority;

}
