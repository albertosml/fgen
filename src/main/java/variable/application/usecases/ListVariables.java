package variable.application.usecases;

import java.util.ArrayList;
import variable.application.Variable;
import variable.persistence.VariableRepository;

/**
 * List variables use case.
 */
public class ListVariables {

    /**
     * @see VariableRepository
     */
    private VariableRepository variableRepository;

    /**
     * Constructor.
     *
     * @param variableRepository Variable repository.
     */
    public ListVariables(VariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    /**
     * List all variables.
     *
     * @return A list with all variables.
     */
    public ArrayList<Variable> execute() {
        return variableRepository.get();
    }

}
