package invoice.application.usecases;

import invoice.application.Invoice;
import invoice.persistence.InvoiceRepository;

/**
 * Save invoice use case.
 */
public class SaveInvoice {

    /**
     * @see InvoiceRepository
     */
    private InvoiceRepository invoiceRepository;

    /**
     * Constructor.
     *
     * @param invoiceRepository Invoice repository.
     */
    public SaveInvoice(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Execute the invoice save.
     *
     * @param invoice The invoice entity.
     */
    public void execute(Invoice invoice) {
        invoiceRepository.save(invoice);
    }
}
