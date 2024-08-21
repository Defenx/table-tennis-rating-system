package service.validator;

import java.util.Collections;
import java.util.List;

public class EmailValidator implements FieldValidator {
    private final String emailPattern;
    private final List<String> errorMessages;

    public EmailValidator() {
        this.emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        this.errorMessages = List.of(
                "Email doesn't match the pattern",
                "Example of a valid email: mail@example.com"
        );
    }

    @Override
    public List<String> validate(String value) {
        if (value == null || !value.matches(emailPattern)) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
