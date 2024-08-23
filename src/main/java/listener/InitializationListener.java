package listener;

import config.AppConfig;
import config.LiquibaseConfig;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import listener.ObjectCreator.ContextObjectCreator;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

@WebListener
public class InitializationListener implements ServletContextListener {

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        initializeLiquibase(servletContext);
        initializeAppConfig();

        ContextObjectCreator authentication = new ContextObjectCreator();
        authentication.createAuthentication();

        Map<String, Object> contextServices = authentication.getServices();
        contextServices.forEach(servletContext::setAttribute);
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

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent.getServletContext().getAttribute("sessionFactory");
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}