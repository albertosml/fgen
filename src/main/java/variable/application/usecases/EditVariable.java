package variable.application.usecases;

import java.util.Map;
import variable.application.SubtotalVariable;
import variable.application.Variable;
import variable.application.VariableAttribute;
import variable.persistence.VariableRepository;

/**
 * Edit variable use case.
 */
public class EditVariable {

    /**
     * @see VariableRepository
     */
    private VariableRepository variableRepository;

    /**
     * Constructor.
     *
     * @param variableRepository Variable repository.
     */
    public EditVariable(VariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    /**
     * Update the given variable.
     *
     * @param variableAttributes The variable attributes.
     */
    public void execute(Map<VariableAttribute, Object> variableAttributes) {
        boolean isSubtotalIncluded = variableAttributes.containsKey(VariableAttribute.SUBTOTAL);
        Variable variable = isSubtotalIncluded
                ? SubtotalVariable.from(variableAttributes)
                : Variable.from(variableAttributes);
        variableRepository.update(variable);
    }

}
