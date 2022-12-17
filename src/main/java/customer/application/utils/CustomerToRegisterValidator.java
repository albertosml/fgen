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
     * @return true if the customer can be registered, otherwise false.
     */
    public static boolean canBeRegistered(Customer customer, CustomerRepository customerRepository) {
        if (!DuplicatedCustomerValidator.isDuplicated(customer, customerRepository)) {
            return false;
        }

        return CustomerValidator.isValid(customer);
    }

}
