package template.persistence;

import shared.persistence.Repository;
import template.application.Template;

/**
 * Represents the repository model for the template entity.
 */
public interface TemplateRepository extends Repository {

    /**
     * Register the given template.
     *
     * @param template The template to register.
     * @return Whether the template has been registered or not.
     */
    public boolean register(Template template);

}
