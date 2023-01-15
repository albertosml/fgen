package variable.persistence.mongo;

import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;
import subtotal.application.Subtotal;
import subtotal.application.usecases.FindSubtotal;
import subtotal.persistence.mongo.MongoSubtotalRepository;
import variable.application.EntityAttribute;
import variable.application.SubtotalVariable;
import variable.application.Variable;
import variable.application.VariableAttribute;
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

        document.append("isDeleted", variable.isDeleted());

        return document;
    }

    /**
     * It creates a variable from a Mongo document.
     *
     * @param document A document obtained from the Mongo collection.
     * @return A variable object based on the data obtained from the given
     * document.
     */
    private Variable createVariableFrom(Document document) {
        Map<VariableAttribute, Object> attributes = new HashMap<>();
        attributes.put(VariableAttribute.NAME, document.get("name"));
        attributes.put(VariableAttribute.DESCRIPTION, document.get("description"));
        attributes.put(VariableAttribute.ISDELETED, document.get("isDeleted"));

        String attribute = (String) document.get("attribute");
        EntityAttribute entityAttribute = EntityAttribute.valueOf(attribute);
        attributes.put(VariableAttribute.ATTRIBUTE, entityAttribute);

        Integer subtotalCode = (Integer) document.get("subtotal");
        if (subtotalCode != null) {
            Subtotal subtotal = this.findSubtotal(subtotalCode);
            if (subtotal != null) {
                attributes.put(VariableAttribute.SUBTOTAL, subtotal);
                return SubtotalVariable.from(attributes);
            }
        }

        return Variable.from(attributes);
    }

    /**
     * Find subtotal associated with the given code.
     *
     * @param code The code of the subtotal to find.
     * @return The found subtotal, otherwise null.
     */
    private Subtotal findSubtotal(int code) {
        try {
            MongoSubtotalRepository subtotalRepository = new MongoSubtotalRepository();
            FindSubtotal findSubtotal = new FindSubtotal(subtotalRepository);
            return findSubtotal.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            Logger.getLogger(MongoVariableRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Obtain the filter for the variable name.
     *
     * @param name The variable name.
     * @return A filter indicating that the query must only obtain the variable
     * which contains the given variable name.
     */
    private Bson getVariableNameFilter(String name) {
        return Filters.eq("name", name);
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
    public ArrayList<Variable> get() {
        ArrayList<Document> foundDocuments = super.find();

        ArrayList<Variable> variables = new ArrayList<>();
        for (Document document : foundDocuments) {
            variables.add(this.createVariableFrom(document));
        }

        return variables;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Variable variable) {
        Bson variableNameFilter = this.getVariableNameFilter(variable.getName());
        return super.replaceOne(variableNameFilter, this.createDocumentFrom(variable));
    }

}
