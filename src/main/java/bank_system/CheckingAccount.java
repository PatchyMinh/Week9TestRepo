package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho tài khoản vãng lai (Checking Account).
 * Cho phép thực hiện các giao dịch nạp và rút tiền cơ bản.
 */
public class CheckingAccount extends Account {
  private static final Logger logger = LoggerFactory.getLogger(CheckingAccount.class);

  /**
   * Khởi tạo tài khoản vãng lai.
   *
   * @param accountNumber số tài khoản.
   * @param balance số dư ban đầu.
   */
  public CheckingAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      
      Transaction transaction = new Transaction(
          Transaction.TYPE_DEPOSIT_CHECKING,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(transaction);
      
      logger.info("Nạp tiền thành công vào TK {}: +{}", getAccountNumber(), amount);
    } catch (BankException e) {
      // Sử dụng logger.error thay vì System.out.println
      logger.error("Lỗi nạp tiền vào TK {}: {}", getAccountNumber(), e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      doWithdrawing(amount);
      double finalBalance = getBalance();
      
      Transaction transaction = new Transaction(
          Transaction.TYPE_WITHDRAW_CHECKING,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(transaction);

      logger.info("Rút tiền thành công từ TK {}: -{}", getAccountNumber(), amount);
    } catch (BankException e) {
      logger.error("Lỗi rút tiền từ TK {}: {}", getAccountNumber(), e.getMessage());
    }
  }
}