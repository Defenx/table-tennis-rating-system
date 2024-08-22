package config.strategy;

import java.util.Map;

/**
 * The type Pages config.
 */
public class PagesConfig implements ConfigStrategy {
    private final Map<String, Object> pages;

    /**
     * Instantiates a new Pages config.
     *
     * @param config the config
     */
    public PagesConfig(Object config) {
        if (config instanceof Map) {
            this.pages = (Map<String, Object>) config;
        } else {
            throw new IllegalArgumentException("Неверный формат конфигурации для секции 'pages'");
        }
    }

    @Override
    public String getValue(String key) {
        Object value = pages.get(key);
        if (value instanceof String) {
            return (String) value;
        }
        throw new IllegalArgumentException("Значение для ключа '" + key + "' не является строкой.");
    }
}