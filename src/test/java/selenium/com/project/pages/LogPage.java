package selenium.com.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LogPage {
    WebDriver driver;
    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[6]/div[1]/div[1]")
    private WebElement btnReport;

    @FindBy(xpath = "//a[@href='/report']")
    private WebElement btnCommonReport;

    @FindBy(xpath = "//table[@id='table-report']")
    private WebElement tableLog;

    @FindBy(css = "#start-date")
    private WebElement startDate;

    @FindBy(css = "#end-date")
    private WebElement endDate;

    @FindBy(xpath = "//body/div[@id='root']/div[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]")
    private WebElement calendarStart;

    @FindBy(xpath = "//body/div[@id='root']/div[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[2]/div[3]/div[2]/div[1]/div[1]/div[1]")
    private WebElement calendarEnd;

    @FindBy(xpath = "//button[@id='btn-export-excel']")
    private WebElement btnExportExcel;

    public LogPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goToReportPage() {
        waitForPageLoaded();
        btnReport.click();
        btnCommonReport.click();
        waitForPageLoaded();
    }

    public List<WebElement> getTableRows() {
        List<WebElement> rows = tableLog.findElements(By.tagName("tr"));
        return rows;
    }

    public List<String[]> getTableData() {
        List<String[]> tableData = new ArrayList<>();
        List<WebElement> rows = getTableRows();

        for (int i = 2; i < rows.size(); i++) {
            WebElement row = rows.get(i);

            // Sử dụng phương thức helper để kiểm tra và thay thế giá trị trống.
            String date = getTextOrDefault(row, "#date");
            String transactionCode = getTextOrDefault(row, "#transaction-code");
            String bookId = getTextOrDefault(row, "#book-id");
            String bookName = getTextOrDefault(row, "#book-name");
            String startQuantity = getTextOrDefault(row, "#start-quantity");
            String startPrice = getTextOrDefault(row, "#start-price");
            String startAmount = getTextOrDefault(row, "#opening-sub-total");
            String importQuantity = getTextOrDefault(row, "#import-quantity");
            String importPrice = getTextOrDefault(row, "#import-price");
            String importAmount = getTextOrDefault(row, "#importing-sub-total");
            String exportQuantity = getTextOrDefault(row, "#export-quantity");
            String exportPrice = getTextOrDefault(row, "#export-price");
            String exportAmount = getTextOrDefault(row, "#exporting-sub-total");
            String endQuantity = getTextOrDefault(row, "#end-quantity");
            String endAmount = getTextOrDefault(row, "#end-sub-total");

            tableData.add(new String[]{
                    date, transactionCode, bookId, bookName,
                    startQuantity, startPrice, startAmount,
                    importQuantity, importPrice, importAmount,
                    exportQuantity, exportPrice, exportAmount,
                    endQuantity, endAmount
            });
        }
        return tableData;
    }

    private String getTextOrDefault(WebElement row, String cssSelector) {
        String text = row.findElement(By.cssSelector(cssSelector)).getText();
        return text.isEmpty() ? "0" : text;
    }


    public boolean openCalendar(WebElement element, WebElement calendar) {
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(calendar));
        boolean isDisplay = calendar.isDisplayed();
        return isDisplay;
    }

    public boolean checkCalendarStart() {
        return openCalendar(startDate, calendarStart);
    }

    public boolean checkCalendarEnd() {
        return openCalendar(endDate, calendarEnd);
    }

    public void sendKeysStartDate(String date) {
        startDate.sendKeys(date);
    }

    public void sendKeysEndDate(String date) {
        endDate.sendKeys(date);
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
