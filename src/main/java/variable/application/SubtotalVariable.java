package variable.application;

import java.util.Map;
import subtotal.application.Subtotal;

/**
 * Represents a variable associated to a subtotal.
 */
public class SubtotalVariable extends Variable {

    /**
     * The associated subtotal.
     */
    private Subtotal subtotal;

    /**
     * Constructor.
     *
     * @param name Variable name.
     * @param description Variable description.
     * @param attribute Variable entity attribute.
     * @param isDeleted Whether the variable is deleted or not.
     * @param subtotal The associated subtotal.
     */
    private SubtotalVariable(String name, String description, EntityAttribute attribute, boolean isDeleted, Subtotal subtotal) {
        super(name, description, attribute, isDeleted);
        this.subtotal = subtotal;
    }

    /**
     * Retrieve the associated subtotal with the variable.
     *
     * @return A subtotal object.
     */
    public Subtotal getSubtotal() {
        return this.subtotal;
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
        Subtotal subtotal = (Subtotal) attributes.getOrDefault(VariableAttribute.SUBTOTAL, null);

        if (subtotal == null) {
            return new Variable(name, description, attribute, isDeleted);
        }

        return new SubtotalVariable(name, description, attribute, isDeleted, subtotal);
    }

}
