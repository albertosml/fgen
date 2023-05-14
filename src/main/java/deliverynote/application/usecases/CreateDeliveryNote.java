package deliverynote.application.usecases;

import deliverynote.application.DeliveryNote;
import deliverynote.application.DeliveryNoteAttribute;
import deliverynote.application.utils.DeliveryNoteValidationState;
import deliverynote.application.utils.DeliveryNoteValidator;
import deliverynote.persistence.DeliveryNoteRepository;
import java.util.Map;
import shared.application.Pair;
import shared.application.utils.CodeAutoGenerator;

/**
 * Create delivery note use case.
 */
public class CreateDeliveryNote {

    /**
     * @see DeliveryNoteRepository
     */
    private DeliveryNoteRepository deliveryNoteRepository;

    /**
     * Constructor.
     *
     * @param deliveryNoteRepository Delivery note repository.
     */
    public CreateDeliveryNote(DeliveryNoteRepository deliveryNoteRepository) {
        this.deliveryNoteRepository = deliveryNoteRepository;
    }

    /**
     * Execute the delivery note object creation.
     *
     * Note that the delivery note code is automatically generated if it has not
     * been introduced manually.
     *
     * @param newDeliveryNoteAttributes The attributes for the delivery note to
     * create.
     * @return A pair indicating the delivery note and its validation state.
     */
    public Pair<DeliveryNote, DeliveryNoteValidationState> execute(Map<DeliveryNoteAttribute, Object> newDeliveryNoteAttributes) {
        boolean isCodeManuallyAdded = newDeliveryNoteAttributes.containsKey(DeliveryNoteAttribute.CODE);
        if (!isCodeManuallyAdded) {
            int generatedDeliveryNoteCode = CodeAutoGenerator.generate(deliveryNoteRepository);
            newDeliveryNoteAttributes.put(DeliveryNoteAttribute.CODE, generatedDeliveryNoteCode);
        }

        DeliveryNote deliveryNote = DeliveryNote.from(newDeliveryNoteAttributes);

        DeliveryNoteValidationState state = DeliveryNoteValidator.isValid(deliveryNote);

        return new Pair<>(deliveryNote, state);
    }

}
