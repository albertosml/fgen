package subtotal.application.usecases;

import subtotal.application.Subtotal;
import subtotal.persistence.SubtotalRepository;

/**
 * Restore subtotal use case.
 */
public class RestoreSubtotal {

    /**
     * @see SubtotalRepository
     */
    private SubtotalRepository subtotalRepository;

    /**
     * Constructor.
     *
     * @param subtotalRepository Subtotal repository.
     */
    public RestoreSubtotal(SubtotalRepository subtotalRepository) {
        this.subtotalRepository = subtotalRepository;
    }

    /**
     * Restore the subtotal which matches with the given code.
     *
     * @param code The code of the subtotal to restore.
     * @return Whether the subtotal has been restored or not.
     */
    public boolean execute(int code) {
        Subtotal subtotal = subtotalRepository.find(code);

        if (subtotal == null) {
            return false;
        }

        subtotal.setIsDeleted(false);
        return subtotalRepository.update(subtotal);
    }

}
