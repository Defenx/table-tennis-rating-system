package service.validation.validator;

import service.UserService;

import java.util.Collections;
import java.util.List;

public class EmailRepeatValidator implements Validator{

    private final UserService userService;
    private final List<String> errorMessages;

    public EmailRepeatValidator(UserService userService) {
        this.userService = userService;
        this.errorMessages = List.of(
                "Этот адрес электронной почты уже зарегистрирован"
        );
    }

    @Override
    public List<String> validate(String email) {
        if (email == null || userService.findByEmail(email).isPresent()) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
