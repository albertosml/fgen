package container.application.usecases;

import container.application.Container;
import container.persistence.ContainerRepository;
import java.util.ArrayList;

/**
 * List containers use case.
 */
public class ListContainers {

    /**
     * @see ContainerRepository
     */
    private ContainerRepository containerRepository;

    /**
     * Constructor.
     *
     * @param containerRepository Container repository.
     */
    public ListContainers(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    /**
     * List all containers.
     *
     * @param includeRemoved Whether we should include removed containers or
     * not.
     * @return A list with all available containers.
     */
    public ArrayList<Container> execute(boolean includeRemoved) {
        return containerRepository.get(includeRemoved);
    }

}
