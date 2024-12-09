package selenium.com.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GoodReceivedNotePage {
    private WebDriver driver;
    @FindBy(id = "modal-add-transaction")
    private WebElement modalAddTransaction;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[4]/div[1]/div[1]")
    private WebElement btnTransaction;

    @FindBy(xpath = "//a[contains(text(),'Phiếu nhập')]")
    private WebElement btnImport;

    @FindBy(id = "add-transaction-btn")
    private WebElement btnAddTransaction;

    @FindBy(xpath = "//input[@id='field-delivery-person']")
    private WebElement fieldDeliveryPerson;

    @FindBy(id = "field-transaction-code")
    private WebElement fieldTransactionCode;

    @FindBy(xpath = "//select[@id='select-supplier']")
    private WebElement selectSupplier;

    @FindBy(id = "field-bill-code")
    private WebElement fieldBillCode;

    @FindBy(id = "select-transaction-request")
    private WebElement selectTransactionRequest;

    @FindBy(id = "field-address")
    private WebElement fieldAddress;

    @FindBy(xpath = "//select[@id='select-book']")
    private WebElement selectBook;

    @FindBy(id = "field-actual-quantity")
    private WebElement fieldActualQuantity;

    @FindBy(xpath = "//tbody/tr[1]/td[9]/button[1]")
    private WebElement iconDeleteRow1;

    @FindBy(id = "field-note")
    private WebElement fieldNote;

    @FindBy(id = "field-price")
    private WebElement fieldPrice;

    @FindBy(id = "field-total-price")
    private WebElement txtTotalPrice;

    @FindBy(xpath = "//button[@id='icon-add-row']//*[name()='svg']")
    private WebElement iconAddRow;

    @FindBy(id = "txt-total-amount")
    private WebElement txtTotalAmount;

    @FindBy(id = "btn-save")
    private WebElement btnSave;

    @FindBy(id = "btn-cancel")
    private WebElement btnCancel;

    @FindBy(id = "btn-cancel-confirm")
    private WebElement btnCancelConfirm;

    @FindBy(id = "btn-confirm")
    private WebElement btnSaveConfirm;

    @FindBy(xpath = "//div[@class='MuiAlert-message css-zioonp-MuiAlert-message']")
    private WebElement txtSnackBar;

    @FindBy(xpath = "//body[1]/div[2]/div[3]/div[1]/div[1]/div[1]/form[1]/div[1]/table[1]/tbody[1]/tr[1]/td[5]/div[1]/div[1]/input[1]")
    private WebElement fieldActualQuantity1;

    @FindBy(xpath = "//body[1]/div[2]/div[3]/div[1]/div[1]/div[1]/form[1]/div[1]/table[1]/tbody[1]/tr[2]/td[5]/div[1]/div[1]/input[1]")
    private WebElement fieldActualQuantity2;

    @FindBy(xpath = "//p[@id='field-transaction-code-helper-text']")
    private WebElement helperTransactionCode;

    @FindBy(id = "error-supplier")
    private WebElement msgSupplierError;

    @FindBy(id = "field-bill-code-helper-text")
    private WebElement helperBillCode;

    @FindBy(id = "field-address-helper-text")
    private WebElement helperAddress;

    @FindBy(xpath = "//p[@id='field-delivery-person-helper-text']")
    private WebElement helperDeliveryPerson;

    @FindBy(id = "error-transaction-request")
    private WebElement msgTransactionRequestError;

    @FindBy(id = "field-actual-quantity-helper-text")
    private WebElement msgActualQuantityError;

    @FindBy(xpath = "//select[@id='select-book']")
    private WebElement selectBookRow1;

    @FindBy(id = "confirm-modal")
    private WebElement modalConfirmDelete;

    @FindBy(id = "table-transaction-item")
    private WebElement tableTransactionItem;

    @FindBy(xpath = "//span[@id='msg-book']")
    private WebElement msgBookName;

    @FindBy(xpath = "//tbody/tr[2]/td[2]/select[1]")
    private WebElement bookNameRow2;

    @FindBy(xpath = "//p[@id='field-price-helper-text']")
    private WebElement msgPrice;


    public GoodReceivedNotePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goToTransactionPage() {
        waitForPageLoaded();
        btnTransaction.click();
        btnImport.click();
        waitForPageLoaded();
        btnAddTransaction.click();
    }

    public void fillTransactionForm(String deliveryPerson, int supplier, String billCode, String address, int transactionRequest, String actualQuantity1, String actualQuantity2) throws InterruptedException {
//        fieldTransactionCode.clear();
//        fieldTransactionCode.sendKeys(transactionCode);

        fieldDeliveryPerson.clear();
        fieldDeliveryPerson.sendKeys(deliveryPerson);

        Select supplierDropdown = new Select(selectSupplier);
        supplierDropdown.selectByIndex(supplier);

        fieldBillCode.clear();
        fieldBillCode.sendKeys(billCode);

        Select transactionRequestDropdown = new Select(selectTransactionRequest);
        transactionRequestDropdown.selectByIndex(transactionRequest);

        fieldAddress.clear();
        fieldAddress.sendKeys(address);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement actualQuantity1Element = wait.until(ExpectedConditions.visibilityOf(fieldActualQuantity1));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", actualQuantity1Element);

        actualQuantity1Element.clear();
        actualQuantity1Element.sendKeys(actualQuantity1);

        WebElement actualQuantity2Element = wait.until(ExpectedConditions.visibilityOf(fieldActualQuantity2));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", actualQuantity2Element);

        actualQuantity2Element.clear();
        actualQuantity2Element.sendKeys(actualQuantity2);

        WebElement btnSaveElement = wait.until(ExpectedConditions.elementToBeClickable(btnSave));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnSaveElement);
    }

    public void clickSaveButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement btnSaveElement = wait.until(ExpectedConditions.elementToBeClickable(btnSave));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnSaveElement);
        btnSave.click();
    }

    public String getSnackBarMessage() {
        return txtSnackBar.getText();
    }

    public boolean checkCancelCreateTransaction() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement modal = wait.until(ExpectedConditions.visibilityOf(modalAddTransaction));
        btnCancel.click();

        boolean isModalClosed;
        try {
            wait.until(ExpectedConditions.invisibilityOf(modal));
            isModalClosed = true;
        } catch (Exception e) {
            isModalClosed = false;
        }

        return isModalClosed;
    }

    public String getTransactionCode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(fieldTransactionCode));
        return fieldTransactionCode.getAttribute("value");
    }

    public String getTransactionCodeHelperText() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", helperTransactionCode);
        return helperTransactionCode.getText();
    }

    public String getDeliveryPersonHelperText() {
        return helperDeliveryPerson.getText();
    }

    public String getDeliveryPersonText() {
        return fieldDeliveryPerson.getAttribute("value");
    }

    public void selectSupplier(String supplier) {
        Select select = new Select(selectSupplier);
        select.selectByValue(supplier);
    }

    public void selectBook(String book) {
        Select select = new Select(selectBook);
        select.selectByVisibleText(book);
    }

    public List<String> getListSupplier() {
        Select dropdownSupplier = new Select(selectSupplier);
        List<String> actOptions = new ArrayList<String>();
        for(WebElement option : dropdownSupplier.getOptions()) {
            actOptions.add(option.getText());
        }

        if (!actOptions.isEmpty()) {
            actOptions.remove(0);
        }
        return actOptions;
    }

    public List<String> getListBook() {
        Select dropdownBook = new Select(selectBook);
        List<String> actOptions = new ArrayList<String>();
        for(WebElement option : dropdownBook.getOptions()) {
            actOptions.add(option.getText());
        }
        if (!actOptions.isEmpty()) {
            actOptions.remove(0);
        }
        return actOptions;
    }


    public String getSelectedSupplier() {
        Select select = new Select(selectSupplier);
        return select.getFirstSelectedOption().getText();
    }

    public String getSelectedTransactionRequest() {
        Select select = new Select(selectTransactionRequest);
        return select.getFirstSelectedOption().getText();
    }

    public String getSelectedBook() {
        Select select = new Select(selectBook);
        return select.getFirstSelectedOption().getText();
    }

    public String getSupplierErrorMessage() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", msgSupplierError);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(msgSupplierError));
        return msgSupplierError.getText();
    }

    public String getBillCodeHelperText() {
        return helperBillCode.getText();
    }

    public void setBillCode(String billCode) {
        fieldBillCode.sendKeys(billCode);
    }

    public String getBillCodeValue() {
        return fieldBillCode.getAttribute("value");
    }

    public String getAddressHelperText() {
        return helperAddress.getText();
    }

    public void enterTransactionCode(String code) {
        fieldTransactionCode.sendKeys(code);
    }

    public void enterDeliveryPerson(String name) {
        fieldDeliveryPerson.sendKeys(name);
    }

    public void selectSupplierByIndex(int index) {
        Select select = new Select(selectSupplier);
        select.selectByIndex(index);
    }

    public void enterBillCode(String billCode) {
        fieldBillCode.sendKeys(billCode);
    }

    public void enterAddress(String address) {
        fieldAddress.sendKeys(address);
    }


    public void selectTransactionRequest(String request) {
        Select select = new Select(selectTransactionRequest);
        select.selectByVisibleText(request);
    }

    public void waitForTransactionRequestError() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(msgTransactionRequestError));
    }

    public String getTransactionRequestErrorText() {
        return msgTransactionRequestError.getText();
    }

    public void enterActualQuantity(String quantity) {
        fieldActualQuantity.sendKeys(quantity);
    }

    public String getActualQuantityValue() {
        return fieldActualQuantity.getAttribute("value");
    }

    public String getPriceValue() {
        return fieldPrice.getAttribute("value");
    }

    public void enterPrice(String price) {
        fieldPrice.sendKeys(price);
    }

    public String getAddressText() {
        return fieldAddress.getAttribute("value");
    }

    public int getLenTransactionRequest() {
        Select dropdownSupplier = new Select(selectTransactionRequest);
        List<WebElement> options = dropdownSupplier.getOptions();

        if (!options.isEmpty()) {
            options.remove(0);
        }

        return options.size();
    }

    public String getActualQuantityErrorMessage() {
        return msgActualQuantityError.getText();
    }

    public String getBookNameErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement msgError = wait.until(ExpectedConditions.visibilityOf(msgBookName));
        return msgError.getText();
    }

    public String getPriceErrorMessage() {
        return msgPrice.getText();
    }

    public boolean deleteRowAndConfirm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement dropDownBookRow1 = wait.until(ExpectedConditions.visibilityOf(selectBookRow1));
        String bookName1 = dropDownBookRow1.getText();
        iconDeleteRow1.click();
        btnSaveConfirm.click();
        boolean isRowDeleted = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[name()='path' and contains(@d,'M6 19c0 1.')]")));
        System.out.println("Đã xóa thành công hàng chứa sách: " + bookName1);
        return isRowDeleted;
    }

    public boolean cancelDeleteRow() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement dropDownBookRow1 = wait.until(ExpectedConditions.visibilityOf(selectBookRow1));
        String bookName1 = dropDownBookRow1.getText();
        iconDeleteRow1.click();
        btnCancelConfirm.click();
        boolean isModalClosed = wait.until(ExpectedConditions.invisibilityOf(modalConfirmDelete));
        return isModalClosed;
    }

    public boolean addRow() {
        List<WebElement> rowsBeforeAdd = tableTransactionItem.findElements(By.tagName("tr"));
        int rowsCountBeforeAdd = rowsBeforeAdd.size();
        iconAddRow.click();
        List<WebElement> rowsAfterAdd = tableTransactionItem.findElements(By.tagName("tr"));
        int rowsCountAfterAdd = rowsAfterAdd.size();
        return rowsCountAfterAdd == rowsCountBeforeAdd + 1;
    }

    public boolean isBookNameInRow2ComboBox() {
        String bookNameRow1 = getSelectedBook();
        Select selectRow2 = new Select(bookNameRow2);
        List<WebElement> optionsRow2 = selectRow2.getOptions();

        for (WebElement option : optionsRow2) {
            if (option.getText().trim().equals(bookNameRow1)) {
                return true;
            }
        }
        return false;
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

