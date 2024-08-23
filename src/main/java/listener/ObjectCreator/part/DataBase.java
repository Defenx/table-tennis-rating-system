package listener.ObjectCreator.part;

import config.HibernateConfig;
import config.LiquibaseConfig;
import dao.UserDao;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import listener.ObjectCreator.AppPart;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

public class DataBase implements AppPart {
    @Override
    @SneakyThrows
    public Map<String, Object> addPart(Map<String, Object> services) {
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

        return Map.of("sessionFactory", sessionFactory, "userDao", userDao);
    }
}
