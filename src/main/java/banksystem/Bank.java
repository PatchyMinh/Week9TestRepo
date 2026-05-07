package banksystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a bank that manages customers.
 */
public final class Bank {
    /** Logger for bank operations. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Bank.class);
    /** Index of account type in CSV line. */
    private static final int ACCOUNT_TYPE_INDEX = 3;

    /** List of customers in the bank. */
    private List<Customer> customerList;

    /**
     * Constructs an empty bank.
     */
    public Bank() {
        customerList = new ArrayList<>();
    }

    /**
     * Returns the customer list.
     *
     * @return the list of customers
     */
    public List<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * Sets the customer list.
     *
     * @param newCustomerList the new customer list
     */
    public void setCustomerList(final List<Customer> newCustomerList) {
        this.customerList = newCustomerList;
        LOGGER.debug("Customer list updated, size: {}", newCustomerList.size());
    }

    /**
     * Loads customer data from an input stream.
     *
     * @param inputStream the stream to read from
     * @throws IOException if an I/O error occurs
     */
    public void loadDataFromInputStream(final InputStream inputStream)
            throws IOException {
        List<Customer> customers = new ArrayList<>();
        Customer currentCustomer = null;

        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                currentCustomer = processLine(line, currentCustomer, customers);
            }
        }
        setCustomerList(customers);
        LOGGER.info("Loaded {} customers", customers.size());
    }

    private Customer processLine(final String line,
                                 final Customer currentCustomer,
                                 final List<Customer> customers) {
        if (line.startsWith("C")) {
            Customer newCustomer = createCustomerFromLine(line);
            customers.add(newCustomer);
            LOGGER.debug("Added customer: {}", newCustomer.getFullName());
            return newCustomer;
        } else if (line.startsWith("A")) {
            if (currentCustomer != null) {
                Account account = createAccountFromLine(line);
                currentCustomer.addAccount(account);
                LOGGER.debug("Added account {} to customer {}",
                        account.getAccountNumber(),
                        currentCustomer.getFullName());
            } else {
                LOGGER.warn("Account line without preceding customer: {}",
                        line);
            }
            return currentCustomer;
        } else {
            LOGGER.warn("Unknown line type: {}", line);
            return currentCustomer;
        }
    }

    private Customer createCustomerFromLine(final String line) {
        String[] parts = line.split(";");
        String idNumber = parts[1];
        String fullName = parts[2];
        return new Customer(idNumber, fullName);
    }

    private Account createAccountFromLine(final String line) {
        String[] parts = line.split(";");
        String accountNumber = parts[1];
        double balance = Double.parseDouble(parts[2]);
        String type = parts[ACCOUNT_TYPE_INDEX];
        if ("SAVINGS".equalsIgnoreCase(type)) {
            return new SavingsAccount(accountNumber, balance);
        } else {
            return new CheckingAccount(accountNumber, balance);
        }
    }
}
