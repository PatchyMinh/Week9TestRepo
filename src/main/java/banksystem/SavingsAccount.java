package banksystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a savings account that requires a minimum balance.
 */
public class SavingsAccount extends Account {
    /** Logger for savings account events. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(SavingsAccount.class);
    /** Minimum allowed balance for a savings account. */
    private static final double MINIMUM_BALANCE = 500.0;

    /**
     * Constructs a savings account with given number and balance.
     *
     * @param accountNumber the account number
     * @param balance the initial balance (should be >= MINIMUM_BALANCE)
     */
    public SavingsAccount(final String accountNumber,
            final double balance) {
        super(accountNumber, balance);
        if (balance < MINIMUM_BALANCE) {
            LOGGER.warn("Savings account {} opened, balance below minimum: {}",
            accountNumber, balance);
        }
    }

    /**
     * Deposits an amount into the account.
     *
     * @param amount the amount to deposit (positive)
     * @throws InvalidFundingAmountException if amount is not positive
     */
    @Override
    public void deposit(final double amount)
            throws InvalidFundingAmountException {
        super.deposit(amount);
        LOGGER.info("Savings deposit: {} into account {}", amount,
                getAccountNumber());
    }

    /**
     * Withdraws an amount, ensuring minimum balance is maintained.
     *
     * @param amount the amount to withdraw
     * @throws InvalidFundingAmountException if amount is not positive
     * @throws InsufficientFundsException if withdrawal would fall below minimum
     */
    @Override
    public void withdraw(final double amount)
            throws InvalidFundingAmountException, InsufficientFundsException {
        if (getBalance() - amount < MINIMUM_BALANCE) {
            throw new InsufficientFundsException(amount);
        }
        super.withdraw(amount);
        LOGGER.info("Savings withdrawal: {} from account {}", amount,
                getAccountNumber());
    }
}
