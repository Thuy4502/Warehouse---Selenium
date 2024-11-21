package selenium.com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    By modalAddTransaction = By.xpath("//div[@id='modal-add-transaction']");
    By btnTransaction = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[4]/div[1]/div[1]");
    By btnImport = By.xpath("//a[contains(text(),'Phiếu nhập')]");
    By btnAddTransaction = By.xpath("//button[@id='add-transaction-btn']");
    By fieldDeliveryPerson = By.xpath("//input[@id='field-delivery-person']");
    By fieldTransactionCode = By.xpath("//input[@id='field-transaction-code']");
    By selectSupplier = By.xpath("//select[@id='select-supplier']");
    By fieldBillCode = By.xpath("//input[@id='field-bill-code']");
    By selectTransactionRequest = By.xpath("//select[@id='select-transaction-request']");
    By fieldAddress = By.xpath("//input[@id='field-address']");
    By selectBook = By.xpath("//div[@id='select-book']");
    By fieldActualQuantity = By.xpath("//input[@id='field-actual-quantity']");
    By iconDeleteRow1 = By.xpath("//tbody/tr[1]/td[9]/button[1]");
    By fieldNote = By.xpath("//input[@id='field-note']");
    By fieldPrice = By.xpath("//input[@id='field-price']");
    By txtTotalPrice = By.xpath("//input[@id='field-total-price']");
    By iconAddRow = By.xpath("//button[@id='icon-add-row']//*[name()='svg']");
    By txtTotalAmount = By.xpath("//p[@id='txt-total-amount']");
    By btnSave = By.xpath("//button[@id='btn-save']");
    By btnCancel = By.xpath("//button[@id='btn-cancel']");
    By btnCancelConfirm = By.xpath("//button[@id='btn-cancel-confirm']");
    By btnSaveConfirm = By.xpath("//button[@id='btn-confirm']");
    By txtSnackBar = By.xpath("//div[@class='MuiAlert-message css-zioonp-MuiAlert-message']");
    By fieldActualQuantity1 = By.xpath("//body[1]/div[2]/div[3]/div[1]/div[1]/div[1]/form[1]/div[1]/table[1]/tbody[1]/tr[1]/td[5]/div[1]/div[1]/input[1]");
    By fieldActualQuantity2 = By.xpath("//body[1]/div[2]/div[3]/div[1]/div[1]/div[1]/form[1]/div[1]/table[1]/tbody[1]/tr[2]/td[5]/div[1]/div[1]/input[1]");
    String expectedMessage = "Tạo phiếu nhập thành công!";
    By helperTransactionCode = By.xpath("//p[@id='field-transaction-code-helper-text']");
    By msgSupplierError = By.xpath("//p[@id='error-supplier']");
    By helperBillCode = By.xpath("//p[@id='field-bill-code-helper-text']");
    By helperAddress = By.xpath("//p[@id='field-address-helper-text']");
    By helperDeliveryPerson = By.xpath("//p[@id='field-delivery-person-helper-text']");
    By msgTransactionRequestError = By.xpath("//p[@id='error-transaction-request']");
    By msgActualQuantityError = By.xpath("//p[@id='field-actual-quantity-helper-text']");
    By selectBookRow1 = By.xpath("//div[normalize-space()='Sách 19']");
    By modalConfirmDelete = By.xpath("//div[@id='confirm-modal']");
    By tableTransactionItem = By.id("table-transaction-item");


    public GoodReceivedNotePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToTransactionPage() {
        waitForPageLoaded();
        driver.findElement(btnTransaction).click();
        driver.findElement(btnImport).click();
        waitForPageLoaded();
        driver.findElement(btnAddTransaction).click();
    }


    public void checkCreateTransactionSuccessful(String transactionCode, String deliveryPerson, int supplier, String billCode, String address, int transactionReQuest, String actualQuantity1, String actualQuantity2) {waitForPageLoaded();
        driver.findElement(fieldTransactionCode).clear();
        driver.findElement(fieldTransactionCode).sendKeys(transactionCode);

        driver.findElement(fieldDeliveryPerson).clear();
        driver.findElement(fieldDeliveryPerson).sendKeys(deliveryPerson);

        WebElement dropdownSupplier = driver.findElement(selectSupplier);
        Select selectSupplier = new Select(dropdownSupplier);
        selectSupplier.selectByIndex(supplier);

        driver.findElement(fieldBillCode).clear();
        driver.findElement(fieldBillCode).sendKeys(billCode);

        WebElement dropdownTransactionCode = driver.findElement(selectTransactionRequest);
        Select selectTransactionRequest = new Select(dropdownTransactionCode);
        selectTransactionRequest.selectByIndex(supplier);

        driver.findElement(fieldAddress).clear();
        driver.findElement(fieldAddress).sendKeys(address);

        driver.findElement(fieldActualQuantity1).clear();
        driver.findElement(fieldActualQuantity1).sendKeys(actualQuantity1);

        WebElement quantity2 = driver.findElement(fieldActualQuantity2);
        quantity2.clear();

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", quantity2);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(quantity2));

        quantity2.sendKeys(actualQuantity2);

        WebElement buttonSave = driver.findElement(btnSave);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonSave);
        buttonSave.click();

        String snackBarContent = driver.findElement(txtSnackBar).getText();
        Assert.assertEquals(snackBarContent, expectedMessage);
    }

    public void checkCancelCreateTransaction() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(modalAddTransaction));
        driver.findElement(btnCancel).click();
        boolean isModalClosed;
        try {
            wait.until(ExpectedConditions.invisibilityOf(modal));
            isModalClosed = true;
        } catch (Exception e) {
            isModalClosed = false;
        }
        System.out.println("Kết quả hiển thị modal: " + isModalClosed);
        Assert.assertTrue(isModalClosed);
    }

    public void checkTransactionCodeEmpty() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(modalAddTransaction));
        driver.findElement(btnSave).click();
        String result = driver.findElement(helperTransactionCode).getText();
        Assert.assertEquals(result,"Không được bỏ trống số phiếu nhập");

    }

    public void checkValidTransactionCode(String data) {
        WebElement transactionCode = driver.findElement(fieldTransactionCode);
        transactionCode.sendKeys(data);
        String result = transactionCode.getAttribute("value");
        driver.findElement(btnSave).click();
        String sanitizedText = data.replaceAll("[^a-zA-Z0-9]", "");
        Assert.assertEquals(result, sanitizedText);
    }

    public void checkDeliveryPersonEmpty(String expResult) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(modalAddTransaction));
        driver.findElement(fieldTransactionCode).sendKeys("Trần Thị Thúy");

        driver.findElement(btnSave).click();
        String result = driver.findElement(helperDeliveryPerson).getText();
        Assert.assertEquals(result,expResult);

    }

    public void checkValidDeliveryPerson(String data) {
        driver.findElement(fieldTransactionCode).sendKeys("PN099");
        WebElement deliveryPerson = driver.findElement(fieldDeliveryPerson);
        String result = deliveryPerson.getAttribute("value");
        driver.findElement(btnSave).click();
        String sanitizedText = data.replaceAll("[^a-zA-Z0-9]", "");
        Assert.assertEquals(result, sanitizedText);
    }

    public void checkDisplaySupplier(String supplier) {
        WebElement dropdownSupplier = driver.findElement(selectSupplier);
        Select selectSupplier = new Select(dropdownSupplier);
        selectSupplier.selectByValue(supplier);
        String result = selectSupplier.getFirstSelectedOption().getText();
        Assert.assertEquals(result, supplier);
    }


    public void checkSupplierList(List<String> expOptions) {
        WebElement dropdownSupplier = driver.findElement(selectSupplier);
        Select selectSupplier = new Select(dropdownSupplier);
        List<String> actOptions = new ArrayList<String>();
        for(WebElement option : selectSupplier.getOptions()) {
            actOptions.add(option.getText());
        }

        if (!actOptions.isEmpty()) {
            actOptions.remove(0);
        }

        Assert.assertEquals(expOptions.toArray(), actOptions.toArray());

    }

    public void checkEmptySupplier(String expResult) {
        driver.findElement(fieldTransactionCode).sendKeys("PN099");
        driver.findElement(fieldDeliveryPerson).sendKeys("Trần Thị Thúy");

        WebElement buttonSave = driver.findElement(btnSave);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonSave);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(buttonSave));
        buttonSave.click();
        WebElement errorSupplier = driver.findElement(msgSupplierError);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", errorSupplier);
        wait.until(ExpectedConditions.visibilityOf(errorSupplier));
        String actualResult = errorSupplier.getText();
        Assert.assertEquals(actualResult, expResult);
    }

    public void checkEmptyBillCode(String expResult) {
        driver.findElement(fieldTransactionCode).sendKeys("PN099");
        driver.findElement(fieldDeliveryPerson).sendKeys("Trần Thị Thúy");
        WebElement dropdownSupplier = driver.findElement(selectSupplier);
        Select selectSupplier = new Select(dropdownSupplier);
        selectSupplier.selectByIndex(1);

        driver.findElement(btnSave).click();
        String result = driver.findElement(helperBillCode).getText();
        Assert.assertEquals(result, expResult);

    }

    public void checkValidBillCode(String data) {
        driver.findElement(fieldTransactionCode).sendKeys("PN099");
        driver.findElement(fieldDeliveryPerson).sendKeys("Trần Thị Thúy");
        WebElement dropdownSupplier = driver.findElement(selectSupplier);
        Select selectSupplier = new Select(dropdownSupplier);
        selectSupplier.selectByIndex(1);

        WebElement billCode = driver.findElement(fieldBillCode);
        billCode.sendKeys(data);
        String result = billCode.getAttribute("value");
        driver.findElement(btnSave).click();
        String sanitizedText = data.replaceAll("[^a-zA-Z0-9]", "");
        System.out.println("Mong đợi " + sanitizedText);
        System.out.println("Thực tế " + result);

        Assert.assertEquals(result, sanitizedText);
    }

    public void checkEmptyAdress(String expResult) {
        driver.findElement(fieldTransactionCode).sendKeys("PN099");
        driver.findElement(fieldDeliveryPerson).sendKeys("Trần Thị Thúy");
        WebElement dropdownSupplier = driver.findElement(selectSupplier);
        Select selectSupplier = new Select(dropdownSupplier);
        selectSupplier.selectByIndex(1);
        driver.findElement(fieldBillCode).sendKeys("HD02");

        driver.findElement(btnSave).click();
        String result = driver.findElement(helperAddress).getText();
        Assert.assertEquals(result, expResult);

    }

    public void checkValidAddress(String data) {
        driver.findElement(fieldTransactionCode).sendKeys("PN099");
        driver.findElement(fieldDeliveryPerson).sendKeys("Trần Thị Thúy");
        WebElement dropdownSupplier = driver.findElement(selectSupplier);
        Select selectSupplier = new Select(dropdownSupplier);
        selectSupplier.selectByIndex(1);
        driver.findElement(fieldBillCode).sendKeys("HD02");

        WebElement address = driver.findElement(fieldAddress);
        address.sendKeys(data);
        String result = address.getAttribute("value");
        driver.findElement(btnSave).click();
        String sanitizedText = data.replaceAll("[^a-zA-Z0-9\\s\\.\\,àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]", "");
        System.out.println("Mong đợi " + sanitizedText);
        System.out.println("Thực tế " + result);

        Assert.assertEquals(result, sanitizedText);
    }

    public void checkDisplayTransactionRequest(String transactionRequest) {
        WebElement dropdownTransactionRequest = driver.findElement(selectTransactionRequest);
        Select selectTransactionRequest = new Select(dropdownTransactionRequest);
        selectTransactionRequest.selectByVisibleText(transactionRequest);
        String result = selectTransactionRequest.getFirstSelectedOption().getText();
        System.out.println("Mong đợi " + transactionRequest);
        System.out.println("Thực tế " + result);
        Assert.assertEquals(result, transactionRequest);
    }

    public void checkTransactionRequestList(List<String> expOptions) {
        WebElement dropdownTransactionRequest = driver.findElement(selectTransactionRequest);
        Select selectTransactionRequest = new Select(dropdownTransactionRequest);
        List<String> actOptions = new ArrayList<String>();
        for(WebElement option : selectTransactionRequest.getOptions()) {
            actOptions.add(option.getText());
        }

        if (!actOptions.isEmpty()) {
            actOptions.remove(0);
        }

        Assert.assertEquals(expOptions.toArray(), actOptions.toArray());

    }

    public void checkEmptyTransactionCode(String expResult) {
        driver.findElement(fieldTransactionCode).sendKeys("PN099");
        driver.findElement(fieldDeliveryPerson).sendKeys("Trần Thị Thúy");
        WebElement dropdownSupplier = driver.findElement(selectSupplier);
        Select selectSupplier = new Select(dropdownSupplier);
        selectSupplier.selectByIndex(1);
        driver.findElement(fieldBillCode).sendKeys("HD02");
        driver.findElement(fieldAddress).sendKeys("97 Man Thiện, P. Hiệp Phú");

        WebElement buttonSave = driver.findElement(btnSave);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonSave);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(buttonSave));
        buttonSave.click();
        WebElement errorTransactionRequest = driver.findElement(msgTransactionRequestError);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", errorTransactionRequest);
        wait.until(ExpectedConditions.visibilityOf(errorTransactionRequest));
        String actualResult = errorTransactionRequest.getText();
        Assert.assertEquals(actualResult, expResult);
    }

    public void checkActualQuantityWithNegativeNumber(String negativeNumber) {
        WebElement actualQuantity = driver.findElement(fieldActualQuantity);
        actualQuantity.sendKeys(negativeNumber);
        String actualResult = actualQuantity.getAttribute("value");
        String filteredString = negativeNumber.replaceAll("[^0-9]", "");
        Assert.assertEquals(actualResult, filteredString);
    }

    public void checkActualQuantityWithZero() {
        WebElement actualQuantity = driver.findElement(fieldActualQuantity);
        actualQuantity.sendKeys("0");
        String actualResult = actualQuantity.getAttribute("value");
        String expectedResult = "";
        Assert.assertEquals(actualResult, expectedResult);
    }

    public void checkActualQuantityIsOnlyNumericInput(String letters) {
        WebElement actualQuantity = driver.findElement(fieldActualQuantity);
        actualQuantity.sendKeys(letters);
        String actualResult = actualQuantity.getAttribute("value");
        String filteredString = letters.replaceAll("[^0-9]", "");
        Assert.assertEquals(actualResult, filteredString);

    }

    public void checkValidActualQuantity(String transactionRequest, String expectedMessage) {
        WebElement dropdownTransactionRequest = driver.findElement(selectTransactionRequest);
        Select selectTransactionRequest = new Select(dropdownTransactionRequest);
        selectTransactionRequest.selectByVisibleText(transactionRequest);
        driver.findElement(btnSave).click();
        String actualMsg = driver.findElement(msgActualQuantityError).getText();
        Assert.assertEquals(actualMsg, expectedMessage);

    }

    public void checkActualQuantityWithFloat(String floatNumber) throws InterruptedException {
        WebElement actualQuantity = driver.findElement(fieldActualQuantity);
        actualQuantity.sendKeys(floatNumber);
        String actualResult = actualQuantity.getAttribute("value");
        Thread.sleep(2000);
        String filteredString = floatNumber.replaceAll("[^0-9]", "");
        System.out.println("Thực tế " + actualResult);
        System.out.println("Mong đợi " +filteredString);
        Assert.assertEquals(actualResult, filteredString);
    }

    public void checkActualQuantityLimit(String limit, String expectedMessage) {
        driver.findElement(fieldActualQuantity).sendKeys(limit);
        driver.findElement(btnSave).click();
        String actualMsg = driver.findElement(msgActualQuantityError).getText();
        Assert.assertEquals(actualMsg, expectedMessage);

    }

    public void checkDeleteRow(String transactionRequest) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement dropdownTransactionRequest = wait.until(ExpectedConditions.visibilityOfElementLocated(selectTransactionRequest));
        Select selectTransactionRequest = new Select(dropdownTransactionRequest);
        selectTransactionRequest.selectByVisibleText(transactionRequest);

        WebElement dropDownBookRow1 = wait.until(ExpectedConditions.visibilityOfElementLocated(selectBookRow1));
        String bookName1 = dropDownBookRow1.getText();
        driver.findElement(iconDeleteRow1).click();
        driver.findElement(btnSaveConfirm).click();
        boolean isRowDeleted = wait.until(ExpectedConditions.invisibilityOfElementLocated(selectBookRow1));
        Assert.assertTrue(isRowDeleted, "Hàng chưa được xóa thành công");
        System.out.println("Đã xóa thành công hàng chứa sách: " + bookName1);
    }

    public void checkCancelDeleteRow(String transactionRequest) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement dropdownTransactionRequest = wait.until(ExpectedConditions.visibilityOfElementLocated(selectTransactionRequest));
        Select selectTransactionRequest = new Select(dropdownTransactionRequest);
        selectTransactionRequest.selectByVisibleText(transactionRequest);

        WebElement dropDownBookRow1 = wait.until(ExpectedConditions.visibilityOfElementLocated(selectBookRow1));
        String bookName1 = dropDownBookRow1.getText();
        driver.findElement(iconDeleteRow1).click();
        driver.findElement(btnCancelConfirm).click();
        boolean isRowDeleted = wait.until(ExpectedConditions.invisibilityOfElementLocated(modalConfirmDelete));
        Assert.assertTrue(isRowDeleted, "Hủy xóa hàng thất bại");
    }


    public void checkAddRow() {
        WebElement table = driver.findElement(tableTransactionItem);
        List<WebElement> rowsBeforeAdd = table.findElements(By.tagName("tr"));
        int rowsCountBeforeAdd = rowsBeforeAdd.size();

        driver.findElement(iconAddRow).click();
        List<WebElement> rowsAfterAdd = table.findElements(By.tagName("tr"));
        int rowsCountAfterAdd = rowsAfterAdd.size();

        Assert.assertEquals(rowsCountAfterAdd, rowsCountBeforeAdd + 1);
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
