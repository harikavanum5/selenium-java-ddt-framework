package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import io.qameta.allure.Step;

public class RecruitmentPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public RecruitmentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }



    private By vacancyDropdown =
            By.xpath("//label[text()='Vacancy']/following::div[contains(@class,'select-text')][1]");

    private By statusDropdown =
            By.xpath("//label[text()='Status']/following::div[contains(@class,'select-text')][1]");

    private By searchBtn =
            By.xpath("//button[normalize-space()='Search']");

    private By resetBtn =
            By.xpath("//button[normalize-space()='Reset']");

    private By addBtn =
            By.xpath("//button[normalize-space()='Add']");

    private By tableRows =
            By.xpath("//div[contains(@class,'oxd-table-body')]//div[@role='row']");

    private By loader =
            By.className("oxd-loading-spinner");




    private void waitForLoader() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        } catch (Exception ignored) {
        }
    }

    private void safeClick(By locator) {

        waitForLoader();

        WebElement element =
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

        wait.until(ExpectedConditions.elementToBeClickable(element));

        try {
            element.click();
        }
        catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
        }
    }

    private void selectDropdown(By locator, String value) {

        safeClick(locator);

        By option =
                By.xpath("//div[@role='listbox']//span[normalize-space()='" + value + "']");

        safeClick(option);
    }


    @Step("Select Vacancy: {vacancy}")
    public void selectVacancy(String vacancy) {
        selectDropdown(vacancyDropdown, vacancy);
    }

    @Step("Select Status: {status}")
    public void selectStatus(String status) {
        selectDropdown(statusDropdown, status);
    }

    @Step("Click Search")
    public void clickSearch() {
        safeClick(searchBtn);
    }

    @Step("Click Reset")
    public void clickReset() {
        safeClick(resetBtn);
    }

    @Step("Click Add Candidate")
    public AddCandidatePage clickAdd() {

        waitForLoader();

        wait.until(ExpectedConditions.visibilityOfElementLocated(addBtn));

        safeClick(addBtn);

        return new AddCandidatePage(driver);
    }

    @Step("Capture Recruitment Results")
    public List<String[]> captureResults() {

        waitForLoader();

        List<String[]> data = new ArrayList<>();

        // rows unte capture
        List<WebElement> rows = driver.findElements(tableRows);

        if (rows.size() == 0) {
            // No records case handled safely
            System.out.println("No Records Found in UI");
            return data; // empty list
        }

        for (WebElement row : rows) {

            List<WebElement> cells =
                    row.findElements(By.xpath(".//div[@role='cell']"));

            String[] rowData = new String[cells.size()];

            for (int i = 0; i < cells.size(); i++) {
                rowData[i] = cells.get(i).getText().trim();
            }

            data.add(rowData);
        }

        return data;
    }
}

//package pages;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.*;
//
//import io.qameta.allure.Step;
//
//public class RecruitmentPage {
//
//    private WebDriver driver;
//    private WebDriverWait wait;
//
//    public RecruitmentPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//    }
//
//    /* ========= Stable Locators ========= */
//
//    private By vacancyDropdown =
//            By.xpath("//label[text()='Vacancy']/following::div[contains(@class,'select-text')][1]");
//
//    private By statusDropdown =
//            By.xpath("//label[text()='Status']/following::div[contains(@class,'select-text')][1]");
//
//    private By searchBtn = By.xpath("//button[normalize-space()='Search']");
//    private By resetBtn = By.xpath("//button[normalize-space()='Reset']");
//    private By addBtn = By.xpath("//button[normalize-space()='Add']");
//
//    private By tableRows =
//            By.xpath("//div[contains(@class,'oxd-table-body')]//div[@role='row']");
//
//    /* ========= Common Dropdown ========= */
//
//    private void selectDropdown(By locator, String value) {
//
//        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
//
//        By option =
//                By.xpath("//div[@role='listbox']//span[normalize-space()='" + value + "']");
//
//        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
//    }
//
//    /* ========= Actions ========= */
//
//    @Step("Select Vacancy: {vacancy}")
//    public void selectVacancy(String vacancy) {
//        selectDropdown(vacancyDropdown, vacancy);
//    }
//
//    @Step("Select Status: {status}")
//    public void selectStatus(String status) {
//        selectDropdown(statusDropdown, status);
//    }
//
//    @Step("Click Search")
//    public void clickSearch() {
//        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
//    }
//
//    @Step("Click Reset")
//    public void clickReset() {
//        wait.until(ExpectedConditions.elementToBeClickable(resetBtn)).click();
//    }
//
//    @Step("Click Add")
//    public void clickAdd() {
//        wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
//    }
//
//    /* ========= Capture Table Results (Stable + No Timeout) ========= */
//
//    @Step("Capture Recruitment Results Table")
//    public List<String[]> captureResults() {
//
//        wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
//
//        List<WebElement> rows = driver.findElements(tableRows);
//
//        List<String[]> data = new ArrayList<>();
//
//        for (WebElement row : rows) {
//
//            List<WebElement> cells =
//                    row.findElements(By.xpath(".//div[@role='cell']"));
//
//            String[] rowData = new String[cells.size()];
//
//            for (int i = 0; i < cells.size(); i++) {
//                rowData[i] = cells.get(i).getText().trim();
//            }
//
//            data.add(rowData);
//        }
//
//        return data;
//    }
//}
//


















//package pages;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.*;
//
//import io.qameta.allure.Step;
//
//public class RecruitmentPage {
//
//    private WebDriver driver;
//    private WebDriverWait wait;
//
//    public RecruitmentPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//    }
//
//
//
//    private By vacancyDropdown =
//            By.xpath("//label[normalize-space()='Vacancy']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'select-text')]");
//
//    private By statusDropdown =
//            By.xpath("//label[normalize-space()='Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'select-text')]");
//
//    private By searchBtn =
//            By.xpath("//button[normalize-space()='Search']");
//
//    private By resetBtn =
//            By.xpath("//button[normalize-space()='Reset']");
//
//    private By addBtn =
//            By.xpath("//button[normalize-space()='Add']");
//
//    private By tableRows =
//            By.xpath("//div[@class='oxd-table-body']//div[@role='row']");
//
//
//    private void selectDropdown(By locator, String value) {
//
//        if (value == null || value.isEmpty())
//            throw new RuntimeException("Dropdown value is NULL or empty");
//
//        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
//
//        By option =
//                By.xpath("//div[@role='listbox']//span[normalize-space()='" + value + "']");
//
//        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
//    }
//
//
//
//    @Step("Select Vacancy: {vacancy}")
//    public void selectVacancy(String vacancy) {
//        selectDropdown(vacancyDropdown, vacancy);
//    }
//
//
//    @Step("Select Status: {status}")
//    public void selectStatus(String status) {
//        selectDropdown(statusDropdown, status);
//    }
//
//
//    @Step("Click Search button")
//    public void clickSearch() {
//        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
//    }
//
//
//    @Step("Click Reset button")
//    public void clickReset() {
//        wait.until(ExpectedConditions.elementToBeClickable(resetBtn)).click();
//    }
//
//
//    @Step("Click Add Candidate button")
//    public void clickAdd() {
//        wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
//    }
//
//
//    @Step("Capture table results")
//    public List<String[]> captureResults() {
//
//        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
//
//        List<WebElement> rows = driver.findElements(tableRows);
//
//        List<String[]> data = new ArrayList<>();
//
//        for (WebElement row : rows) {
//
//            List<WebElement> cols = row.findElements(By.xpath(".//div[@role='cell']"));
//
//            String[] rowData = new String[cols.size()];
//
//            for (int i = 0; i < cols.size(); i++) {
//                rowData[i] = cols.get(i).getText().trim();
//            }
//
//            data.add(rowData);
//        }
//
//        return data;
//    }
//}
