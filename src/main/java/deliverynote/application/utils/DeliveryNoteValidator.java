package deliverynote.application.utils;

import container.application.Pallet;
import customer.application.Customer;
import deliverynote.application.DeliveryNote;
import java.util.ArrayList;
import product.application.Product;
import template.application.Template;
import weighing.application.Weighing;

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

        Pallet pallet = deliveryNote.getPallet();
        if (pallet == null) {
            return DeliveryNoteValidationState.INVALID_PALLET;
        }

        int numPallets = deliveryNote.getNumPallets();
        if (numPallets == 0) {
            return DeliveryNoteValidationState.INVALID_NUM_PALLETS;
        }

        ArrayList<Weighing> weighings = deliveryNote.getWeighings();
        if (weighings == null) {
            return DeliveryNoteValidationState.INVALID_WEIGHINGS;
        }

        return DeliveryNoteValidationState.VALID;
    }

}
