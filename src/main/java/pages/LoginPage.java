package pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import io.qameta.allure.Step;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private By recruitmentMenu = By.xpath("//span[text()='Recruitment']");
    private By errorMsg = By.xpath("//p[contains(@class,'oxd-alert-content-text')]");

    @Step("Enter Username: {username}")
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
    }

    @Step("Enter Password")
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
    }

    @Step("Click Login")
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    @Step("Login with credentials")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    @Step("Verify dashboard visible")
    public boolean verifyLoginSuccess() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Verify error message visible")
    public boolean isErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Click Recruitment menu")
    public void clickRecruitment() {
        wait.until(ExpectedConditions.elementToBeClickable(recruitmentMenu)).click();
    }
}














//package pages;
//
//import java.time.Duration;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.*;
//
//import io.qameta.allure.Step;
//
//public class LoginPage {
//
//    private WebDriver driver;
//    private WebDriverWait wait;
//
//    public LoginPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//    }
//
//
//    private By usernameField = By.name("username");
//    private By passwordField = By.name("password");
//    private By loginButton = By.cssSelector("button[type='submit']");
//    private By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
//    private By recruitmentMenu =
//            By.xpath("//span[normalize-space()='Recruitment']");
//
//
//
//
//    @Step("Enter Username: {username}")
//    public void enterUsername(String username) {
//        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
//        el.clear();
//        el.sendKeys(username);
//    }
//
//
//    @Step("Enter Password")
//    public void enterPassword(String password) {
//        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
//        el.clear();
//        el.sendKeys(password);
//    }
//
//
//    @Step("Click Login Button")
//    public void clickLogin() {
//        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
//    }
//
//
//    @Step("Login with valid credentials")
//    public void login(String username, String password) {
//        enterUsername(username);
//        enterPassword(password);
//        clickLogin();
//    }
//
//
//    @Step("Verify Login Success - Dashboard displayed")
//    public boolean verifyLoginSuccess() {
//        try {
//            return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader)).isDisplayed();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//
//    @Step("Click Recruitment Menu")
//    public void clickRecruitment() {
//        wait.until(ExpectedConditions.elementToBeClickable(recruitmentMenu)).click();
//    }
//}
