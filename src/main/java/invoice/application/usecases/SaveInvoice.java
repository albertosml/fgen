package invoice.application.usecases;

import invoice.application.Invoice;
import invoice.persistence.InvoiceRepository;
import java.io.File;

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
     * @param farmerInvoice A PDF file containing the farmer invoice file.
     * @param supplierInvoice A PDF file containing the supplier invoice file.
     */
    public void execute(Invoice invoice, File farmerInvoice, File supplierInvoice) {
        // Add files to invoice.
        invoice.setFarmerFile(farmerInvoice);
        invoice.setSupplierFile(supplierInvoice);

        invoiceRepository.save(invoice);
    }
}
