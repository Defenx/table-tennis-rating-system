package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.util.Properties;

public class LiquibaseConfig {

    public final static String CHANGELOG_FILE = "/db/changelog/master.xml";

    @SneakyThrows
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        Properties properties = new Properties();
        properties.load((LiquibaseConfig.class.getClassLoader().getResourceAsStream("/liquibase/liquibase.properties")));

        config.setJdbcUrl(properties.getProperty("url"));
        config.setUsername(properties.getProperty("username"));
        config.setPassword(properties.getProperty("password"));
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config);
    }

}