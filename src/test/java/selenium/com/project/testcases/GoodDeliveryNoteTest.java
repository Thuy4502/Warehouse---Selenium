package selenium.com.project.testcases;

import helpers.CaptureHelpers;
import helpers.ExcelHelpers;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selenium.com.browsers.base.BaseSetup;
import selenium.com.project.pages.GoodDeliveryNotePage;
import selenium.com.project.pages.LoginPage;

import java.time.Duration;

public class GoodDeliveryNoteTest extends BaseSetup {
    private GoodDeliveryNotePage goodDeliveryNotePage;
    private LoginPage loginPage;
    ExcelHelpers excel = new ExcelHelpers();

    @BeforeMethod
    public void setUp() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        goodDeliveryNotePage = new GoodDeliveryNotePage(driver);
        loginPage.loginWarehouse("thukho", "123456");
        goodDeliveryNotePage.goToTransactionPage();
        excel.setExcelFile("src/test/resources/WarehouseTestcase.xlsx", "Tạo phiếu xuất");

    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
    }

    @DataProvider(name = "transactionData")
    public Object[][] transactionData() {
        return new Object[][] {
                {"Lê Thanh Tú", "97 Man Thiện", 4, "20", "40"}
        };
    }

    @Test(dataProvider = "transactionData", priority = 1)
    public void createTransactionSuccessfullyTest(String customerName, String address, int transactionRequest, String actualQuantity1, String actualQuantity2) throws InterruptedException {
        goodDeliveryNotePage.fillTransactionForm(customerName, address, transactionRequest, actualQuantity1, actualQuantity2);
        goodDeliveryNotePage.clickSaveButton();

        String actualMessage = goodDeliveryNotePage.getSnackBarMessage();
        Assert.assertEquals(actualMessage, "Tạo phiếu xuất thành công!", "Transaction creation message mismatch!");
    }

    @Test(priority = 2)
    public void checkCancelCreateTransaction() {
        goodDeliveryNotePage.checkCancelCreateTransaction();
    }

//    @Test(priority = 3)
//    public void testTransactionCodeEmpty() {
//        try {
//            goodDeliveryNotePage.clickSaveButton();
//            String result = goodDeliveryNotePage.getTransactionCodeHelperText();
//            Assert.assertEquals(result, "Không được bỏ trống số phiếu xuất");
//            excel.setCellData("pass", 5, 11);
//        } catch (AssertionError e) {
//            excel.setCellData("fail", 5, 11);
//            throw e;
//        }
//    }
//
//    @Test(priority = 4)
//    public void testValidTransactionCode() {
//        String code = "PX1&%$234";
//        goodDeliveryNotePage.enterTransactionCode(code);
//        String result = goodDeliveryNotePage.getTransactionCode();
//        String sanitizedText = code.replaceAll("[^a-zA-Z0-9]", "");
//        Assert.assertEquals(result, sanitizedText);
//    }

    @Test(priority = 3)
    public void testEmptyAddressErrorMessage() {
        goodDeliveryNotePage.enterCustomerName("Trần Thị Thúy");
        goodDeliveryNotePage.clickSaveButton();
        String actualErrorMessage = goodDeliveryNotePage.getAddressHelperText();
        String expectedErrorMessage = "Không được bỏ trống địa chỉ";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test(priority = 4)
    public void testValidAddress() {
        String address = "123 Main St, &%4Hanoi";
        goodDeliveryNotePage.enterCustomerName("Trần Thị Thúy");
        goodDeliveryNotePage.clickSaveButton();
        goodDeliveryNotePage.enterAddress(address);
        String result = goodDeliveryNotePage.getAddressText();
        String sanitizedText = address.replaceAll("[^a-zA-Z0-9\\s\\.\\,àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]", "");
        Assert.assertEquals(result, sanitizedText);
    }

    @Test(priority = 5)
    public void testTransactionRequestDisplay() {
        String transactionRequest = "PYCX240005";
        goodDeliveryNotePage.selectTransactionRequest(transactionRequest);
        String result = goodDeliveryNotePage.getSelectedTransactionRequest();
        Assert.assertEquals(result, transactionRequest);
    }

    @Test(priority = 6)
    public void testTransactionRequestList() {
        int expectedLen = 2;
        int actualLen = goodDeliveryNotePage.getLenTransactionRequest();
        Assert.assertEquals(actualLen, expectedLen);

    }

    @Test(priority = 7)
    public void testEmptyTransactionCodeError() {
        String expectedErrorMessage = "Không được bỏ trống phiếu yêu cầu xuất";
        goodDeliveryNotePage.enterCustomerName("Trần Thị Thúy");
        goodDeliveryNotePage.enterAddress("97 Man Thiện, P. Hiệp Phú");
        goodDeliveryNotePage.clickSaveButton();
        goodDeliveryNotePage.waitForTransactionRequestError();
        String actualErrorMessage = goodDeliveryNotePage.getTransactionRequestErrorText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test(priority = 8)
    public void testChangeTransactionRequest() {
        String firstOption = "PYCX240004";
        String expectedResult = "PYCX240005";
        goodDeliveryNotePage.selectTransactionRequest(firstOption);
        goodDeliveryNotePage.selectTransactionRequest(expectedResult);
        String actualResult = goodDeliveryNotePage.getSelectedTransactionRequest();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(priority = 9)
    public void testTransactionRequestDefault() {
        String expectedResult = "Chọn phiếu yêu cầu";
        String actualResult = goodDeliveryNotePage.getSelectedTransactionRequest();
        Assert.assertEquals(actualResult, expectedResult);
    }

//    @Test(priority = 12)
//    public void testBookList() {
//        List<String> expectedBooks = Arrays.asList("Dế mèn phiêu lưu ký", "Sách 19", "Cho tôi xin một vé đi tuổi thơ", "Ký ức Đông Dương");
//        List<String> actualBooks = goodDeliveryNotePage.getListBook();
//        Assert.assertEquals(actualBooks, expectedBooks);
//    }
//
//    @Test(priority = 13)
//    public void testBookDefault() {
//        String expectedResult = "Chọn sách";
//        String actualResult = goodDeliveryNotePage.getSelectedBook();
//        Assert.assertEquals(actualResult, expectedResult);
//    }
//
//    @Test(priority = 14)
//    public void testSelectedBook() {
//        String expectedResult = "Ký ức Đông Dương";
//        goodDeliveryNotePage.selectBook(expectedResult);
//        String actualResult = goodDeliveryNotePage.getSelectedBook();
//        Assert.assertEquals(actualResult, expectedResult);
//    }
//
//    @Test(priority = 15)
//    public void testBookNameEmpty() {
//        String expectedMessage = "Không được bỏ trống tên sách";
//        goodDeliveryNotePage.clickSaveButton();
//        String actualMsg = goodDeliveryNotePage.getBookNameErrorMessage();
//        Assert.assertEquals(expectedMessage, actualMsg);
//    }
//
//    @Test(priority = 16)
//    public void testChangeBook() {
//        String firstOptions = "Cho tôi xin một vé đi tuổi thơ";
//        String expectedResult = "Ký ức Đông Dương";
//        goodDeliveryNotePage.selectBook(firstOptions);
//        goodDeliveryNotePage.selectBook(expectedResult);
//        String actualResult = goodDeliveryNotePage.getSelectedBook();
//        Assert.assertEquals(actualResult, expectedResult);
//    }
//
//    @Test(priority = 17)
//    public void testDoubleBookName() {
//        String option = "Cho tôi xin một vé đi tuổi thơ";
//        goodDeliveryNotePage.selectBook(option);
//        goodDeliveryNotePage.addRow();
//        boolean isDisplay = goodDeliveryNotePage.isBookNameInRow2ComboBox();
//        Assert.assertFalse(isDisplay, "Tên sách đã bị trùng");
//    }

    @Test(priority = 10)
    public void testActualQuantityWithNegativeNumber() {
        String negativeNumber = "-5";
        goodDeliveryNotePage.enterActualQuantity(negativeNumber);
        String actualResult = goodDeliveryNotePage.getActualQuantityValue();
        String filteredString = negativeNumber.replaceAll("[^0-9]", "");
        Assert.assertEquals(actualResult, filteredString);
    }

    @Test(priority = 11)
    public void testCheckActualQuantityIsOnlyNumericInput() {
        String input = "abc123";
        goodDeliveryNotePage.enterActualQuantity(input);
        String actualResult = goodDeliveryNotePage.getActualQuantityValue();
        String filteredString = input.replaceAll("[^0-9]", "");
        System.out.println("Actual :" + actualResult);
        System.out.println("Expected :" + filteredString);
        Assert.assertEquals(actualResult, filteredString);
    }


    @Test(priority = 12)
    public void testCheckValidActualQuantity() {
        String transactionRequest = "PYCX240004";
        String expectedMessage = "Không được bỏ trống số lượng";
        goodDeliveryNotePage.selectTransactionRequest(transactionRequest);
        goodDeliveryNotePage.clickSaveButton();
        String actualMsg = goodDeliveryNotePage.getActualQuantityErrorMessage();
        System.out.println("Actual :" + actualMsg);
        System.out.println("Expected :" + expectedMessage);
        Assert.assertEquals(expectedMessage, actualMsg);

    }

    @Test(priority = 13)
    public void testCheckActualQuantityWithFloat() throws InterruptedException {
        String floatNumber = "123,45";
        goodDeliveryNotePage.enterActualQuantity(floatNumber);
        String actualResult = goodDeliveryNotePage.getActualQuantityValue();
        Thread.sleep(2000);
        String filteredString = floatNumber.replaceAll("[^0-9]", "");
        System.out.println("Actual :" + actualResult);
        System.out.println("Expected :" + filteredString);
        Assert.assertEquals(actualResult, filteredString);
    }

    @Test(priority = 14)
    public void testCheckActualQuantityLimit() {
        String limit = "9999999";
        String expectedMessage = "Số lượng vượt quá giới hạn (999,999)";
        goodDeliveryNotePage.enterActualQuantity(limit);
        goodDeliveryNotePage.clickSaveButton();
        String actualMsg = goodDeliveryNotePage.getActualQuantityErrorMessage();
        Assert.assertEquals(expectedMessage, actualMsg);
    }

    @Test(priority = 15)
    public void testAddRow() {
        boolean isRowAdded = goodDeliveryNotePage.addRow();
        Assert.assertTrue(isRowAdded, "Hàng chưa được thêm thành công");
    }

    @Test(priority = 16)
    public void testDeleteRow() {
        String transactionRequest = "PYCX240004";
        goodDeliveryNotePage.selectTransactionRequest(transactionRequest);
        boolean isRowDeleted = goodDeliveryNotePage.deleteRowAndConfirm();
        Assert.assertTrue(isRowDeleted,"Hàng chưa được xóa thành công");
    }

    @Test(priority = 17)
    public void testCancelDeleteRow() {
        String transactionRequest = "PYCX240004";
        goodDeliveryNotePage.selectTransactionRequest(transactionRequest);
        boolean isModalClosed = goodDeliveryNotePage.cancelDeleteRow();
        Assert.assertTrue(isModalClosed, "Hủy xóa hàng thất bại");
    }

    @Test(priority = 18)
    public void testNoteField() {
        String expectedResult = "Số lượng ký tự giới hạn là 500 ký tự";
        String note = "Khi thiết kế và phát triển hệ thống quản lý kho, các yếu tố cần được chú trọng không chỉ bao gồm tính chính xác và hiệu quả trong việc quản lý hàng hóa, mà còn phải đảm bảo khả năng mở rộng, tương thích với các hệ thống khác, và đặc biệt là trải nghiệm người dùng. Một hệ thống tốt cần cung cấp giao diện trực quan, giúp người sử dụng dễ dàng thao tác mà không cần phải đào tạo quá nhiều. Ngoài ra, việc bảo mật thông tin cũng là một ưu tiên hàng đầu, đặc biệt khi dữ liệu liên quan đến hàng tồn kho, xuất nhập kho và các giao dịch tài chính quan trọng. Một số phương pháp bảo mật hiện đại bao gồm mã hóa dữ liệu, phân quyền truy cập chặt chẽ, và sử dụng các giao thức bảo mật như HTTPS. Trong quá trình triển khai, cần liên tục kiểm tra và đánh giá để đảm bảo hệ thống đáp ứng được nhu cầu thực tế của người dùng và hoạt động ổn định trong môi trường áp lực cao.";
        goodDeliveryNotePage.enterNote(note);
        goodDeliveryNotePage.clickSaveButton();
        String actualResult = goodDeliveryNotePage.getNoteHelperText();
        Assert.assertEquals(actualResult, expectedResult);
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
