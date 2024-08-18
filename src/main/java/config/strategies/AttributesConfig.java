package config.strategies;

import java.util.Map;

public class AttributesConfig implements ConfigStrategy {
    private final Map<String, Object> attributes;

    public AttributesConfig(Object config) {
        if (config instanceof Map) {
            this.attributes = (Map<String, Object>) config;
        }
        else {
            throw new IllegalArgumentException("Expected a Map for attributes configuration.");
        }
    }

    @Override
    public String getValue(String key) {
        return attributes != null ? (String) attributes.get(key) : null;
    }
}
