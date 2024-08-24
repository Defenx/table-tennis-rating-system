package service;

import dao.UserDao;

import dto.RegistrationFormDto;
import entity.User;
import enums.Role;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RequiredArgsConstructor
public class UserService implements Service {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDao userDao;

    public void addUser(RegistrationFormDto userData) throws HibernateException {
        userDao.create(User.builder()
                .email(userData.getEmail())
                .password(passwordEncoder.encode(userData.getPassword()))
                .firstname(userData.getFirstname())
                .surname(userData.getSurname())
                .role(Role.USER)
                .rating(1000)
                .build());
    }
}
