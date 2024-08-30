package listener;

import config.HibernateConfig;
import config.LiquibaseConfig;
import dao.TournamentDao;
import dao.TournamentParticipantDao;
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
import service.TournamentService;
import service.UserService;
import service.home.TournamentAttributeResolver;
import service.login.CredentialsExtractor;
import service.tournament.create.TournamentCreateExtractorService;
import service.tournament.create.TournamentCreateService;
import service.tournament.create.TournamentMapper;
import service.validation.ValidationRegistry;
import service.validation.ValidationService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

@WebListener
public class ContextListener implements ServletContextListener {
    public static final String VALIDATION_SERVICE = "validationService";
    public static final String CREDENTIALS_EXTRACTOR = "credentialsExtractor";
    public static final String USER_SERVICE = "userService";
    public static final String SESSION_FACTORY = "sessionFactory";
    public static final String USER_DAO = "userDao";
    public static final String TOURNAMENT_DAO = "tournamentDao";
    public static final String TOURNAMENT_CREATE_SERVICE = "tournamentCreateService";
    public static final String TOURNAMENT_CREATE_EXTRACTOR_SERVICE = "tournamentCreateExtractorService";
    public static final String TOURNAMENT_SERVICE = "tournamentService";
    public static final String TOURNAMENT_ATTRIBUTE_RESOLVER = "tournamentAttributeResolver";

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        initializeLiquibase();
        SessionFactory sessionFactory = new HibernateConfig().buildSessionFactory();

        var userDao = new UserDao(sessionFactory);
        var tournamentDao = new TournamentDao(sessionFactory);
        var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var userService = new UserService(userDao, bCryptPasswordEncoder);
        var credentialsExtractor = new CredentialsExtractor();
        var tournamentParticipantDao = new TournamentParticipantDao(sessionFactory);
        var tournamentService = new TournamentService(tournamentDao, tournamentParticipantDao);
        var tournamentAttributeResolver = new TournamentAttributeResolver(tournamentService);
        var validationFactory = new ValidationRegistry(userDao);
        var validationService = new ValidationService(validationFactory);
        var tournamentMapper = TournamentMapper.INSTANCE;
        var tournamentCreateService = new TournamentCreateService(tournamentDao, tournamentMapper);
        var tournamentCreateExtractorService = new TournamentCreateExtractorService();

        Map<String, Object> attributes = Map.ofEntries(
                Map.entry(CREDENTIALS_EXTRACTOR, credentialsExtractor),
                Map.entry(SESSION_FACTORY, sessionFactory),
                Map.entry(USER_DAO, userDao),
                Map.entry(TOURNAMENT_DAO, tournamentDao),
                Map.entry(USER_SERVICE, userService),
                Map.entry(TOURNAMENT_SERVICE, tournamentService),
                Map.entry(TOURNAMENT_ATTRIBUTE_RESOLVER, tournamentAttributeResolver),
                Map.entry(VALIDATION_SERVICE, validationService),
                Map.entry(TOURNAMENT_CREATE_SERVICE, tournamentCreateService),
                Map.entry(TOURNAMENT_CREATE_EXTRACTOR_SERVICE, tournamentCreateExtractorService)
        );

        attributes.forEach(servletContext::setAttribute);
    }

    @SneakyThrows
    private void initializeLiquibase() {
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
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent
                .getServletContext()
                .getAttribute("sessionFactory");

        sessionFactory.close();
    }
}
