package variable.application.utils;

import variable.application.Variable;
import variable.persistence.VariableRepository;

/**
 * Validate if the variable to register already exists on the repository by
 * checking its name.
 *
 * @see Variable
 */
public class DuplicatedVariableValidator {

    /**
     * Check if there is a variable with the same name or not.
     *
     * @param variable The variable to validate.
     * @param variableRepository The variable repository.
     * @return true if the variable is duplicated, otherwise false.
     */
    public static boolean isDuplicated(Variable variable, VariableRepository variableRepository) {
        String variableName = variable.getName();

        for (String name : variableRepository.getNamesList()) {
            if (name.equals(variableName)) {
                return true;
            }
        }

        return false;
    }

}
