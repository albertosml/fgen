package container.persistence.mongo;

import com.mongodb.client.model.Filters;
import container.application.Box;
import container.application.Container;
import container.application.ContainerAttribute;
import container.application.Pallet;
import container.persistence.ContainerRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;

/**
 * Interacts with the container collection on the Mongo database.
 */
public class MongoContainerRepository extends MongoRepository implements ContainerRepository {

    /**
     * Constructor.
     *
     * @throws NotDefinedDatabaseContextException Thrown when there is not a
     * connected database to the system.
     */
    public MongoContainerRepository() throws NotDefinedDatabaseContextException {
        super("container");
    }

    /**
     * It creates a container from a Mongo document.
     *
     * @param document A document obtained from the Mongo collection.
     * @return A container object based on the data obtained from the given
     * document.
     */
    private Container createContainerFrom(Document document) {
        Map<ContainerAttribute, Object> attributes = new HashMap<>();
        attributes.put(ContainerAttribute.CODE, document.get("code"));
        attributes.put(ContainerAttribute.NAME, document.get("name"));
        attributes.put(ContainerAttribute.WEIGHT, document.get("weight"));
        attributes.put(ContainerAttribute.ISBOX, document.get("isBox"));
        attributes.put(ContainerAttribute.ISDELETED, document.get("isDeleted"));

        return Container.from(attributes);
    }

    /**
     * It creates a Mongo document from a container.
     *
     * @param container The container to get the data.
     * @return A document with the given container data.
     */
    private Document createDocumentFrom(Container container) {
        Document document = new Document();

        document.append("code", container.getCode());
        document.append("name", container.getName());
        document.append("weight", container.getWeight());
        document.append("isBox", container.isBox());
        document.append("isDeleted", container.isDeleted());

        return document;
    }

    /**
     * Obtain the filter for the container code.
     *
     * @param code The container code.
     * @return A filter indicating that the query must only obtain the container
     * which contains the given container code.
     */
    private Bson getContainerCodeFilter(int code) {
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
    public Container find(int code) {
        Bson containerCodeFilter = this.getContainerCodeFilter(code);
        ArrayList<Document> foundContainerDocuments = super.find(containerCodeFilter);

        if (foundContainerDocuments.isEmpty()) {
            return null;
        } else {
            Document foundContainerDocument = foundContainerDocuments.get(0);
            return this.createContainerFrom(foundContainerDocument);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Container> get(boolean includeRemoved) {
        ArrayList<Document> foundDocuments = super.find();

        ArrayList<Container> containers = new ArrayList<>();
        for (Document document : foundDocuments) {
            Container container = this.createContainerFrom(document);

            if (includeRemoved || !container.isDeleted()) {
                containers.add(container);
            }
        }

        return containers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Box> getBoxes(boolean includeRemoved) {
        ArrayList<Box> boxes = new ArrayList<>();

        for (Container container : this.get(includeRemoved)) {
            if (container instanceof Box) {
                boxes.add((Box) container);
            }
        }

        return boxes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Integer> getCodeList() {
        return super.distinct("code", Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Pallet> getPallets(boolean includeRemoved) {
        ArrayList<Pallet> pallets = new ArrayList<>();

        for (Container container : this.get(includeRemoved)) {
            if (container instanceof Pallet) {
                pallets.add((Pallet) container);
            }
        }

        return pallets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(Container container) {
        Document document = this.createDocumentFrom(container);
        super.insertOne(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Container container) {
        Bson containerCodeFilter = this.getContainerCodeFilter(container.getCode());
        return super.replaceOne(containerCodeFilter, this.createDocumentFrom(container));
    }

}
