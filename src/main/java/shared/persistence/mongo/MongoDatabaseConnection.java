package shared.dataaccess.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import shared.dataaccess.exceptions.NotDefinedDatabaseContextException;

/**
 * Manages the connection to the Mongo database.
 */
public class MongoDatabaseConnection {

    /**
     * Mongo database instance.
     */
    public static MongoDatabase INSTANCE = null;

    /**
     * Set the Mongo database instance based on the given data.
     *
     * @param dbUsername Database user name.
     * @param dbPassword Database user password.
     * @param dbHost Database host.
     * @param dbName Database name.
     */
    public static void setInstance(String dbUsername, String dbPassword, String dbHost, String dbName) {
        String mongoDBUri = "mongodb+srv://%s:%s@%s/?retryWrites=true&w=majority";
        String connectionString = String.format(mongoDBUri, dbUsername, dbPassword, dbHost);
        MongoClient client = MongoClients.create(connectionString);
        MongoDatabase db = client.getDatabase(dbName);
        INSTANCE = db;
    }

    /**
     * Get the Mongo database connection instance.
     *
     * @return The Mongo database which the system is connected.
     * @throws NotDefinedDatabaseContextException Thrown when the instance has
     * not been already created.
     */
    public static MongoDatabase getInstance() throws NotDefinedDatabaseContextException {
        if (INSTANCE == null) {
            throw new NotDefinedDatabaseContextException("The connection to the Mongo Database has not been defined.");
        }

        return INSTANCE;
    }
}
