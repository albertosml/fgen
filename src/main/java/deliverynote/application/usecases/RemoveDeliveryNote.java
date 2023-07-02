package deliverynote.application.usecases;

import deliverynote.application.DeliveryNoteData;
import deliverynote.persistence.DeliveryNoteRepository;

/**
 * Remove delivery note use case.
 */
public class RemoveDeliveryNote {

    /**
     * @see DeliveryNoteRepository
     */
    private DeliveryNoteRepository deliveryNoteRepository;

    /**
     * Constructor.
     *
     * @param deliveryNoteRepository Delivery note repository.
     */
    public RemoveDeliveryNote(DeliveryNoteRepository deliveryNoteRepository) {
        this.deliveryNoteRepository = deliveryNoteRepository;
    }

    /**
     * Execute the delivery note removal.
     *
     * @param deliveryNoteData The delivery note data.
     * @return Whether the delivery note has been removed or not.
     */
    public boolean execute(DeliveryNoteData deliveryNoteData) {
        deliveryNoteData.setIsDeleted(true);
        return deliveryNoteRepository.update(deliveryNoteData);
    }

}
