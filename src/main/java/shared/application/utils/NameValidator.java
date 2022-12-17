package shared.application.utils;

/**
 * Validate a name.
 */
public class NameValidator {

    /**
     * Whether the name is valid or not.
     *
     * @param name The name to validate.
     * @return true if the name is not empty or null, otherwise false.
     */
    public static boolean isValid(String name) {
        return !(name == null || name.isEmpty());
    }
}
