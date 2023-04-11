package product.persistence.mongo;

import java.util.ArrayList;
import org.bson.Document;
import product.application.Product;
import product.persistence.ProductRepository;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;

/**
 * Interacts with the product collection on the Mongo database.
 */
public class MongoProductRepository extends MongoRepository implements ProductRepository {

    /**
     * Constructor.
     *
     * @throws NotDefinedDatabaseContextException Thrown when there is not a
     * connected database to the system.
     */
    public MongoProductRepository() throws NotDefinedDatabaseContextException {
        super("product");
    }

    /**
     * It creates a Mongo document from a product.
     *
     * @param product The product to get the data.
     * @return A document with the given product data.
     */
    private Document createDocumentFrom(Product product) {
        Document document = new Document();

        document.append("code", product.getCode());
        document.append("name", product.getName());
        document.append("price", product.getPrice());
        document.append("isDeleted", product.isDeleted());

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
    public ArrayList<String> getCodeList() {
        return super.distinct("code", String.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(Product product) {
        Document document = this.createDocumentFrom(product);
        super.insertOne(document);
    }

}
