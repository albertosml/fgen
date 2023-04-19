package shared.application.utils;

/**
 * Validate a weight.
 */
public class WeightValidator {

    /**
     * Whether the weight is valid or not.
     *
     * @param weight The weight to validate.
     * @return true if the weight is greater than 0, otherwise false.
     */
    public static boolean isValid(double weight) {
        return weight > 0;
    }

}
