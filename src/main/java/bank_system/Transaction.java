package bank_system;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Đại diện cho một giao dịch ngân hàng.
 * Lưu trữ thông tin về kiểu giao dịch, số tiền và biến động số dư.
 */
public class Transaction {
  private static final Logger logger = LoggerFactory.getLogger(Transaction.class);

  public static final int TYPE_DEPOSIT_CHECKING = 1;
  public static final int TYPE_WITHDRAW_CHECKING = 2;
  public static final int TYPE_DEPOSIT_SAVINGS = 3;
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /**
   * Khởi tạo một giao dịch mới.
   *
   * @param type kiểu giao dịch (1-4).
   * @param amount số tiền giao dịch.
   * @param initialBalance số dư trước giao dịch.
   * @param finalBalance số dư sau giao dịch.
   */
  public Transaction(int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getInitialBalance() {
    return initialBalance;
  }

  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  public double getFinalBalance() {
    return finalBalance;
  }

  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /**
   * Chuyển đổi mã kiểu giao dịch sang chuỗi mô tả tiếng Việt.
   *
   * @param typeCode mã kiểu giao dịch.
   * @return tên kiểu giao dịch tương ứng.
   */
  public static String getTypeString(int typeCode) {
    switch (typeCode) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /**
   * Trả về tóm tắt thông tin giao dịch dưới dạng chuỗi văn bản.
   *
   * @return chuỗi mô tả chi tiết giao dịch.
   */
  public String getTransactionSummary() {
    logger.debug("Đang xử lý tóm tắt giao dịch cho kiểu: {}", this.type);

    // Sử dụng hằng số Locale và tách dòng để đảm bảo Line Length < 100
    String initialStr = String.format(Locale.US, "%.2f", initialBalance);
    String amountStr = String.format(Locale.US, "%.2f", amount);
    String finalStr = String.format(Locale.US, "%.2f", finalBalance);

    StringBuilder summary = new StringBuilder();
    summary.append("- Kiểu giao dịch: ").append(getTypeString(type))
           .append(". Số dư ban đầu: $").append(initialStr)
           .append(". Số tiền: $").append(amountStr)
           .append(". Số dư cuối: $").append(finalStr)
           .append(".");

    return summary.toString();
  }
}