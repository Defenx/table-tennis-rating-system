package config.strategies;

import java.util.Map;

/**
 * Класс конфигурации ошибок.
 */
public class ErrorsConfig implements ConfigStrategy {
    private final Map<String, Object> errors;

    /**
     * Создает новый экземпляр класса ErrorsConfig.
     *
     * @param config объект конфигурации, который должен быть представлен в виде карты (Map)
     * @throws IllegalArgumentException если переданный объект не является картой
     */
    public ErrorsConfig(Object config) {
        if (config instanceof Map) {
            this.errors = (Map<String, Object>) config;
        } else {
            throw new IllegalArgumentException("Ожидается карта (Map) для конфигурации ошибок.");
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
        return errors != null ? (String) errors.get(key) : null;
    }
}
