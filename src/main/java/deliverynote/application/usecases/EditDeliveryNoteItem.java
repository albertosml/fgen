package deliverynote.application.usecases;

import container.application.Container;
import deliverynote.application.DeliveryNoteItemAttribute;
import java.util.Map;
import shared.application.utils.QtyValidator;

/**
 * Edit delivery note item use case.
 */
public class EditDeliveryNoteItem {

    /**
     * Constructor.
     */
    public EditDeliveryNoteItem() {
    }

    /**
     * Edit a delivery note item based on the given attributes.
     *
     * @param attributes The delivery note item attributes.
     * @return Whether the delivery note item can be edited or not.
     */
    public boolean execute(Map<DeliveryNoteItemAttribute, Object> attributes) {
        int qty = (int) attributes.get(DeliveryNoteItemAttribute.QTY);

        boolean isQtyValid = QtyValidator.isValid(qty);

        if (!isQtyValid) {
            return false;
        }

        Container container = (Container) attributes.get(DeliveryNoteItemAttribute.CONTAINER);
        return container != null;
    }
}
