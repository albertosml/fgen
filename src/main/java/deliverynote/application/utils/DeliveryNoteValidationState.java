package deliverynote.application.utils;

/**
 * Represents the validation state for a concrete delivery note.
 */
public enum DeliveryNoteValidationState {
    /**
     * Valid delivery note.
     */
    VALID,
    /**
     * Invalid delivery note customer.
     */
    INVALID_CUSTOMER,
    /**
     * Invalid delivery note product.
     */
    INVALID_PRODUCT,
    /**
     * Invalid delivery note template.
     */
    INVALID_TEMPLATE,
    /**
     * Invalid delivery note weight.
     */
    INVALID_WEIGHT,
    /**
     * Invalid delivery note.
     */
    INVALID,
}
