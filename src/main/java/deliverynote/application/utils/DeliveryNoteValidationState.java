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
     * Invalid delivery note farmer customer.
     */
    INVALID_FARMER,
    /**
     * Invalid delivery note trader customer.
     */
    INVALID_TRADER,
    /**
     * Invalid delivery note product.
     */
    INVALID_PRODUCT,
    /**
     * Invalid delivery note template.
     */
    INVALID_TEMPLATE,
    /**
     * Invalid delivery note pallet.
     */
    INVALID_PALLET,
    /**
     * Invalid delivery note number of pallets.
     */
    INVALID_NUM_PALLETS,
    /**
     * Invalid delivery note weighings.
     */
    INVALID_WEIGHINGS,
    /**
     * Invalid delivery note.
     */
    INVALID,
}
