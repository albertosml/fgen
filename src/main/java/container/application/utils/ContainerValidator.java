package container.application.utils;

import container.application.Container;
import container.persistence.ContainerRepository;
import product.application.utils.*;
import product.application.Product;
import product.persistence.ProductRepository;
import shared.application.utils.CodeValidator;
import shared.application.utils.NameValidator;
import shared.application.utils.WeightValidator;

/**
 * Validate the container.
 *
 * @see Container
 */
public class ContainerValidator {

    /**
     * Check whether a container is valid or not.
     *
     * Note that the container repository is optional. It only needs to be
     * specified when finding duplicated container codes.
     *
     * @param container The container to validate.
     * @param containerRepository The container repository to check for
     * duplicated container codes.
     * @return The validation state for the container.
     */
    public static ContainerValidationState isValid(Container container, ContainerRepository containerRepository) {
        boolean isValidName = NameValidator.isValid(container.getName());
        if (!isValidName) {
            return ContainerValidationState.INVALID_NAME;
        }

        boolean isValidWeight = WeightValidator.isValid(container.getWeight());
        if (!isValidWeight) {
            return ContainerValidationState.INVALID_WEIGHT;
        }

        int containerCode = container.getCode();
        boolean isValidCode = CodeValidator.isValid(Integer.toString(containerCode));
        if (!isValidCode) {
            return ContainerValidationState.INVALID_CODE;
        }

        if (containerRepository != null && DuplicatedContainerValidator.isDuplicated(container, containerRepository)) {
            return ContainerValidationState.DUPLICATED;
        }

        return ContainerValidationState.VALID;
    }

}
