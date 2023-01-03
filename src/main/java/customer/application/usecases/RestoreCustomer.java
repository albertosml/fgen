package customer.application.usecases;

import customer.application.Customer;
import customer.persistence.CustomerRepository;

/**
 * Restore customer use case.
 */
public class RestoreCustomer {

    /**
     * @see CustomerRepository
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructor.
     *
     * @param customerRepository Customer repository.
     */
    public RestoreCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Restore the customer which matches with the given code.
     *
     * @param code The code of the customer to restore.
     * @return Whether the customer has been restored or not.
     */
    public boolean execute(int code) {
        ShowCustomer showCustomer = new ShowCustomer(customerRepository);
        Customer customer = showCustomer.execute(code);

        if (customer == null) {
            return false;
        }

        customer.setIsDeleted(false);
        return customerRepository.update(customer);
    }

}
