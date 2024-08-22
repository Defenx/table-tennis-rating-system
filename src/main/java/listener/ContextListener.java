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

import javax.sql.DataSource;
import java.sql.Connection;

@WebListener
public class ContextListener implements ServletContextListener {
    
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
        context.setAttribute("userDao",userDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent
                .getServletContext()
                .getAttribute("sessionFactory");

            sessionFactory.close();
    }
}
