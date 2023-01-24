package template.application.usecases;

import java.util.Map;
import template.application.TemplateField;
import template.application.TemplateFieldAttribute;
import template.application.utils.SpreadsheetPositionValidator;

/**
 * Create template field use case.
 */
public class CreateTemplateField {

    /**
     * Constructor.
     */
    public CreateTemplateField() {
    }

    /**
     * Create a template field based on the given attributes.
     *
     * @param attributes The template field attribute.
     * @return The created template field if the attributes are valid, otherwise
     * null.
     */
    public TemplateField execute(Map<TemplateFieldAttribute, String> attributes) {
        String spreadsheetPosition = attributes.getOrDefault(TemplateFieldAttribute.POSITION, null);

        boolean isPositionValid = SpreadsheetPositionValidator.isValid(spreadsheetPosition);

        if (!isPositionValid) {
            return null;
        }

        return TemplateField.from(attributes);
    }
}
