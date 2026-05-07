package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản tiết kiệm.
 * Thực thi các quy định nghiêm ngặt về số dư tối thiểu và hạn mức rút tiền.
 */
public class SavingsAccount extends Account {
  private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);

  // Khai báo hằng số để loại bỏ Magic Numbers
  private static final double MAX_WITHDRAW_LIMIT = 1000.0;
  private static final double MIN_BALANCE_REQUIRED = 5000.0;

  /**
   * Khởi tạo tài khoản tiết kiệm.
   *
   * @param accountNumber số tài khoản.
   * @param balance số dư ban đầu.
   */
  public SavingsAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();

      Transaction transaction = new Transaction(
          Transaction.TYPE_DEPOSIT_SAVINGS,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(transaction);

      logger.info("Nạp tiền tiết kiệm thành công. TK: {}, Số tiền: +{}", 
          getAccountNumber(), amount);
    } catch (InvalidFundingAmountException e) {
      logger.error("Lỗi nạp tiền tiết kiệm (Số tiền không hợp lệ): {}", e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      // Kiểm tra hạn mức rút tiền tối đa
      if (amount > MAX_WITHDRAW_LIMIT) {
        throw new InvalidFundingAmountException(amount);
      }
      // Kiểm tra điều kiện số dư tối thiểu sau khi rút
      if (initialBalance - amount < MIN_BALANCE_REQUIRED) {
        throw new InsufficientFundsException(amount);
      }

      doWithdrawing(amount);
      double finalBalance = getBalance();

      Transaction transaction = new Transaction(
          Transaction.TYPE_WITHDRAW_SAVINGS,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(transaction);

      logger.info("Rút tiền tiết kiệm thành công. TK: {}, Số dư còn lại: {}", 
          getAccountNumber(), finalBalance);
    } catch (BankException e) {
      logger.error("Giao dịch rút tiền tiết kiệm thất bại: {}", e.getMessage());
    }
  }
}