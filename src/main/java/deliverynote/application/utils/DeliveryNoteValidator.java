package deliverynote.application.utils;

import container.application.Pallet;
import customer.application.Customer;
import deliverynote.application.DeliveryNote;
import java.util.ArrayList;
import product.application.Product;
import template.application.Template;
import weighing.application.Weighing;
import weighing.application.utils.WeighingValidator;

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
        Customer farmer = deliveryNote.getFarmer();
        if (farmer == null) {
            return DeliveryNoteValidationState.INVALID_FARMER;
        }

        Customer trader = deliveryNote.getTrader();
        if (trader == null) {
            return DeliveryNoteValidationState.INVALID_TRADER;
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

        for (Weighing weighing : weighings) {
            if (!WeighingValidator.isValid(weighing)) {
                return DeliveryNoteValidationState.INVALID_WEIGHINGS;
            }
        }

        return DeliveryNoteValidationState.VALID;
    }

}
