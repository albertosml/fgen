package template.application;

import java.util.Map;

public class TemplateField {

    /**
     * The position on the spreadsheet file.
     *
     * It can be a field or a range.
     */
    private String position;

    /**
     * The expression which indicates the content to render.
     */
    private String expression;

    /**
     * Constructor.
     *
     * @param position The position in the spreadsheet file.
     * @param expression An expression that indicates the content to render.
     */
    private TemplateField(String position, String expression) {
        this.position = position;
        this.expression = expression;
    }

    /**
     * Retrieve the template field position.
     *
     * @return The template field position.
     */
    public String getPosition() {
        return this.position;
    }

    /**
     * Retrieve the template field expression.
     *
     * @return The template field expression.
     */
    public String getExpression() {
        return this.expression;
    }

    /**
     * Update the template field position.
     *
     * @param position The template field position.
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Update the template field expression.
     *
     * @param expression The template field expression.
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * Create a template field given its attribute values.
     *
     * @param attributes Map containing the value for each template field
     * attribute.
     * @return The created template field.
     */
    public static TemplateField from(Map<TemplateFieldAttribute, String> attributes) {
        String position = attributes.get(TemplateFieldAttribute.POSITION);
        String expression = attributes.getOrDefault(TemplateFieldAttribute.EXPRESSION, "");

        return new TemplateField(position, expression);
    }
}
