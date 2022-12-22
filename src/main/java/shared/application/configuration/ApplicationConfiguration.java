package shared.application.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores all the configuration for the application.
 */
public class ApplicationConfiguration {

    /**
     * Contains all the values for the system configuration variables.
     */
    private static Map<ConfigurationVariable, Object> variables = new HashMap<>();

    /**
     * Add a configuration variable to the system.
     *
     * @param configurationVariable Configuration variable to set the value.
     * @param value The value to add to the configuration variable.
     */
    public static void addConfigurationVariable(ConfigurationVariable configurationVariable, Object value) {
        variables.put(configurationVariable, value);
    }

    /**
     * Obtains the configuration variable value.
     *
     * @param <Type> Type which the configuration variable value will be cast.
     * @param configurationVariable Configuration variable to get the value.
     * @return The value for the specified configuration variable.
     */
    public static <Type> Type getConfigurationVariable(ConfigurationVariable configurationVariable) {
        return (Type) variables.get(configurationVariable);
    }

}
