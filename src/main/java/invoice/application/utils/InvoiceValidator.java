package invoice.application.utils;

import customer.application.Customer;
import deliverynote.application.DeliveryNoteData;
import invoice.application.Invoice;
import java.util.ArrayList;
import java.util.Date;

/**
 * Validate the invoice.
 *
 * @see Invoice
 */
public class InvoiceValidator {

    /**
     * Check whether an invoice is valid or not.
     *
     * @param invoice The invoice to validate.
     * @return Whether the invoice is valid or not.
     */
    public static InvoiceValidationState isValid(Invoice invoice) {
        Customer customer = invoice.getCustomer();
        if (customer == null) {
            return InvoiceValidationState.INVALID_CUSTOMER;
        }

        Date startPeriod = invoice.getStartPeriod();
        if (startPeriod == null) {
            return InvoiceValidationState.INVALID_START_PERIOD;
        }

        Date endPeriod = invoice.getEndPeriod();
        if (endPeriod == null) {
            return InvoiceValidationState.INVALID_END_PERIOD;
        }

        ArrayList<DeliveryNoteData> deliveryNotes = invoice.getDeliveryNotes();
        if (deliveryNotes == null || deliveryNotes.isEmpty()) {
            return InvoiceValidationState.INVALID_DELIVERY_NOTES;
        }

        return InvoiceValidationState.VALID;
    }

}
