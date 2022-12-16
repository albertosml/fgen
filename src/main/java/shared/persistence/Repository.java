package shared.dataaccess.components;

/**
 * Represents the repository model for any entity.
 */
public interface Repository {

    /**
     * Count the elements existing on the repository.
     *
     * @return The number of items on the repository.
     */
    public int count();
}
