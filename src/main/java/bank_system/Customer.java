package bank_system;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp Customer đại diện cho một khách hàng của ngân hàng.
 * Quản lý thông tin định danh và danh sách các tài khoản sở hữu.
 */
public class Customer {
  private static final Logger logger = LoggerFactory.getLogger(Customer.class);

  private long idNumber;
  private String fullName;
  private List<Account> accountList;

  /**
   * Constructor không tham số.
   */
  public Customer() {
    this(0L, "");
  }

  /**
   * Khởi tạo khách hàng mới với CMND và họ tên.
   *
   * @param idNumber số CMND/CCCD của khách hàng.
   * @param fullName họ và tên đầy đủ.
   */
  public Customer(long idNumber, String fullName) {
    this.idNumber = idNumber;
    this.fullName = fullName;
    this.accountList = new ArrayList<>();
  }

  public long getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(long idNumber) {
    this.idNumber = idNumber;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public List<Account> getAccountList() {
    return accountList;
  }

  /**
   * Cập nhật danh sách tài khoản. Nếu danh sách truyền vào null, khởi tạo danh sách mới.
   *
   * @param accountList danh sách tài khoản mới.
   */
  public void setAccountList(List<Account> accountList) {
    this.accountList = (accountList == null) ? new ArrayList<>() : accountList;
  }

  /**
   * Thêm tài khoản cho khách hàng nếu chưa tồn tại trong danh sách.
   *
   * @param account đối tượng tài khoản cần thêm.
   */
  public void addAccount(Account account) {
    if (account != null && !accountList.contains(account)) {
      accountList.add(account);
      logger.debug("Đã thêm tài khoản {} cho khách hàng {}", 
          account.getAccountNumber(), idNumber);
    }
  }

  /**
   * Xóa tài khoản khỏi danh sách sở hữu của khách hàng.
   *
   * @param account đối tượng tài khoản cần xóa.
   */
  public void removeAccount(Account account) {
    if (account != null) {
      accountList.remove(account);
    }
  }

  /**
   * Trả về thông tin cơ bản của khách hàng dưới dạng văn bản.
   *
   * @return chuỗi thông tin định danh.
   */
  public String getCustomerInfo() {
    return new StringBuilder()
        .append("Số CMND: ")
        .append(idNumber)
        .append(". Họ tên: ")
        .append(fullName)
        .append(".")
        .toString();
  }
}