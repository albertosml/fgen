package template.persistence;

import java.util.ArrayList;
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

    /**
     * Obtain all the templates registered on the system, even if they have been
     * removed after.
     *
     * @return A list with all templates.
     */
    public ArrayList<Template> get();

    /**
     * Find the template which matches with the given code.
     *
     * @param code The code of the template to find.
     * @return The found template, otherwise null.
     */
    public Template find(int code);

}
