
package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.AllureUtils;

public class BaseTest {

    protected WebDriver driver;

    /* =================================
       OPEN ONLY ONCE
       ================================= */

    // @BeforeClass
    // public void setup() {

    //     WebDriverManager.chromedriver().setup();

    //     driver = new ChromeDriver();

    //     driver.manage().window().maximize();
    //     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    //     driver.get("https://opensource-demo.orangehrmlive.com/");
    // }
    @BeforeClass
public void setup() {

    WebDriverManager.chromedriver().setup();

    ChromeOptions options = new ChromeOptions();

    // 🔥 Jenkins safe settings
    options.addArguments("--headless=new");     // CI mode
    options.addArguments("--window-size=1920,1080");
    options.addArguments("--disable-gpu");
    options.addArguments("--remote-allow-origins=*");
    options.addArguments("--no-sandbox");

    driver = new ChromeDriver(options);

    //  remove implicit wait (causes flakiness)
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

    driver.get("https://opensource-demo.orangehrmlive.com/");
}


    /* =================================
       FAILURE SCREENSHOT
       ================================= */

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {
            AllureUtils.attachScreenshot(driver, "FAILED - " + result.getName());
        }
    }


    /* =================================
       CLOSE ONLY ONCE
       ================================= */

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}



