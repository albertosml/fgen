package subtotal.application.usecases;

import java.util.Map;
import subtotal.application.Subtotal;
import subtotal.application.SubtotalAttribute;
import subtotal.persistence.SubtotalRepository;

/**
 * Edit subtotal use case.
 */
public class EditSubtotal {

    /**
     * @see SubtotalRepository
     */
    private SubtotalRepository subtotalRepository;

    /**
     * Constructor.
     *
     * @param subtotalRepository Subtotal repository.
     */
    public EditSubtotal(SubtotalRepository subtotalRepository) {
        this.subtotalRepository = subtotalRepository;
    }

    /**
     * Update the given subtotal.
     *
     * @param subtotalAttributes The subtotal attributes.
     */
    public void execute(Map<SubtotalAttribute, Object> subtotalAttributes) {
        Subtotal subtotal = Subtotal.from(subtotalAttributes);
        subtotalRepository.update(subtotal);
    }

}
