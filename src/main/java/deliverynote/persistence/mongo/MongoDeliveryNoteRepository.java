package deliverynote.persistence.mongo;

import deliverynote.persistence.DeliveryNoteRepository;
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
     * {@inheritDoc}
     */
    @Override
    public int count() {
        return super.count();
    }

}
