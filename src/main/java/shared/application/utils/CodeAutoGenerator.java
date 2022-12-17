package shared.application.utils;

import shared.persistence.Repository;

/**
 * It is responsible of auto generating a code based on the given repository.
 */
public class CodeAutoGenerator {

    /**
     * Generate a numeric code based on the number of items on the given
     * repository.
     *
     * @param repository The repository.
     * @return An auto generated numeric code.
     */
    public static int generate(Repository repository) {
        int itemsCount = repository.count();
        return itemsCount + 1;
    }

}
