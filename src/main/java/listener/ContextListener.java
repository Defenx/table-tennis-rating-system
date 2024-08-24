package listener;

import config.HibernateConfig;
import config.LiquibaseConfig;
import dao.UserDao;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.UserService;
import service.login.BasicCredentialsExtractorService;
import service.login.UserAuthenticationService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebListener
public class ContextListener implements ServletContextListener {
    public static final String VALIDATION_SERVICE = "validationService";
    public static final String CREDENTIALS_EXTRACTOR = "credentialsExtractor";
    public static final String USER_AUTH_SERVICE = "userAuthService";
    public static final String SESSION_FACTORY = "sessionFactory";
    public static final String USER_DAO = "userDao";

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        initializeLiquibase(servletContext);
        SessionFactory sessionFactory = new HibernateConfig().buildSessionFactory();

        var userDao = new UserDao(sessionFactory);
        var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var userService = new UserService(userDao, bCryptPasswordEncoder);
        var userAuthenticationService = new UserAuthenticationService(userService);
        var credentialsExtractor = new BasicCredentialsExtractorService();

        Map<String, Object> attributes = Stream.of(
                new AbstractMap.SimpleEntry<>(CREDENTIALS_EXTRACTOR, credentialsExtractor),
                new AbstractMap.SimpleEntry<>(USER_AUTH_SERVICE, userAuthenticationService),
                new AbstractMap.SimpleEntry<>(SESSION_FACTORY, sessionFactory),
                new AbstractMap.SimpleEntry<>(USER_DAO, userDao)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        attributes.forEach(servletContext::setAttribute);
    }

    @SneakyThrows
    private void initializeLiquibase(ServletContext servletContext) {
        LiquibaseConfig liquibaseConfig = new LiquibaseConfig();
        DataSource dataSource = liquibaseConfig.getDataSource();
        try (
                Connection connection = dataSource.getConnection();
                JdbcConnection jdbcConnection = new JdbcConnection(connection);
                Liquibase liquibase = new Liquibase(
                        LiquibaseConfig.CHANGELOG_FILE,
                        new ClassLoaderResourceAccessor(),
                        jdbcConnection)
        ) {
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent.getServletContext().getAttribute(SESSION_FACTORY);
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}