package customer.application.usecases;

import customer.application.Customer;
import customer.persistence.CustomerRepository;
import java.util.ArrayList;

/**
 * Obtain customers use case.
 */
public class ObtainCustomers {

    /**
     * @see CustomerRepository
     */
    private CustomerRepository customerRepository;

    /**
     * Constructor.
     *
     * @param customerRepository Customer repository.
     */
    public ObtainCustomers(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Obtain all customers.
     *
     * @param getFarmers Whether we should get the farmer customers or not.
     * @return A list with all obtained customers.
     */
    public ArrayList<Customer> execute(boolean getFarmers) {
        return customerRepository.obtain(getFarmers);
    }

}
