package variable.persistence;

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

}
