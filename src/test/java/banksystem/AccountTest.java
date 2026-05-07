package banksystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for account operations.
 */
public class AccountTest {
    private static final double INITIAL_BALANCE_CHECKING = 1000.0;
    private static final double DEPOSIT_AMOUNT = 500.0;
    private static final double EXPECTED_BALANCE = 1500.0;

    private static final double SAVINGS_INITIAL = 600.0;
    private static final double WITHDRAW_AMOUNT = 200.0;

    /**
     * Tests deposit operation on a checking account.
     */
    @Test
    void testDeposit() {
        CheckingAccount acc = new CheckingAccount("123456789",
                INITIAL_BALANCE_CHECKING);
        try {
            acc.deposit(DEPOSIT_AMOUNT);
        } catch (InvalidFundingAmountException e) {
            // Exception should not occur for a valid deposit
            throw new RuntimeException("Deposit should not fail", e);
        }
        assertEquals(EXPECTED_BALANCE, acc.getBalance(),
                "Số dư sau khi nạp phải là 1500");
    }

    /**
     * Tests withdrawal that violates minimum balance of savings account.
     */
    @Test
    void testWithdrawInsufficientFunds() {
        SavingsAccount acc = new SavingsAccount("987654321", SAVINGS_INITIAL);
        assertThrows(InsufficientFundsException.class,
                () -> acc.withdraw(WITHDRAW_AMOUNT));
    }
}