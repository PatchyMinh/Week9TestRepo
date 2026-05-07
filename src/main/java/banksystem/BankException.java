package banksystem;

/**
 * Base exception for banking-related errors.
 */
public class BankException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a bank exception with a message.
     *
     * @param message the detail message
     */
    public BankException(final String message) {
        super(message);
    }
}