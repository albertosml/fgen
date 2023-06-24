package deliverynote.application.usecases;

import customer.application.Customer;
import deliverynote.application.DeliveryNoteData;
import deliverynote.persistence.DeliveryNoteRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
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
     * @param customer The customer to get the delivery notes.
     * @param product The product to get the delivery notes.
     * @param start The start date to get the delivery notes.
     * @param end The end date to get the delivery notes.
     * @return A list with all delivery notes.
     */
    public ArrayList<DeliveryNoteData> execute(Customer customer, Product product, Date start, Date end) {
        return deliveryNoteRepository.get(customer, product, start, end);
    }

}
