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
     * @param month The month to get the delivery notes.
     * @param year The year to get the delivery notes.
     * @return A list with all delivery notes.
     */
    public ArrayList<DeliveryNoteData> execute(Customer customer, Product product, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startMonthDate = calendar.getTime();

        Date startNextMonthDate = DateUtils.addMonths(startMonthDate, 1);

        return deliveryNoteRepository.get(customer, product, startMonthDate, startNextMonthDate);
    }

}
