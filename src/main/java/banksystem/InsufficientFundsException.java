package banksystem;

/**
 * Thrown when a withdrawal amount exceeds the available balance.
 */
public class InsufficientFundsException extends BankException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an exception with the requested amount.
     *
     * @param amount the amount that could not be withdrawn
     */
    public InsufficientFundsException(final double amount) {
        super("Insufficient funds for withdrawal of " + amount);
    }
}