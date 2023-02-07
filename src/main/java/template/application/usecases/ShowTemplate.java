package template.application.usecases;

import template.application.Template;
import template.persistence.TemplateRepository;

/**
 * Show template use case.
 */
public class ShowTemplate {

    /**
     * @see TemplateRepository
     */
    private TemplateRepository templateRepository;

    /**
     * Constructor.
     *
     * @param templateRepository Template repository.
     */
    public ShowTemplate(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * Show the details of a template.
     *
     * @param code The code of the template to find.
     * @return The found template, otherwise null.
     */
    public Template execute(int code) {
        return templateRepository.find(code);
    }
}
