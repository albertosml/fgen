package container.application.usecases;

import container.application.Box;
import container.application.Container;
import container.persistence.ContainerRepository;
import java.util.ArrayList;

/**
 * List boxes use case.
 */
public class ListBoxes {

    /**
     * @see ContainerRepository
     */
    private ContainerRepository containerRepository;

    /**
     * Constructor.
     *
     * @param containerRepository Container repository.
     */
    public ListBoxes(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    /**
     * List all boxes.
     *
     * @param includeRemoved Whether we should include removed boxes or not.
     * @return A list with all available boxes.
     */
    public ArrayList<Box> execute(boolean includeRemoved) {
        return containerRepository.getBoxes(includeRemoved);
    }

}
