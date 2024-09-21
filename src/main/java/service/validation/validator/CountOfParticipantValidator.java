package service.validation.validator;

import enums.Error;
import service.validation.chain.BaseValidator;

public class CountOfParticipantValidator extends BaseValidator {

    public CountOfParticipantValidator(int priority) {
        super(priority);
    }

    @Override
    public String validate(String value) {
        int count = Integer.parseInt(value);

        if (count % 2 != 0) {
            return Error.COUNT_OF_PARTICIPANT_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return "";
    }

}
