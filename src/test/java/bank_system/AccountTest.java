package bank_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class AccountTest {
    @Test
    void testDeposit() {
        CheckingAccount acc = new CheckingAccount(123456789L, 1000.0);
        acc.deposit(500.0);
        assertEquals(1500.0, acc.getBalance(), "Số dư sau khi nạp phải là 1500");
    }

    @Test
    void testWithdrawInsufficientFunds() {
        SavingsAccount acc = new SavingsAccount(987654321L, 6000.0);
        // Rút quá hạn mức tối thiểu (5000)
        assertThrows(InsufficientFundsException.class, () -> acc.withdraw(2000.0));
    }
}