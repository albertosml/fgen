package customer.application.utils;

/**
 * Represents the validation state for a concrete customer.
 */
public enum CustomerValidationState {
    /**
     * Valid customer.
     */
    VALID,
    /**
     * Invalid customer because of the name.
     */
    INVALID_NAME,
    /**
     * Invalid customer because of the TIN.
     */
    INVALID_TIN,
    /**
     * Invalid customer because of the ZIP code.
     */
    INVALID_ZIPCODE,
    /**
     * Invalid customer because of the IBAN.
     */
    INVALID_IBAN,
    /**
     * Invalid customer because it has a duplicated TIN.
     */
    DUPLICATED
}
