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
     * @param customer The customer to get the delivery notes.
     * @param product The product to get the delivery notes.
     * @param month The month to get the delivery notes.
     * @param year The year to get the delivery notes.
     * @return A list with all delivery notes.
     */
    public ArrayList<DeliveryNoteData> execute(Customer customer, Product product, int month, int year) {
        //Date startDate = new Date();

        //return deliveryNoteRepository.get(customer, product, startDate, endDate);
        return new ArrayList<>();
    }

}
