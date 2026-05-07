package banksystem;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a bank customer with personal information and accounts.
 */
public class Customer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Customer.class);

    private String idNumber;
    private String fullName;
    private List<Account> accountList;

    /**
     * Constructs a customer with ID and name.
     *
     * @param newIdNumber   the customer ID
     * @param newFullName   the full name
     */
    public Customer(final String newIdNumber, final String newFullName) {
        this.idNumber = newIdNumber;
        this.fullName = newFullName;
        this.accountList = new ArrayList<>();
    }

    /**
     * Returns the ID number.
     *
     * @return the ID number
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * Sets the ID number.
     *
     * @param newIdNumber the new ID number
     */
    public void setIdNumber(final String newIdNumber) {
        this.idNumber = newIdNumber;
    }

    /**
     * Returns the full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name.
     *
     * @param newFullName the new full name
     */
    public void setFullName(final String newFullName) {
        this.fullName = newFullName;
    }

    /**
     * Returns the list of accounts.
     *
     * @return the account list
     */
    public List<Account> getAccountList() {
        return accountList;
    }

    /**
     * Sets the account list.
     *
     * @param newAccountList the new account list
     */
    public void setAccountList(final List<Account> newAccountList) {
        this.accountList = newAccountList;
        LOGGER.debug("Account list updated for customer {}", fullName);
    }

    /**
     * Adds an account to the customer.
     *
     * @param account the account to add
     */
    public void addAccount(final Account account) {
        accountList.add(account);
        LOGGER.info("Added account {} to customer {}",
                account.getAccountNumber(), fullName);
    }

    /**
     * Removes an account from the customer.
     *
     * @param account the account to remove
     * @return true if removed, false otherwise
     */
    public boolean removeAccount(final Account account) {
        boolean removed = accountList.remove(account);
        if (removed) {
            LOGGER.info("Removed account {} from customer {}",
                    account.getAccountNumber(), fullName);
        }
        return removed;
    }

    /**
     * Returns a string representation of the customer.
     *
     * @return customer details
     */
    @Override
    public String toString() {
        return "Customer{"
                + "idNumber='" + idNumber + '\''
                + ", fullName='" + fullName + '\''
                + ", accountCount=" + accountList.size()
                + '}';
    }
}