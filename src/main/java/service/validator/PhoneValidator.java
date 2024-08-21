package service.validator;

import java.util.Collections;
import java.util.List;

public class PhoneValidator implements FieldValidator {
    private final String phonePattern;
    private final List<String> errorMessages;

    public PhoneValidator() {
        this.phonePattern = "^\\+?[0-9]{10,13}$";
        this.errorMessages = List.of(
                "Phone doesn't match the pattern",
                "Example of a valid phone: +71234567890"
        );
    }

    @Override
    public List<String> validate(String value) {
        if (value == null || !value.matches(phonePattern)) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
