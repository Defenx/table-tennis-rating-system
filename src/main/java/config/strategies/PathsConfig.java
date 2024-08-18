package config.strategies;

import java.util.Map;

public class PathsConfig implements ConfigStrategy {
    private final Map<String, Object> paths;

    public PathsConfig(Object config) {
        if (config instanceof Map) {
            this.paths = (Map<String, Object>) config;
        }
        else {
            throw new IllegalArgumentException("Expected a Map for paths configuration.");
        }
    }

    @Override
    public String getValue(String key) {
        return paths != null ? (String) paths.get(key) : null;
    }
}
