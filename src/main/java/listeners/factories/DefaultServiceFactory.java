package listeners.factories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import services.login.BasicCredentialsExtractor;
import services.login.CredentialsExtractor;
import services.login.UserService;
import services.login.DefaultUserService;
import services.login.auth.JwtTokenService;
import services.login.auth.strategies.AuthenticationStrategy;
import services.login.auth.strategies.JwtAuthenticationStrategy;
import services.login.auth.strategies.StandardAuthenticationStrategy;

/**
 * Реализация фабрики сервисов по умолчанию.
 * Предоставляет методы для создания экземпляров различных сервисов, таких как кодировщик паролей,
 * сервис аутентификации, сервис извлечения учетных данных и сервис JWT.
 */
public class DefaultServiceFactory implements ServiceFactory {

    /**
     * Создает и возвращает экземпляр BCryptPasswordEncoder для кодирования паролей.
     *
     * @return экземпляр BCryptPasswordEncoder
     */
    @Override
    public BCryptPasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Создает и возвращает экземпляр LoginService для выполнения операций входа в систему.
     *
     * @param passwordEncoder экземпляр BCryptPasswordEncoder для кодирования паролей
     * @return экземпляр LoginService
     */
    @Override
    public UserService createLoginService(BCryptPasswordEncoder passwordEncoder) {
        return new DefaultUserService(passwordEncoder);
    }

    /**
     * Создает и возвращает экземпляр AuthenticationStrategy для выполнения аутентификации пользователей.
     * В зависимости от переданного типа стратегии, будет создан соответствующий экземпляр стратегии.
     *
     * @param userService    экземпляр LoginService для выполнения операций входа в систему
     * @param jwtTokenService экземпляр JwtTokenService для работы с JWT токенами
     * @param strategyType    тип стратегии аутентификации ("JWT" или другой)
     * @return экземпляр AuthenticationStrategy
     */
    @Override
    public AuthenticationStrategy createUserAuthenticationService(UserService userService,
                                                                  JwtTokenService jwtTokenService,
                                                                  String strategyType) {
        if ("JWT".equalsIgnoreCase(strategyType)) {
            return new JwtAuthenticationStrategy(userService, jwtTokenService);
        } else {
            return new StandardAuthenticationStrategy(userService);
        }
    }

    /**
     * Создает и возвращает экземпляр JwtTokenService для работы с JWT токенами.
     *
     * @return экземпляр JwtTokenService
     */
    @Override
    public JwtTokenService createJwtTokenService() {
        return new JwtTokenService();
    }

    /**
     * Создает и возвращает экземпляр CredentialsExtractor для извлечения учетных данных из запросов.
     *
     * @return экземпляр CredentialsExtractor
     */
    @Override
    public CredentialsExtractor createCredentialsExtractor() {
        return new BasicCredentialsExtractor();
    }
}