package shared.application;

/**
 * Represents a set of two data elements.
 *
 * @param <T> The data type for the first element.
 * @param <U> The data type for the second element.
 */
public class Pair<T, U> {

    /**
     * First element.
     */
    private T first;

    /**
     * Second element.
     */
    private U second;

    /**
     * Constructor.
     *
     * @param first The first data element.
     * @param second The second data element.
     */
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Retrieve the first element.
     *
     * @return The first element.
     */
    public T getFirst() {
        return this.first;
    }

    /**
     * Retrieve the second element.
     *
     * @return The second element.
     */
    public U getSecond() {
        return this.second;
    }

}
