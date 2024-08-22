package listener;

import config.AppConfig;
import config.HibernateConfig;
import config.LiquibaseConfig;
import dao.UserDao;
import enums.ConfigSection;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import listener.factory.ServiceFactory;
import listener.factory.ServiceRegistrar;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

@WebListener
public class InitializationListener implements ServletContextListener {

    private ServiceFactory serviceFactory;

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        initializeLiquibase(servletContext);
        initializeAppConfig();
        SessionFactory sessionFactory = initializeHibernate();

        serviceFactory = new ServiceFactory();
        ServiceRegistrar.registerServices(serviceFactory);

        UserDao userDao = new UserDao(sessionFactory);

        saveAttributesToContext(servletContext, sessionFactory, userDao);
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

    private void initializeAppConfig() {
        try {
            AppConfig.loadConfig();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось инициализировать приложение", e);
        }
    }

    private SessionFactory initializeHibernate() {
        return new HibernateConfig().buildSessionFactory();
    }

    private void saveAttributesToContext(ServletContext servletContext, SessionFactory sessionFactory, UserDao userDao) {
        servletContext.setAttribute(AppConfig.getConfigValue(ConfigSection.ATTRIBUTES, "credentialsExtractor"),
                                    serviceFactory.getService("credentialsExtractor"));
        servletContext.setAttribute(AppConfig.getConfigValue(ConfigSection.ATTRIBUTES, "userAuthService"),
                                    serviceFactory.getService("authenticationStrategy"));
        servletContext.setAttribute(AppConfig.getConfigValue(ConfigSection.ATTRIBUTES, "jwtTokenService"),
                                    serviceFactory.getService("jwtTokenService"));
        servletContext.setAttribute("sessionFactory", sessionFactory);
        servletContext.setAttribute("userDao", userDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent.getServletContext().getAttribute("sessionFactory");
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}