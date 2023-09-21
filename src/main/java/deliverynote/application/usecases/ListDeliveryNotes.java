package deliverynote.application.usecases;

import customer.application.Customer;
import deliverynote.application.DeliveryNoteData;
import deliverynote.persistence.DeliveryNoteRepository;
import java.util.ArrayList;
import java.util.Date;
import product.application.Product;

/**
 * List delivery notes use case.
 */
public class ListDeliveryNotes {

    /**
     * @see DeliveryNoteRepository
     */
    private DeliveryNoteRepository deliveryNoteRepository;

    /**
     * Constructor.
     *
     * @param deliveryNoteRepository Delivery note repository.
     */
    public ListDeliveryNotes(DeliveryNoteRepository deliveryNoteRepository) {
        this.deliveryNoteRepository = deliveryNoteRepository;
    }

    /**
     * List all delivery notes.
     *
     * @param farmer The farmer customer to get the delivery notes.
     * @param trader The trader customer to get the delivery notes.
     * @param product The product to get the delivery notes.
     * @param start The start date to get the delivery notes.
     * @param end The end date to get the delivery notes.
     * @return A list with all delivery notes.
     */
    public ArrayList<DeliveryNoteData> execute(Customer farmer, Customer trader, Product product, Date start, Date end) {
        return deliveryNoteRepository.get(farmer, trader, product, start, end);
    }

}
