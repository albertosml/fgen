package container.application.utils;

import container.application.Container;
import container.persistence.ContainerRepository;

/**
 * Validate if the container to register already exists on the repository by
 * checking its code.
 *
 * @see Container
 */
public class DuplicatedContainerValidator {

    /**
     * Check if there is a container with the same code or not.
     *
     * @param container The container to validate.
     * @param containerRepository The container repository.
     * @return true if the container is duplicated, otherwise false.
     */
    public static boolean isDuplicated(Container container, ContainerRepository containerRepository) {
        int containerCode = container.getCode();

        for (Integer code : containerRepository.getCodeList()) {
            if (code.equals(containerCode)) {
                return true;
            }
        }

        return false;
    }

}
