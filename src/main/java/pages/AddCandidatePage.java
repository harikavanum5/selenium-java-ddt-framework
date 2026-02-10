package pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import io.qameta.allure.Step;

public class AddCandidatePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public AddCandidatePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // wait until page loads
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[normalize-space()='Add Candidate']")));
    }

    private By firstName  = By.name("firstName");
    private By middleName = By.name("middleName");
    private By lastName   = By.name("lastName");

    private By email      = By.xpath("//label[text()='Email']/following::input[1]");
    private By phone      = By.xpath("//label[text()='Contact Number']/following::input[1]");

    private By saveBtn    = By.xpath("//button[normalize-space()='Save']");

    /* ================================= */

    @Step("Fill Candidate Details")
    public void fillCandidate(String f, String m, String l,
                              String mail, String ph) throws Exception {

        driver.findElement(firstName).sendKeys(f);
        Thread.sleep(1000);

        driver.findElement(middleName).sendKeys(m);
        driver.findElement(lastName).sendKeys(l);
        Thread.sleep(2000);

        driver.findElement(email).sendKeys(mail);
        driver.findElement(phone).sendKeys(ph);
    }

    @Step("Click Save")
    public void clickSave() throws InterruptedException {
        driver.findElement(saveBtn).click();
        Thread.sleep(2000);
    }
}
