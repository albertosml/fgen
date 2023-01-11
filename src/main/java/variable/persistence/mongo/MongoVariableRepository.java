package variable.persistence.mongo;

import java.util.ArrayList;
import org.bson.Document;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;
import subtotal.application.Subtotal;
import variable.application.EntityAttribute;
import variable.application.SubtotalVariable;
import variable.application.Variable;
import variable.persistence.VariableRepository;

/**
 * Interacts with the variable collection on the Mongo database.
 */
public class MongoVariableRepository extends MongoRepository implements VariableRepository {

    /**
     * Constructor.
     *
     * @throws NotDefinedDatabaseContextException Thrown when there is not a
     * connected database to the system.
     */
    public MongoVariableRepository() throws NotDefinedDatabaseContextException {
        super("variable");
    }

    /**
     * It creates a Mongo document from a variable.
     *
     * @param variable The variable to get the data.
     * @return A document with the given variable data.
     */
    private Document createDocumentFrom(Variable variable) {
        Document document = new Document();

        document.append("name", variable.getName());

        document.append("description", variable.getDescription());

        EntityAttribute entityAttribute = variable.getAttribute();
        document.append("attribute", entityAttribute.name());

        if (variable instanceof SubtotalVariable) {
            SubtotalVariable subtotalVariable = (SubtotalVariable) variable;
            Subtotal subtotal = subtotalVariable.getSubtotal();
            document.append("subtotal", subtotal.getCode());
        }

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
    public ArrayList<String> getNamesList() {
        return super.distinct("name", null, String.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(Variable variable) {
        Document document = this.createDocumentFrom(variable);
        super.insertOne(document);
    }

}
