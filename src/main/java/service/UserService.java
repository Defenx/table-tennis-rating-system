package service;

import dao.UserDao;
import dto.RegistrationFormDto;
import entity.User;
import enums.Role;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;


    public Optional<User> getExistedUser(String email, String password) {
        return userDao.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    public void addUser(RegistrationFormDto userData) throws HibernateException {
        userDao.create(User.builder()
                .email(userData.email())
                .password(passwordEncoder.encode(userData.password()))
                .firstname(userData.firstname())
                .surname(userData.surname())
                .role(Role.USER)
                .rating(1000)
                .build());
    }
}
