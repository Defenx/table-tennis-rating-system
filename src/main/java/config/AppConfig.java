package config;

import config.strategies.AttributesConfig;
import config.strategies.ConfigStrategy;
import config.strategies.ErrorsConfig;
import config.strategies.PathsConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

/**
 * Класс для загрузки и управления конфигурацией приложения.
 */
public class AppConfig {
    private static final String CONFIG_FILE = "application.yaml";
    private static Map<String, Object> config;
    private static ConfigStrategy paths;
    private static ConfigStrategy errors;
    private static ConfigStrategy attributes;

    /**
     * Загружает конфигурацию из файла.
     *
     * @throws RuntimeException если произошла ошибка при загрузке файла конфигурации
     */
    public static void loadConfig() throws RuntimeException {
        Yaml yaml = new Yaml();
        try (InputStream in = AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new FileNotFoundException("Файл конфигурации не найден: " + CONFIG_FILE);
            }
            config = yaml.load(in);
            initializeStrategies();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось загрузить файл конфигурации", e);
        }
    }

    /**
     * Инициализирует стратегии конфигурации на основе данных из конфигурационного файла.
     *
     * @throws IllegalStateException если секция 'app' отсутствует или имеет неверный формат
     */
    private static void initializeStrategies() {
        Object appConfig = config.get("app");

        if (appConfig instanceof Map) {
            Map<String, Object> appConfigMap = (Map<String, Object>) appConfig;
            paths = new PathsConfig(appConfigMap.get("paths"));
            errors = new ErrorsConfig(appConfigMap.get("errors"));
            attributes = new AttributesConfig(appConfigMap.get("attributes"));
        } else {
            throw new IllegalStateException("Секция конфигурации 'app' отсутствует или не является картой.");
        }
    }

    /**
     * Возвращает путь для входа в систему.
     *
     * @return путь для входа
     */
    public static String getLoginPath() {
        return paths.getValue("login");
    }

    /**
     * Возвращает путь для домашней страницы.
     *
     * @return путь для домашней страницы
     */
    public static String getHomePath() {
        return paths.getValue("home");
    }

    /**
     * Возвращает сообщение об ошибке, если сервис не инициализирован.
     *
     * @return сообщение об ошибке
     */
    public static String getServiceNotInitializedMessage() {
        return errors.getValue("serviceNotInitialized");
    }

    /**
     * Возвращает сообщение об ошибке при неудачной аутентификации.
     *
     * @return сообщение об ошибке аутентификации
     */
    public static String getAuthenticationFailedMessage() {
        return errors.getValue("authenticationFailed");
    }

    /**
     * Возвращает атрибут сервиса аутентификации пользователя.
     *
     * @return атрибут сервиса аутентификации
     */
    public static String getUserAuthServiceAttribute() {
        return attributes.getValue("loginService");
    }

    /**
     * Возвращает атрибут идентификатора пользователя.
     *
     * @return атрибут идентификатора пользователя
     */
    public static String getUserIdAttribute() {
        return attributes.getValue("userID");
    }

    /**
     * Возвращает атрибут имени пользователя.
     *
     * @return атрибут имени
     */
    public static String getFirstNameAttribute() {
        return attributes.getValue("firstName");
    }

    /**
     * Возвращает атрибут фамилии пользователя.
     *
     * @return атрибут фамилии
     */
    public static String getLastNameAttribute() {
        return attributes.getValue("lastName");
    }

    /**
     * Возвращает атрибут рейтинга пользователя.
     *
     * @return атрибут рейтинга
     */
    public static String getRatingAttribute() {
        return attributes.getValue("rating");
    }

    /**
     * Возвращает атрибут роли пользователя.
     *
     * @return атрибут роли
     */
    public static String getUserRoleAttribute() {
        return attributes.getValue("userRole");
    }

    /**
     * Возвращает атрибут для извлечения учетных данных.
     *
     * @return атрибут для извлечения учетных данных
     */
    public static String getCredentialsExtractorAttribute() {
        return "credentialsExtractor";
    }

    /**
     * Возвращает сообщение об ошибке.
     *
     * @return сообщение об ошибке
     */
    public static String getErrorMessage() {
        return errors.getValue("errorMessage");
    }

    /**
     * Возвращает сообщение о недействительных учётных данных.
     *
     * @return сообщение о недействительных учётных данных
     */
    public static String getInvalidCredentials() {
        return errors.getValue("invalidCredentials");
    }
}
