package customer.application.utils;

import customer.application.Customer;

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
        if (!NameValidator.isValid(customer.getName())) {
            return false;
        }

        if (!TinValidator.isValid(customer.getTin())) {
            return false;
        }

        if (!ZipCodeValidator.isValid(customer.getZipCode())) {
            return false;
        }

        if (!IbanValidator.isValid(customer.getIban())) {
            return false;
        }

        return true;
    }

}
