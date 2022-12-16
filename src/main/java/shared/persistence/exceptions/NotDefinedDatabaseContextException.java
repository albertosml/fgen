package shared.dataaccess.exceptions;

/**
 * Defines the exception to show when a database has not been found.
 */
public class NotDefinedDatabaseContextException extends ContextException {

    /**
     * Constructor.
     *
     * @param message Context exception message.
     */
    public NotDefinedDatabaseContextException(String message) {
        super(message);
    }
}
