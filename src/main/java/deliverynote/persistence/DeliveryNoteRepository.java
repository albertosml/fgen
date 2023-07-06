package deliverynote.persistence;

import customer.application.Customer;
import deliverynote.application.DeliveryNote;
import deliverynote.application.DeliveryNoteData;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import product.application.Product;
import shared.persistence.Repository;

/**
 * Represents the repository model for the delivery note entity.
 */
public interface DeliveryNoteRepository extends Repository {

    /**
     * Save the delivery note data.
     *
     * @param deliveryNote The delivery note entity.
     * @param pdfFile A PDF file containing the delivery note data.
     */
    public void save(DeliveryNote deliveryNote, File pdfFile);

    /**
     * List all the delivery notes.
     *
     * Note that if the farmer, the supplier or the product are null, they will
     * not be included on the filters.
     *
     * @param farmer The farmer customer to get the delivery notes.
     * @param supplier The supplier customer to get the delivery notes.
     * @param product The product to get the delivery notes.
     * @param from The start date to get the delivery notes.
     * @param to The end date to get the delivery notes.
     * @return A list with all delivery notes.
     */
    public ArrayList<DeliveryNoteData> get(Customer farmer, Customer supplier, Product product, Date from, Date to);

    /**
     * Update the given delivery note with its associated data.
     *
     * @param deliveryNoteData The delivery note data.
     * @return Whether the delivery note has been updated or not.
     */
    public boolean update(DeliveryNoteData deliveryNoteData);

}
