package invoice.application.utils;

/**
 * Represents the validation state for a concrete invoice.
 */
public enum InvoiceValidationState {
    /**
     * Valid invoice.
     */
    VALID,
    /**
     * Invalid invoice farmer customer.
     */
    INVALID_FARMER,
    /**
     * Invalid invoice supplier customer.
     */
    INVALID_SUPPLIER,
    /**
     * Invalid invoice start period date.
     */
    INVALID_START_PERIOD,
    /**
     * Invalid invoice end period date.
     */
    INVALID_END_PERIOD,
    /**
     * Invalid invoice delivery notes.
     */
    INVALID_DELIVERY_NOTES,
    /**
     * Invalid invoice.
     */
    INVALID,
}
