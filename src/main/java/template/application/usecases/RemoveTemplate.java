package template.application.usecases;

import template.application.Template;
import template.persistence.TemplateRepository;

/**
 * Update template use case.
 */
public class RemoveTemplate {

    /**
     * @see TemplateRepository
     */
    private TemplateRepository templateRepository;

    /**
     * Constructor.
     *
     * @param templateRepository Template repository.
     */
    public RemoveTemplate(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * Remove the given template.
     *
     * @param code The code of the template to remove.
     * @return Whether the template has been removed or not.
     */
    public boolean execute(int code) {
        Template template = templateRepository.find(code);
        
        if (template == null) {
            return false;
        }

        template.setIsDeleted(true);
        return templateRepository.update(template);
    }

}
