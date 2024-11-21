package selenium.com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import selenium.com.base.ConfigData;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;


    By fieldUsername = By.xpath("//input[@id='username']");
    By fieldPassword = By.xpath("//input[@id='password']");
    By btnLogin = By.xpath("//button[@id='btn-login']");
    By btnDasboard = By.xpath("//div[@class='font-medium rounded-md py-2 px-5 hover:bg-gray-100 hover:text-indigo-00 bg-indigo-100 text-indigo-500']");

    public LoginPage(WebDriver driver) {
        if(driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void setUsername(String userName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fieldUsername));
        driver.findElement(fieldUsername).clear();
        driver.findElement(fieldUsername).sendKeys(userName);
    }

    public void setPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fieldPassword));
        driver.findElement(fieldPassword).clear();
        driver.findElement(fieldPassword).sendKeys(password);
    }

    public void clickBtnLogin() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(btnLogin));
        driver.findElement(btnLogin).click();
    }

    public void loginWarehouse(String username, String password) throws InterruptedException {
        driver.get(ConfigData.URL);
        setUsername(username);
        setPassword(password);
        Thread.sleep(3000);
        clickBtnLogin();
        Thread.sleep(10000);

    }

    public void checkLoginSuccessful(String username, String password) throws InterruptedException {
        loginWarehouse(username, password);
    }


}
