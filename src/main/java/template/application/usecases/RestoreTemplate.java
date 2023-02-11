package template.application.usecases;

import template.application.Template;
import template.persistence.TemplateRepository;

/**
 * Restore template use case.
 */
public class RestoreTemplate {

    /**
     * @see TemplateRepository
     */
    private TemplateRepository templateRepository;

    /**
     * Constructor.
     *
     * @param templateRepository Template repository.
     */
    public RestoreTemplate(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * Restore the given template.
     *
     * @param code The code of the template to restore.
     * @return Whether the template has been restored or not.
     */
    public boolean execute(int code) {
        Template template = templateRepository.find(code);
        
        if (template == null) {
            return false;
        }

        template.setIsDeleted(false);
        return templateRepository.update(template);
    }

}
