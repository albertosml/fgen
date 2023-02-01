package template.application.usecases;

import java.util.ArrayList;
import template.application.Template;
import template.persistence.TemplateRepository;

/**
 * List templates use case.
 */
public class ListTemplates {

    /**
     * @see TemplateRepository
     */
    private TemplateRepository templateRepository;

    /**
     * Constructor.
     *
     * @param templateRepository Template repository.
     */
    public ListTemplates(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * List all templates.
     *
     * @return A list with all templates.
     */
    public ArrayList<Template> execute() {
        return templateRepository.get();
    }

}
