package utils;

import java.io.ByteArrayInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;

public class AllureUtils {

    public static void attachScreenshot(WebDriver driver, String name) {

        byte[] screenshot =
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
    }
}
