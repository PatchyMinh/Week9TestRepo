package banksystem;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        InputStream inputStream = Main.class.getResourceAsStream("/customers.txt");
        if (inputStream != null) {
            try {
                bank.loadDataFromInputStream(inputStream);
                System.out.println("Bank loaded successfully. Total customers: " 
                        + bank.getCustomerList().size());
            } catch (Exception e) {
                System.err.println("Error loading data: " + e.getMessage());
            }
        } else {
            System.err.println("File customers.txt not found in resources.");
        }
    }
}