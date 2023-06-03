package template.application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Template entity class.
 */
public class Template {

    /**
     * Code.
     */
    private int code;

    /**
     * Name.
     */
    private String name;

    /**
     * File.
     */
    private File file;

    /**
     * List of fields.
     */
    private ArrayList<TemplateField> fields;

    /**
     * Whether the subtotal is deleted or not.
     */
    private boolean isDeleted;

    /**
     * Constructor.
     *
     * @param code Template code.
     * @param name Template name.
     * @param file Template file.
     * @param fields Template fields.
     * @param isDeleted Whether the template is deleted or not.
     */
    private Template(int code, String name, File file, ArrayList<TemplateField> fields, boolean isDeleted) {
        this.code = code;
        this.name = name;
        this.file = file;
        this.fields = fields;
        this.isDeleted = isDeleted;
    }

    /**
     * Retrieve the template code.
     *
     * @return The template code.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Retrieve the template name.
     *
     * @return The template name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieve the template file.
     *
     * @return The template file.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Retrieve the template fields.
     *
     * @return The template fields.
     */
    public Map<String, String> getFields() {
        Map<String, String> templateFields = new HashMap<>();

        for (TemplateField field : this.fields) {
            templateFields.put(field.getPosition(), field.getExpression());
        }

        return templateFields;
    }

    /**
     * Whether the template is deleted from the system or not.
     *
     * @return true if the template is removed, otherwise false.
     */
    public boolean isDeleted() {
        return this.isDeleted;
    }
    
    /**
     * Update the deletion state for the template.
     *
     * @param isDeleted Whether the template is deleted from the system or not.
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Create a template given its attribute values.
     *
     * @param attributes Map containing the value for each template attribute.
     * @return The created template.
     */
    public static Template from(Map<TemplateAttribute, Object> attributes) {
        int code = (int) attributes.get(TemplateAttribute.CODE);
        String name = (String) attributes.getOrDefault(TemplateAttribute.NAME, "");
        File file = (File) attributes.get(TemplateAttribute.FILE);
        ArrayList<TemplateField> fields = (ArrayList<TemplateField>) attributes.get(TemplateAttribute.FIELDS);
        boolean isDeleted = (boolean) attributes.getOrDefault(TemplateAttribute.ISDELETED, false);

        return new Template(code, name, file, fields, isDeleted);
    }

}
