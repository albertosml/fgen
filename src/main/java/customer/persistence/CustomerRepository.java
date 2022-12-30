package customer.persistence;

import customer.application.Customer;
import java.util.ArrayList;
import shared.persistence.Repository;

/**
 * Represents the repository model for the customer entity.
 */
public interface CustomerRepository extends Repository {

    /**
     * Get the TIN from all non-removed customers from the repository.
     *
     * @return A list with all the retrieved TINs.
     */
    public ArrayList<String> getTinList();

    /**
     * Register the given customer.
     *
     * @param customer The customer to register.
     */
    public void register(Customer customer);

    /**
     * Obtain all the customers registered on the system, even if they have been
     * removed after.
     *
     * @return A list with all customers.
     */
    public ArrayList<Customer> get();

    /**
     * Find the customer which matches with the given code.
     *
     * @param code The code of the customer to find.
     * @return The found customer, otherwise null.
     */
    public Customer find(int code);

}
