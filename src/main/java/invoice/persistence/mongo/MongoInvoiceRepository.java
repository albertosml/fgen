package invoice.persistence.mongo;

import deliverynote.application.DeliveryNoteData;
import invoice.application.Invoice;
import invoice.persistence.InvoiceRepository;
import java.util.ArrayList;
import java.util.Map;
import org.bson.Document;
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
     * It creates a Mongo document from an invoice.
     *
     * @param invoice The invoice.
     * @return A document with the given invoice data if it has been created,
     * otherwise null.
     */
    private Document createDocumentFrom(Invoice invoice) {
        Document document = new Document();

        Map<String, String> farmerInvoiceFileAttributes = Base64Converter.encode(invoice.getFarmerInvoice());
        if (farmerInvoiceFileAttributes == null) {
            return null;
        }

        Map<String, String> supplierInvoiceFileAttributes = Base64Converter.encode(invoice.getSupplierInvoice());
        if (supplierInvoiceFileAttributes == null) {
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
        document.append("farmer", invoice.getFarmer().getCode());
        document.append("farmerInvoice", farmerInvoiceFileAttributes);
        document.append("supplier", invoice.getSupplier().getCode());
        document.append("supplierInvoice", supplierInvoiceFileAttributes);
        document.append("isDeleted", invoice.isDeleted());

        return document;
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
    public void save(Invoice invoice) {
        Document document = this.createDocumentFrom(invoice);
        this.insertOne(document);
    }

}
