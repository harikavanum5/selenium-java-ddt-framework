

package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

	@BeforeSuite
	public void setupReport() {
		extent = ExtentReportManager.getExtentReport();
	}

	@BeforeClass
	public void setup() {

		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

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
		extent.flush();
		driver.quit();
	}

	@AfterSuite
	public void tearDownReport() {
		extent.flush();
	}
}
//
//












