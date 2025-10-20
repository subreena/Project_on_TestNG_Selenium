package testrunner;

import config.Setup;
import jdk.jshell.execution.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AdminDashboard;
import pages.DashboardPage;
import pages.Login;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;

public class LoginTestRunner extends Setup {

    @Test(priority = 1, description = "Check if admin can login")
    public void adminLogin() throws IOException {

        Login login = new Login(driver);
        String adminEmail = System.getProperty("email");
        String adminPassword = System.getProperty("password");
        login.doLogin(adminEmail, adminPassword);

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertEquals(dashboardPage.btnUserProfileIcon.get(0).isDisplayed(), true);
        String actual = driver.findElement(By.tagName("h2")).getText();
        String expected = "Admin Dashboard";
        Assert.assertEquals(actual, expected);
        Utils.getToken(driver);

    }

    @Test(priority = 2, description = "Check if admin can search user")
    public void adminSearchUser() throws IOException, ParseException {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody tr")));
        JSONObject userObj = Utils.readJsonData("./src/test/resources/users.json");
        String email = userObj.get("email").toString();
        AdminDashboard adminDashboard = new AdminDashboard(driver);
        String actualCount = adminDashboard.searchBox(driver,email);
        String expectedCount = "1";
        Assert.assertTrue(actualCount.contains(expectedCount), "Updated user email is showing on admin dashboard");
    }






}

