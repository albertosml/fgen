package deliverynote.application.utils;

import customer.application.Customer;
import deliverynote.application.DeliveryNote;
import deliverynote.application.DeliveryNoteItem;
import java.util.ArrayList;
import product.application.Product;
import template.application.Template;

/**
 * Validate the delivery note.
 *
 * @see DeliveryNote
 */
public class DeliveryNoteValidator {

    /**
     * Check whether a delivery note is valid or not.
     *
     * @param deliveryNote The delivery note to validate.
     * @return The validation state for the delivery note.
     */
    public static DeliveryNoteValidationState isValid(DeliveryNote deliveryNote) {
        Customer customer = deliveryNote.getCustomer();
        if (customer == null) {
            return DeliveryNoteValidationState.INVALID_CUSTOMER;
        }

        Product product = deliveryNote.getProduct();
        if (product == null) {
            return DeliveryNoteValidationState.INVALID_PRODUCT;
        }

        Template template = deliveryNote.getTemplate();
        if (template == null) {
            return DeliveryNoteValidationState.INVALID_TEMPLATE;
        }

        double weight = deliveryNote.getWeight();
        if (!Double.isFinite(weight)) {
            return DeliveryNoteValidationState.INVALID_WEIGHT;
        }

        ArrayList<DeliveryNoteItem> items = deliveryNote.getItems();
        if (items == null) {
            return DeliveryNoteValidationState.INVALID_ITEMS;
        }

        return DeliveryNoteValidationState.VALID;
    }

}
