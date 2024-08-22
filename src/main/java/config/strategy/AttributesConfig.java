package config.strategy;

import java.util.Map;


/**
 * The type Attributes config.
 */
public class AttributesConfig implements ConfigStrategy {
    private final Map<String, Object> attributes;


    /**
     * Instantiates a new Attributes config.
     *
     * @param config the config
     */
    public AttributesConfig(Object config) {
        if (config instanceof Map) {
            this.attributes = (Map<String, Object>) config;
        } else {
            throw new IllegalArgumentException("Ожидается карта (Map) для конфигурации атрибутов.");
        }
    }

    @Override
    public String getValue(String key) {
        return attributes != null ? (String) attributes.get(key) : null;
    }
}
