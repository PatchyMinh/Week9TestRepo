package banksystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a single transaction (deposit or withdrawal).
 */
public class Transaction {
    private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);
    private static final int DEPOSIT_CODE = 1;
    private static final int WITHDRAW_CODE = 2;

    private String type;
    private double amount;
    private double initialBalance;
    private double finalBalance;

    /**
     * Constructs a transaction.
     *
     * @param transType       the type ("DEPOSIT" or "WITHDRAW")
     * @param transAmount     the transaction amount
     * @param initBalance     the balance before transaction
     * @param finalBal        the balance after transaction
     */
    public Transaction(final String transType, final double transAmount,
                       final double initBalance, final double finalBal) {
        this.type = transType;
        this.amount = transAmount;
        this.initialBalance = initBalance;
        this.finalBalance = finalBal;
    }

    /**
     * Returns the transaction type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the transaction type.
     *
     * @param newType the new type
     */
    public void setType(final String newType) {
        this.type = newType;
    }

    /**
     * Returns the transaction amount.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the transaction amount.
     *
     * @param newAmount the new amount
     */
    public void setAmount(final double newAmount) {
        this.amount = newAmount;
    }

    /**
     * Returns the initial balance.
     *
     * @return the initial balance
     */
    public double getInitialBalance() {
        return initialBalance;
    }

    /**
     * Sets the initial balance.
     *
     * @param newInitialBalance the new initial balance
     */
    public void setInitialBalance(final double newInitialBalance) {
        this.initialBalance = newInitialBalance;
    }

    /**
     * Returns the final balance.
     *
     * @return the final balance
     */
    public double getFinalBalance() {
        return finalBalance;
    }

    /**
     * Sets the final balance.
     *
     * @param newFinalBalance the new final balance
     */
    public void setFinalBalance(final double newFinalBalance) {
        this.finalBalance = newFinalBalance;
    }

    /**
     * Converts a transaction type code to a string.
     *
     * @param typeCode 1 for DEPOSIT, 2 for WITHDRAW
     * @return the type string
     */
    public static String typeCodeToString(final int typeCode) {
        return switch (typeCode) {
            case DEPOSIT_CODE -> "DEPOSIT";
            case WITHDRAW_CODE -> "WITHDRAW";
            default -> "UNKNOWN";
        };
    }

    /**
     * Returns a string representation of the transaction.
     *
     * @return transaction details
     */
    @Override
    public String toString() {
        return String.format(
                "Transaction{type=%s, amount=%.2f, initial=%.2f, final=%.2f}",
                type, amount, initialBalance, finalBalance);
    }
}