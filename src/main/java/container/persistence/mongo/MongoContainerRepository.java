package container.persistence.mongo;

import container.application.Container;
import container.persistence.ContainerRepository;
import java.util.ArrayList;
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
