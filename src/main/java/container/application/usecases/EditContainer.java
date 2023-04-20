package container.application.usecases;

import container.application.Container;
import container.application.ContainerAttribute;
import container.persistence.ContainerRepository;
import java.util.Map;

/**
 * Edit container use case.
 */
public class EditContainer {

    /**
     * @see ContainerRepository
     */
    private ContainerRepository containerRepository;

    /**
     * Constructor.
     *
     * @param containerRepository Container repository.
     */
    public EditContainer(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    /**
     * Update the given container.
     *
     * @param containerAttributes The container attributes.
     */
    public void execute(Map<ContainerAttribute, Object> containerAttributes) {
        Container container = Container.from(containerAttributes);
        containerRepository.update(container);
    }

}
