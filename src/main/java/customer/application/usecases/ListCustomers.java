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
     * @param includeRemoved Whether we should include removed customers or not.
     * @return A list with all available customers.
     */
    public ArrayList<Customer> execute(boolean includeRemoved) {
        return customerRepository.get(includeRemoved);
    }

}
