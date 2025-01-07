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

public class ImportLogPage {
    private WebDriver driver;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[6]/div[1]/div[1]")
    private WebElement btnReport;

    @FindBy(xpath = "//a[@href='/import-report']")
    private WebElement btnImportReport;

    @FindBy(xpath = "//table[@id='table-import-report']")
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

    @FindBy(xpath = "//div[contains(text(),'Nhật ký nhập')]")
    private WebElement txtTitle;


    public ImportLogPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean closeCalendar(WebElement element, WebElement calendar) {
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(calendar));
        txtTitle.click();
        try {
            wait.until(ExpectedConditions.invisibilityOf(calendar));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Calendar không đóng sau thời gian chờ.");
            return false;
        } catch (NoSuchElementException e) {
            System.out.println("Calendar không tồn tại trong DOM.");
            return true;
        }
    }

    public void goToReportPage() {
        waitForPageLoaded();
        btnReport.click();
        btnImportReport.click();
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
            String date = row.findElement(By.cssSelector("#date")).getText();
            String transactionCode = row.findElement(By.cssSelector("#transaction-code")).getText();
            String bookId = row.findElement(By.cssSelector("#book-id")).getText();
            String bookName = row.findElement(By.cssSelector("#book-name")).getText();
            String startQuantity = row.findElement(By.cssSelector("#start-quantity")).getText();
            String startPrice = row.findElement(By.cssSelector("#start-price")).getText();
            String startAmount = row.findElement(By.cssSelector("#opening-sub-total")).getText();
            String importQuantity = row.findElement(By.cssSelector("#import-quantity")).getText();
            String importPrice = row.findElement(By.cssSelector("#import-price")).getText();
            String importAmount = row.findElement(By.cssSelector("#importing-sub-total")).getText();
            String endQuantity = row.findElement(By.cssSelector("#end-quantity")).getText();
            String endAmount = row.findElement(By.cssSelector("#end-sub-total")).getText();
            tableData.add(new String[]{date, transactionCode, bookId, bookName, startQuantity, startPrice, startAmount, importQuantity, importPrice, importAmount, endQuantity, endAmount});
        }
        return tableData;
    }

    public boolean openCalendar(WebElement element, WebElement calendar) {
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(calendar));
        boolean isDisplay = calendar.isDisplayed();
        return isDisplay;
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
