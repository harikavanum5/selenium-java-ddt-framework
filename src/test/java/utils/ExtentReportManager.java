package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getExtentReport() {

        if (extent == null) {
            ExtentSparkReporter reporter =
                    new ExtentSparkReporter("reports/ExtentReport.html");

            reporter.config().setReportName("OrangeHRM Automation Report");
            reporter.config().setDocumentTitle("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(reporter);

            extent.setSystemInfo("Project", "OrangeHRM");
            extent.setSystemInfo("Tester", "Sri Harika");
            extent.setSystemInfo("Browser", "Chrome");
        }
        return extent;
    }
}
