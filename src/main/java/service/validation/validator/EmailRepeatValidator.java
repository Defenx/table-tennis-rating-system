package service.validation.validator;

import dao.UserDao;

import java.util.Collections;
import java.util.List;

public class EmailRepeatValidator implements Validator{

    private final UserDao userDao;
    private final List<String> errorMessages;

    public EmailRepeatValidator(UserDao userDao) {
        this.userDao = userDao;
        this.errorMessages = List.of(
                "This email is already registered"
        );
    }

    @Override
    public List<String> validate(String email) {
        if (email == null || userDao.findByEmail(email).isPresent()) {
            return errorMessages;
        }

        return Collections.emptyList();
    }
}
