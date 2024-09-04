package listener;

import config.HibernateConfig;
import config.LiquibaseConfig;
import constant.RouteConstants;
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
import org.mapstruct.factory.Mappers;
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
import service.validation.validator.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
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

        var credentialsExtractor = new CredentialsExtractor();
        var userDao = new UserDao(sessionFactory);
        var tournamentDao = new TournamentDao(sessionFactory);
        var userService = initUserService(userDao);
        var tournamentService = initTournamentService(tournamentDao, sessionFactory);
        var tournamentAttributeResolver = new TournamentAttributeResolver(tournamentService);
        var validationService = initValidation(userService);
        var tournamentCreateService = initTournamentCreateService(tournamentDao);
        var tournamentCreateExtractorService = new TournamentCreateExtractorService();

        Map<String, Object> attributes = Map.ofEntries(
                Map.entry(SESSION_FACTORY, sessionFactory),
                Map.entry(CREDENTIALS_EXTRACTOR, credentialsExtractor),
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

    private ValidationService initValidation(UserService userService) {
        var emptinessValidator = new EmptinessValidator();
        var languageValidator = new LanguageValidator();
        var capitalLetterValidator = new CapitalLetterValidator();
        var emailPatternValidator = new EmailPatternValidator();
        var emailRepeatValidator = new EmailRepeatValidator(userService);
        var minLengthValidator = new MinLengthValidator(5);
        var maxLengthValidator = new MaxLengthValidator(16);
        var specialCharacterValidator = new SpecialCharacterValidator(1);
        var spaceSymbolsValidator = new SpaceSymbolsValidator();

        Map<String, Map<String, List<Validator>>> routesToValidationMap =
                Map.of(
                        RouteConstants.REGISTRATION, Map.of(
                                "firstname", List.of(
                                        emptinessValidator,
                                        languageValidator,
                                        capitalLetterValidator
                                ),

                                "surname", List.of(
                                        emptinessValidator,
                                        languageValidator,
                                        capitalLetterValidator
                                ),

                                "email", List.of(
                                        emptinessValidator,
                                        emailPatternValidator,
                                        emailRepeatValidator
                                ),

                                "password", List.of(
                                        emptinessValidator,
                                        minLengthValidator,
                                        maxLengthValidator,
                                        specialCharacterValidator,
                                        spaceSymbolsValidator
                                )
                        ),
                        RouteConstants.LOGIN, Map.of(
                                "email", List.of(
                                        emptinessValidator
                                ),
                                "password", List.of(
                                        emptinessValidator
                                )

                        ),
                        RouteConstants.ADMIN_TOURNAMENT_CREATE, Map.of(
                                "training_sets", List.of(
                                        emptinessValidator
                                ),
                                "playoff_sets", List.of(
                                        emptinessValidator
                                )

                        )
                );

        var validationFactory = new ValidationRegistry(routesToValidationMap);
        return new ValidationService(validationFactory);
    }

    private UserService initUserService(UserDao userDao){
        var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return new UserService(userDao, bCryptPasswordEncoder);
    }

    private TournamentService initTournamentService(TournamentDao tournamentDao, SessionFactory sessionFactory){
        var tournamentParticipantDao = new TournamentParticipantDao(sessionFactory);
        return new TournamentService(tournamentDao, tournamentParticipantDao);
    }

    private TournamentCreateService initTournamentCreateService(TournamentDao tournamentDao) {
        var tournamentMapper = Mappers.getMapper(TournamentMapper.class);
        return new TournamentCreateService(tournamentDao, tournamentMapper);
    }
}