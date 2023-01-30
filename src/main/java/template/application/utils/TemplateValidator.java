package template.application.utils;

import shared.application.utils.NameValidator;
import template.application.Template;

/**
 * Validate the template.
 *
 * @see Template
 */
public class TemplateValidator {

    /**
     * Check whether a template is valid or not.
     *
     * @param template The template to validate.
     * @return The validation state for the template.
     */
    public static TemplateValidationState isValid(Template template) {
        boolean isValidName = NameValidator.isValid(template.getName());
        if (!isValidName) {
            return TemplateValidationState.INVALID_NAME;
        }

        return TemplateValidationState.VALID;
    }

}
