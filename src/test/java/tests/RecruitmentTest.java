package tests;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.AddCandidatePage;
import pages.LoginPage;
import pages.RecruitmentPage;
import utils.AllureUtils;
import utils.ExcelUtils;

import io.qameta.allure.*;

@Epic("OrangeHRM Recruitment Module")
@Feature("Recruitment Flow")
public class RecruitmentTest extends BaseTest {

    LoginPage loginPage;
    RecruitmentPage recruitment;
    AddCandidatePage addPage;

    private static final String EXCEL_PATH =
            "src/test/resources/New-orgh-data.xlsx";

    private static final String SHEET_NAME = "Sheet1";

    @Test(priority = 1)
    public void loginTest() throws Exception {

        Map<String, String> inputData =
                ExcelUtils.readInput(EXCEL_PATH, SHEET_NAME);

        loginPage = new LoginPage(driver);
        loginPage.login(
                inputData.get("Username"),
                inputData.get("Password")
        );
    }

    @Test(priority = 2, dependsOnMethods = "loginTest")
    public void searchCandidateTest() throws Exception {

        Map<String, String> inputData =
                ExcelUtils.readInput(EXCEL_PATH, SHEET_NAME);

        loginPage = new LoginPage(driver);
        loginPage.clickRecruitment();

        recruitment = new RecruitmentPage(driver);

        recruitment.selectVacancy(inputData.get("Vacancy"));
        recruitment.selectStatus(inputData.get("Status"));
        recruitment.clickSearch();

        AllureUtils.attachScreenshot(driver, "Search Results");

        List<String[]> results = recruitment.captureResults();

        String outputPath = "src/test/resources/Output.xlsx";
        String sheetName = "Result";

        String[] headers = {
                "Candidate Name",
                "Vacancy",
                "Status",
                "Hiring Manager",
                "Date of Application"
        };

        if (results.isEmpty()) {
            ExcelUtils.writeEmptySheetWithHeaders(
                    outputPath,
                    sheetName,
                    headers
            );
        } else {
            ExcelUtils.writeResultWithAutoId(
                    outputPath,
                    sheetName,
                    results
            );
        }
    }

    @Test(priority = 3, dependsOnMethods = "loginTest")
    public void addCandidateTest() throws Exception {

        loginPage = new LoginPage(driver);
        loginPage.clickRecruitment();

        recruitment = new RecruitmentPage(driver);
        addPage = recruitment.clickAdd();

        addPage.fillCandidate(
                "Siri",
                "Ramya",
                "Vanum",
                "siri@test.com",
                "9876543210"
        );

        addPage.clickSave();
    }
}



//package tests;
//
//import java.util.List;
//import java.util.Map;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import base.BaseTest;
//import pages.AddCandidatePage;
//import pages.LoginPage;
//import pages.RecruitmentPage;
//import utils.AllureUtils;
//import utils.ExcelUtils;
//
//import io.qameta.allure.*;
//
//@Epic("OrangeHRM Recruitment Module")
//@Feature("Recruitment Flow")
//public class RecruitmentTest extends BaseTest {
//
//    LoginPage loginPage;
//    RecruitmentPage recruitment;
//    AddCandidatePage addPage;
//
//    private static final String EXCEL_PATH =
//            "src/test/resources/New-orgh-data.xlsx";
//
//    private static final String SHEET_NAME = "Sheet1";
//
//
//    @Test(priority = 1, description = "Login with valid credentials")
//    @Severity(SeverityLevel.CRITICAL)
//    @Story("User Login")
//    public void loginTest() throws Exception {
//
//        Map<String, String> inputData =
//                ExcelUtils.readInput(EXCEL_PATH, SHEET_NAME);
//
//        loginPage = new LoginPage(driver);
//
//        loginPage.login(
//                inputData.get("Username"),
//                inputData.get("Password")
//        );
//
////        AllureUtils.attachScreenshot(driver, "Login Successful");
//    }
//
//
//    @Test(priority = 2,
//          dependsOnMethods = "loginTest",
//          description = "Search candidates using Vacancy & Status")
//    @Severity(SeverityLevel.NORMAL)
//    @Story("Search Candidates")
//    public void searchCandidateTest() throws Exception {
//
//        Map<String, String> inputData =
//                ExcelUtils.readInput(EXCEL_PATH, SHEET_NAME);
//
//        loginPage = new LoginPage(driver);
//        loginPage.clickRecruitment();
//
//        recruitment = new RecruitmentPage(driver);
//
//        recruitment.selectVacancy(inputData.get("Vacancy"));
//        recruitment.selectStatus(inputData.get("Status"));
//        recruitment.clickSearch();
//
//        AllureUtils.attachScreenshot(driver, "Search Results");
//
//        List<String[]> results = recruitment.captureResults();
//
//        Assert.assertTrue(results.size() > 0,
//                "No candidates found!");
//
//        ExcelUtils.writeResultWithAutoId(
//                "src/test/resources/Output.xlsx",
//                "Result",
//                results);
//    }
//
//    @Test(priority = 3,
//          dependsOnMethods = "loginTest",
//          description = "Add new candidate to Recruitment")
//    @Severity(SeverityLevel.MINOR)
//    @Story("Add Candidate")
//    public void addCandidateTest() throws Exception {
//
//        loginPage = new LoginPage(driver);
//        loginPage.clickRecruitment();
//
//        recruitment = new RecruitmentPage(driver);
//        addPage = recruitment.clickAdd();
//
//        AllureUtils.attachScreenshot(driver, "Add Candidate Page");
//
//        addPage.fillCandidate(
//                "Siri",
//                "Ramya",
//                "Vanum",
//                "siri@test.com",
//                "9876543210"
//        );
//
//        AllureUtils.attachScreenshot(driver, "Filled Details");
//
//        addPage.clickSave();
//
//        AllureUtils.attachScreenshot(driver, "Candidate Saved");
//    }
//}
//















