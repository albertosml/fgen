package shared.application.utils;

/**
 * Validate a quantity.
 */
public class QtyValidator {

    /**
     * Whether the quantity is valid or not.
     *
     * @param qty The quantity to validate.
     * @return true if the quantity is greater than 0, otherwise false.
     */
    public static boolean isValid(int qty) {
        return qty > 0;
    }

}
