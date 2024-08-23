package listener.ObjectCreator.part;

import config.HibernateConfig;
import dao.UserDao;
import listener.ObjectCreator.AppPart;
import org.hibernate.SessionFactory;

import java.util.Map;

public class DataBase implements AppPart {
    @Override
    public Map<String, Object> getAppParts(Map<String, Object> services) {
        SessionFactory sessionFactory = new HibernateConfig().buildSessionFactory();
        UserDao userDao = new UserDao(sessionFactory);

        return Map.of("sessionFactory", sessionFactory, "userDao", userDao);
    }
}
