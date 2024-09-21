package service.validation.validator;

import enums.Error;
import service.user.UserService;
import service.validation.chain.BaseValidator;

public class EmailRepeatValidator extends BaseValidator {

    private final UserService userService;

    public EmailRepeatValidator(UserService userService, int priority) {
        super(priority);
        this.userService = userService;
    }

    @Override
    public String validate(String email) {

        if (userService.findByEmail(email).isPresent()) {
            return Error.EMAIL_REPEAT_ERROR.getMessage();
        }

        if (getNext() != null) {
            return getNext().validate(email);
        }

        return "";
    }
}
