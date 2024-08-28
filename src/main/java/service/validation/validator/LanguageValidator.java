package service.validation.validator;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageValidator implements Validator {

    private final List<String> errorMessages;

    private final String regularExpression;

    public LanguageValidator() {
        this.regularExpression = "[^а-яёА-ЯЁ]";
        this.errorMessages = List.of(
                "Разрешены только символы русского алфавита"
        );
    }

    @Override
    public List<String> validate(String value) {

        if (value == null) {
            return errorMessages;
        }

        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
