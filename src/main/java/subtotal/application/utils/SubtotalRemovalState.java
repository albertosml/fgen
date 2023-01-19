package subtotal.application.utils;

/**
 * Represents all the states which the subtotal can be after trying to remove
 * it.
 */
public enum SubtotalRemovalState {
    /**
     * The subtotal has been removed successfully.
     */
    REMOVED,
    /**
     * The subtotal cannot be removed because it is associated with at least one
     * variable.
     */
    ASSOCIATED_WITH_VARIABLE,
    /**
     * The subtotal cannot be removed because it has not been updated.
     */
    NOT_UPDATED,
    /**
     * The subtotal cannot be removed because it has not been found.
     */
    NOT_FOUND
}
