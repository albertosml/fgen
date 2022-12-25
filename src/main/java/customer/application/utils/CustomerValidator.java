package customer.application.utils;

import customer.application.Customer;
import shared.application.utils.NameValidator;

/**
 * Validate the customer.
 *
 * @see Customer
 */
public class CustomerValidator {

    /**
     * Check whether a customer is valid or not.
     *
     * @param customer The customer to validate.
     * @return The validation state for the customer.
     */
    public static CustomerValidationState isValid(Customer customer) {
        boolean isValidName = NameValidator.isValid(customer.getName());
        if (!isValidName) {
            return CustomerValidationState.INVALID_NAME;
        }

        boolean isValidTin = TinValidator.isValid(customer.getTin());
        if (!isValidTin) {
            return CustomerValidationState.INVALID_TIN;
        }

        String customerZipCode = customer.getZipCode();
        boolean isEmptyZipCode = customerZipCode == null || customerZipCode.isBlank();
        if (!(isEmptyZipCode || ZipCodeValidator.isValid(customerZipCode))) {
            return CustomerValidationState.INVALID_ZIPCODE;
        }

        String customerIban = customer.getIban();
        boolean isEmptyIban = customerIban == null || customerIban.isBlank();
        if (!(isEmptyIban || IbanValidator.isValid(customerIban))) {
            return CustomerValidationState.INVALID_IBAN;
        }

        return CustomerValidationState.VALID;
    }

}
