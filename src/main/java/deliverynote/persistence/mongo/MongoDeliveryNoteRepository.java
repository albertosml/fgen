package deliverynote.persistence.mongo;

import deliverynote.application.DeliveryNote;
import deliverynote.application.DeliveryNoteData;
import deliverynote.application.DeliveryNoteDataAttribute;
import deliverynote.persistence.DeliveryNoteRepository;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
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
     * It creates a Mongo document from a delivery note and the PDF file.
     *
     * @param deliveryNote The delivery note entity.
     * @param pdfFile A PDF file containing the delivery note data.
     * @return A document with the given delivery note data if it has been
     * created, otherwise null.
     */
    private Document createDocumentFrom(DeliveryNote deliveryNote, File pdfFile) {
        Document document = new Document();

        Map<String, String> fileAttributes = Base64Converter.encode(pdfFile);
        if (fileAttributes == null) {
            return null;
        }

        document.append("code", deliveryNote.getCode());
        document.append("date", deliveryNote.getDate());
        document.append("customer", deliveryNote.getCustomer().getCode());
        document.append("product", deliveryNote.getProduct().getCode());
        document.append("file", fileAttributes);
        document.append("numBoxes", deliveryNote.calculateTotalBoxes());
        document.append("numPallets", deliveryNote.calculateTotalPallets());
        document.append("netWeight", deliveryNote.calculateNetWeight());

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
        attributes.put(DeliveryNoteDataAttribute.CUSTOMER, document.get("customer"));
        attributes.put(DeliveryNoteDataAttribute.PRODUCT, document.get("product"));
        attributes.put(DeliveryNoteDataAttribute.FILE, document.get("file"));
        attributes.put(DeliveryNoteDataAttribute.NUM_BOXES, document.get("numBoxes"));
        attributes.put(DeliveryNoteDataAttribute.NUM_PALLETS, document.get("numPallets"));
        attributes.put(DeliveryNoteDataAttribute.NET_WEIGHT, document.get("netWeight"));

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
    public ArrayList<DeliveryNoteData> get() {
        ArrayList<Document> foundDocuments = super.find();

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
    public void save(DeliveryNote deliveryNote, File pdfFile) {
        Document document = this.createDocumentFrom(deliveryNote, pdfFile);
        this.insertOne(document);
    }

}
