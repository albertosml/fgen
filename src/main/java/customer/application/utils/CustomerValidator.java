package customer.application.utils;

import customer.application.Customer;
import shared.application.utils.NameValidator;

/**
 * Validates the customer.
 *
 * @see Customer
 */
public class CustomerValidator {

    /**
     * Check whether a customer is valid or not.
     *
     * @param customer The customer to validate.
     * @return true if the customer is valid, otherwise false.
     */
    public static boolean isValid(Customer customer) {
        boolean isValidName = NameValidator.isValid(customer.getName());
        if (!isValidName) {
            return false;
        }

        boolean isValidTin = TinValidator.isValid(customer.getTin());
        if (!isValidTin) {
            return false;
        }

        String customerZipCode = customer.getZipCode();
        if (!(customerZipCode == null || ZipCodeValidator.isValid(customerZipCode))) {
            return false;
        }

        String customerIban = customer.getIban();
        return customerIban == null || IbanValidator.isValid(customerIban);
    }

}
