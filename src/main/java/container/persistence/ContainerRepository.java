package container.persistence;

import container.application.Container;
import java.util.ArrayList;
import shared.persistence.Repository;

/**
 * Represents the repository model for the container entity.
 */
public interface ContainerRepository extends Repository {

    /**
     * Get the code from all containers from the repository.
     *
     * @return A list with all the retrieved container codes.
     */
    public ArrayList<Integer> getCodeList();

    /**
     * Register the given container.
     *
     * @param container The container to register.
     */
    public void register(Container container);

}
