package customer.application.utils;

import customer.application.Customer;
import customer.persistence.CustomerRepository;

/**
 * Validate the customer to register.
 *
 * @see Customer
 */
public class CustomerToRegisterValidator {

    /**
     * Check whether a customer can be registered or not.
     *
     * @param customer The customer to validate.
     * @param customerRepository The customer repository.
     * @return The validation state for the customer.
     */
    public static CustomerValidationState isValidForRegistration(Customer customer, CustomerRepository customerRepository) {
        if (DuplicatedCustomerValidator.isDuplicated(customer, customerRepository)) {
            return CustomerValidationState.DUPLICATED;
        }

        return CustomerValidator.isValid(customer);
    }

}
