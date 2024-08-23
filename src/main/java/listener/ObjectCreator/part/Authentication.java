package listener.ObjectCreator.part;

import dao.UserDao;
import listener.ObjectCreator.AppPart;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.UserService;

import java.util.Map;

public class Authentication implements AppPart {

    @Override
    public Map<String, Object> getAppParts(Map<String, Object> services) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserService userService = new UserService(bCryptPasswordEncoder, (UserDao) services.get("userDao"));

        return Map.of("userService", userService);
    }
}
