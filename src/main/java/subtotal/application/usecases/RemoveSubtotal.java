package subtotal.application.usecases;

import subtotal.application.Subtotal;
import subtotal.application.utils.SubtotalRemovalState;
import subtotal.persistence.SubtotalRepository;
import variable.persistence.VariableRepository;

/**
 * Remove variable use case.
 */
public class RemoveSubtotal {

    /**
     * @see SubtotalRepository
     */
    private SubtotalRepository subtotalRepository;

    /**
     * @see VariableRepository
     */
    private VariableRepository variableRepository;

    /**
     * Constructor.
     *
     * @param subtotalRepository Subtotal repository.
     * @param variableRepository Variable repository.
     */
    public RemoveSubtotal(SubtotalRepository subtotalRepository, VariableRepository variableRepository) {
        this.subtotalRepository = subtotalRepository;
        this.variableRepository = variableRepository;
    }

    /**
     * Check if the subtotal can be removed.
     *
     * That can be done if the subtotal is not associated with a variable.
     *
     * @param code The subtotal code.
     * @return Whether the subtotal can be removed or not.
     */
    public boolean canBeRemoved(int code) {
        return !variableRepository.existVariableWithSubtotal(code);
    }

    /**
     * Remove the subtotal which matches with the given code.
     *
     * @param code The code of the subtotal to remove.
     * @return The subtotal removal state.
     */
    public SubtotalRemovalState execute(int code) {
        if (!this.canBeRemoved(code)) {
            return SubtotalRemovalState.ASSOCIATED_WITH_VARIABLE;
        }

        Subtotal subtotal = subtotalRepository.find(code);

        if (subtotal == null) {
            return SubtotalRemovalState.NOT_FOUND;
        }

        subtotal.setIsDeleted(true);

        boolean isSubtotalUpdated = subtotalRepository.update(subtotal);

        if (isSubtotalUpdated) {
            return SubtotalRemovalState.REMOVED;
        }

        return SubtotalRemovalState.NOT_UPDATED;
    }

}
