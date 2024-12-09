package selenium.com.browsers.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BaseSetup {

    public static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            // Initialize WebDriver here if it's not already initialized
            driver = new ChromeDriver();  // Example for ChromeDriver
        }
        return driver;
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void createBrowser(@Optional("chrome") String browserName) {
        if (browserName.equals("chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.equals("edge")) {
            driver = new EdgeDriver();
        } else if (browserName.equals("firefox")) {
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Browser \"" + browserName + "\" not supported.");
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40)); //40
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); //20
    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }

}
