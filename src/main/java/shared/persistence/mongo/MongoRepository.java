package shared.persistence.mongo;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;

/**
 * Represents the Mongo Repository for any entity.
 */
public abstract class MongoRepository {

    /**
     * Mongo collection associated with the repository.
     */
    private final MongoCollection<Document> collection;

    /**
     * Constructor.
     *
     * @param collectionName The collection name which the repository will
     * check.
     * @throws NotDefinedDatabaseContextException Thrown when there is not a
     * connected database to the system.
     */
    protected MongoRepository(String collectionName) throws NotDefinedDatabaseContextException {
        MongoDatabase database = MongoDatabaseConnection.getInstance();
        this.collection = database.getCollection(collectionName);
    }

    /**
     * Count all the documents on the collection.
     *
     * @return The number of documents on the collection.
     */
    protected int count() {
        return (int) this.collection.countDocuments();
    }

    /**
     * Create a list with the different values that the given field has based on
     * the received filters.
     *
     * @param <TResult> The results type.
     * @param field The field name.
     * @param filters The filters.
     * @param type The class of the field type.
     * @return A list with the different field values.
     */
    protected <TResult extends Object> ArrayList<TResult> distinct(String field, Bson filters, Class<TResult> type) {
        DistinctIterable<TResult> distinctIterator = collection.distinct(field, filters, type);
        return distinctIterator.into(new ArrayList<>());
    }

    /**
     * Find all the documents on the collection.
     *
     * @return A list with all found documents on the collection.
     */
    protected ArrayList<Document> find() {
        FindIterable<Document> findIterator = collection.find();
        return findIterator.into(new ArrayList<>());
    }

    /**
     * Insert the given document to the associated Mongo collection.
     *
     * @param document The document to insert.
     */
    protected void insertOne(Document document) {
        collection.insertOne(document);
    }

}
