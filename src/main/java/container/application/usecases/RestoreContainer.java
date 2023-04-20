package container.application.usecases;

import container.application.Container;
import container.persistence.ContainerRepository;

/**
 * Restore container use case.
 */
public class RestoreContainer {

    /**
     * @see ContainerRepository
     */
    private ContainerRepository containerRepository;

    /**
     * Constructor.
     *
     * @param containerRepository Container repository.
     */
    public RestoreContainer(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    /**
     * Restore the container which matches with the given code.
     *
     * @param code The code of the container to restore.
     * @return Whether the container has been restored or not.
     */
    public boolean execute(int code) {
        Container container = containerRepository.find(code);

        if (container == null) {
            return false;
        }

        container.setIsDeleted(false);
        return containerRepository.update(container);
    }

}
