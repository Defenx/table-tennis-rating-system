package service;

import dao.UserDao;
import dto.RegistrationDto;
import dto.UserDto;
import entity.User;
import enums.Role;
import jakarta.servlet.ServletContext;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class UserService implements Service{
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDao userDao;

    private List<UserDto> mockedFindAllUsers() {
        return List.of(
                UserDto.builder().id(UUID.randomUUID()).build(),
                UserDto.builder().id(UUID.randomUUID()).build()
        );
    }

    private List<UserDto> mockedFindUsersByEmail(String email) {
        return mockedFindAllUsers().stream().filter(user -> user.lastName().equals(email)).toList();
    }


    public Optional<UserDto> getExistedUser(String email, String password) {
        return mockedFindUsersByEmail(email).stream()
                .findFirst();
    }

    public void addUser(RegistrationDto userData) throws HibernateException {
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
