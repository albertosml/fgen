package invoice.application.usecases;

import customer.application.Customer;
import invoice.application.Invoice;
import invoice.persistence.InvoiceRepository;
import java.util.ArrayList;
import java.util.Date;

/**
 * List invoices use case.
 */
public class ListInvoices {

    /**
     * @see InvoiceRepository
     */
    private InvoiceRepository invoiceRepository;

    /**
     * Constructor.
     *
     * @param invoiceRepository Invoice repository.
     */
    public ListInvoices(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * List all invoices.
     *
     * @param farmer The farmer customer to get the invoices.
     * @param trader The trader customer to get the invoices.
     * @param start The start date to get the invoices.
     * @param end The end date to get the invoices.
     * @return A list with all invoices.
     */
    public ArrayList<Invoice> execute(Customer farmer, Customer trader, Date start, Date end) {
        return invoiceRepository.get(farmer, trader, start, end);
    }

}
