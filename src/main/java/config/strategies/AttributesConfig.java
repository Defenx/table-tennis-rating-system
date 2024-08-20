package config.strategies;

import java.util.Map;

/**
 * Класс конфигурации атрибутов.
 */
public class AttributesConfig implements ConfigStrategy {
    private final Map<String, Object> attributes;

    /**
     * Создает новый экземпляр класса AttributesConfig.
     *
     * @param config объект конфигурации, который должен быть представлен в виде карты (Map)
     * @throws IllegalArgumentException если переданный объект не является картой
     */
    public AttributesConfig(Object config) {
        if (config instanceof Map) {
            this.attributes = (Map<String, Object>) config;
        } else {
            throw new IllegalArgumentException("Ожидается карта (Map) для конфигурации атрибутов.");
        }
    }

    /**
     * Возвращает значение по указанному ключу.
     *
     * @param key ключ для поиска значения
     * @return значение, соответствующее ключу, или null, если ключ не найден
     */
    @Override
    public String getValue(String key) {
        return attributes != null ? (String) attributes.get(key) : null;
    }
}
