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
     * @return A list with all available containers.
     */
    public ArrayList<Container> execute() {
        return containerRepository.get();
    }

}
