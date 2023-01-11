package variable.application.utils;

/**
 * Represents the validation state for a concrete variable.
 */
public enum VariableValidationState {
    /**
     * Valid variable.
     */
    VALID,
    /**
     * Invalid variable because of the name.
     */
    INVALID_NAME,
    /**
     * Invalid variable because it has a duplicated name.
     */
    DUPLICATED
}
