package customer.application.utils;

import customer.application.Customer;
import customer.persistence.CustomerRepository;
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
     * Note that the customer repository is optional. It only needs to be
     * specified when finding duplicated TINs.
     *
     * @param customer The customer to validate.
     * @param customerRepository The customer repository to check for duplicated
     * TIN.
     * @return The validation state for the customer.
     */
    public static CustomerValidationState isValid(Customer customer, CustomerRepository customerRepository) {
        boolean isValidName = NameValidator.isValid(customer.getName());
        if (!isValidName) {
            return CustomerValidationState.INVALID_NAME;
        }

        boolean isValidTin = TinValidator.isValid(customer.getTin());
        if (!isValidTin) {
            return CustomerValidationState.INVALID_TIN;
        }

        if (customerRepository != null && DuplicatedCustomerValidator.isDuplicated(customer, customerRepository)) {
            return CustomerValidationState.DUPLICATED;
        }

        String customerZipCode = customer.getZipCode();
        boolean isEmptyZipCode = customerZipCode == null || customerZipCode.trim().isEmpty();
        if (!(isEmptyZipCode || ZipCodeValidator.isValid(customerZipCode))) {
            return CustomerValidationState.INVALID_ZIPCODE;
        }

        String customerIban = customer.getIban();
        boolean isEmptyIban = customerIban == null || customerIban.trim().isEmpty();
        if (!(isEmptyIban || IbanValidator.isValid(customerIban))) {
            return CustomerValidationState.INVALID_IBAN;
        }

        return CustomerValidationState.VALID;
    }

}
