package deliverynote.persistence;

import deliverynote.application.DeliveryNote;
import deliverynote.application.DeliveryNoteData;
import java.io.File;
import java.util.ArrayList;
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
     * @return A list with all delivery notes.
     */
    public ArrayList<DeliveryNoteData> get();

}
