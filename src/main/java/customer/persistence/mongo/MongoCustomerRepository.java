package customer.persistence.mongo;

import com.mongodb.client.model.Filters;
import customer.application.Customer;
import customer.persistence.CustomerRepository;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;

/**
 * Interacts with the customer collection on the Mongo database.
 */
public class MongoCustomerRepository extends MongoRepository implements CustomerRepository {

    /**
     * Constructor.
     *
     * @throws NotDefinedDatabaseContextException Thrown when there is not a
     * connected database to the system.
     */
    public MongoCustomerRepository() throws NotDefinedDatabaseContextException {
        super("customer");
    }

    /**
     * It creates a Mongo document from a customer.
     *
     * @param customer The customer to get the data.
     * @return A document with the given customer data.
     */
    private Document createDocumentFrom(Customer customer) {
        Document document = new Document();

        document.append("code", customer.getCode());
        document.append("name", customer.getName());
        document.append("tin", customer.getTin());
        document.append("address", customer.getAddress());
        document.append("city", customer.getCity());
        document.append("province", customer.getProvince());
        document.append("zipcode", customer.getZipCode());
        document.append("iban", customer.getIban());
        document.append("isDeleted", customer.isDeleted());

        return document;
    }

    /**
     * Obtain the filter to get all the customers which are not deleted.
     *
     * @return A filter indicating that the query must only obtain non-removed
     * customers.
     */
    private Bson isNotDeletedFilter() {
        return Filters.eq("isDeleted", false);
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
    public ArrayList<String> getTinList() {
        return super.distinct("tin", isNotDeletedFilter(), String.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(Customer customer) {
        Document document = this.createDocumentFrom(customer);
        super.insertOne(document);
    }

}
