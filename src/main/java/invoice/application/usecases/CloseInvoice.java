package invoice.application.usecases;

import deliverynote.application.DeliveryNoteData;
import deliverynote.persistence.DeliveryNoteRepository;
import invoice.application.Invoice;
import invoice.persistence.InvoiceRepository;

/**
 * Create invoice use case.
 */
public class CloseInvoice {

    /**
     * @see DeliveryNoteRepository
     */
    private DeliveryNoteRepository deliveryNoteRepository;

    /**
     * @see InvoiceRepository
     */
    private InvoiceRepository invoiceRepository;

    /**
     * Constructor.
     *
     * @param invoiceRepository Invoice repository.
     * @param deliveryNoteRepository Delivery note repository.
     */
    public CloseInvoice(InvoiceRepository invoiceRepository, DeliveryNoteRepository deliveryNoteRepository) {
        this.invoiceRepository = invoiceRepository;
        this.deliveryNoteRepository = deliveryNoteRepository;
    }

    /**
     * Execute the invoice closing.
     *
     * @param invoice The invoice.
     * @return Whether the invoice has been closed or not.
     */
    public boolean execute(Invoice invoice) {
        // Close the delivery notes of the invoice.
        for (DeliveryNoteData deliveryNote : invoice.getDeliveryNotes()) {
            deliveryNote.setIsClosed(true);
            boolean isUpdated = deliveryNoteRepository.update(deliveryNote);
            if (!isUpdated) {
                return false;
            }
        }

        // Close invoice.
        invoice.setIsClosed(true);
        return invoiceRepository.update(invoice);
    }

}
