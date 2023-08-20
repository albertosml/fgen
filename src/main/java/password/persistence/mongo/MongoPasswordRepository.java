package password.persistence.mongo;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import password.application.Password;
import password.persistence.PasswordRepository;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;

/**
 * Interacts with the password collection on the Mongo database.
 */
public class MongoPasswordRepository extends MongoRepository implements PasswordRepository {

    /**
     * Constructor.
     *
     * @throws NotDefinedDatabaseContextException Thrown when there is not a
     * connected database to the system.
     */
    public MongoPasswordRepository() throws NotDefinedDatabaseContextException {
        super("password");
    }


    /**
     * It creates a Mongo document from a password entity.
     *
     * @param password The password entity.
     * @return A document with the given password data if it has been created,
     * otherwise null.
     */
    private Document createDocumentFrom(Password password) {
        Document document = new Document();
        document.append("username", password.getUsername());
        document.append("password", password.getPassword());

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
    public boolean set(Password password) {
        Bson usernameFilter = Filters.eq("username", password.getUsername());
        return super.upsertOne(usernameFilter, this.createDocumentFrom(password));
    }

}
