package customer.application.usecases;

import customer.application.Customer;
import customer.persistence.CustomerRepository;

/**
 * Show customer use case.
 */
public class ShowCustomer {

    /**
     * @see CustomerRepository
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructor.
     *
     * @param customerRepository Customer repository.
     */
    public ShowCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Show the details of a customer.
     *
     * @param code The code of the customer to find.
     * @return The found customer, otherwise null.
     */
    public Customer execute(int code) {
        return customerRepository.find(code);
    }
}
