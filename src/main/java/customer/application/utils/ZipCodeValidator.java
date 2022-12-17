package customer.application.utils;

import shared.application.utils.RegexValidator;

/**
 * Validate the ZIP code, which can only the Spanish format (5 numbers).
 */
public class ZipCodeValidator {

    /**
     * Check whether the ZIP code is valid or not.
     *
     * @param zipCode The ZIP code to validate.
     * @return true if the ZIP code is valid, otherwise false.
     */
    public static boolean isValid(String zipCode) {
        if (zipCode == null) {
            return false;
        }

        // Example valid ZIP code: 23127
        String zipCodeRegex = "^[0-9]{5}$";

        return RegexValidator.matches(zipCode, zipCodeRegex);
    }

}
