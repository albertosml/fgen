package deliverynote.application.usecases;

import deliverynote.application.DeliveryNoteData;
import deliverynote.persistence.DeliveryNoteRepository;
import java.util.ArrayList;

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
     * @return A list with all delivery notes.
     */
    public ArrayList<DeliveryNoteData> execute() {
        return deliveryNoteRepository.get();
    }

}
