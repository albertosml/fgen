package product.persistence.mongo;

import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.bson.conversions.Bson;
import product.application.Product;
import product.application.ProductAttribute;
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
        document.append("isDeleted", product.isDeleted());

        return document;
    }

    /**
     * It creates a product a Mongo document.
     *
     * @param document A document obtained from the Mongo collection.
     * @return A product object based on the data obtained from the given
     * document.
     */
    private Product createProductFrom(Document document) {
        Map<ProductAttribute, Object> attributes = new HashMap<>();
        attributes.put(ProductAttribute.CODE, document.get("code"));
        attributes.put(ProductAttribute.NAME, document.get("name"));
        attributes.put(ProductAttribute.ISDELETED, document.get("isDeleted"));

        return Product.from(attributes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product find(String code) {
        Bson productCodeFilter = this.getProductCodeFilter(code);
        ArrayList<Document> foundProductDocuments = super.find(productCodeFilter);

        if (foundProductDocuments.isEmpty()) {
            return null;
        } else {
            Document foundProductDocument = foundProductDocuments.get(0);
            return this.createProductFrom(foundProductDocument);
        }
    }

    /**
     * Obtain the filter for the product code.
     *
     * @param code The product code.
     * @return A filter indicating that the query must only obtain the product
     * which contains the given product code.
     */
    private Bson getProductCodeFilter(String code) {
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
    public ArrayList<String> getCodeList() {
        return super.distinct("code", String.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Product> get() {
        ArrayList<Document> foundDocuments = super.find();

        ArrayList<Product> products = new ArrayList<>();
        for (Document document : foundDocuments) {
            products.add(this.createProductFrom(document));
        }

        return products;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(Product product) {
        Document document = this.createDocumentFrom(product);
        super.insertOne(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Product product) {
        Bson productCodeFilter = this.getProductCodeFilter(product.getCode());
        return super.replaceOne(productCodeFilter, this.createDocumentFrom(product));
    }

}
