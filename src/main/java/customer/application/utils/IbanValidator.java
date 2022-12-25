package customer.application.utils;

import shared.application.utils.RegexValidator;

/**
 * Validate the IBAN.
 */
public class IbanValidator {

    /**
     * Check whether the IBAN is valid or not.
     *
     * @param iban The IBAN to validate.
     * @return true if the IBAN is valid, otherwise false.
     */
    public static boolean isValid(String iban) {
        if (iban == null) {
            return false;
        }

        // Example valid IBAN (first format): AU22 0302 3202 20 3223022392
        String firstIbanRegex = "^[A-Z]{2}[0-9]{2}\\s[0-9]{4}\\s[0-9]{4}\\s[0-9]{2}\\s[0-9]{10}$";

        // Example valid IBAN (second format): UK22 0302 3202 2032 2302 2392
        String secondIbanRegex = "^[A-Z]{2}[0-9]{2}\\s[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}$";

        // Example valid IBAN (third format): ES2203023202203223022392
        String thirdIbanRegex = "^[A-Z]{2}[0-9]{22}$";

        String ibanRegex = String.format("%s|%s|%s", firstIbanRegex, secondIbanRegex, thirdIbanRegex);

        return RegexValidator.matches(iban, ibanRegex);
    }

}
