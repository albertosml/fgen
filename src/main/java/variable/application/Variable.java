package variable.application;

import java.util.Map;

/**
 * Variable entity class.
 */
public class Variable {

    /**
     * Name.
     */
    private String name;

    /**
     * Description.
     */
    private String description;

    /**
     * The entity attribute associated with this variable
     *
     * @see EntityAttribute
     */
    private EntityAttribute attribute;

    /**
     * Whether the variable is deleted or not.
     */
    private boolean isDeleted;

    /**
     * Constructor.
     *
     * @param name Variable name.
     * @param description Variable description.
     * @param attribute Variable entity attribute.
     * @param isDeleted Whether the variable is deleted or not.
     */
    private Variable(String name, String description, EntityAttribute attribute, boolean isDeleted) {
        this.name = name;
        this.description = description;
        this.attribute = attribute;
        this.isDeleted = isDeleted;
    }

    /**
     * Retrieve the variable name.
     *
     * @return The variable name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieve the variable description.
     *
     * @return The variable description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retrieve the variable entity attribute.
     *
     * @return The variable entity attribute.
     */
    public EntityAttribute getAttribute() {
        return this.attribute;
    }

    /**
     * Whether the variable is deleted from the system or not.
     *
     * @return true if the variable is removed, otherwise false.
     */
    public boolean isDeleted() {
        return this.isDeleted;
    }

    /**
     * Create a variable given its attribute values.
     *
     * @param attributes Map containing the value for each variable attribute.
     * @return The created variable.
     */
    public static Variable from(Map<VariableAttribute, Object> attributes) {
        String name = (String) attributes.getOrDefault(VariableAttribute.NAME, "");
        String description = (String) attributes.getOrDefault(VariableAttribute.DESCRIPTION, "");
        EntityAttribute attribute = (EntityAttribute) attributes.get(VariableAttribute.ATTRIBUTE);
        boolean isDeleted = (boolean) attributes.getOrDefault(VariableAttribute.ISDELETED, false);

        return new Variable(name, description, attribute, isDeleted);
    }

}
