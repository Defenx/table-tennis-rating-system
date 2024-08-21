package service.validator;

import java.util.List;

public interface FieldValidator {
    List<String> validate(String value);
}
