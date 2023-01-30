package template.persistence.mongo;

import java.util.Map;
import org.bson.Document;
import shared.persistence.Base64Converter;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoRepository;
import template.application.Template;
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
    public boolean register(Template template) {
        Document document = this.createDocumentFrom(template);

        if (document == null) {
            return false;
        }

        super.insertOne(document);
        return true;
    }

}
