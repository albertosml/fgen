package invoice.persistence.mongo;

import com.mongodb.client.model.Filters;
import customer.application.Customer;
import customer.application.usecases.ShowCustomer;
import customer.persistence.mongo.MongoCustomerRepository;
import deliverynote.application.DeliveryNoteData;
import deliverynote.application.usecases.FindDeliveryNote;
import deliverynote.persistence.mongo.MongoDeliveryNoteRepository;
import invoice.application.Invoice;
import invoice.application.InvoiceAttribute;
import invoice.persistence.InvoiceRepository;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.persistence.Base64Converter;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;

/**
 * Interacts with the invoice collection on the Mongo database.
 */
public class MongoInvoiceRepository extends MongoRepository implements InvoiceRepository {

    /**
     * Constructor.
     *
     * @throws NotDefinedDatabaseContextException Thrown when there is not a
     * connected database to the system.
     */
    public MongoInvoiceRepository() throws NotDefinedDatabaseContextException {
        super("invoice");
    }

    /**
     * Obtain the filter for the invoice code.
     *
     * @param code The invoice code.
     * @return A filter indicating that the query must only obtain the invoice
     * which contains the given invoice code.
     */
    private Bson getInvoiceCodeFilter(int code) {
        return Filters.eq("code", code);
    }

    /**
     * Obtain the filter for non-closed invoices.
     *
     * @return A filter indicating that the query must only obtain the invoices
     * which are not closed.
     */
    private Bson isNotClosedFilter() {
        return Filters.eq("isClosed", false);
    }

    /**
     * It creates a Mongo document from an invoice.
     *
     * @param invoice The invoice.
     * @return A document with the given invoice data if it has been created,
     * otherwise null.
     */
    private Document createDocumentFrom(Invoice invoice) {
        Document document = new Document();

        Customer customer = invoice.getCustomer();
        if (customer == null) {
            return null;
        }

        Map<String, String> invoiceFileAttributes = Base64Converter.encode(invoice.getFile());
        if (invoiceFileAttributes == null) {
            return null;
        }

        ArrayList<Integer> deliveryNoteCodes = new ArrayList<>();
        for (DeliveryNoteData deliveryNote : invoice.getDeliveryNotes()) {
            deliveryNoteCodes.add(deliveryNote.getCode());
        }

        document.append("code", invoice.getCode());
        document.append("date", invoice.getDate());
        document.append("deliveryNotes", deliveryNoteCodes);
        document.append("startPeriod", invoice.getStartPeriod());
        document.append("endPeriod", invoice.getEndPeriod());
        document.append("customer", customer.getCode());
        document.append("file", invoiceFileAttributes);
        document.append("totalAmount", invoice.getTotal());
        document.append("totalWeight", invoice.getTotalWeight());
        document.append("isClosed", invoice.isClosed());
        document.append("isDeleted", invoice.isDeleted());

        return document;
    }

    /**
     * It creates an invoice from a Mongo document.
     *
     * @param document A document obtained from the Mongo collection.
     * @return An invoice object based on the data obtained from the given
     * document.
     */
    private Invoice createInvoiceFrom(Document document) {
        ArrayList<Integer> deliveryNoteCodes = (ArrayList<Integer>) document.get("deliveryNotes");
        ArrayList<DeliveryNoteData> deliveryNotes = new ArrayList<>();
        for (int deliveryNoteCode : deliveryNoteCodes) {
            DeliveryNoteData deliveryNote = this.findDeliveryNote(deliveryNoteCode);
            if (deliveryNote != null) {
                deliveryNotes.add(deliveryNote);
            }
        }

        Customer customer = this.findCustomer((int) document.get("customer"));

        Map<String, String> fileAttributes = (Map<String, String>) document.get("file");
        File file = Base64Converter.decode(fileAttributes);

        // Create an invoice entry.
        Map<InvoiceAttribute, Object> attributes = new HashMap<>();
        attributes.put(InvoiceAttribute.CODE, document.get("code"));
        attributes.put(InvoiceAttribute.DATE, document.get("date"));
        attributes.put(InvoiceAttribute.DELIVERY_NOTES, deliveryNotes);
        attributes.put(InvoiceAttribute.START_PERIOD, document.get("startPeriod"));
        attributes.put(InvoiceAttribute.END_PERIOD, document.get("endPeriod"));
        attributes.put(InvoiceAttribute.CUSTOMER, customer);
        attributes.put(InvoiceAttribute.FILE, file);
        attributes.put(InvoiceAttribute.TOTAL_AMOUNT, document.get("totalAmount"));
        attributes.put(InvoiceAttribute.TOTAL_WEIGHT, document.get("totalWeight"));
        attributes.put(InvoiceAttribute.IS_CLOSED, document.get("isClosed"));
        attributes.put(InvoiceAttribute.IS_DELETED, document.get("isDeleted"));

        return Invoice.from(attributes);
    }

    /**
     * Find delivery note associated with the given code.
     *
     * @param code The code of the delivery note to find.
     * @return The found delivery note, otherwise null.
     */
    private DeliveryNoteData findDeliveryNote(int code) {
        try {
            MongoDeliveryNoteRepository deliveryNoteRepository = new MongoDeliveryNoteRepository();
            FindDeliveryNote findDeliveryNote = new FindDeliveryNote(deliveryNoteRepository);
            return findDeliveryNote.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            Logger.getLogger(MongoDeliveryNoteRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Find customer associated with the given code.
     *
     * @param code The code of the customer to find.
     * @return The found customer, otherwise null.
     */
    private Customer findCustomer(int code) {
        try {
            MongoCustomerRepository customerRepository = new MongoCustomerRepository();
            ShowCustomer showCustomer = new ShowCustomer(customerRepository);
            return showCustomer.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            Logger.getLogger(MongoInvoiceRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int count() {
        return super.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Invoice> get(Customer farmer, Customer supplier, Date from, Date to) {
        Bson isNotDeletedFilter = Filters.eq("isDeleted", false);
        Bson isNotClosedFilter = this.isNotClosedFilter();
        Bson fromDate = Filters.gte("date", from);
        Bson toDate = Filters.lte("date", to);

        Bson filters = Filters.and(isNotDeletedFilter, isNotClosedFilter, fromDate, toDate);

        if (farmer != null) {
            Bson farmerFilter = Filters.eq("customer", farmer.getCode());
            filters = Filters.and(filters, farmerFilter);
        }

        if (supplier != null) {
            Bson supplierFilter = Filters.eq("customer", supplier.getCode());
            filters = Filters.and(filters, supplierFilter);
        }

        ArrayList<Document> foundDocuments = super.find(filters);

        ArrayList<Invoice> invoices = new ArrayList<>();
        for (Document document : foundDocuments) {
            Invoice invoice = this.createInvoiceFrom(document);
            invoices.add(invoice);
        }

        return invoices;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Invoice invoice) {
        Document document = this.createDocumentFrom(invoice);
        this.insertOne(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Invoice invoice) {
        Bson invoiceCodeFilter = this.getInvoiceCodeFilter(invoice.getCode());
        return super.replaceOne(invoiceCodeFilter, this.createDocumentFrom(invoice));
    }
}
