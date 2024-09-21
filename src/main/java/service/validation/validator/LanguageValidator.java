package service.validation.validator;

import enums.Error;
import service.validation.chain.BaseValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageValidator extends BaseValidator {

    private final String regularExpression;

    public LanguageValidator(int priority) {
        super(priority);
        this.regularExpression = "[^а-яёА-ЯЁ]";
    }

    @Override
    public String validate(String value) {

        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            return Error.LANGUAGE_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return "";
    }
}
