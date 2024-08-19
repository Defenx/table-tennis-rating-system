package config.strategies;

/**
 * Интерфейс стратегии конфигурации.
 */
public interface ConfigStrategy {
    /**
     * Возвращает значение по указанному ключу.
     *
     * @param key ключ для поиска значения
     * @return значение, соответствующее ключу
     */
    String getValue(String key);
}
