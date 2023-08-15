package shared.application.utils;

/**
 * Validate a description.
 */
public class DescriptionValidator {

    /**
     * Whether the description is valid or not.
     *
     * @param description The description to validate.
     * @return true if the description is not empty or null, otherwise false.
     */
    public static boolean isValid(String description) {
        return !(description == null || description.trim().isEmpty());
    }

}
