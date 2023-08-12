package invoice.application.usecases;

import invoice.application.Invoice;
import invoice.persistence.InvoiceRepository;

/**
 * Remove invoice use case.
 */
public class RemoveInvoice {

    /**
     * @see InvoiceRepository
     */
    private InvoiceRepository invoiceRepository;

    /**
     * Constructor.
     *
     * @param invoiceRepository Invoice repository.
     */
    public RemoveInvoice(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Execute the invoice removal.
     *
     * @param invoice The invoice.
     * @return Whether the invoice has been removed or not.
     */
    public boolean execute(Invoice invoice) {
        invoice.setIsDeleted(true);
        return invoiceRepository.update(invoice);
    }

}
