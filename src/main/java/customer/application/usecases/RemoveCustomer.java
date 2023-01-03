package customer.application.usecases;

import customer.application.Customer;
import customer.persistence.CustomerRepository;

/**
 * Remove customer use case.
 */
public class RemoveCustomer {

    /**
     * @see CustomerRepository
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructor.
     *
     * @param customerRepository Customer repository.
     */
    public RemoveCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Remove the customer which matches with the given code.
     *
     * @param code The code of the customer to remove.
     * @return Whether the customer has been removed or not.
     */
    public boolean execute(int code) {
        ShowCustomer showCustomer = new ShowCustomer(customerRepository);
        Customer customer = showCustomer.execute(code);

        if (customer == null) {
            return false;
        }

        customer.setIsDeleted(true);
        return customerRepository.update(customer);
    }

}
