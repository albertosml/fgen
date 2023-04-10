package shared.application.utils;

/**
 * Validate a price.
 */
public class PriceValidator {

    /**
     * Whether the price is valid or not.
     *
     * @param price The price to validate.
     * @return true if the price is greater than 0, otherwise false.
     */
    public static boolean isValid(double price) {
        return price > 0;
    }

}
