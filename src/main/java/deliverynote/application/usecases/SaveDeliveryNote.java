package deliverynote.application.usecases;

import deliverynote.application.DeliveryNote;
import deliverynote.persistence.DeliveryNoteRepository;
import java.io.File;

/**
 * Save delivery note use case.
 */
public class SaveDeliveryNote {

    /**
     * @see DeliveryNoteRepository
     */
    private DeliveryNoteRepository deliveryNoteRepository;

    /**
     * Constructor.
     *
     * @param deliveryNoteRepository Delivery note repository.
     */
    public SaveDeliveryNote(DeliveryNoteRepository deliveryNoteRepository) {
        this.deliveryNoteRepository = deliveryNoteRepository;
    }

    /**
     * Execute the delivery note save.
     *
     * @param deliveryNote The delivery note entity.
     * @param pdfFile A PDF file containing the delivery note data.
     */
    public void execute(DeliveryNote deliveryNote, File pdfFile) {
        deliveryNoteRepository.save(deliveryNote, pdfFile);
    }
}
