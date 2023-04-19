package container.persistence;

import container.application.Container;
import java.util.ArrayList;
import shared.persistence.Repository;

/**
 * Represents the repository model for the container entity.
 */
public interface ContainerRepository extends Repository {

    /**
     * Obtain all the containers registered on the system, even if they have
     * been removed after.
     *
     * @return A list with all containers.
     */
    public ArrayList<Container> get();

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
