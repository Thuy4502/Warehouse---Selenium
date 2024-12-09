package selenium.com.project.testcases;

import helpers.CaptureHelpers;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import selenium.com.browsers.base.BaseSetUp;
import selenium.com.project.pages.ImportLogPage;
import selenium.com.project.pages.LoginPage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static helpers.ExcelHelpers.getDownloadedFileName;

public class ImportLogTest extends BaseSetUp {
    private ImportLogPage imporLogPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        imporLogPage = new ImportLogPage(driver);
        loginPage.loginWarehouse("thukho", "123456");
        imporLogPage.goToReportPage();
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
    }

    @Test(priority = 1)
    public void testBeginningAmount() {
        List<String[]> tableData = imporLogPage.getTableData();
        List<String> errors = validateAmounts(tableData);

        if (!errors.isEmpty()) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }



    @Test(priority = 3)
    public void testEndAmount() {
        List<String[]> tableData = imporLogPage.getTableData();
        List<String> errors = validateEndAmount(tableData);

        if (!errors.isEmpty()) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }

    @Test(priority = 4)
    public void testEndQuantity() {
        List<String[]> tableData = imporLogPage.getTableData();
        List<String> errors = validateEndQuantity(tableData);

        if (!errors.isEmpty()) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }

    @Test(priority = 5)
    public void checkInventoryBookConsistency() {
        List<String[]> tableData = imporLogPage.getTableData();
        List<String> errors = verifyInventoryBookConsistency(tableData);

        if (!errors.isEmpty()) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }

    @Test(priority = 6)
    public void testStartCalendar() {
        boolean result = imporLogPage.checkCalendarStart();
        Assert.assertTrue(result, "Lịch chưa được hiển thị");
    }

    @Test(priority = 7)
    public void testEndCalendar() {
        boolean result = imporLogPage.checkCalendarEnd();
        Assert.assertTrue(result, "Lịch chưa được hiển thị");
    }

    @Test(priority = 8)
    public void testFilterByDate() {
        String startDate = "2024-11-25";
        String endDate = "2024-11-27";

        if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
            Assert.fail("Start date or end date is null or empty");
            return;
        }

        imporLogPage.sendKeysStartDate(startDate);
        imporLogPage.sendKeysEndDate(endDate);

        List<String[]> tableData = imporLogPage.getTableData();
        List<String> errors = verifyFilterByDate(tableData, startDate, endDate);
        if (!errors.isEmpty()) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }

    @Test(priority = 9)
    public void checkDownloadedExcelFile() throws InterruptedException {
        String expectedFileName = "ImportHistory";
        String expectedFilePath = "C:\\Users\\thith\\Downloads\\ImportHistory.xlsx";

        imporLogPage.clickExportExcelBtn();
        Thread.sleep(3000);
        String downloadedFilePath = getDownloadedFileName(expectedFileName);

        if (downloadedFilePath != null) {
            System.out.println("Tệp đã tải xuống: " + downloadedFilePath);
        } else {
            System.out.println("Không tìm thấy tệp trong thư mục Downloads.");
        }

        Assert.assertEquals(downloadedFilePath, expectedFilePath);
    }

    private List<String> validateAmounts(List<String[]> tableData) {
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < tableData.size(); i++) {
            String[] row = tableData.get(i);
            try {
                int startQuan = Integer.parseInt(row[4].trim());
                double price = parseCurrencyToDouble(row[5]);
                double actualAmount = parseCurrencyToDouble(row[6]);

                if (actualAmount != startQuan * price) {
                    errors.add("Sai sót ở dòng " + (i + 1) + ": Giá trị thực tế = " + actualAmount + ", Giá trị mong đợi = " + (startQuan * price));
                }
            } catch (NumberFormatException e) {
                errors.add("Dữ liệu không hợp lệ ở dòng " + (i + 1) + ": " + e.getMessage());
            }
        }

        return errors;
    }

    private double parseCurrencyToDouble(String currency) {
        String numericValue = currency.substring(0, currency.length() - 2);
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
            try {
                int startQuan = Integer.parseInt(row[4].trim());
                int importQuan = Integer.parseInt(row[7].trim());
                int actualQuan = Integer.parseInt(row[10].trim());

                if (actualQuan != startQuan + importQuan) {
                    errors.add("Sai sót ở dòng " + (i + 1) + ": Giá trị thực tế = " + actualQuan + ", Giá trị mong đợi = " + (startQuan + importQuan));
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
            try {
                double startPrice = parseCurrencyToDouble(row[5].trim());
                double importPrice = parseCurrencyToDouble(row[8].trim());
                double endQuantity = Integer.parseInt(row[10].trim());
                double actualAmount = parseCurrencyToDouble(row[11].trim());
                double expectedAmount = ((startPrice + importPrice) / 2) * endQuantity;

                if (Math.abs(actualAmount - expectedAmount) > tolerance) {
                    errors.add("Sai sót ở dòng " + (i + 1) + ": Giá trị thực tế = " + actualAmount + ", Giá trị mong đợi = " + expectedAmount);
                }
            } catch (NumberFormatException e) {
                errors.add("Dữ liệu không hợp lệ ở dòng " + (i + 1) + ": " + e.getMessage());
            }
        }
        return errors;
    }

    private List<String> verifyInventoryBookConsistency(List<String[]> tableData) {
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < tableData.size() - 1; i++) {
            String[] currentRow = tableData.get(i);
            String currentStartQuantity = currentRow[4].trim(); // Tồn đầu kỳ
            String currentBookId = currentRow[2].trim(); // Mã sách
            String date = currentRow[0].trim();
            String transactionCode = currentRow[1].trim();

            if (currentStartQuantity.isEmpty()) {
                errors.add("Dữ liệu không hợp lệ ở ngày " + (date) + ": Tồn đầu kỳ bị rỗng.");
                continue;
            }

            String previousEndQuantity = null;

            for (int j = i + 1; j < tableData.size(); j++) {
                String[] previousRow = tableData.get(j);
                String previousBookId = previousRow[2].trim(); // Mã sách dòng trước

                // So sánh mã sách
                if (currentBookId.equals(previousBookId)) {
                    previousEndQuantity = previousRow[10].trim(); // Tồn cuối kỳ
                    System.out.println("  Dòng so sánh: " + (j + 1));
                    System.out.println("    Mã sách trùng: " + previousBookId);
                    System.out.println("    Tồn cuối kỳ: " + previousEndQuantity);
                    break;
                }
            }

            if (previousEndQuantity != null && !previousEndQuantity.isEmpty()) {
                try {

                    int startQuantity = Integer.parseInt(currentStartQuantity);
                    int endQuantity = Integer.parseInt(previousEndQuantity);

                    System.out.println("  So sánh giá trị:");
                    System.out.println("    Tồn đầu kỳ (đã chuyển đổi): " + startQuantity);
                    System.out.println("    Tồn cuối kỳ (đã chuyển đổi): " + endQuantity);

                    if (startQuantity != endQuantity) {
                        errors.add("Sai sót ở sản phẩm mã " + currentBookId + ": " + ", Ngày :" + date +", số phiếu nhập : " + transactionCode
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

    @AfterMethod
    public void takeScreenshot(ITestResult result) throws InterruptedException {
        Thread.sleep(1000);
        String className = result.getTestClass().getName();
        className = className.substring(className.lastIndexOf('.') + 1);
        if (ITestResult.FAILURE == result.getStatus())
            CaptureHelpers.captureScreenshot(driver, className +"_" + result.getName());
    }

}
