package subtotal.persistence;

import subtotal.application.Subtotal;
import shared.persistence.Repository;

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

}
