package selenium.com.project.testcases;

import org.testng.Assert;
import org.testng.annotations.*;
import selenium.com.browsers.base.BaseSetup;
import selenium.com.project.pages.DashboardPage;
import selenium.com.project.pages.LogPage;
import selenium.com.utils.listeners.ReportListener;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static helpers.ExcelHelpers.getDownloadedFileName;

@Listeners(ReportListener.class)
public class LogTest extends BaseSetup {
    private LogPage logPage;
    private DashboardPage dashboardPage;

    @BeforeClass
    public void setUp() throws InterruptedException {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized.");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        dashboardPage = new DashboardPage(driver);
        logPage = dashboardPage.goToLogPage();
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void testStartCalendar() {
        boolean result = logPage.checkCalendarStart();
        Assert.assertTrue(result, "Lịch chưa được hiển thị");
    }

    @Test(priority = 2)
    public void testEndCalendar() {
        boolean result = logPage.checkCalendarEnd();
        Assert.assertTrue(result, "Lịch chưa được hiển thị");
    }

    @Test(priority = 3)
    public void testCloseStartCalendar() {
        boolean result = logPage.checkCloseCalendarStart();
        Assert.assertTrue(result, "Lịch cho ngày bắt đầu chưa được đóng\"");
    }

    @Test(priority = 4)
    public void testCloseEndCalendar() {
        boolean result = logPage.checkCloseCalendarEnd();
        Assert.assertTrue(result, "Lịch cho ngày kết thúc chưa được đóng");
    }

    @Test(priority = 5)
    public void checkBeginningAmount() {
        List<String[]> tableData = logPage.getTableData();
        List<String> errors = validateAmounts(tableData);

        if (!errors.isEmpty()) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }

    @Test(priority = 6)
    public void checkImportingAmount() {
        List<String[]> tableData = logPage.getTableData();
        List<String> errors = validateAmounts(tableData);

        if (!errors.isEmpty()) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }

    @Test(priority = 7)
    public void checkEndQuantity() {
        List<String[]> tableData = logPage.getTableData();
        List<String> errors = validateEndQuantity(tableData);

        if (!errors.isEmpty()) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }

    @Test(priority = 8)
    public void checkEndAmount() {
        List<String[]> tableData = logPage.getTableData();
        List<String> errors = validateEndAmount(tableData);

        if (!errors.isEmpty()) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }

    @Test(priority = 9)
    public void checkDownloadedExcelFile() throws InterruptedException {
        String expectedFileName = "Transaction_Report";
        String expectedFilePath = "C:\\Users\\thith\\Downloads\\Transaction_Report.xlsx";

        logPage.clickExportExcelBtn();
        Thread.sleep(3000);
        String downloadedFilePath = getDownloadedFileName(expectedFileName);

        if (downloadedFilePath != null) {
            System.out.println("Tệp đã tải xuống: " + downloadedFilePath);
        } else {
            System.out.println("Không tìm thấy tệp trong thư mục Downloads.");
        }

        Assert.assertEquals(downloadedFilePath, expectedFilePath);
    }

    @Test(priority = 10)
    public void checkInventoryBookConsistency() {
//        Lấy dữ liệu từ bảng nhật ký kho chung để kiểm tra
        List<String[]> tableData = logPage.getTableData();
//        Gọi hàm để kiểm tra trong bảng có lỗi không, nếu có
//        lỗi đó sẽ được lưu vào list error
        List<String> errors = verifyInventoryBookConsistency(tableData);
        //Nếu trong bảng có lỗi tính toán
        if (!errors.isEmpty()) {
//            Assert.fail() làm cho test case thất bại, và hiển thị
//            thông báo lỗi đã truyền vào
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }

    @Test(priority = 11)
    public void testFilterByDate() {
        String startDate = "2024-11-25";
        String endDate = "2024-11-27";

        logPage.sendKeysStartDate(startDate);
        logPage.sendKeysEndDate(endDate);

        List<String[]> tableData = logPage.getTableData();
        List<String> errors = verifyFilterByDate(tableData, startDate, endDate);
        if (!errors.isEmpty()) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }



    private double parseCurrencyToDouble(String currency) {
        String numericValue="";
        if(!currency.equals("0")) {
            numericValue = currency.substring(0, currency.length() - 2);
        }
        else {
            numericValue = currency;
        }
        if (numericValue.contains(".")) {
            numericValue = numericValue.replace(".", "");
        }
        System.out.println("Số   : " + numericValue);

        return Double.parseDouble(numericValue);
    }

    private List<String> validateEndQuantity(List<String[]> tableData) {
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < tableData.size(); i++) {
            String[] row = tableData.get(i);
            String currentBookId = row[2].trim();
            String date = row[0].trim();
            String transactionCode = row[1].trim();

            try {
                int startQuan = Integer.parseInt(row[4].trim());
                int importQuan = Integer.parseInt(row[7].trim());
                int exportQuan = Integer.parseInt(row[10].trim());
                int actualQuan = Integer.parseInt(row[13].trim());

                int expectedQuan = startQuan + importQuan - exportQuan;
                if (actualQuan != expectedQuan) {
                    errors.add("Sai sót ở sản phẩm mã " + currentBookId + ": " + ", Ngày :" + date +", số phiếu nhập : " + transactionCode
                            + ": Giá trị thực tế = " + actualQuan + ", Giá trị mong đợi = " + expectedQuan);
                }
            } catch (NumberFormatException e) {
                errors.add("Dữ liệu không hợp lệ ở dòng " + (i + 1) + ": " + e.getMessage());
            }
        }

        return errors;
    }

    private List<String> validateEndAmount(List<String[]> tableData) {
        List<String> errors = new ArrayList<>();
        double tolerance = 0.01;
        for (int i = 0; i < tableData.size(); i++) {
            String[] row = tableData.get(i);
            String currentBookId = row[2].trim();
            String date = row[0].trim();
            String transactionCode = row[1].trim();
            try {
                double startPrice = parseCurrencyToDouble(row[5].trim());
                double importPrice = parseCurrencyToDouble(row[8].trim());
                double price;
                double exportPrice = parseCurrencyToDouble(row[11].trim());
                double endQuantity = Integer.parseInt(row[13].trim());
                double actualAmount = parseCurrencyToDouble(row[14].trim());
                System.out.println("Gía nhập "+ importPrice);
                System.out.println("Gía xuất " + exportPrice);


                if(importPrice == 0.0) {
                    price = exportPrice;
                }
                else {
                    price = importPrice;
                }
                double expectedAmount = ((startPrice + price) / 2) * endQuantity;

                if (Math.abs(actualAmount - expectedAmount) > tolerance) {
                    errors.add("Sai sót ở sản phẩm mã " + currentBookId + ": " + ", Ngày :" + date +", số phiếu nhập : " + transactionCode
                            + ": Giá trị thực tế = " + actualAmount + ", Giá trị mong đợi = " + expectedAmount);
                }
            } catch (NumberFormatException e) {
                errors.add("Dữ liệu không hợp lệ ở dòng " + (i + 1) + ": " + e.getMessage());
            }
        }
        return errors;
    }

    private List<String> validateAmounts(List<String[]> tableData) {
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < tableData.size(); i++) {
            String[] row = tableData.get(i);
            String currentBookId = row[2].trim();
            String date = row[0].trim();
            String transactionCode = row[1].trim();
            try {
                int startQuan = Integer.parseInt(row[4].trim());
                double price = parseCurrencyToDouble(row[5]);
                double actualAmount = parseCurrencyToDouble(row[6]);

                if (actualAmount != startQuan * price) {
                    errors.add("Sai sót ở sản phẩm mã " + currentBookId + ": " + ", Ngày :" + date +", số phiếu nhập : " + transactionCode
                            + ": Giá trị thực tế = " + actualAmount + ", Giá trị mong đợi = " + (startQuan * price));
                }
            } catch (NumberFormatException e) {
                errors.add("Dữ liệu không hợp lệ ở dòng " + (i + 1) + ": " + e.getMessage());

            }
        }

        return errors;
    }

    private List<String> verifyInventoryBookConsistency(List<String[]> tableData) {
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < tableData.size(); i++) {
            String[] currentRow = tableData.get(i);
            String currentStartQuantity = currentRow[4].trim();
            String currentBookId = currentRow[2].trim();
            String date = currentRow[0].trim();
            String transactionCode = currentRow[1].trim();
            if (i==0) {
                System.out.println(currentStartQuantity);
            }

            if (currentStartQuantity.isEmpty()) {
                errors.add("Dữ liệu không hợp lệ ở ngày " + date + ": Tồn đầu kỳ bị rỗng.");
                continue;
            }

            // Tìm dòng gần nhất có cùng mã sách
            String previousEndQuantity = null;
            for (int j = i + 1; j < tableData.size(); j++) { // Duyệt ngược từ dòng trước
                String[] previousRow = tableData.get(j);
                String previousBookId = previousRow[2].trim();

                if (currentBookId.equals(previousBookId)) { // Mã sách trùng nhau
                    previousEndQuantity = previousRow[13].trim();
                    System.out.println("  Dòng so sánh: " + (j + 1));
                    System.out.println("    Mã sách trùng: " + previousBookId);
                    System.out.println("    Tồn cuối kỳ: " + previousEndQuantity);
                    break; // Chỉ lấy dòng gần nhất
                }
            }

            if (previousEndQuantity != null && !previousEndQuantity.isEmpty()) {
                try {
                    int startQuantity = Integer.parseInt(currentStartQuantity);
                    int endQuantity = Integer.parseInt(previousEndQuantity);

                    System.out.println("GÍA TRỊ TỒN TRƯỚC ĐÓ " + endQuantity);
                    System.out.println("  So sánh giá trị:");
                    System.out.println("    Tồn đầu kỳ (đã chuyển đổi): " + startQuantity);
                    System.out.println("    Tồn cuối kỳ (đã chuyển đổi): " + endQuantity);

                    if (startQuantity != endQuantity) {
                        errors.add("Sai sót ở sản phẩm mã " + currentBookId + ": " + ", Ngày: " + date + ", số phiếu nhập: " + transactionCode
                                + " Tồn đầu kỳ = " + startQuantity
                                + ", Tồn cuối kỳ trước đó = " + endQuantity);
                    }
                } catch (NumberFormatException e) {
                    errors.add("Dữ liệu không hợp lệ ở sản phẩm mã " + currentBookId + ": " + e.getMessage());
                }
            }
        }

        return errors;
    }


    private List<String> verifyFilterByDate(List<String[]> tableData, String startDate, String endDate) {
        List<String> errors = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        for (int i = 0; i < tableData.size(); i++) {
            String[] currentRow = tableData.get(i);
            String dateString = currentRow[0].trim();

            try {
                LocalDate date = LocalDate.parse(dateString, formatter);
                if (date.isBefore(start) || date.isAfter(end)) {
                    errors.add("Row " + (i + 1) + " has a date (" + dateString + ") outside the range.");
                }
            } catch (Exception e) {
                errors.add("Row " + (i + 1) + " has an invalid date format: " + dateString);
            }
        }

        return errors;
    }


}
