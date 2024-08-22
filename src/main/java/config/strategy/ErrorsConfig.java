package config.strategy;

import java.util.Map;


/**
 * The type Errors config.
 */
public class ErrorsConfig implements ConfigStrategy {
    private final Map<String, Object> errors;


    /**
     * Instantiates a new Errors config.
     *
     * @param config the config
     */
    public ErrorsConfig(Object config) {
        if (config instanceof Map) {
            this.errors = (Map<String, Object>) config;
        } else {
            throw new IllegalArgumentException("Ожидается карта (Map) для конфигурации ошибок.");
        }
    }

    @Override
    public String getValue(String key) {
        return errors != null ? (String) errors.get(key) : null;
    }
}
