package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentReports;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.AllureUtils;
import utils.ExtentReportManager;

@Listeners(listeners.ExtentTestListener.class)
public class BaseTest {

    protected static ExtentReports extent;
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentReportManager.getExtentReport(); // Initialize ExtentReports
    }

    @BeforeClass
    public void setup() {
        // Setup ChromeDriver automatically
        WebDriverManager.chromedriver().setup();

        // Configure ChromeOptions for Jenkins / CI
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");          // Maximize window
        options.addArguments("--no-sandbox");               // Needed for CI
        options.addArguments("--disable-dev-shm-usage");    // Overcome limited /dev/shm
        options.addArguments("--headless=new");             // Headless mode for CI
        options.addArguments("--disable-gpu");              // Disable GPU (headless)

        // Initialize driver
        driver = new ChromeDriver(options);

        // Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Navigate to the application
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            AllureUtils.attachScreenshot(driver, "FAILED - " + result.getName());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (extent != null) {
            extent.flush();
        }
    }

    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
