package banksystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a bank account with basic deposit/withdraw operations.
 */
public class Account {
    /** Logger for account-related events. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Account.class);
    /** Prime number for hash code calculation. */
    private static final int HASH_PRIME = 32;

    /** The unique account number. */
    private String accountNumber;
    /** The current balance of the account. */
    private double balance;
    /** The list of transactions performed on this account. */
    private List<Transaction> transactionList;

    /**
     * Constructs an account with the given number and initial balance.
     *
     * @param accNumber the account number
     * @param initBalance the initial balance
     */
    public Account(final String accNumber, final double initBalance) {
        this.accountNumber = accNumber;
        this.balance = initBalance;
        this.transactionList = new ArrayList<>();
    }

    /**
     * Returns the account number.
     *
     * @return the account number
     */
    public final String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the account number.
     *
     * @param newAccountNumber the new account number
     */
    public final void setAccountNumber(final String newAccountNumber) {
        this.accountNumber = newAccountNumber;
    }

    /**
     * Returns the current balance.
     *
     * @return the balance
     */
    public final double getBalance() {
        return balance;
    }

    /**
     * Sets the balance.
     *
     * @param newBalance the new balance
     */
    public final void setBalance(final double newBalance) {
        this.balance = newBalance;
    }

    /**
     * Returns the transaction list.
     *
     * @return the list of transactions
     */
    public final List<Transaction> getTransactionList() {
        return transactionList;
    }

    /**
     * Sets the transaction list.
     *
     * @param newTransactionList the new transaction list
     */
    public final void setTransactionList(
            final List<Transaction> newTransactionList) {
        this.transactionList = newTransactionList;
    }

    /**
     * Adds a transaction to the account's history.
     *
     * @param transaction the transaction to add
     */
    public void addTransaction(final Transaction transaction) {
        transactionList.add(transaction);
        LOGGER.debug("Transaction added: {}", transaction);
    }

    /**
     * Performs a deposit operation.
     *
     * @param amount the amount to deposit (must be positive)
     * @throws InvalidFundingAmountException if amount is not positive
     */
    public void deposit(final double amount)
            throws InvalidFundingAmountException {
        if (amount <= 0) {
            throw new InvalidFundingAmountException(amount);
        }
        double initialBalance = balance;
        balance += amount;
        Transaction transaction = new Transaction("DEPOSIT", amount,
                initialBalance, balance);
        addTransaction(transaction);
        LOGGER.info("Deposited: {} into account {}", amount, accountNumber);
    }

    /**
     * Performs a withdrawal operation.
     *
     * @param amount the amount to withdraw (must be positive and ≤ balance)
     * @throws InvalidFundingAmountException if amount is not positive
     * @throws InsufficientFundsException    if balance is insufficient
     */
    public void withdraw(final double amount)
            throws InvalidFundingAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidFundingAmountException(amount);
        }
        if (balance < amount) {
            throw new InsufficientFundsException(amount);
        }
        double initialBalance = balance;
        balance -= amount;
        Transaction transaction = new Transaction("WITHDRAW", amount,
                initialBalance, balance);
        addTransaction(transaction);
        LOGGER.info("Withdrew: {} from account {}", amount, accountNumber);
    }

    /**
     * Compares this account to another object for equality.
     *
     * @param obj the reference object
     * @return true if account numbers are equal
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Account account = (Account) obj;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    /**
     * Returns a hash code based on the account number.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return HASH_PRIME + (accountNumber == null
                ? 0 : accountNumber.hashCode());
    }

    /**
     * Returns a string representation of the account.
     *
     * @return account details as a string
     */
    @Override
    public String toString() {
        return "Account{"
                + "accountNumber='" + accountNumber + '\''
                + ", balance=" + balance
                + '}';
    }
}
