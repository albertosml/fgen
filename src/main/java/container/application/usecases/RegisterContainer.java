package container.application.usecases;

import container.application.Container;
import container.application.ContainerAttribute;
import container.application.utils.ContainerValidationState;
import container.application.utils.ContainerValidator;
import container.persistence.ContainerRepository;
import java.util.Map;
import shared.application.utils.CodeAutoGenerator;

/**
 * Register container use case.
 */
public class RegisterContainer {

    /**
     * @see ContainerRepository
     */
    private ContainerRepository containerRepository;

    /**
     * Constructor.
     *
     * @param containerRepository Container repository.
     */
    public RegisterContainer(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    /**
     * Execute the container registration.
     *
     * Note that the container code is automatically generated if it has not
     * been introduced manually.
     *
     * Keep in mind that the container is validated before the registration.
     *
     * @param newContainerAttributes The attributes for the container to
     * register.
     * @return The validation state for the container to register.
     */
    public ContainerValidationState execute(Map<ContainerAttribute, Object> newContainerAttributes) {
        boolean isCodeManuallyAdded = newContainerAttributes.containsKey(ContainerAttribute.CODE);
        if (!isCodeManuallyAdded) {
            int generatedContainerCode = CodeAutoGenerator.generate(containerRepository);
            newContainerAttributes.put(ContainerAttribute.CODE, generatedContainerCode);
        }

        Container container = Container.from(newContainerAttributes);

        ContainerValidationState containerValidationState = ContainerValidator.isValid(container, containerRepository);
        if (containerValidationState == ContainerValidationState.VALID) {
            containerRepository.register(container);
        }

        return containerValidationState;
    }

}
