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
     * Obtain all the variables registered on the system, even if they have been
     * removed after.
     *
     * @return A list with all variables.
     */
    public ArrayList<Variable> get();

    /**
     * Get the names from all variables on the repository.
     *
     * @return A list with all the retrieved variable names.
     */
    public ArrayList<String> getNamesList();

    /**
     * Update the given variable with its associated data.
     *
     * @param variable The variable to update.
     * @return Whether the variable has been updated or not.
     */
    public boolean update(Variable variable);

    /**
     * Find the variable which matches with the given name.
     *
     * @param name The name of the variable to find.
     * @return The found variable, otherwise null.
     */
    public Variable find(String name);

    /**
     * Checks if there is at least one variable which contains a subtotal with
     * the given code.
     *
     * @param code The subtotal code.
     * @return Whether a subtotal is associated to at least one variable.
     */
    public boolean existVariableWithSubtotal(int code);

}
