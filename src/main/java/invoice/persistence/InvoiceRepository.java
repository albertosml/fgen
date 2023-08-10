package invoice.persistence;

import customer.application.Customer;
import invoice.application.Invoice;
import java.util.ArrayList;
import java.util.Date;
import shared.persistence.Repository;

/**
 * Represents the repository model for the invoice entity.
 */
public interface InvoiceRepository extends Repository {

    /**
     * List all the invoices.
     *
     * Note that if the farmer or the supplier are null, they will not be
     * included on the filters.
     *
     * @param farmer The farmer customer to get the invoices.
     * @param supplier The supplier customer to get the invoices.
     * @param from The start date to get the invoices.
     * @param to The end date to get the invoices.
     * @return A list with all invoices.
     */
    public ArrayList<Invoice> get(Customer farmer, Customer supplier, Date from, Date to);

    /**
     * Save the invoice.
     *
     * @param invoice The invoice.
     */
    public void save(Invoice invoice);

    /**
     * Update the given invoice with its associated data.
     *
     * @param invoice The invoice.
     * @return Whether the invoice has been updated or not.
     */
    public boolean update(Invoice invoice);

}
