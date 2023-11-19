package deliverynote.application.usecases;

import deliverynote.application.DeliveryNoteData;
import deliverynote.persistence.DeliveryNoteRepository;

/**
 * Update delivery note use case.
 */
public class UpdateDeliveryNote {

    /**
     * @see DeliveryNoteRepository
     */
    private DeliveryNoteRepository deliveryNoteRepository;

    /**
     * Constructor.
     *
     * @param deliveryNoteRepository Delivery note repository.
     */
    public UpdateDeliveryNote(DeliveryNoteRepository deliveryNoteRepository) {
        this.deliveryNoteRepository = deliveryNoteRepository;
    }

    /**
     * Execute the delivery note update.
     *
     * @param deliveryNoteData The delivery note data.
     */
    public void execute(DeliveryNoteData deliveryNoteData) {
        deliveryNoteRepository.update(deliveryNoteData);
    }

}
