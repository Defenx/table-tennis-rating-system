package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Config class loads and provides access to properties from a configuration file.
 */
public class Config {
    private static final String PROPERTIES_FILE = "config.properties";
    private final Properties properties = new Properties();

    /**
     * Constructor for the Config class.
     * Loads properties from the config.properties file.
     */
    public Config() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Sorry, unable to find " + PROPERTIES_FILE);
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Returns the value of the property for the given key.
     *
     * @param key the property key
     * @return the property value
     * @throws IllegalArgumentException if the property is not found
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Property '" + key + "' not found.");
        }
        return value;
    }
}