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
    private static final Logger LOGGER = LoggerFactory.getLogger(Bank.class);
    private static final int ACCOUNT_TYPE_INDEX = 3;

    private List<Customer> customerList;

    /**
     * Constructs an empty bank.
     */
    public Bank() {
        customerList = new ArrayList<>();
    }

    /**
     * Returns the list of customers.
     *
     * @return the customer list
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
     * Loads customer data from a given input stream.
     *
     * @param inputStream the input stream containing the data
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

    /**
     * Processes a single line of input and returns the current customer.
     *
     * @param line            the line to process
     * @param currentCustomer the currently active customer
     * @param customers       the list of all customers
     * @return the (possibly updated) current customer
     */
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
                LOGGER.warn("Account line without preceding customer: {}", line);
            }
            return currentCustomer;
        } else {
            LOGGER.warn("Unknown line type: {}", line);
            return currentCustomer;
        }
    }

    /**
     * Creates a customer from a line of text.
     *
     * @param line the line in format "C;idNumber;fullName"
     * @return the new customer
     */
    private Customer createCustomerFromLine(final String line) {
        String[] parts = line.split(";");
        String idNumber = parts[1];
        String fullName = parts[2];
        return new Customer(idNumber, fullName);
    }

    /**
     * Creates an account from a line of text.
     *
     * @param line the line in format "A;accountNumber;balance;type"
     * @return the new account (Savings or Checking)
     */
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