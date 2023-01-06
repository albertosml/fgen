package subtotal.persistence.mongo;

import org.bson.Document;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;
import subtotal.application.Subtotal;
import subtotal.persistence.SubtotalRepository;

/**
 * Interacts with the subtotal collection on the Mongo database.
 */
public class MongoSubtotalRepository extends MongoRepository implements SubtotalRepository {

    /**
     * Constructor.
     *
     * @throws NotDefinedDatabaseContextException Thrown when there is not a
     * connected database to the system.
     */
    public MongoSubtotalRepository() throws NotDefinedDatabaseContextException {
        super("subtotal");
    }

    /**
     * It creates a Mongo document from a subtotal.
     *
     * @param subtotal The subtotal to get the data.
     * @return A document with the given subtotal data.
     */
    private Document createDocumentFrom(Subtotal subtotal) {
        Document document = new Document();

        document.append("code", subtotal.getCode());
        document.append("name", subtotal.getName());
        document.append("percentage", subtotal.getPercentage());
        document.append("isDiscount", subtotal.isDiscount());
        document.append("isDeleted", subtotal.isDeleted());

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
    public void register(Subtotal subtotal) {
        Document document = this.createDocumentFrom(subtotal);
        super.insertOne(document);
    }

}
