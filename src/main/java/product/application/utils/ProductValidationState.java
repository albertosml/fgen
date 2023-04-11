package product.application.utils;

/**
 * Represents the validation state for a concrete product.
 */
public enum ProductValidationState {
    /**
     * Valid product.
     */
    VALID,
    /**
     * Invalid product because of the code.
     */
    INVALID_CODE,
    /**
     * Invalid product because of the name.
     */
    INVALID_NAME,
    /**
     * Invalid product because of the price.
     */
    INVALID_PRICE,
    /**
     * Invalid product because it has a duplicated code.
     */
    DUPLICATED
}
