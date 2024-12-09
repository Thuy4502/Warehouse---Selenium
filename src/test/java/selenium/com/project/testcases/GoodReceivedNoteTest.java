package selenium.com.project.testcases;

import helpers.CaptureHelpers;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selenium.com.browsers.base.BaseSetup;
import selenium.com.project.pages.GoodReceivedNotePage;
import selenium.com.project.pages.LoginPage;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class GoodReceivedNoteTest extends BaseSetup {
    private GoodReceivedNotePage goodReceivedNotePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        goodReceivedNotePage = new GoodReceivedNotePage(driver);
        loginPage.loginWarehouse("thukho", "123456");
        goodReceivedNotePage.goToTransactionPage();
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
    }

    @DataProvider(name = "transactionData")
    public Object[][] transactionData() {
        return new Object[][] {
                {"Trần Thi Thúy", 1, "HD0004", "105 Võ Văn Ngân, Tp. Thủ Đức", 2, "55", "40"}
        };
    }

    @Test(dataProvider = "transactionData", priority = 1)
    public void createTransactionSuccessfullyTest(String deliveryPerson, int supplier, String billCode, String address,  int transactionRequest, String actualQuantity1, String actualQuantity2) throws InterruptedException {
        goodReceivedNotePage.fillTransactionForm( deliveryPerson, supplier, billCode, address, transactionRequest, actualQuantity1, actualQuantity2);
        goodReceivedNotePage.clickSaveButton();

        String actualMessage = goodReceivedNotePage.getSnackBarMessage();
        Assert.assertEquals(actualMessage, "Tạo phiếu nhập thành công!", "Transaction creation message mismatch!");
    }

    @Test(priority = 2)
    public void checkCancelCreateTransaction() {
        goodReceivedNotePage.checkCancelCreateTransaction();
    }

//    @Test(priority = 3)
//    public void testTransactionCodeEmpty() {
//        goodReceivedNotePage.clickSaveButton();
//        String result = goodReceivedNotePage.getTransactionCodeHelperText();
//        Assert.assertEquals(result, "Không được bỏ trống số phiếu nhập");
//    }
//
//    @Test(priority = 4)
//    public void testValidTransactionCode() {
//        String code = "PN1234";
//        goodReceivedNotePage.enterTransactionCode(code);
//        String result = goodReceivedNotePage.getTransactionCode();
//        String sanitizedText = code.replaceAll("[^a-zA-Z0-9]", "");
//        Assert.assertEquals(result, sanitizedText);
//    }

    @Test(priority = 4)
    public void testSupplierList() {
        List<String> expectedSuppliers = Arrays.asList("Công Ty Cổ Phần Phát Hành Sách Tp.HCM", "Công Ty Cổ Phần Sách & Thiết Bị Giao Dục Trí Tuệ", "Công Ty TNHH Văn Hóa Việt Long", "Công Ty TNHH Đăng Nguyên", "Công Ty Cổ Phần Sách Mcbooks");
        List<String> actualSuppliers = goodReceivedNotePage.getListSupplier();
        Assert.assertEquals(actualSuppliers, expectedSuppliers);
    }

    @Test(priority = 5)
    public void testDisplaySupplier() {
        String supplier = "Công Ty Cổ Phần Phát Hành Sách Tp.HCM";
        goodReceivedNotePage.selectSupplier(supplier);
        String result = goodReceivedNotePage.getSelectedSupplier();
        Assert.assertEquals(result, supplier);
    }


    @Test(priority = 6)
    public void testDeliveryPersonEmpty() {
//        String name = "Trần Thị Thúy";
//        goodReceivedNotePage.enterTransactionCode("PN123");
        goodReceivedNotePage.clickSaveButton();
        String result = goodReceivedNotePage.getDeliveryPersonHelperText();
        Assert.assertEquals(result, "Không được bỏ trống họ và tên người giao hàng");
    }

    @Test(priority = 7)
    public void testValidDeliveryPerson() {
        String name = "Trần Thị Thúy";
        goodReceivedNotePage.enterDeliveryPerson(name);
        goodReceivedNotePage.clickSaveButton();
        String result = goodReceivedNotePage.getDeliveryPersonText();
        String sanitizedText = name.replaceAll("[^a-zA-Z0-9\\s\\.\\,àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]", "");
        Assert.assertEquals(result, sanitizedText);
    }


    @Test(priority = 8)
    public void testEmptySupplierErrorMessage() {
//        goodReceivedNotePage.enterTransactionCode("PN099");
        goodReceivedNotePage.enterDeliveryPerson("Trần Thị Thúy");
        goodReceivedNotePage.clickSaveButton();
        String actualErrorMessage = goodReceivedNotePage.getSupplierErrorMessage();
        String expectedErrorMessage = "Không được bỏ trống nhà cung cấp";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test(priority = 9)
    public void testEmptyBillCode() {
//        goodReceivedNotePage.enterTransactionCode("PN099");
        goodReceivedNotePage.enterDeliveryPerson("Trần Thị Thúy");
        goodReceivedNotePage.selectSupplierByIndex(1);
        goodReceivedNotePage.clickSaveButton();
        String actualHelperText = goodReceivedNotePage.getBillCodeHelperText();
        String expectedHelperText = "Không được bỏ trống số hóa đơn";
        Assert.assertEquals(actualHelperText, expectedHelperText);
    }

    @Test(priority = 10)
    public void testValidBillCode() {
        String data = "HD0003";
//        goodReceivedNotePage.enterTransactionCode("PN099");
        goodReceivedNotePage.enterDeliveryPerson("Trần Thị Thúy");
        goodReceivedNotePage.selectSupplierByIndex(1);
        goodReceivedNotePage.setBillCode(data);
        String actualBillCode = goodReceivedNotePage.getBillCodeValue();
        String sanitizedText = data.replaceAll("[^a-zA-Z0-9]", "");
        Assert.assertEquals(actualBillCode, sanitizedText);
    }

    @Test(priority = 11)
    public void testEmptyAddressErrorMessage() {
//        goodReceivedNotePage.enterTransactionCode("PN099");
        goodReceivedNotePage.enterDeliveryPerson("Trần Thị Thúy");
        goodReceivedNotePage.selectSupplierByIndex(1);
        goodReceivedNotePage.setBillCode("HD0003");
        goodReceivedNotePage.clickSaveButton();
        String actualErrorMessage = goodReceivedNotePage.getAddressHelperText();
        String expectedErrorMessage = "Không được bỏ trống địa chỉ";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test(priority = 12)
    public void testValidAddress() {
        String address = "123 Main St, &%4Hanoi";
        goodReceivedNotePage.enterDeliveryPerson("Trần Thị Thúy");
        goodReceivedNotePage.selectSupplierByIndex(1);
        goodReceivedNotePage.enterBillCode("HD0003");
        goodReceivedNotePage.enterAddress(address);
        String result = goodReceivedNotePage.getAddressText();
        String sanitizedText = address.replaceAll("[^a-zA-Z0-9\\s\\.\\,àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]", "");
        Assert.assertEquals(result, sanitizedText);
    }

    @Test(priority = 13)
    public void testTransactionRequestDisplay() {
        String transactionRequest = "PYCN240004";
        goodReceivedNotePage.selectTransactionRequest(transactionRequest);
        String result = goodReceivedNotePage.getSelectedTransactionRequest();
        Assert.assertEquals(result, transactionRequest);
    }

    @Test(priority = 14)
    public void testTransactionRequestList() {
        int expectedLen = 2;
        int actualLen = goodReceivedNotePage.getLenTransactionRequest();
        Assert.assertEquals(actualLen, expectedLen);

    }

    @Test(priority = 15)
    public void testActualQuantityWithNegativeNumber() {
        String negativeNumber = "-5";
        goodReceivedNotePage.enterActualQuantity(negativeNumber);
        String actualResult = goodReceivedNotePage.getActualQuantityValue();
        String filteredString = negativeNumber.replaceAll("[^0-9]", "");
        Assert.assertEquals(actualResult, filteredString);
    }

    @Test(priority = 16)
    public void testCheckActualQuantityIsOnlyNumericInput() {
        String input = "abc123";
        goodReceivedNotePage.enterActualQuantity(input);
        String actualResult = goodReceivedNotePage.getActualQuantityValue();
        String filteredString = input.replaceAll("[^0-9]", "");
        System.out.println("Actual :" + actualResult);
        System.out.println("Expected :" + filteredString);
        Assert.assertEquals(actualResult, filteredString);
    }

    @Test(priority = 17)
    public void testCheckValidActualQuantity() {
        String transactionRequest = "PYCN240004";
        String expectedMessage = "Không được bỏ trống số lượng";
        goodReceivedNotePage.selectTransactionRequest(transactionRequest);
        goodReceivedNotePage.clickSaveButton();
        String actualMsg = goodReceivedNotePage.getActualQuantityErrorMessage();
        System.out.println("Actual :" + actualMsg);
        System.out.println("Expected :" + expectedMessage);
        Assert.assertEquals(expectedMessage, actualMsg);

    }

    @Test(priority = 18)
    public void testCheckActualQuantityWithFloat() throws InterruptedException {
        String floatNumber = "123,45";
        goodReceivedNotePage.enterActualQuantity(floatNumber);
        String actualResult = goodReceivedNotePage.getActualQuantityValue();
        Thread.sleep(2000);
        String filteredString = floatNumber.replaceAll("[^0-9]", "");
        System.out.println("Actual :" + actualResult);
        System.out.println("Expected :" + filteredString);
        Assert.assertEquals(actualResult, filteredString);
    }

    @Test(priority = 19)
    public void testCheckActualQuantityLimit() {
        String limit = "9999999";
        String expectedMessage = "Số lượng vượt quá giới hạn (999,999)";
        goodReceivedNotePage.enterActualQuantity(limit);
        goodReceivedNotePage.clickSaveButton();
        String actualMsg = goodReceivedNotePage.getActualQuantityErrorMessage();
        Assert.assertEquals(expectedMessage, actualMsg);
    }

    @Test(priority = 20)
    public void testDeleteRow() {
        String transactionRequest = "PYCN240004";
        goodReceivedNotePage.selectTransactionRequest(transactionRequest);
        boolean isRowDeleted = goodReceivedNotePage.deleteRowAndConfirm();
        Assert.assertTrue(isRowDeleted,"Hàng chưa được xóa thành công");
    }

    @Test(priority = 21)
    public void testCancelDeleteRow() {
        String transactionRequest = "PYCN240004";
        goodReceivedNotePage.selectTransactionRequest(transactionRequest);
        boolean isModalClosed = goodReceivedNotePage.cancelDeleteRow();
        Assert.assertTrue(isModalClosed, "Hủy xóa hàng thất bại");
    }

    @Test(priority = 22)
    public void testAddRow() {
        boolean isRowAdded = goodReceivedNotePage.addRow();
        Assert.assertTrue(isRowAdded, "Hàng chưa được thêm thành công");
    }

    @Test(priority = 23)
    public void voidTestSupplierDefault() {
        String expectedResult = "Chọn nhà cung cấp";
        String actualResult = goodReceivedNotePage.getSelectedSupplier();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(priority = 24)
    public void voidTestTransactionRequestDefault() {
        String expectedResult = "Chọn phiếu yêu cầu";
        String actualResult = goodReceivedNotePage.getSelectedTransactionRequest();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(priority = 25)
    public void voidTestChangeSupplier() {
        String firstOption = "Công Ty Cổ Phần Phát Hành Sách Tp.HCM";
        String expectedResult = "Công Ty Cổ Phần Sách & Thiết Bị Giao Dục Trí Tuệ";
        goodReceivedNotePage.selectSupplier(firstOption);
        goodReceivedNotePage.selectSupplier(expectedResult);
        String actualResult = goodReceivedNotePage.getSelectedSupplier();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(priority = 26)
    public void testChangeTransactionRequest() {
        String firstOption = "PYCN240004";
        String expectedResult = "PYCN240006";
        goodReceivedNotePage.selectTransactionRequest(firstOption);
        goodReceivedNotePage.selectTransactionRequest(expectedResult);
        String actualResult = goodReceivedNotePage.getSelectedTransactionRequest();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(priority = 27)
    public void testBookList() {
        List<String> expectedBooks = Arrays.asList("Ký ức Đông Dương", "Dạy trẻ quản lý và tiêu tiền", "Thơ Tết dành cho thiếu nhi", "Kính vạn hoa", "Bong bóng trên trời", "Ngày xưa có một chuyện tình");
        List<String> actualBooks = goodReceivedNotePage.getListBook();
        Assert.assertEquals(actualBooks, expectedBooks);
    }

    @Test(priority = 28)
    public void testBookDefault() {
        String expectedResult = "Chọn sách";
        String actualResult = goodReceivedNotePage.getSelectedBook();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(priority = 29)
    public void testSelectedBook() {
        String expectedResult = "Ký ức Đông Dương";
        goodReceivedNotePage.selectBook(expectedResult);
        String actualResult = goodReceivedNotePage.getSelectedBook();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(priority = 30)
    public void testBookNameEmpty() {
        String expectedMessage = "Không được bỏ trống tên sách";
        goodReceivedNotePage.clickSaveButton();
        String actualMsg = goodReceivedNotePage.getBookNameErrorMessage();
        Assert.assertEquals(expectedMessage, actualMsg);
    }

    @Test(priority = 31)
    public void testChangeBook() {
        String firstOptions = "Dạy trẻ quản lý và tiêu tiền";
        String expectedResult = "Ký ức Đông Dương";
        goodReceivedNotePage.selectBook(firstOptions);
        goodReceivedNotePage.selectBook(expectedResult);
        String actualResult = goodReceivedNotePage.getSelectedBook();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(priority = 31)
    public void testDoubleBookName() {
        String option = "Dạy trẻ quản lý và tiêu tiền";
        goodReceivedNotePage.selectBook(option);
        goodReceivedNotePage.addRow();
        boolean isDisplay = goodReceivedNotePage.isBookNameInRow2ComboBox();
        Assert.assertFalse(isDisplay, "Tên sách đã bị trùng");
    }

    @Test(priority = 32)
    public void testPriceEmpty() {
        String expectedMessage = "Không được bỏ trống đơn giá";
        goodReceivedNotePage.clickSaveButton();
        String actualMsg = goodReceivedNotePage.getPriceErrorMessage();
        Assert.assertEquals(expectedMessage, actualMsg);
    }

    @Test(priority = 33)
    public void testCheckPriceIsOnlyNumericInput() {
        String input = "abc123";
        goodReceivedNotePage.enterPrice(input);
        String actualResult = goodReceivedNotePage.getPriceValue();
        String filteredString = input.replaceAll("[^0-9]", "");
        System.out.println("Actual :" + actualResult);
        System.out.println("Expected :" + filteredString);
        Assert.assertEquals(actualResult, filteredString);
    }

    @Test(priority = 34)
    public void testPriceWithNegativeNumber() {
        String negativeNumber = "-500000";
        goodReceivedNotePage.enterPrice(negativeNumber);
        String actualResult = goodReceivedNotePage.getPriceValue();
        String filteredString = negativeNumber.replaceAll("[^0-9]", "");
        System.out.println("Actual :" + actualResult);
        System.out.println("Expected :" + filteredString);
        Assert.assertEquals(actualResult, filteredString);
    }

    @Test(priority = 35)
    public void testCheckPriceLimit() {
        String limit = "9999999";
        String expectedMessage = "Đơn giá vượt quá giới hạn (999,999)";
        goodReceivedNotePage.enterPrice(limit);
        goodReceivedNotePage.clickSaveButton();
        String actualMsg = goodReceivedNotePage.getPriceErrorMessage();
        Assert.assertEquals(expectedMessage, actualMsg);
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
