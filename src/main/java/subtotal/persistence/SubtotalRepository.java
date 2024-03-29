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

    /**
     * Update the given subtotal with its associated data.
     *
     * @param subtotal The subtotal to update.
     * @return Whether the subtotal has been updated or not.
     */
    public boolean update(Subtotal subtotal);

    /**
     * Find the subtotal which matches with the given code.
     *
     * @param code The code of the subtotal to find.
     * @return The found subtotal, otherwise null.
     */
    public Subtotal find(int code);

}
