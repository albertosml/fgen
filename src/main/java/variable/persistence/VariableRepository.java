package variable.persistence;

import java.util.ArrayList;
import shared.persistence.Repository;
import variable.application.Variable;

/**
 * Represents the repository model for the variable entity.
 */
public interface VariableRepository extends Repository {

    /**
     * Register the given variable.
     *
     * @param variable The variable to register.
     */
    public void register(Variable variable);

    /**
     * Get the names from all variables on the repository.
     *
     * @return A list with all the retrieved variable names.
     */
    public ArrayList<String> getNamesList();

}
