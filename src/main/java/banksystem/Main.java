package banksystem;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the banking system application.
 */
public final class Main {
    /** Logger for the main class. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Main.class);

    private Main() {
        // Utility class constructor
    }

    /**
     * The main method that loads the bank data from the default resource.
     *
     * @param args command line arguments (not used)
     */
    public static void main(final String[] args) {
        Bank bank = new Bank();
        InputStream inputStream = Main.class.getResourceAsStream(
            "/customers.txt");
        if (inputStream != null) {
            try {
                bank.loadDataFromInputStream(inputStream);
                LOGGER.info("Bank loaded successfully. Total customers: {}",
                        bank.getCustomerList().size());
            } catch (Exception e) {
                LOGGER.error("Error loading data: {}", e.getMessage(), e);
            }
        } else {
            LOGGER.error("File customers.txt not found in resources.");
        }
    }
}
