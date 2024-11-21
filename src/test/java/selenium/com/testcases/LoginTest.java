package selenium.com.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import selenium.com.pages.LoginPage;
import selenium.com.base.BaseSetUp;


public class LoginTest extends BaseSetUp{
    LoginPage loginPage;
    @BeforeMethod
    public void setUp() throws InterruptedException {
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1)
    public void loginSuccessfulTest() throws InterruptedException {
        loginPage.checkLoginSuccessful("mung", "12345");
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
    }

}
