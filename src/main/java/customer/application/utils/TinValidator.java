package customer.application.utils;

import shared.application.utils.RegexValidator;

/**
 * Validate the TIN (Tax Identification Number), which can only be a Spanish
 * NIF, NIE or CIF.
 */
public class TinValidator {

    /**
     * Check whether the TIN is valid or not.
     *
     * @param tin The TIN to validate.
     * @return true if the TIN is valid, otherwise false.
     */
    public static boolean isValid(String tin) {
        if (tin == null) {
            return false;
        }

        // Example valid NIF: 04234323P.
        String nifRegex = "^[0-9]{8}[A-Z]$";

        // Example valid NIE: Y4234323C.
        String nieRegex = "^[XYZ][0-9]{7}[A-Z]$";

        // Example valid CIF: A58228501.
        String cifRegex = "^[ABCDEFGHJKLMNPQRSUVW][0-9]{7}[0-9A-J]$";

        String tinRegex = String.format("%s|%s|%s", nifRegex, nieRegex, cifRegex);

        return RegexValidator.matches(tin, tinRegex);
    }

}
