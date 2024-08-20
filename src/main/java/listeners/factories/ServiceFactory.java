package listeners.factories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import services.login.CredentialsExtractor;
import services.login.UserService;
import services.login.auth.JwtTokenService;
import services.login.auth.strategies.AuthenticationStrategy;

/**
 * Фабрика для создания сервисов, используемых в приложении.
 * Предоставляет методы для создания экземпляров различных сервисов, таких как кодировщик паролей,
 * сервис аутентификации, сервис извлечения учетных данных и сервис JWT.
 */
public interface ServiceFactory {

    /**
     * Создает и возвращает экземпляр BCryptPasswordEncoder для кодирования паролей.
     *
     * @return экземпляр BCryptPasswordEncoder
     */
    BCryptPasswordEncoder createPasswordEncoder();

    /**
     * Создает и возвращает экземпляр LoginService для выполнения операций входа в систему.
     *
     * @param passwordEncoder экземпляр BCryptPasswordEncoder для кодирования паролей
     * @return экземпляр LoginService
     */
    UserService createLoginService(BCryptPasswordEncoder passwordEncoder);

    /**
     * Создает и возвращает экземпляр AuthenticationStrategy для выполнения аутентификации пользователей.
     * В зависимости от переданного типа стратегии, будет создан соответствующий экземпляр стратегии.
     *
     * @param userService    экземпляр LoginService для выполнения операций входа в систему
     * @param jwtTokenService экземпляр JwtTokenService для работы с JWT токенами
     * @param strategyType    тип стратегии аутентификации ("JWT" или другой)
     * @return экземпляр AuthenticationStrategy
     */
    AuthenticationStrategy createUserAuthenticationService(UserService userService, JwtTokenService jwtTokenService, String strategyType);

    /**
     * Создает и возвращает экземпляр CredentialsExtractor для извлечения учетных данных из запросов.
     *
     * @return экземпляр CredentialsExtractor
     */
    CredentialsExtractor createCredentialsExtractor();

    /**
     * Создает и возвращает экземпляр JwtTokenService для работы с JWT токенами.
     *
     * @return экземпляр JwtTokenService
     */
    JwtTokenService createJwtTokenService();
}