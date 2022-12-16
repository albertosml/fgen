package shared.dataaccess.exceptions;

/**
 * Represents a known exception thrown inside the program context.
 */
public class ContextException extends Exception {

    /**
     * Constructor.
     *
     * @param message Context exception message.
     */
    public ContextException(String message) {
        super(message);
    }
}
