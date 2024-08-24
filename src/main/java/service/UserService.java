package service;

import dao.UserDao;
import dto.UserDto;
import entity.User;
import enums.Role;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;


    public Optional<User> getExistedUser(String email, String password) {
        return userDao.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }
}
