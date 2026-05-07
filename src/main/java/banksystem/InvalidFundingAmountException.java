package banksystem;

/**
 * Thrown when a deposit or withdrawal amount is non-positive.
 */
public class InvalidFundingAmountException extends BankException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an exception with the invalid amount.
     *
     * @param amount the invalid amount
     */
    public InvalidFundingAmountException(final double amount) {
        super("Invalid funding amount: " + amount + " (must be > 0)");
    }
}