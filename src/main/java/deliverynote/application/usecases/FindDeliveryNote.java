package deliverynote.application.usecases;

import deliverynote.application.DeliveryNoteData;
import deliverynote.persistence.DeliveryNoteRepository;

/**
 * Find delivery note use case.
 */
public class FindDeliveryNote {

    /**
     * @see DeliveryNoteRepository
     */
    private DeliveryNoteRepository deliveryNoteRepository;

    /**
     * Constructor.
     *
     * @param deliveryNoteRepository Delivery note repository.
     */
    public FindDeliveryNote(DeliveryNoteRepository deliveryNoteRepository) {
        this.deliveryNoteRepository = deliveryNoteRepository;
    }

    /**
     * Find the delivery note which contains the given code.
     *
     * @param code The code of the delivery note to find.
     * @return The found delivery note, otherwise null.
     */
    public DeliveryNoteData execute(int code) {
        return deliveryNoteRepository.find(code);
    }

}
