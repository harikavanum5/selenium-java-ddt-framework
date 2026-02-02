//package base;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.ITestResult;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Listeners;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import listeners.ExtentTestListener;
//import utils.AllureUtils;
//
//
//@Listeners(ExtentTestListener.class)
//
//public class BaseTest {
//
//    public WebDriver driver;
//
//    @BeforeMethod
//    public void setup() {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
//
//    }
//
//    @AfterMethod
//    public void tearDown() {
//        if(driver != null) {
//            driver.quit();
//        }
//    }
// 
//}
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

    @BeforeClass
    public void setup() {

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

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



