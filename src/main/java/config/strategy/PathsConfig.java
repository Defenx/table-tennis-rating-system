package config.strategy;

import java.util.Map;

/**
 * Класс конфигурации путей.
 */
public class PathsConfig implements ConfigStrategy {
    private final Map<String, Object> paths;

    /**
     * Instantiates a new Paths config.
     *
     * @param config the config
     */
    public PathsConfig(Object config) {
        if (config instanceof Map) {
            this.paths = (Map<String, Object>) config;
        } else {
            throw new IllegalArgumentException("Ожидается карта (Map) для конфигурации путей.");
        }
    }

    @Override
    public String getValue(String key) {
        return paths != null ? (String) paths.get(key) : null;
    }
}
