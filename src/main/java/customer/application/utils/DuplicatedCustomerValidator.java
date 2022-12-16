package customer.application.utils;

import customer.application.Customer;
import customer.dataaccess.components.CustomerRepository;

/**
 * Validate if the customer to register already exists on the repository by
 * checking its TIN.
 *
 * @see Customer
 */
public class DuplicatedCustomerValidator {

    /**
     * Check if there is customer with the same TIN or not.
     *
     * @param customer The customer to validate.
     * @param customerRepository The customer repository.
     * @return true if the customer is duplicated, otherwise false.
     */
    public static boolean isDuplicated(Customer customer, CustomerRepository customerRepository) {
        String customerTin = customer.getTin();

        for (String tin : customerRepository.getTinList()) {
            if (tin.equals(customerTin)) {
                return true;
            }
        }

        return false;
    }
}
