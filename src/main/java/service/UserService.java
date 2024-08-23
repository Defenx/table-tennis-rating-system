package service;

import dao.UserDao;
import dto.RegistrationFormDto;
import entity.User;
import enums.Role;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@AllArgsConstructor
public class UserService implements Service{
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDao userDao;

    public void addUser(RegistrationFormDto userData) throws HibernateException {
//        throw new HibernateException("OOPSy.");
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
