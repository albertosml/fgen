package customer.application.usecases;

import customer.application.Customer;
import customer.application.CustomerAttribute;
import customer.application.utils.CustomerToRegisterValidator;
import customer.dataaccess.components.CustomerRepository;
import java.util.Map;
import shared.application.utils.CodeAutoGenerator;

/**
 * Register customer use case.
 */
public class RegisterCustomer {

    /**
     * @see CustomerRepository
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructor.
     *
     * @param customerRepository Customer repository.
     */
    public RegisterCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Execute the customer registration.
     *
     * Before that, an auto incremental customer code is generated. Then, that
     * customer is validated.
     *
     * @param newCustomerAttributes The attributes for the customer to register.
     * @return Whether the customer has been registered successfully or not.
     */
    public boolean execute(Map<CustomerAttribute, Object> newCustomerAttributes) {
        int generatedCustomerCode = CodeAutoGenerator.generate(customerRepository);

        newCustomerAttributes.put(CustomerAttribute.CODE, generatedCustomerCode);

        Customer customer = Customer.from(newCustomerAttributes);

        boolean isValidCustomer = CustomerToRegisterValidator.canBeRegistered(customer, customerRepository);

        if (isValidCustomer) {
            customerRepository.register(customer);
        }

        return isValidCustomer;
    }
}
