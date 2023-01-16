package variable.application.usecases;

import variable.application.Variable;
import variable.persistence.VariableRepository;

/**
 * Remove variable use case.
 */
public class RemoveVariable {

    /**
     * @see VariableRepository
     */
    private VariableRepository variableRepository;

    /**
     * Constructor.
     *
     * @param variableRepository Variable repository.
     */
    public RemoveVariable(VariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    /**
     * Remove the variable which matches with the given name.
     *
     * @param name The name of the variable to remove.
     * @return Whether the variable has been removed or not.
     */
    public boolean execute(String name) {
        Variable variable = variableRepository.find(name);

        if (variable == null) {
            return false;
        }

        variable.setIsDeleted(true);
        return variableRepository.update(variable);
    }

}
