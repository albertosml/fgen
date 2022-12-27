package customer.application.usecases;

import customer.application.Customer;
import customer.persistence.CustomerRepository;
import java.util.ArrayList;

/**
 * List customers use case.
 */
public class ListCustomers {

    /**
     * @see CustomerRepository
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructor.
     *
     * @param customerRepository Customer repository.
     */
    public ListCustomers(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * List all customers.
     *
     * @return A list with all available customers.
     */
    public ArrayList<Customer> execute() {
        return customerRepository.get();
    }

}
