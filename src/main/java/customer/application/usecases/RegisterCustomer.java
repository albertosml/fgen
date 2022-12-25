package customer.application.usecases;

import customer.application.Customer;
import customer.application.CustomerAttribute;
import customer.application.utils.CustomerToRegisterValidator;
import customer.application.utils.CustomerValidationState;
import customer.persistence.CustomerRepository;
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
     * Note that the customer code is automatically generated if it has not been
     * introduced manually.
     *
     * Keep in mind that the customer is validated before the registration.
     *
     * @param newCustomerAttributes The attributes for the customer to register.
     * @return The validation state for the customer to register.
     */
    public CustomerValidationState execute(Map<CustomerAttribute, Object> newCustomerAttributes) {
        boolean isCodeManuallyAdded = newCustomerAttributes.containsKey(CustomerAttribute.CODE);
        if (!isCodeManuallyAdded) {
            int generatedCustomerCode = CodeAutoGenerator.generate(customerRepository);
            newCustomerAttributes.put(CustomerAttribute.CODE, generatedCustomerCode);
        }

        Customer customer = Customer.from(newCustomerAttributes);

        CustomerValidationState customerValidationState = CustomerToRegisterValidator.isValidForRegistration(customer, customerRepository);
        if (customerValidationState == CustomerValidationState.VALID) {
            customerRepository.register(customer);
        }

        return customerValidationState;
    }

}
