package service.validation.validator;

import java.util.List;

public interface Validator {
    List<String> validate(String value);
}
