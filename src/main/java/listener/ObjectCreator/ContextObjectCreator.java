package listener.ObjectCreator;

import config.HibernateConfig;
import dao.UserDao;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ContextObjectCreator {
    private final Map<String, Object> services;
    private final SessionFactory sessionFactory;
    private final UserDao userDao;

    {
        services = new HashMap<>();
        sessionFactory = new HibernateConfig().buildSessionFactory();
        userDao = new UserDao(sessionFactory);
        services.put("sessionFactory", sessionFactory);
        services.put("userDao", userDao);
    }

    public void createAuthentication() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserService userService = new UserService(bCryptPasswordEncoder, userDao);

        services.put("userService", userService);
    }
}