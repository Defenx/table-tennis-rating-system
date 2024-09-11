package service;

import dao.UserDao;
import dto.RegistrationFormDto;
import entity.User;
import enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<User> getExistedUser(String email, String password) {
        return userDao.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }
    public List<User> getOrderByRatingAsc(){
        return userDao.findAllOrderByRatingAsc();
    }

    public void addUser(RegistrationFormDto userData) {
        userDao.create(User.builder()
                .email(userData.email())
                .password(passwordEncoder.encode(userData.password()))
                .firstname(userData.firstname())
                .surname(userData.surname())
                .role(Role.USER)
                .rating(BigDecimal.valueOf(100))
                .build());
    }
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
