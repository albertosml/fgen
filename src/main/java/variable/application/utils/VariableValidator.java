package variable.application.utils;

import shared.application.utils.NameValidator;
import variable.application.Variable;
import variable.persistence.VariableRepository;

/**
 * Validate the variable.
 *
 * @see Variable
 */
public class VariableValidator {

    /**
     * Check whether a variable is valid or not.
     *
     * @param variable The variable to validate.
     * @param variableRepository The variable repository to check for duplicated
     * names.
     * @return The validation state for the variable.
     */
    public static VariableValidationState isValid(Variable variable, VariableRepository variableRepository) {
        boolean isValidName = NameValidator.isValid(variable.getName());
        if (!isValidName) {
            return VariableValidationState.INVALID_NAME;
        }

        if (DuplicatedVariableValidator.isDuplicated(variable, variableRepository)) {
            return VariableValidationState.DUPLICATED;
        }

        return VariableValidationState.VALID;
    }

}
