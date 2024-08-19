package config.strategies;

import java.util.Map;

/**
 * Класс конфигурации путей.
 */
public class PathsConfig implements ConfigStrategy {
    private final Map<String, Object> paths;

    /**
     * Создает новый экземпляр класса PathsConfig.
     *
     * @param config объект конфигурации, который должен быть представлен в виде карты (Map)
     * @throws IllegalArgumentException если переданный объект не является картой
     */
    public PathsConfig(Object config) {
        if (config instanceof Map) {
            this.paths = (Map<String, Object>) config;
        } else {
            throw new IllegalArgumentException("Ожидается карта (Map) для конфигурации путей.");
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
        return paths != null ? (String) paths.get(key) : null;
    }
}
