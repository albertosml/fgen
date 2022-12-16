package customer.dataaccess.components;

import customer.application.Customer;
import java.util.ArrayList;
import shared.dataaccess.components.Repository;

/**
 * Represents the repository model for the customer entity.
 */
public interface CustomerRepository extends Repository {
    /**
     * Register the given customer.
     *
     * @param customer The customer to register.
     */
    public void register(Customer customer);

    /**
     * Get the TIN from all non-removed customers from the repository.
     *
     * @return A list with all the retrieved TINs.
     */
    public ArrayList<String> getTinList();
}
