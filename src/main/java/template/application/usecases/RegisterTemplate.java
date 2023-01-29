package template.application.usecases;

import java.util.Map;
import shared.application.utils.CodeAutoGenerator;
import template.application.Template;
import template.application.TemplateAttribute;
import template.application.utils.TemplateValidationState;
import template.application.utils.TemplateValidator;
import template.persistence.TemplateRepository;

/**
 * Register template use case.
 */
public class RegisterTemplate {

    /**
     * @see TemplateRepository
     */
    private final TemplateRepository templateRepository;

    /**
     * Constructor.
     *
     * @param templateRepository Template repository.
     */
    public RegisterTemplate(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * Execute the template registration.
     *
     * Note that the template code is automatically generated if it has not been
     * introduced manually.
     *
     * @param newTemplateAttributes The attributes for the template to register.
     * @return The template validation state.
     */
    public TemplateValidationState execute(Map<TemplateAttribute, Object> newTemplateAttributes) {
        boolean isCodeManuallyAdded = newTemplateAttributes.containsKey(TemplateAttribute.CODE);
        if (!isCodeManuallyAdded) {
            int generatedTemplateCode = CodeAutoGenerator.generate(templateRepository);
            newTemplateAttributes.put(TemplateAttribute.CODE, generatedTemplateCode);
        }

        Template template = Template.from(newTemplateAttributes);

        TemplateValidationState state = TemplateValidator.isValid(template);
        if (state.equals(TemplateValidationState.INVALID_NAME)) {
            return TemplateValidationState.INVALID_NAME;
        }

        boolean isTemplateRegistered = templateRepository.register(template);
        return isTemplateRegistered ? TemplateValidationState.VALID : TemplateValidationState.INVALID_FILE;
    }

}
