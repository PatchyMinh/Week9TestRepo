package banksystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a checking account with no overdraft protection.
 */
public class CheckingAccount extends Account {
    /** Logger for checking account events. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CheckingAccount.class);

    /**
     * Constructs a checking account.
     *
     * @param accountNumber the account number
     * @param balance the initial balance
     */
    public CheckingAccount(final String accountNumber,
            final double balance) {
        super(accountNumber, balance);
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
        LOGGER.info("Checking deposit: {} into account {}", amount,
                getAccountNumber());
    }

    /**
     * Withdraws an amount from the account.
     *
     * @param amount the amount to withdraw (positive, ≤ balance)
     * @throws InvalidFundingAmountException if amount is not positive
     * @throws InsufficientFundsException if balance is insufficient
     */
    @Override
    public void withdraw(final double amount)
            throws InvalidFundingAmountException, InsufficientFundsException {
        super.withdraw(amount);
        LOGGER.info("Checking withdrawal: {} from account {}", amount,
                getAccountNumber());
    }
}
