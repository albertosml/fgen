package template.application.utils;

/**
 * Represents the validation state for a concrete template.
 */
public enum TemplateValidationState {
    /**
     * Valid template.
     */
    VALID,
    /**
     * Invalid template because of the name.
     */
    INVALID_NAME,
    /**
     * Invalid template because of the file.
     */
    INVALID_FILE
}
