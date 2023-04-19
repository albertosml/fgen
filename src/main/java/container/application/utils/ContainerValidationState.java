package container.application.utils;

/**
 * Represents the validation state for a concrete container.
 */
public enum ContainerValidationState {
    /**
     * Valid container.
     */
    VALID,
    /**
     * Invalid container because of the code.
     */
    INVALID_CODE,
    /**
     * Invalid container because of the name.
     */
    INVALID_NAME,
    /**
     * Invalid container because of the weight.
     */
    INVALID_WEIGHT,
    /**
     * Invalid container because it has a duplicated code.
     */
    DUPLICATED
}
