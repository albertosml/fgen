package template.persistence.mongo;

import com.mongodb.client.model.Filters;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.bson.conversions.Bson;
import shared.persistence.Base64Converter;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;
import template.application.Template;
import template.application.TemplateAttribute;
import template.application.TemplateField;
import template.application.TemplateFieldAttribute;
import template.persistence.TemplateRepository;

/**
 * Interacts with the template collection on the Mongo database.
 */
public class MongoTemplateRepository extends MongoRepository implements TemplateRepository {

    /**
     * Constructor.
     *
     * @throws NotDefinedDatabaseContextException Thrown when there is not a
     * connected database to the system.
     */
    public MongoTemplateRepository() throws NotDefinedDatabaseContextException {
        super("template");
    }

    /**
     * It creates a Mongo document from a template.
     *
     * @param template The template to get the data.
     * @return A document with the given template data if it has been created,
     * otherwise null.
     */
    private Document createDocumentFrom(Template template) {
        Document document = new Document();

        Map<String, String> fileAttributes = Base64Converter.encode(template.getFile());
        if (fileAttributes == null) {
            return null;
        }

        document.append("code", template.getCode());
        document.append("name", template.getName());
        document.append("file", fileAttributes);
        document.append("fields", template.getFields());
        document.append("isDeleted", template.isDeleted());

        return document;
    }

    /**
     * It creates a template from a Mongo document.
     *
     * @param document A document obtained from the Mongo collection.
     * @return A template object based on the data obtained from the given
     * document.
     */
    private Template createTemplateFrom(Document document) {
        Map<TemplateAttribute, Object> attributes = new HashMap<>();
        attributes.put(TemplateAttribute.CODE, document.get("code"));
        attributes.put(TemplateAttribute.NAME, document.get("name"));
        attributes.put(TemplateAttribute.ISDELETED, document.get("isDeleted"));

        // Template file.
        Map<String, String> fileAttributes = (Map<String, String>) document.get("file");
        File file = Base64Converter.decode(fileAttributes);
        attributes.put(TemplateAttribute.FILE, file);

        // Template fields.
        Map<String, String> fields = (Map<String, String>) document.get("fields");
        ArrayList<TemplateField> templateFields = new ArrayList<>();

        for (Map.Entry<String, String> field : fields.entrySet()) {
            Map<TemplateFieldAttribute, String> templateFieldAttributes = new HashMap<>();
            templateFieldAttributes.put(TemplateFieldAttribute.POSITION, field.getKey());
            templateFieldAttributes.put(TemplateFieldAttribute.EXPRESSION, field.getValue());

            TemplateField templateField = TemplateField.from(templateFieldAttributes);
            templateFields.add(templateField);
        }

        attributes.put(TemplateAttribute.FIELDS, templateFields);

        return Template.from(attributes);
    }

    /**
     * Obtain the filter for the template code.
     *
     * @param code The template code.
     * @return A filter indicating that the query must only obtain the template
     * which contains the given template code.
     */
    private Bson getTemplateCodeFilter(int code) {
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
    public Template find(int code) {
        Bson templateCodeFilter = this.getTemplateCodeFilter(code);
        ArrayList<Document> foundTemplateDocuments = super.find(templateCodeFilter);

        if (foundTemplateDocuments.isEmpty()) {
            return null;
        } else {
            Document foundTemplateDocument = foundTemplateDocuments.get(0);
            return this.createTemplateFrom(foundTemplateDocument);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Template> get() {
        ArrayList<Document> foundDocuments = super.find();

        ArrayList<Template> templates = new ArrayList<>();
        for (Document document : foundDocuments) {
            templates.add(this.createTemplateFrom(document));
        }

        return templates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean register(Template template) {
        Document document = this.createDocumentFrom(template);

        if (document == null) {
            return false;
        }

        super.insertOne(document);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Template template) {
        Bson templateCodeFilter = this.getTemplateCodeFilter(template.getCode());
        return super.replaceOne(templateCodeFilter, this.createDocumentFrom(template));
    }

}
