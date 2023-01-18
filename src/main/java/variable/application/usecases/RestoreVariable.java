package variable.application.usecases;

import variable.application.Variable;
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
     * @param name The name of the variable to restore.
     * @return Whether the variable has been restored or not.
     */
    public boolean execute(String name) {
        Variable variable = variableRepository.find(name);

        if (variable == null) {
            return false;
        }

        variable.setIsDeleted(false);
        return variableRepository.update(variable);
    }

}
