package template.application.usecases;

import java.util.Map;
import template.application.TemplateFieldAttribute;
import template.application.utils.SpreadsheetPositionValidator;

/**
 * Edit template field use case.
 */
public class EditTemplateField {

    /**
     * Constructor.
     */
    public EditTemplateField() {
    }

    /**
     * Edit a template field based on the given attributes.
     *
     * @param attributes The template field attributes.
     * @return Whether the template field has been edited or not.
     */
    public boolean execute(Map<TemplateFieldAttribute, String> attributes) {
        String spreadsheetPosition = attributes.getOrDefault(TemplateFieldAttribute.POSITION, null);

        // The template field can be edited if it has a valid position.
        return SpreadsheetPositionValidator.isValid(spreadsheetPosition);
    }

}
