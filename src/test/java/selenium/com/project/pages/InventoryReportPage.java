package selenium.com.project.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class InventoryReportPage {
    private WebDriver driver;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[6]/div[1]/div[1]")
    private WebElement btnReport;

    @FindBy(xpath = "//a[@href='/inventory-report']")
    private WebElement btnInventoryReport;

    @FindBy(css = "#start-date")
    private WebElement startDate;

    @FindBy(css = "#end-date")
    private WebElement endDate;

    @FindBy(css = "#table-inventory-report")
    private WebElement tableLog;

    @FindBy(xpath = "//body/div[@id='root']/div[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]")
    private WebElement calendarStart;

    @FindBy(xpath = "//body/div[@id='root']/div[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[2]/div[3]/div[2]/div[1]/div[1]/div[1]")
    private WebElement calendarEnd;

    @FindBy(xpath = "//button[@id='btn-export-excel']")
    private WebElement btnExportExcel;

    @FindBy(xpath = "//div[contains(text(),'Báo cáo tồn kho')]")
    private WebElement txtTitle;


    public InventoryReportPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goToReportPage() {
        waitForPageLoaded();
        btnReport.click();
        btnInventoryReport.click();
        waitForPageLoaded();
    }

    public List<WebElement> getTableRows() {
        List<WebElement> rows = tableLog.findElements(By.tagName("tr"));
        System.out.println("Kích thước bảng " + rows.size());
        return rows;
    }

    public boolean openCalendar(WebElement element, WebElement calendar) {
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(calendar));
        boolean isDisplay = calendar.isDisplayed();
        return isDisplay;
    }

    public boolean closeCalendar(WebElement element, WebElement calendar) {
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(calendar));
        txtTitle.click();
        try {
            // Chờ phần tử ẩn đi
            wait.until(ExpectedConditions.invisibilityOf(calendar));
            return true; // Calendar đã đóng
        } catch (TimeoutException e) {
            System.out.println("Calendar không đóng sau thời gian chờ.");
            return false; // Calendar vẫn hiển thị
        } catch (NoSuchElementException e) {
            System.out.println("Calendar không tồn tại trong DOM.");
            return true; // Calendar đã bị loại khỏi DOM
        }
    }

    public List<String[]> getTableData() {
        List<String[]> tableData = new ArrayList<>();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(tableLog));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tr")));

        List<WebElement> rows = getTableRows();

        if (rows.isEmpty()) {
            System.out.println("Bảng không chứa dữ liệu.");
            return tableData;
        }

        for (int i = 2; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            try {
                String isbn = row.findElement(By.cssSelector("#isbn")).getText();
                String bookName = row.findElement(By.cssSelector("#book-name")).getText();
                String startQuantity = row.findElement(By.cssSelector("#start-quantity")).getText();
                String importQuantity = row.findElement(By.cssSelector("#import-quantity")).getText();
                String exportQuantity = row.findElement(By.cssSelector("#export-quantity")).getText();
                String endQuantity = row.findElement(By.cssSelector("#end-quantity")).getText();
                String endPrice = row.findElement(By.cssSelector("#end-price")).getText();
                String endAmount = row.findElement(By.cssSelector("#end-amount")).getText();

                tableData.add(new String[]{isbn, bookName, startQuantity, importQuantity, exportQuantity, endQuantity, endPrice, endAmount});
            } catch (NoSuchElementException e) {
                System.out.println("Dữ liệu không hợp lệ ở dòng " + (i + 1) + ": " + e.getMessage());
            }
        }
        return tableData;
    }

    public boolean checkCloseCalendarStart() {
        return closeCalendar(startDate, calendarStart);
    }

    public boolean checkCloseCalendarEnd() {
        return closeCalendar(endDate, calendarEnd);
    }

    public boolean checkCalendarStart() {
        return openCalendar(startDate, calendarStart);
    }

    public boolean checkCalendarEnd() {
        return openCalendar(endDate, calendarEnd);
    }

    public void clickExportExcelBtn() {
        btnExportExcel.click();
    }


    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver webDriver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(expectation);
        } catch (InterruptedException e) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");

        }
    }

}
