package bank_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp quản lý các hoạt động của ngân hàng bao gồm đọc dữ liệu và xuất báo cáo khách hàng.
 */
public class Bank {
  private static final Logger logger = LoggerFactory.getLogger(Bank.class);
  private static final String ID_REGEX = "\\d{9}";

  private List<Customer> customerList;

  public Bank() {
    this.customerList = new ArrayList<>();
  }

  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * Cập nhật danh sách khách hàng.
   *
   * @param customerList danh sách khách hàng mới.
   */
  public void setCustomerList(List<Customer> customerList) {
    this.customerList = (customerList == null) ? new ArrayList<>() : customerList;
  }

  /**
   * Đọc danh sách khách hàng từ InputStream.
   *
   * @param inputStream luồng dữ liệu đầu vào.
   */
  public void readCustomerList(InputStream inputStream) {
    if (inputStream == null) {
      logger.warn("InputStream đầu vào bị null.");
      return;
    }

    logger.info("Bắt đầu đọc dữ liệu khách hàng...");
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      Customer currentCustomer = null;

      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }
        currentCustomer = parseLine(line, currentCustomer);
      }
    } catch (IOException e) {
      logger.error("Lỗi khi đọc luồng dữ liệu: {}", e.getMessage());
    }
  }

  /**
   * Hàm hỗ trợ phân tách logic đọc từng dòng để giảm độ phức tạp (Nested IF).
   */
  private Customer parseLine(String line, Customer currentCustomer) {
    int lastSpaceIndex = line.lastIndexOf(' ');
    if (lastSpaceIndex <= 0) {
      return currentCustomer;
    }

    String lastToken = line.substring(lastSpaceIndex + 1).trim();

    // Nếu dòng là thông tin khách hàng (kết thúc bằng ID 9 số)
    if (lastToken.matches(ID_REGEX)) {
      String name = line.substring(0, lastSpaceIndex).trim();
      Customer newCustomer = new Customer(Long.parseLong(lastToken), name);
      customerList.add(newCustomer);
      return newCustomer;
    }

    // Nếu dòng là thông tin tài khoản
    if (currentCustomer != null) {
      processAccountLine(line, currentCustomer);
    }
    return currentCustomer;
  }

  private void processAccountLine(String line, Customer customer) {
    String[] parts = line.split("\\s+");
    if (parts.length < 3) {
      return;
    }

    long accNum = Long.parseLong(parts[0]);
    String type = parts[1];
    double balance = Double.parseDouble(parts[2]);

    if (Account.CHECKING_TYPE.equals(type)) {
      customer.addAccount(new CheckingAccount(accNum, balance));
    } else if (Account.SAVINGS_TYPE.equals(type)) {
      customer.addAccount(new SavingsAccount(accNum, balance));
    }
  }

  /**
   * Trả về thông tin khách hàng sắp xếp theo ID tăng dần.
   */
  public String getCustomersInfoByIdOrder() {
    return customerList.stream()
        .sorted(Comparator.comparingLong(Customer::getIdNumber))
        .map(Customer::getCustomerInfo)
        .collect(Collectors.joining("\n"));
  }

  /**
   * Trả về thông tin khách hàng sắp xếp theo Tên (sau đó đến ID).
   */
  public String getCustomersInfoByNameOrder() {
    return customerList.stream()
        .sorted(Comparator.comparing(Customer::getFullName)
            .thenComparingLong(Customer::getIdNumber))
        .map(Customer::getCustomerInfo)
        .collect(Collectors.joining("\n"));
  }
}