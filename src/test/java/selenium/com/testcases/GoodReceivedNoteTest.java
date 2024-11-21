package selenium.com.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import selenium.com.base.BaseSetUp;
import selenium.com.pages.GoodReceivedNotePage;
import selenium.com.pages.LoginPage;

import java.util.Arrays;
import java.util.List;

public class GoodReceivedNoteTest extends BaseSetUp {
    private GoodReceivedNotePage goodReceivedNotePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        loginPage = new LoginPage(driver);
        goodReceivedNotePage = new GoodReceivedNotePage(driver);
        loginPage.loginWarehouse("tuanta", "Mv3O9N");
        goodReceivedNotePage.goToTransactionPage();

    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
    }

    @DataProvider(name = "transactionData")
    public Object[][] transactionData() {
        return new Object[][] {
                {"TXN67890", "Trần Thị B", 1, "BILL002", "97 Man Thiện", 1, "20", "40"}
        };
    }

    @Test(dataProvider = "transactionData", priority = 1)
    public void createTransactionSuccessfullyTest(String transactionCode, String deliveryPerson, int supplier, String billCode, String address,  int transactionRequest, String actualQuantity1, String actualQuantity2) {
        goodReceivedNotePage.checkCreateTransactionSuccessful(transactionCode, deliveryPerson, supplier, billCode, address, transactionRequest, actualQuantity1, actualQuantity2);
    }

    @Test(priority = 2)
    public void cancelCreateTransactionTest() {
        goodReceivedNotePage.checkCancelCreateTransaction();
    }

    @Test(priority = 3)
    public void checkTransactionCodeEmptyTest() {
        goodReceivedNotePage.checkTransactionCodeEmpty();
    }

    @Test(priority = 4)
    public void checkDeliveryPersonEmptyTest() {
        goodReceivedNotePage.checkDeliveryPersonEmpty("Không được bỏ trống họ và tên người giao hàng");
    }

    @Test(priority = 5)
    public void checkValidDeliveryPersonTest() {
        goodReceivedNotePage.checkValidDeliveryPerson("PN0999@%$");
    }


    @Test(priority = 6)
    public void checkValidTransactionCodeTest() {
        goodReceivedNotePage.checkValidTransactionCode("PN0999@%$");
    }

    @Test(priority = 7)
    public void checkDisplaySupplierTest() {
        goodReceivedNotePage.checkDisplaySupplier("Công Ty Cổ Phần Phát Hành Sách Tp.HCM");
    }

    @Test(priority = 8)
    public void checkSupplierList() {
        List<String> expectedSuppliers = Arrays.asList("Công Ty Cổ Phần Phát Hành Sách Tp.HCM", "Công Ty Cổ Phần Sách & Thiết Bị Giao Dục Trí Tuệ", "Công Ty TNHH Văn Hóa Việt Long", "Công Ty TNHH Đăng Nguyên", "Công Ty Cổ Phần Sách Mcbooks");

        goodReceivedNotePage.checkSupplierList(expectedSuppliers);
    }

    @Test(priority = 9)
    public void checkEmptySupplierTest() {
        String expResult = "Không được bỏ trống nhà cung cấp";
        goodReceivedNotePage.checkEmptySupplier(expResult);
    }

    @Test(priority = 10)
    public void checkEmptyBillCodeTest() {
        goodReceivedNotePage.checkEmptyBillCode("Không được bỏ trống số hóa đơn");
    }

    @Test(priority = 11)
    public void checkValidBillCode() {
        goodReceivedNotePage.checkValidBillCode("HD03?!2#");
    }

    @Test(priority = 12)
    public void checkEmptyAddressTest() {
        goodReceivedNotePage.checkEmptyAdress("Không được bỏ trống địa chỉ");
    }

    @Test(priority = 13)
    public void checkValidAddress() {
        goodReceivedNotePage.checkValidAddress("46 Man Thiện, P. Hiệp Phú $(#");
    }

    @Test(priority = 14)
    public void checkTransactionCodeList() {
        List<String> expOptions = Arrays.asList("TR-0008");
        goodReceivedNotePage.checkTransactionRequestList(expOptions);
    }

    @Test(priority = 15)
    public void checkDisplayTransactionRequestTest() {
        String expResult = "TR-0008";
        goodReceivedNotePage.checkDisplayTransactionRequest(expResult);
    }

    @Test(priority = 16)
    public void checkEmptyTransactionRequestTest() {
        String expResult = "Không được bỏ trống phiếu yêu cầu";
        goodReceivedNotePage.checkEmptyTransactionCode(expResult);
    }

    @Test(priority = 17)
    public void checkActualQuantityWithNegativeNumberTest() {
        goodReceivedNotePage.checkActualQuantityWithNegativeNumber("-99999");
    }

    @Test(priority = 18)
    public void checkActualQuantityWithZeroTest() {
        goodReceivedNotePage.checkActualQuantityWithZero();
    }

    @Test(priority = 19)
    public void checkActualQuantityIsOnlyNumericInputTest() {
        String letters = "78bdsdi";
        goodReceivedNotePage.checkActualQuantityIsOnlyNumericInput(letters);
    }

    @Test(priority = 20)
    public void checkValidActualQuantityTest() {
        String expMsg = "Không được bỏ trống số lượng";
        String transactionRequest = "TR-0008";
        goodReceivedNotePage.checkValidActualQuantity(transactionRequest, expMsg);
    }

    @Test(priority = 21)
    public void checkActualQuantityWithFloatTest() throws InterruptedException {
        String floatNumber = "99,26";
        goodReceivedNotePage.checkActualQuantityWithFloat(floatNumber);
    }

    @Test(priority = 22)
    public void checkActualQuantityLimitTest() {
        String expMsg = "Số lượng vượt quá giới hạn (999,999)";
        String limit = "9999999";
        goodReceivedNotePage.checkActualQuantityLimit(limit, expMsg);
    }

    @Test(priority = 23)
    public void checkDeleteRow() {
        String transactionRequest = "TR-0008";
        goodReceivedNotePage.checkDeleteRow(transactionRequest);
    }

    @Test(priority = 24)
    public void checkCancelDeleteRow() {
        String transactionRequest = "TR-0008";
        goodReceivedNotePage.checkCancelDeleteRow(transactionRequest);
    }

    @Test(priority = 25)
    public void checkAddRow() {
        goodReceivedNotePage.checkAddRow();
    }


















}
