package subtotal.application.usecases;

import subtotal.application.Subtotal;
import subtotal.persistence.SubtotalRepository;

/**
 * Find subtotal use case.
 */
public class FindSubtotal {

    /**
     * @see SubtotalRepository
     */
    private SubtotalRepository subtotalRepository;

    /**
     * Constructor.
     *
     * @param subtotalRepository Subtotal repository.
     */
    public FindSubtotal(SubtotalRepository subtotalRepository) {
        this.subtotalRepository = subtotalRepository;
    }

    /**
     * Find the subtotal which contains the given code.
     *
     * @param code The code of the subtotal to find.
     * @return The found subtotal, otherwise null.
     */
    public Subtotal execute(int code) {
        return subtotalRepository.find(code);
    }

}
