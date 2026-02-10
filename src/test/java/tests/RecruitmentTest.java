
package tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
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


    /* =====================================================
       1️⃣ LOGIN TEST
       ===================================================== */

    @Test(priority = 1, description = "Login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User Login")
    public void loginTest() {

        loginPage = new LoginPage(driver);

        loginPage.login("Admin", "admin123");

    }


    /* =====================================================
       2️⃣ SEARCH TEST
       ===================================================== */

    // @Test(priority = 2, dependsOnMethods = "loginTest",
    //         description = "Search candidates using Vacancy & Status")
    // @Severity(SeverityLevel.NORMAL)
    // @Story("Search Candidates")
    // public void searchCandidateTest() throws Exception {

    //     Map<String, String> inputData =
    //             ExcelUtils.readInput(
    //                     "src/test/resources/OrangeHRM_Recruitment_Template.xlsx",
    //                     "Sheet1");

    //     String vacancy = inputData.get("Vacancy");
    //     String status  = inputData.get("Status");
    //     AllureUtils.attachScreenshot(driver, "Recruitment Page");

    //     loginPage = new LoginPage(driver);
    //     loginPage.clickRecruitment();


    //     recruitment = new RecruitmentPage(driver);

    //     recruitment.selectVacancy(vacancy);
    //     recruitment.selectStatus(status);
    //     recruitment.clickSearch();

    //     AllureUtils.attachScreenshot(driver, "Search Results");

    //     List<String[]> results = recruitment.captureResults();

    //     Assert.assertTrue(results.size() > 0, "No candidates found!");

    //     ExcelUtils.writeResultWithAutoId(
    //             "src/test/resources/Output.xlsx",
    //             "Result",
    //             results);
    // }

    @Test(priority = 2, dependsOnMethods = "loginTest")
    public void searchCandidateTest() {

        try {

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
                ExcelUtils.writeEmptySheetWithHeaders(outputPath, sheetName, headers);
            } else {
                ExcelUtils.writeResultWithAutoId(outputPath, sheetName, results);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /* =====================================================
       3️⃣ ADD CANDIDATE TEST
       ===================================================== */

    @Test(priority = 3, dependsOnMethods = "loginTest",
            description = "Add new candidate to Recruitment")
    @Severity(SeverityLevel.MINOR)
    @Story("Add Candidate")
    public void addCandidateTest() throws InterruptedException {

        loginPage = new LoginPage(driver);
        loginPage.clickRecruitment();

        recruitment = new RecruitmentPage(driver);

        addPage = recruitment.clickAdd();

        AllureUtils.attachScreenshot(driver, "Add Candidate Page");

        addPage.fillCandidate( "Siri","Vanum", "Automation",
                "siri@test.com",
                "9876543210"
        );

        AllureUtils.attachScreenshot(driver, "Filled Details");

        addPage.clickSave();

        AllureUtils.attachScreenshot(driver, "Candidate Saved");
    }
}



//
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
//import io.qameta.allure.Description;
//import io.qameta.allure.Severity;
//import io.qameta.allure.SeverityLevel;
//
//public class RecruitmentTest extends BaseTest {
//
//  @Test(
//      description = "Search recruitment candidates and save results to Excel"
//  )
//  @Severity(SeverityLevel.NORMAL)
//  @Description("Verify that recruitment candidates can be searched based on Vacancy and Status, " +
//               "capture the results, and save them in the Output.xlsx file")
//  public void searchRecruitmentAndSaveResults() throws Exception {
//
//      Map<String, String> inputData =
//              ExcelUtils.readInput(
//                      "src/test/resources/OrangeHRM_Recruitment_Template.xlsx",
//                      "Sheet1");
//
//      String vacancy = inputData.get("Vacancy");
//      String status  = inputData.get("Status");
//
//      System.out.println("Input Data: " + inputData);
//
//      LoginPage loginPage = new LoginPage(driver);
//      AllureUtils.attachScreenshot(driver, "After Login");
//
//      loginPage.login("Admin", "admin123");
//
//      loginPage.clickRecruitment();
//
//      RecruitmentPage recruitment = new RecruitmentPage(driver);
//
//      recruitment.selectVacancy(vacancy);
//      recruitment.selectStatus(status);
//      recruitment.clickSearch();
//      AllureUtils.attachScreenshot(driver, "Search Results Page");
//
//      List<String[]> results = recruitment.captureResults();
//   //   Assert.assertTrue(results.size() > 0, "No candidates found!");
//
//      ExcelUtils.writeResultWithAutoId(
//              "src/test/resources/Output.xlsx",
//              "Result",
//              results);
//
//      System.out.println("✅ Results saved successfully with Auto ID");
//
//      recruitment.clickReset();
//      Thread.sleep(2000);
////      recruitment.clickAdd();
//      
//      AddCandidatePage addPage = recruitment.clickAdd();
//      AllureUtils.attachScreenshot(driver, "Add Candidate Page");
//
//      addPage.fillCandidate(
//              "Siri",
//              "Vanum",
//              "Automation",
//              "siri@test.com",
//              "9876543210"
//      );
//      AllureUtils.attachScreenshot(driver, "Filled Candidate Details");
//
//      addPage.clickSave();
//      AllureUtils.attachScreenshot(driver, "Candidate Saved Successfully");
//
//      loginPage.clickRecruitment();
//  }
//}




































//
//package tests;
//
//import java.util.List;
//import java.util.Map;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import base.BaseTest;
//import pages.LoginPage;
//import pages.RecruitmentPage;
//import utils.ExcelUtils;
//
//import io.qameta.allure.Description;
//import io.qameta.allure.Severity;
//import io.qameta.allure.SeverityLevel;
//
//public class RecruitmentTest extends BaseTest {
//
//    /*
//     * =========================================================
//     * 1️⃣ INVALID LOGIN TEST (Demo start with negative scenario)
//     * =========================================================
//     */
////    @Test(priority = 1, description = "Verify login fails with invalid credentials")
////    @Severity(SeverityLevel.CRITICAL)
////    @Description("User should not login with invalid username and password and error message must be displayed")
////    public void invalidLoginTest() {
////
////        LoginPage loginPage = new LoginPage(driver);
////
////        loginPage.login("WrongUser", "WrongPass");
////
////        Assert.assertTrue(loginPage.isErrorDisplayed(),
////                "Error message not displayed for invalid login!");
////    }
//
//    /*
//     * =========================================================
//     * 2️⃣ VALID LOGIN TEST
//     * =========================================================
//     */
//    @Test(priority = 2, description = "Login to OrangeHRM with valid credentials")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Verify user can login successfully and dashboard is displayed")
//    public void loginTest() {
//
//        LoginPage loginPage = new LoginPage(driver);
//
//        loginPage.login("Admin", "admin123");
//
//        Assert.assertTrue(loginPage.verifyLoginSuccess(), "Login failed!");
//    }
//
//    /*
//     * =========================================================
//     * 3️⃣ SEARCH + EXPORT RESULTS
//     * =========================================================
//     */
//    @Test(priority = 3, description = "Search recruitment candidates and save results")
//    @Severity(SeverityLevel.NORMAL)
//    @Description("Search candidates using Vacancy and Status filters, capture table results and export to Excel")
//    public void searchRecruitmentTest() throws Exception {
//
//        Map<String, String> inputData =
//                ExcelUtils.readInput(
//                        "src/test/resources/OrangeHRM_Recruitment_Template.xlsx",
//                        "Sheet1");
//
//        String vacancy = inputData.get("Vacancy");
//        String status = inputData.get("Status");
//
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.login("Admin", "admin123");
//        loginPage.clickRecruitment();
//
//        RecruitmentPage recruitment = new RecruitmentPage(driver);
//
//        recruitment.selectVacancy(vacancy);
//        recruitment.selectStatus(status);
//        recruitment.clickSearch();
//
//        List<String[]> results = recruitment.captureResults();
//
//        Assert.assertTrue(results.size() > 0, "No candidates found!");
//
//        ExcelUtils.writeResultWithAutoId(
//                "src/test/resources/Output.xlsx",
//                "Result",
//                results);
//
//        System.out.println("✅ Results saved successfully with Auto ID");
//    }
//
//    /*
//     * =========================================================
//     * 4️⃣ RESET + ADD
//     * =========================================================
//     */
//    @Test(priority = 4, description = "Reset recruitment filters and click Add")
//    @Severity(SeverityLevel.MINOR)
//    @Description("Verify Reset clears filters and Add button opens Add Candidate form")
//    public void resetAndAddTest() {
//
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.login("Admin", "admin123");
//        loginPage.clickRecruitment();
//
//        RecruitmentPage recruitment = new RecruitmentPage(driver);
//
//        recruitment.clickReset();
//        recruitment.clickAdd();
//
//        System.out.println("✅ Reset and Add actions performed successfully");
//        Assert.assertTrue(true);
//    }
//}





//package tests;
//
//import java.util.List;
//import java.util.Map;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import base.BaseTest;
//import pages.LoginPage;
//import pages.RecruitmentPage;
//import utils.ExcelUtils;
//
//import io.qameta.allure.Description;
//import io.qameta.allure.Severity;
//import io.qameta.allure.SeverityLevel;
//
//public class RecruitmentTest extends BaseTest {
//
//    @Test(description = "Login to OrangeHRM with valid credentials")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Verify user can login successfully to the OrangeHRM application")
//    public void loginTest() throws Exception {
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.login("Admin", "admin123");
//        Assert.assertTrue(loginPage.verifyLoginSuccess(), "Login failed!");
//    }
//
//    @Test(description = "Search recruitment candidates and save results")
//    @Severity(SeverityLevel.NORMAL)
//    @Description("Verify recruitment candidates can be searched based on Vacancy and Status and saved to Excel")
//    public void searchRecruitmentTest() throws Exception {
//        Map<String, String> inputData =
//                ExcelUtils.readInput(
//                        "src/test/resources/OrangeHRM_Recruitment_Template.xlsx",
//                        "Sheet1");
//
//        String vacancy = inputData.get("Vacancy");
//        String status = inputData.get("Status");
//
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.login("Admin", "admin123");
//        loginPage.clickRecruitment();
//
//        RecruitmentPage recruitment = new RecruitmentPage(driver);
//        recruitment.selectVacancy(vacancy);
//        recruitment.selectStatus(status);
//        recruitment.clickSearch();
//
//        List<String[]> results = recruitment.captureResults();
//        Assert.assertTrue(results.size() > 0, "No candidates found!");
//
//        ExcelUtils.writeResultWithAutoId(
//                "src/test/resources/Output.xlsx",
//                "Result",
//                results);
//
//        System.out.println("✅ Results saved successfully with Auto ID");
//    }
//
//    @Test(description = "Reset recruitment filters and click Add")
//    @Severity(SeverityLevel.MINOR)
//    @Description("Verify Reset button clears filters and Add button is clickable")
//    public void resetAndAddTest() throws Exception {
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.login("Admin", "admin123");
//        loginPage.clickRecruitment();
//
//        RecruitmentPage recruitment = new RecruitmentPage(driver);
//        recruitment.clickReset();
//        recruitment.clickAdd();
//
//        System.out.println("✅ Reset and Add actions performed successfully");
//        Assert.assertTrue(true);
//    }
//}


//////////////////////////////working











//package tests;
//
//import java.util.List;
//import java.util.Map;
//
//import org.testng.annotations.Test;
//
//import base.BaseTest;
//import pages.LoginPage;
//import pages.RecruitmentPage;
//import utils.ExcelUtils;
//
//public class RecruitmentTest extends BaseTest {
//
//	@Test
//	public void searchRecruitmentAndSaveResults() throws Exception {
//		Map<String, String> inputData = ExcelUtils.readInput("src/test/resources/OrangeHRM_Recruitment_Template.xlsx",
//				"Sheet1");
//
//		System.out.println("Data read from Excel: " + inputData);
//
//		LoginPage loginPage = new LoginPage(driver);
//
//		loginPage.login("Admin", "admin123");
//
//		Thread.sleep(2000);
//		loginPage.clickRecruitment();
//
//		RecruitmentPage recruitment = new RecruitmentPage(driver);
//
//		recruitment.selectVacancy(inputData.get("Vacancy"));
//		recruitment.selectStatus(inputData.get("Status"));
//
//		recruitment.clickSearch();
//		Thread.sleep(2000);
//
//		List<String[]> results = recruitment.captureResults();
//		ExcelUtils.writeResult("src/test/resources/Output.xlsx", "Result", results);
//		System.out.println("Test Completed. Results saved to Output.xlsx");
//		recruitment.clickReset();
//		Thread.sleep(7000);
//
//		recruitment.clickAdd();
//		Thread.sleep(9000);
//
//	}
//}
