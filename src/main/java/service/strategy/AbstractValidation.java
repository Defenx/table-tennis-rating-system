package service.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import service.validator.FieldValidator;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class AbstractValidation {
    private final String fieldName;
    private final List<FieldValidator> validations;

    public Map<String, List<String>> validate(String value) {
        var errors = validations.stream()
                .flatMap(validation -> validation.validate(value).stream())
                .toList();

        return Map.of(getFieldName(), errors);
    }
}
