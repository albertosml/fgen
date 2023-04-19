package container.persistence.mongo;

import container.application.Container;
import container.application.ContainerAttribute;
import container.persistence.ContainerRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
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
        document.append("isDeleted", container.isDeleted());

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
    public ArrayList<Container> get() {
        ArrayList<Document> foundDocuments = super.find();

        ArrayList<Container> containers = new ArrayList<>();
        for (Document document : foundDocuments) {
            containers.add(this.createContainerFrom(document));
        }

        return containers;
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
    public void register(Container container) {
        Document document = this.createDocumentFrom(container);
        super.insertOne(document);
    }

}
