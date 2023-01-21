package variable.application.usecases;

import subtotal.application.Subtotal;
import variable.application.SubtotalVariable;
import variable.application.Variable;
import variable.application.utils.VariableRestorationState;
import variable.persistence.VariableRepository;

/**
 * Restore variable use case.
 */
public class RestoreVariable {

    /**
     * @see VariableRepository
     */
    private VariableRepository variableRepository;

    /**
     * Constructor.
     *
     * @param variableRepository Variable repository.
     */
    public RestoreVariable(VariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    /**
     * Restore the variable which matches with the given name.
     *
     * Note that a variable associated to a removed subtotal cannot be restored.
     *
     * @param name The name of the variable to restore.
     * @return The variable restoration state.
     */
    public VariableRestorationState execute(String name) {
        Variable variable = variableRepository.find(name);

        if (variable == null) {
            return VariableRestorationState.NOT_FOUND;
        }

        if (variable instanceof SubtotalVariable) {
            SubtotalVariable subtotalVariable = (SubtotalVariable) variable;
            Subtotal subtotal = subtotalVariable.getSubtotal();

            if (subtotal.isDeleted()) {
                return VariableRestorationState.ASSOCIATED_WITH_DELETED_SUBTOTAL;
            }
        }

        variable.setIsDeleted(false);

        boolean isVariableUpdated = variableRepository.update(variable);
        return isVariableUpdated ? VariableRestorationState.RESTORED : VariableRestorationState.NOT_UPDATED;
    }

}
