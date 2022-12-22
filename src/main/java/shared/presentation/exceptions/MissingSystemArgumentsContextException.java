package shared.presentation.exceptions;

import shared.persistence.exceptions.ContextException;

/**
 * Defines the context exception shown when the application is not running with
 * all expected arguments
 */
public class MissingSystemArgumentsContextException extends ContextException {

    /**
     * Constructor.
     *
     * @param message Context exception message.
     */
    public MissingSystemArgumentsContextException(String message) {
        super(message);
    }

}
