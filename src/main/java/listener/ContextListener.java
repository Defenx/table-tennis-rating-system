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
import org.hibernate.SessionFactory;import service.validation.ValidationRegistry;
import service.validation.ValidationService;

import javax.sql.DataSource;
import java.sql.Connection;

@WebListener
public class ContextListener implements ServletContextListener {
    public static final String VALIDATION_SERVICE = "validationService";

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();

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

        SessionFactory sessionFactory = new HibernateConfig().buildSessionFactory();

        UserDao userDao = new UserDao(sessionFactory);

        context.setAttribute("sessionFactory",sessionFactory);


        var validationFactory = new ValidationRegistry(userDao);
        var validationService = new ValidationService(validationFactory);
        context.setAttribute(VALIDATION_SERVICE, validationService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent
                .getServletContext()
                .getAttribute("sessionFactory");

        sessionFactory.close();
    }
}
