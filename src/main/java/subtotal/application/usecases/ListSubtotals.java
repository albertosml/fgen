package subtotal.application.usecases;

import java.util.ArrayList;
import subtotal.application.Subtotal;
import subtotal.persistence.SubtotalRepository;

/**
 * List subtotals use case.
 */
public class ListSubtotals {

    /**
     * @see SubtotalRepository
     */
    private SubtotalRepository subtotalRepository;

    /**
     * Constructor.
     *
     * @param subtotalRepository Subtotal repository.
     */
    public ListSubtotals(SubtotalRepository subtotalRepository) {
        this.subtotalRepository = subtotalRepository;
    }

    /**
     * List all subtotals.
     *
     * @return A list with all subtotals.
     */
    public ArrayList<Subtotal> execute() {
        return subtotalRepository.get();
    }

}
