package config;

import config.strategy.*;
import enums.ConfigSection;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The type App config.
 */
public class AppConfig {
    private static final String CONFIG_FILE = "application.yaml";
    private static Map<String, Object> config;
    private static Map<ConfigSection, ConfigStrategy> strategies;

    /**
     * Load config.
     *
     * @throws RuntimeException the runtime exception
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

    private static void initializeStrategies() {
        strategies = new HashMap<>();
        Object appConfig = config.get("app");

        if (appConfig instanceof Map) {
            Map<String, Object> appConfigMap = (Map<String, Object>) appConfig;
            strategies.put(ConfigSection.PATHS, new PathsConfig(appConfigMap.get(ConfigSection.PATHS.getValue())));
            strategies.put(ConfigSection.ERRORS, new ErrorsConfig(appConfigMap.get(ConfigSection.ERRORS.getValue())));
            strategies.put(ConfigSection.ATTRIBUTES, new AttributesConfig(appConfigMap.get(ConfigSection.ATTRIBUTES.getValue())));
            strategies.put(ConfigSection.PAGES, new PagesConfig(appConfigMap.get(ConfigSection.PAGES.getValue())));
        } else {
            throw new IllegalStateException("Секция конфигурации 'app' отсутствует или не является мапой.");
        }
    }

    /**
     * Gets config value.
     *
     * @param section the section
     * @param key     the key
     * @return the config value
     */
    public static String getConfigValue(ConfigSection section, String key) {
        ConfigStrategy strategy = strategies.get(section);
        if (strategy == null) {
            throw new IllegalArgumentException("Секция '" + section.getValue() + "' не найдена в конфигурации.");
        }
        return strategy.getValue(key);
    }
}