package template.application.usecases;

import java.util.Map;
import template.application.Template;
import template.application.TemplateAttribute;
import template.application.utils.TemplateValidationState;
import template.application.utils.TemplateValidator;
import template.persistence.TemplateRepository;

/**
 * Update template use case.
 */
public class UpdateTemplate {

    /**
     * @see TemplateRepository
     */
    private TemplateRepository templateRepository;

    /**
     * Constructor.
     *
     * @param templateRepository Template repository.
     */
    public UpdateTemplate(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * Update the given template.
     *
     * @param templateAttributes The template attributes.
     * @return The validation state for the template to update.
     */
    public TemplateValidationState execute(Map<TemplateAttribute, Object> templateAttributes) {
        Template template = Template.from(templateAttributes);

        TemplateValidationState templateValidationState = TemplateValidator.isValid(template);
        if (templateValidationState != TemplateValidationState.VALID) {
            return templateValidationState;
        }

        boolean isTemplateUpdated = templateRepository.update(template);
        return isTemplateUpdated ? TemplateValidationState.VALID : TemplateValidationState.INVALID;
    }

}
