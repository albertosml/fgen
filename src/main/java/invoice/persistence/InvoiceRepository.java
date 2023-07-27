package invoice.persistence;

import deliverynote.application.DeliveryNote;
import invoice.application.Invoice;
import java.io.File;
import shared.persistence.Repository;

/**
 * Represents the repository model for the invoice entity.
 */
public interface InvoiceRepository extends Repository {

    /**
     * Save the invoice.
     *
     * @param invoice The invoice.
     */
    public void save(Invoice invoice);

}
