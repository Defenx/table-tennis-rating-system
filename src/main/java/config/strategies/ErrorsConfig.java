package config.strategies;

import java.util.Map;

public class ErrorsConfig implements ConfigStrategy {
    private final Map<String, Object> errors;

    public ErrorsConfig(Object config) {
        if (config instanceof Map) {
            this.errors = (Map<String, Object>) config;
        }
        else {
            throw new IllegalArgumentException("Expected a Map for errors configuration.");
        }
    }

    @Override
    public String getValue(String key) {
        return errors != null ? (String) errors.get(key) : null;
    }
}
