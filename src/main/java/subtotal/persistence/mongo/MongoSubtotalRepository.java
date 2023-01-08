package subtotal.persistence.mongo;

import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;
import subtotal.application.Subtotal;
import subtotal.application.SubtotalAttribute;
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
     * It creates a subtotal a Mongo document.
     *
     * @param document A document obtained from the Mongo collection.
     * @return A subtotal object based on the data obtained from the given
     * document.
     */
    private Subtotal createSubtotalFrom(Document document) {
        Map<SubtotalAttribute, Object> attributes = new HashMap<>();
        attributes.put(SubtotalAttribute.CODE, document.get("code"));
        attributes.put(SubtotalAttribute.NAME, document.get("name"));
        attributes.put(SubtotalAttribute.PERCENTAGE, document.get("percentage"));
        attributes.put(SubtotalAttribute.ISDISCOUNT, document.get("isDiscount"));
        attributes.put(SubtotalAttribute.ISDELETED, document.get("isDeleted"));

        return Subtotal.from(attributes);
    }

    /**
     * Obtain the filter for the subtotal code.
     *
     * @param code The subtotal code.
     * @return A filter indicating that the query must only obtain the subtotal
     * which contains the given subtotal code.
     */
    private Bson getSubtotalCodeFilter(int code) {
        return Filters.eq("code", code);
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
    public ArrayList<Subtotal> get() {
        ArrayList<Document> foundDocuments = super.find();

        ArrayList<Subtotal> subtotals = new ArrayList<>();
        for (Document document : foundDocuments) {
            subtotals.add(this.createSubtotalFrom(document));
        }

        return subtotals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(Subtotal subtotal) {
        Document document = this.createDocumentFrom(subtotal);
        super.insertOne(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Subtotal subtotal) {
        Bson subtotalCodeFilter = this.getSubtotalCodeFilter(subtotal.getCode());
        return super.replaceOne(subtotalCodeFilter, this.createDocumentFrom(subtotal));
    }

}
