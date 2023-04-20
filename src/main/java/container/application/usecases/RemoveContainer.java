package container.application.usecases;

import container.application.Container;
import container.persistence.ContainerRepository;

/**
 * Remove container use case.
 */
public class RemoveContainer {

    /**
     * @see ContainerRepository
     */
    private ContainerRepository containerRepository;

    /**
     * Constructor.
     *
     * @param containerRepository Container repository.
     */
    public RemoveContainer(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    /**
     * Remove the container which matches with the given code.
     *
     * @param code The code of the container to remove.
     * @return Whether the container has been removed or not.
     */
    public boolean execute(int code) {
        Container container = containerRepository.find(code);

        if (container == null) {
            return false;
        }

        container.setIsDeleted(true);
        return containerRepository.update(container);
    }

}
