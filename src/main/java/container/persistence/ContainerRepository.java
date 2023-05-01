package container.persistence;

import container.application.Container;
import java.util.ArrayList;
import shared.persistence.Repository;

/**
 * Represents the repository model for the container entity.
 */
public interface ContainerRepository extends Repository {

    /**
     * Find the container which matches with the given code.
     *
     * @param code The code of the container to find.
     * @return The found container, otherwise null.
     */
    public Container find(int code);

    /**
     * Obtain all the containers registered on the system, even if they have
     * been removed after.
     *
     * @param includeRemoved Whether we should include removed containers or
     * not.
     * @return A list with all containers.
     */
    public ArrayList<Container> get(boolean includeRemoved);

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

    /**
     * Update the given container with its associated data.
     *
     * @param container The container to update.
     * @return Whether the container has been updated or not.
     */
    public boolean update(Container container);

}
