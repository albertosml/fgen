package deliverynote.application.usecases;

import container.application.Container;
import deliverynote.application.DeliveryNoteItem;
import deliverynote.application.DeliveryNoteItemAttribute;
import java.util.Map;
import shared.application.utils.QtyValidator;

/**
 * Create delivery note item use case.
 */
public class CreateDeliveryNoteItem {

    /**
     * Constructor.
     */
    public CreateDeliveryNoteItem() {
    }

    /**
     * Create a delivery note item based on the given attributes.
     *
     * @param attributes The delivery note item attributes.
     * @return The created delivery note item if the attributes are valid,
     * otherwise null.
     */
    public DeliveryNoteItem execute(Map<DeliveryNoteItemAttribute, Object> attributes) {
        int qty = (int) attributes.get(DeliveryNoteItemAttribute.QTY);

        boolean isQtyValid = QtyValidator.isValid(qty);

        if (!isQtyValid) {
            return null;
        }

        Container container = (Container) attributes.get(DeliveryNoteItemAttribute.CONTAINER);

        if (container == null) {
            return null;
        }

        return DeliveryNoteItem.from(attributes);
    }
}
