package container.application.usecases;

import container.application.Pallet;
import container.persistence.ContainerRepository;
import java.util.ArrayList;

/**
 * List pallets use case.
 */
public class ListPallets {

    /**
     * @see ContainerRepository
     */
    private ContainerRepository containerRepository;

    /**
     * Constructor.
     *
     * @param containerRepository Container repository.
     */
    public ListPallets(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    /**
     * List all pallets.
     *
     * @param includeRemoved Whether we should include removed pallets or not.
     * @return A list with all available pallets.
     */
    public ArrayList<Pallet> execute(boolean includeRemoved) {
        return containerRepository.getPallets(includeRemoved);
    }

}
