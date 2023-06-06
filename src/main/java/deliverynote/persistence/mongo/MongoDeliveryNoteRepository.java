package deliverynote.persistence.mongo;

import deliverynote.application.DeliveryNote;
import deliverynote.persistence.DeliveryNoteRepository;
import java.io.File;
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
        document.append("product", deliveryNote.getProduct().getName());
        document.append("file", fileAttributes);
        document.append("numBoxes", deliveryNote.calculateTotalBoxes());
        document.append("numPallets", deliveryNote.calculateTotalPallets());
        document.append("netWeight", deliveryNote.calculateNetWeight());

        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int count() {
        return super.count();
    }

    @Override
    public void save(DeliveryNote deliveryNote, File pdfFile) {
        Document document = this.createDocumentFrom(deliveryNote, pdfFile);
        this.insertOne(document);
    }

}
