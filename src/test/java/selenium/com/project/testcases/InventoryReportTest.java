package selenium.com.project.testcases;

import helpers.CaptureHelpers;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import selenium.com.browsers.base.BaseSetup;
import selenium.com.project.pages.DashboardPage;
import selenium.com.project.pages.InventoryReportPage;
import selenium.com.project.pages.LoginPage;
import selenium.com.utils.listeners.ReportListener;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static helpers.ExcelHelpers.getDownloadedFileName;

@Listeners(ReportListener.class)
public class InventoryReportTest extends BaseSetup {
    private InventoryReportPage inventoryReportPage;
    private DashboardPage dashboardPage;

    @BeforeClass
    public void setUp() throws InterruptedException {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized.");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        dashboardPage = new DashboardPage(driver);
        inventoryReportPage = dashboardPage.goToInventoryReportPage();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void testOpenStartCalendar() {
        boolean result = inventoryReportPage.checkCalendarStart();
        Assert.assertTrue(result, "Lịch cho ngày bắt đầu chưa được hiển thị");
    }

    @Test(priority = 2)
    public void testOpenEndCalendar() {
        boolean result = inventoryReportPage.checkCalendarEnd();
        Assert.assertTrue(result, "Lịch cho ngày kết thúc chưa được hiển thị\"");
    }

    @Test(priority = 3)
    public void testCloseStartCalendar() {
        boolean result = inventoryReportPage.checkCloseCalendarStart();
        Assert.assertTrue(result, "Lịch cho ngày bắt đầu chưa được đóng\"");
    }

    @Test(priority = 4)
    public void testCloseEndCalendar() {
        boolean result = inventoryReportPage.checkCloseCalendarEnd();
        Assert.assertTrue(result, "Lịch cho ngày kết thúc chưa được đóng");
    }

    @Test(priority = 5)
    public void testDownloadedExcelFile() throws InterruptedException {
        String expectedFileName = "InventoryReport";
        String expectedFilePath = "C:\\Users\\thith\\Downloads\\InventoryReport.xlsx";

        inventoryReportPage.clickExportExcelBtn();
        Thread.sleep(3000);
        String downloadedFilePath = getDownloadedFileName(expectedFileName);

        if (downloadedFilePath != null) {
            System.out.println("Tệp đã tải xuống: " + downloadedFilePath);
        } else {
            System.out.println("Không tìm thấy tệp trong thư mục Downloads.");
        }

        Assert.assertEquals(downloadedFilePath, expectedFilePath);
    }

    @Test(priority = 6)
    public void testEndQuantity() {
        List<String[]> tableData = inventoryReportPage.getTableData();
        List<String> errors = checkEndQuantity(tableData);

        if ((!errors.isEmpty())) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }

    @Test(priority = 7)
    public void testEndAmount() {
        List<String[]> tableData = inventoryReportPage.getTableData();
        List<String> errors = checkEndValue(tableData);
        if (!errors.isEmpty()) {
            Assert.fail("Có lỗi trong dữ liệu:\n" + String.join("\n", errors));
        }
    }

    private List<String> checkEndQuantity(List<String[]> tableData) {
        List<String> errors = new ArrayList<>();
        for(int i = 0; i < tableData.size(); i++) {
            String[] row = tableData.get(i);
            try {
                int bookName = Integer.parseInt(row[1]);
                int startQuan = Integer.parseInt(row[2]);
                int importQuan = Integer.parseInt(row[3]);
                int exportQuan = Integer.parseInt(row[4]);
                int endQuan = Integer.parseInt(row[5]);
                double endPrice = parseCurrencyToDouble(row[6]);

                if(endQuan != (startQuan + importQuan - exportQuan)) {
                    errors.add("Sai sót ở dòng " + (i + 1) + "Sách :" + bookName + ", Giá trị thực tế = " + endQuan + ", Giá trị mong đợi = " + (startQuan + importQuan - exportQuan));
                }
            } catch (NumberFormatException e) {
                errors.add("Dữ liệu không hợp lệ ở dòng " + (i + 1) + ": " + e.getMessage());
            }

        }
        return errors;
    }

    private List<String> checkEndValue(List<String[]> tableData) {
        List<String> errors = new ArrayList<>();
        for(int i = 0; i < tableData.size(); i++) {
            String[] row = tableData.get(i);
            try {
                String bookName = row[1];
                int endQuan = Integer.parseInt(row[5]);
                double endPrice = parseCurrencyToDouble(row[6]);
                double endAmount = parseCurrencyToDouble(row[7].trim());
                System.out.println("Gía trị thực tế " + endAmount);
                System.out.println("Gía trị mong đợi " + endQuan*endPrice);

                if(endAmount != (endQuan*endPrice)) {
                    errors.add("Sai sót ở dòng " + (i + 1) + "Sách :" + bookName + ", Giá trị thực tế = " + endAmount + ", Giá trị mong đợi = " + (endQuan*endPrice));
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
        return Double.parseDouble(numericValue);
    }
}
