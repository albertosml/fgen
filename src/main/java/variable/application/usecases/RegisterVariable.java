package variable.application.usecases;

import java.util.Map;
import variable.application.SubtotalVariable;
import variable.application.Variable;
import variable.application.VariableAttribute;
import variable.application.utils.VariableValidationState;
import variable.application.utils.VariableValidator;
import variable.persistence.VariableRepository;

/**
 * Register variable use case.
 */
public class RegisterVariable {

    /**
     * @see VariableRepository
     */
    private VariableRepository variableRepository;

    /**
     * Constructor.
     *
     * @param variableRepository Variable repository.
     */
    public RegisterVariable(VariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    /**
     * Execute the variable registration.
     *
     * Keep in mind that the variable is validated before the registration.
     *
     * @param newVariableAttributes The attributes for the variable to register.
     * @return The validation state for the customer to register.
     */
    public VariableValidationState execute(Map<VariableAttribute, Object> newVariableAttributes) {
        boolean includesSubtotal = newVariableAttributes.containsKey(VariableAttribute.SUBTOTAL);
        Variable variable = includesSubtotal
                ? SubtotalVariable.from(newVariableAttributes)
                : Variable.from(newVariableAttributes);

        VariableValidationState variableValidationState = VariableValidator.isValid(variable, variableRepository);
        if (variableValidationState == VariableValidationState.VALID) {
            variableRepository.register(variable);
        }

        return variableValidationState;
    }

}
