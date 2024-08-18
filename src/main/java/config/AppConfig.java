package config;

import config.strategies.AttributesConfig;
import config.strategies.ConfigStrategy;
import config.strategies.ErrorsConfig;
import config.strategies.PathsConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class AppConfig {
    private static final String CONFIG_FILE = "application.yaml";
    private static Map<String, Object> config;
    private static ConfigStrategy paths;
    private static ConfigStrategy errors;
    private static ConfigStrategy attributes;

    public static void loadConfig() throws RuntimeException {
        Yaml yaml = new Yaml();
        try (InputStream in = AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new FileNotFoundException("Configuration file not found: " + CONFIG_FILE);
            }
            config = yaml.load(in);
            initializeStrategies();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    private static void initializeStrategies() {
        Object appConfig = config.get("app");

        if (appConfig instanceof Map) {
            Map<String, Object> appConfigMap = (Map<String, Object>) appConfig;
            paths = new PathsConfig(appConfigMap.get("paths"));
            errors = new ErrorsConfig(appConfigMap.get("errors"));
            attributes = new AttributesConfig(appConfigMap.get("attributes"));
        }
        else {
            throw new IllegalStateException("Configuration section 'app' is missing or not a Map.");
        }
    }


    public static String getLoginPath() {
        return paths.getValue("login");
    }

    public static String getHomePath() {
        return paths.getValue("home");
    }


    public static String getServiceNotInitializedMessage() {
        return errors.getValue("serviceNotInitialized");
    }

    public static String getAuthenticationFailedMessage() {
        return errors.getValue("authenticationFailed");
    }

    public static String getUserAuthServiceAttribute() {
        return attributes.getValue("loginService");
    }

    public static String getUserIdAttribute() {
        return attributes.getValue("userID");
    }

    public static String getFirstNameAttribute() {
        return attributes.getValue("firstName");
    }

    public static String getLastNameAttribute() {
        return attributes.getValue("lastName");
    }

    public static String getRatingAttribute() {
        return attributes.getValue("rating");
    }

    public static String getUserRoleAttribute() {
        return attributes.getValue("userRole");
    }

    public static String getCredentialsExtractorAttribute() {
        return "credentialsExtractor";
    }

    public static String getErrorMessage() {
        return errors.getValue("errorMessage");
    }

    public static String getInvalidCredentials() {
        return errors.getValue("invalidCredentials");
    }
}
