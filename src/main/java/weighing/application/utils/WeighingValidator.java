package weighing.application.utils;

import container.application.Box;
import weighing.application.Weighing;

/**
 * Validate the weighing.
 *
 * @see Weighing
 */
public class WeighingValidator {

    /**
     * Check whether a weighing is valid or not.
     *
     * @param weighing The weighing to validate.
     * @return Whether the weighing is valid or not.
     */
    public static boolean isValid(Weighing weighing) {
        Box box = weighing.getBox();
        if (box == null) {
            return false;
        }

        int qty = weighing.getQty();
        if (qty <= 0) {
            return false;
        }

        int grossWeight = weighing.getWeight();
        return grossWeight > 0;
    }

}
