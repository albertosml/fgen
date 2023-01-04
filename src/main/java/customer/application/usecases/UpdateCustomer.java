package customer.application.usecases;

import customer.application.Customer;
import customer.application.CustomerAttribute;
import customer.application.utils.CustomerValidationState;
import customer.application.utils.CustomerValidator;
import customer.persistence.CustomerRepository;
import java.util.Map;

/**
 * Update customer use case.
 */
public class UpdateCustomer {

    /**
     * @see CustomerRepository
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructor.
     *
     * @param customerRepository Customer repository.
     */
    public UpdateCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Update the given customer.
     *
     * @param customerAttributes The customer attributes.
     * @param modifiedTin Whether the TIN has been modified or not.
     * @return The validation state for the customer to update.
     */
    public CustomerValidationState execute(Map<CustomerAttribute, Object> customerAttributes, boolean modifiedTin) {
        Customer customer = Customer.from(customerAttributes);

        // The repository will be only passed to the validator when the TIN has changed.
        CustomerRepository repository = modifiedTin ? this.customerRepository : null;

        CustomerValidationState customerValidationState = CustomerValidator.isValid(customer, repository);
        if (customerValidationState == CustomerValidationState.VALID) {
            customerRepository.update(customer);
        }

        return customerValidationState;
    }

}
