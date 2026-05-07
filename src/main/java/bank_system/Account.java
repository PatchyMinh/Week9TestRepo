package bank_system;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp trừu tượng đại diện cho một tài khoản ngân hàng.
 * Cung cấp các phương thức cơ bản như gửi tiền, rút tiền và quản lý giao dịch.
 */
public abstract class Account {
  private static final Logger logger = LoggerFactory.getLogger(Account.class);

  public static final String CHECKING_TYPE = "CHECKING";
  public static final String SAVINGS_TYPE = "SAVINGS";

  private long accNumber;
  private double balance;
  protected List<Transaction> transactionList;

  /**
   * Khởi tạo một tài khoản mới.
   *
   * @param accountNumber số tài khoản duy nhất.
   * @param balance số dư ban đầu.
   */
  public Account(long accountNumber, double balance) {
    this.accNumber = accountNumber;
    this.balance = balance;
    this.transactionList = new ArrayList<Transaction>();
  }

  public long getAccountNumber() {
    return accNumber;
  }

  public void setAccountNumber(long accountNumber) {
    this.accNumber = accountNumber;
  }

  public double getBalance() {
    return balance;
  }

  protected void setBalance(double balance) {
    this.balance = balance;
  }

  public List<Transaction> getTransactionList() {
    return transactionList;
  }

  /**
   * Cập nhật danh sách giao dịch. Nếu null sẽ khởi tạo danh sách trống.
   */
  public void setTransactionList(List<Transaction> transactionList) {
    if (transactionList == null) {
      this.transactionList = new ArrayList<Transaction>();
    } else {
      this.transactionList = transactionList;
    }
  }

  /**
   * Thực hiện gửi tiền vào tài khoản.
   *
   * @param amount số tiền cần gửi.
   */
  public abstract void deposit(double amount);

  /**
   * Thực hiện rút tiền từ tài khoản.
   *
   * @param amount số tiền cần rút.
   */
  public abstract void withdraw(double amount);

  protected void doDepositing(double amount) throws InvalidFundingAmountException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    balance += amount;
  }

  protected void doWithdrawing(double amount) 
      throws InvalidFundingAmountException, InsufficientFundsException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    if (amount > balance) {
      throw new InsufficientFundsException(amount);
    }
    balance -= amount;
  }

  /**
   * Thêm một giao dịch vào lịch sử.
   *
   * @param transaction đối tượng giao dịch cần thêm.
   */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      transactionList.add(transaction);
    }
  }

  /**
   * Trả về lịch sử giao dịch của tài khoản.
   *
   * @return chuỗi văn bản tóm tắt lịch sử giao dịch.
   */
  public String getTransactionHistory() {
    StringBuilder historyBuilder = new StringBuilder();

    historyBuilder.append("Lịch sử giao dịch của tài khoản ")
        .append(this.accNumber)
        .append(":\n");

    for (int i = 0; i < transactionList.size(); i++) {
      historyBuilder.append(transactionList.get(i).getTransactionSummary());
      if (i < transactionList.size() - 1) {
        historyBuilder.append("\n");
      }
    }

    logger.debug("Đã truy xuất lịch sử giao dịch cho tài khoản: {}", this.accNumber);
    return historyBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Account)) {
      return false;
    }
    Account other = (Account) obj;
    return this.accNumber == other.accNumber;
  }

  @Override
  public int hashCode() {
    return (int) (accNumber ^ (accNumber >>> 32));
  }
}