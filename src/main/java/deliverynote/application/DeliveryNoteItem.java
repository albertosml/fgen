package deliverynote.application;

import container.application.Container;
import java.util.Map;

/**
 * Delivery note item element.
 */
public class DeliveryNoteItem {

    /**
     * The container.
     */
    private Container container;

    /**
     * The quantity.
     */
    private int qty;

    /**
     * Constructor.
     *
     * @param container The delivery note item container.
     * @param qty The delivery note item quantity.
     */
    private DeliveryNoteItem(Container container, int qty) {
        this.container = container;
        this.qty = qty;
    }

    /**
     * Retrieve the delivery note item container.
     *
     * @return The delivery note item container.
     */
    public Container getContainer() {
        return this.container;
    }

    /**
     * Retrieve the delivery note item quantity.
     *
     * @return The delivery note item quantity.
     */
    public int getQty() {
        return this.qty;
    }

    /**
     * Create a delivery note item given its attribute values.
     *
     * @param attributes Map containing the value for each delivery note item
     * attribute.
     * @return The created delivery note item.
     */
    public static DeliveryNoteItem from(Map<DeliveryNoteItemAttribute, Object> attributes) {
        Container container = (Container) attributes.get(DeliveryNoteItemAttribute.CONTAINER);
        int qty = (int) attributes.getOrDefault(DeliveryNoteItemAttribute.QTY, 0);

        return new DeliveryNoteItem(container, qty);
    }
}
