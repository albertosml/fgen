package variable.application;

import java.util.Map;

/**
 * Represents a variable associated to a subtotal.
 */
public class SubtotalVariable extends Variable {

    /**
     * The associated subtotal code.
     */
    private int subtotalCode;

    private SubtotalVariable(String name, String description, EntityAttribute attribute, boolean isDeleted) {
        super(name, description, attribute, isDeleted)
    }

    public int getSubtotalCode() {
        return this.subtotalCode;
    }

    /**
     * Create a variable given its attribute values.
     *
     * @param attributes Map containing the value for each variable attribute.
     * @return The created variable.
     */
    @Override
    public static Variable from(Map<SubtotalVariableAttribute, Object> attributes) {
        String name = (String) attributes.getOrDefault(SubtotalVariableAttribute.NAME, "");
        String description = (String) attributes.getOrDefault(SubtotalVariableAttribute.DESCRIPTION, "");
        EntityAttribute attribute = (EntityAttribute) attributes.get(SubtotalVariableAttribute.ATTRIBUTE);
        boolean isDeleted = (boolean) attributes.getOrDefault(SubtotalVariableAttribute.ISDELETED, false);

        return new SubtotalVariable(name, description, attribute, isDeleted);
    }

}
