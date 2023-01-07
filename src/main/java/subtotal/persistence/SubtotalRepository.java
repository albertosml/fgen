package subtotal.persistence;

import java.util.ArrayList;
import shared.persistence.Repository;
import subtotal.application.Subtotal;

/**
 * Represents the repository model for the subtotal entity.
 */
public interface SubtotalRepository extends Repository {

    /**
     * Register the given subtotal.
     *
     * @param subtotal The subtotal to register.
     */
    public void register(Subtotal subtotal);

    /**
     * Obtain all the subtotals registered on the system, even if they have been
     * removed after.
     *
     * @return A list with all subtotals.
     */
    public ArrayList<Subtotal> get();

}
