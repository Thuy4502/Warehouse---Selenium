package selenium.com.project.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.com.project.testcases.GoodDeliveryNoteTest;
import selenium.com.project.testcases.GoodReceivedNoteTest;

import java.time.Duration;

public class DashboardPage {
    private WebDriver driver;
    LoginPage loginPage;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[7]/div[1]")
    private WebElement btnReport;

    @FindBy(xpath = "//a[@href='/export-report']")
    private WebElement btnExportReport;

    @FindBy(xpath = "//a[contains(text(),'Phiếu nhập')]")
    private WebElement btnImportTransaction;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[5]/div[1]/div[1]")
    private WebElement btnImport;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[6]/div[1]")
    private WebElement btnExport;

    @FindBy(xpath = "//a[contains(text(),'Phiếu xuất')]")
    private WebElement btnExportTransaction;

    @FindBy(xpath = "//a[@href='/import-report']")
    private WebElement btnImportReport;

    @FindBy(xpath = "//a[@href='/inventory-report']")
    private WebElement btnInventoryReport;

    @FindBy(xpath = "//a[@href='/report']")
    private WebElement btnCommonReport;


    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ExportLogPage goToExportLogPage() throws InterruptedException {
        loginPage = new LoginPage(driver);
        loginPage.loginWarehouse("thukho", "123456");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(btnReport));
        btnReport.click();

        wait.until(ExpectedConditions.elementToBeClickable(btnExportReport));
        btnExportReport.click();

        return new ExportLogPage(driver);
    }

    public ImportLogPage goToImportLogPage() throws InterruptedException {
        loginPage = new LoginPage(driver);
        loginPage.loginWarehouse("thukho", "123456");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(btnReport));
        btnReport.click();

        wait.until(ExpectedConditions.elementToBeClickable(btnImportReport));
        btnImportReport.click();

        return new ImportLogPage(driver);
    }

    public InventoryReportPage goToInventoryReportPage() throws InterruptedException {
        loginPage = new LoginPage(driver);
        loginPage.loginWarehouse("thukho", "123456");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(btnReport));
        btnReport.click();

        wait.until(ExpectedConditions.elementToBeClickable(btnInventoryReport));
        btnInventoryReport.click();

        return new InventoryReportPage(driver);
    }

    public LogPage goToLogPage() throws InterruptedException {
        loginPage = new LoginPage(driver);
        loginPage.loginWarehouse("thukho", "123456");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(btnReport));
        btnReport.click();

        wait.until(ExpectedConditions.elementToBeClickable(btnCommonReport));
        btnCommonReport.click();

        return new LogPage(driver);
    }


    public GoodReceivedNotePage goToGoodsReceivedNotePage() throws InterruptedException {
        loginPage = new LoginPage(driver);
        loginPage.loginWarehouse("thukho", "123456");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(btnImport));
        btnImport.click();

        wait.until(ExpectedConditions.elementToBeClickable(btnImportTransaction));
        btnImportTransaction.click();

        return new GoodReceivedNotePage(driver);
    }

    public GoodDeliveryNotePage goToGoodsDeliveryNotePage() throws InterruptedException {
        loginPage = new LoginPage(driver);
        loginPage.loginWarehouse("thukho", "123456");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(btnExport));
        btnExport.click();

        wait.until(ExpectedConditions.elementToBeClickable(btnExportTransaction));
        btnExportTransaction.click();

        return new GoodDeliveryNotePage(driver);
    }


}
