package deliverynote.persistence.mongo;

import com.mongodb.client.model.Filters;
import customer.application.Customer;
import deliverynote.application.DeliveryNote;
import deliverynote.application.DeliveryNoteData;
import deliverynote.application.DeliveryNoteDataAttribute;
import deliverynote.persistence.DeliveryNoteRepository;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.bson.conversions.Bson;
import product.application.Product;
import shared.persistence.Base64Converter;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;

/**
 * Interacts with the delivery note collection on the Mongo database.
 */
public class MongoDeliveryNoteRepository extends MongoRepository implements DeliveryNoteRepository {

    /**
     * Constructor.
     *
     * @throws NotDefinedDatabaseContextException Thrown when there is not a
     * connected database to the system.
     */
    public MongoDeliveryNoteRepository() throws NotDefinedDatabaseContextException {
        super("deliverynote");
    }

    /**
     * Obtain the filter for the delivery note code.
     *
     * @param code The delivery note code.
     * @return A filter indicating that the query must only obtain the delivery
     * note which contains the given delivery note code.
     */
    private Bson getDeliveryNoteCodeFilter(int code) {
        return Filters.eq("code", code);
    }

    /**
     * Obtain the filter for non-closed invoices.
     *
     * @return A filter indicating that the query must only obtain the delivery
     * notes which are not closed.
     */
    private Bson isNotClosedFilter() {
        return Filters.eq("isClosed", false);
    }

    /**
     * It creates a Mongo document from a delivery note and the PDF file.
     *
     * @param deliveryNote The delivery note entity.
     * @return A document with the given delivery note data if it has been
     * created, otherwise null.
     */
    private Document createDocumentFrom(DeliveryNote deliveryNote) {
        Document document = new Document();

        Map<String, String> fileAttributes = Base64Converter.encode(deliveryNote.getFile());
        if (fileAttributes == null) {
            return null;
        }

        document.append("code", deliveryNote.getCode());
        document.append("date", deliveryNote.getDate());
        document.append("farmer", deliveryNote.getFarmer().getCode());
        document.append("trader", deliveryNote.getTrader().getCode());
        document.append("product", deliveryNote.getProduct().getCode());
        document.append("file", fileAttributes);
        document.append("numBoxes", deliveryNote.calculateTotalBoxes());
        document.append("numPallets", deliveryNote.calculateTotalPallets());
        document.append("netWeight", deliveryNote.calculateNetWeight());
        document.append("isClosed", false);
        document.append("isDeleted", false);
        document.append("price", 0.0);

        return document;
    }

    /**
     * It creates a Mongo document from a delivery note data.
     *
     * @param deliveryNoteData The delivery note data entity.
     * @return A document with the given delivery note data if it has been
     * created, otherwise null.
     */
    private Document createDocumentFrom(DeliveryNoteData deliveryNoteData) {
        Document document = new Document();

        Map<String, String> fileAttributes = Base64Converter.encode(deliveryNoteData.getFile());
        if (fileAttributes == null) {
            return null;
        }

        document.append("code", deliveryNoteData.getCode());
        document.append("date", deliveryNoteData.getDate());
        document.append("farmer", deliveryNoteData.getFarmer().getCode());
        document.append("trader", deliveryNoteData.getTrader().getCode());
        document.append("product", deliveryNoteData.getProduct().getCode());
        document.append("file", fileAttributes);
        document.append("numBoxes", deliveryNoteData.getNumBoxes());
        document.append("numPallets", deliveryNoteData.getNumPallets());
        document.append("netWeight", deliveryNoteData.getNetWeight());
        document.append("isClosed", deliveryNoteData.isClosed());
        document.append("isDeleted", deliveryNoteData.isDeleted());
        document.append("price", deliveryNoteData.getPrice());

        return document;
    }

    /**
     * It creates a delivery note from a Mongo document.
     *
     * @param document A document obtained from the Mongo collection.
     * @return A delivery note object based on the data obtained from the given
     * document.
     */
    private DeliveryNoteData createDeliveryNoteFrom(Document document) {
        // Create a delivery note data entry.
        // Filter by date interval, customer, product
        Map<DeliveryNoteDataAttribute, Object> attributes = new HashMap<>();
        attributes.put(DeliveryNoteDataAttribute.CODE, document.get("code"));
        attributes.put(DeliveryNoteDataAttribute.DATE, document.get("date"));
        attributes.put(DeliveryNoteDataAttribute.FARMER, document.get("farmer"));
        attributes.put(DeliveryNoteDataAttribute.TRADER, document.get("trader"));
        attributes.put(DeliveryNoteDataAttribute.PRODUCT, document.get("product"));
        attributes.put(DeliveryNoteDataAttribute.FILE, document.get("file"));
        attributes.put(DeliveryNoteDataAttribute.NUM_BOXES, document.get("numBoxes"));
        attributes.put(DeliveryNoteDataAttribute.NUM_PALLETS, document.get("numPallets"));
        attributes.put(DeliveryNoteDataAttribute.NET_WEIGHT, document.get("netWeight"));
        attributes.put(DeliveryNoteDataAttribute.IS_CLOSED, document.get("isClosed"));
        attributes.put(DeliveryNoteDataAttribute.IS_DELETED, document.get("isDeleted"));

        double price = (double) document.get("price");
        attributes.put(DeliveryNoteDataAttribute.PRICE, (float) price);

        return DeliveryNoteData.from(attributes);
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
    public DeliveryNoteData find(int code) {
        Bson deliveryNoteCodeFilter = this.getDeliveryNoteCodeFilter(code);
        Bson isNotClosedFilter = this.isNotClosedFilter();
        Bson filters = Filters.and(deliveryNoteCodeFilter, isNotClosedFilter);

        ArrayList<Document> foundDeliveryNoteDocuments = super.find(filters);

        if (foundDeliveryNoteDocuments.isEmpty()) {
            return null;
        } else {
            Document foundDeliveryNoteDocument = foundDeliveryNoteDocuments.get(0);
            return this.createDeliveryNoteFrom(foundDeliveryNoteDocument);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<DeliveryNoteData> get(Customer farmer, Customer trader, Product product, Date from, Date to) {
        Bson isNotDeletedFilter = Filters.eq("isDeleted", false);
        Bson isNotClosedFilter = this.isNotClosedFilter();
        Bson fromDate = Filters.gte("date", from);
        Bson toDate = Filters.lte("date", to);

        Bson filters = Filters.and(isNotDeletedFilter, isNotClosedFilter, fromDate, toDate);

        if (farmer != null) {
            Bson farmerFilter = Filters.eq("farmer", farmer.getCode());
            filters = Filters.and(filters, farmerFilter);
        }

        if (trader != null) {
            Bson traderFilter = Filters.eq("trader", trader.getCode());
            filters = Filters.and(filters, traderFilter);
        }

        if (product != null) {
            Bson productFilter = Filters.eq("product", product.getCode());
            filters = Filters.and(filters, productFilter);
        }

        ArrayList<Document> foundDocuments = super.find(filters);

        ArrayList<DeliveryNoteData> deliveryNotes = new ArrayList<>();
        for (Document document : foundDocuments) {
            DeliveryNoteData deliveryNote = this.createDeliveryNoteFrom(document);
            deliveryNotes.add(deliveryNote);
        }

        return deliveryNotes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(DeliveryNote deliveryNote) {
        Document document = this.createDocumentFrom(deliveryNote);
        this.insertOne(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(DeliveryNoteData deliveryNoteData) {
        Bson deliveryNoteCodeFilter = this.getDeliveryNoteCodeFilter(deliveryNoteData.getCode());
        return super.replaceOne(deliveryNoteCodeFilter, this.createDocumentFrom(deliveryNoteData));
    }

}
