package variable.application.utils;

/**
 * Represents all the states which the variable can be after trying to restore
 * it.
 */
public enum VariableRestorationState {
    /**
     * The variable has been restored successfully.
     */
    RESTORED,
    /**
     * The variable cannot be restored because it is associated with a removed
     * subtotal.
     */
    ASSOCIATED_WITH_DELETED_SUBTOTAL,
    /**
     * The variable cannot be restored because it has not been updated.
     */
    NOT_UPDATED,
    /**
     * The variable cannot be restored because it has not been found.
     */
    NOT_FOUND
}
