package testrunner;

import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ChangePasswordPage;
import pages.DashboardPage;
import pages.ResetPage;
import services.GmailServices;
import utils.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetTestRunner extends Setup {
    @Test(priority = 1, description = "Click on reset link")
    public void testResest(){
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickReset();
    }

    @Test(priority = 2, description = "Reset by Blank")
    public void resetByBlankEmail(){
        ResetPage resetPage = new ResetPage(driver);
        resetPage.doReset(" ");

        String validationMsg = resetPage.resetEmail.getAttribute("validationMessage");
        System.out.println("Browser Validation Message: " + validationMsg);
        Assert.assertTrue(validationMsg.contains("fill out this field"), "Validation message did not appear as expected!");
        clearResetData();
    }


    @Test(priority = 3, description = "Reset by Unregistered Email")
    public void resetByUnregisteredEmail(){
        ResetPage resetPage = new ResetPage(driver);
        resetPage.doReset("unregistered@gmail.com");

        String actual = driver.findElement(By.tagName("p")).getText();
        String expected = "Your email is not registered";
        Assert.assertEquals(actual,expected);

        clearResetData();
    }


    @Test(priority = 4,description = "Reset using valid email")
    public void resetByValidEmail() throws IOException, InterruptedException, ParseException {

        ResetPage resetPage = new ResetPage(driver);

        JSONObject userObj = Utils.readJsonData("./src/test/resources/users.json");
        String email = userObj.get("email").toString();
        System.out.println(email);
        resetPage.doReset(email);
        Thread.sleep(5000);
        String actual = driver.findElement(By.tagName("p")).getText();
        String expected = "Password reset link sent to your email";
        Assert.assertEquals(actual,expected);

        GmailServices gmailServices = new GmailServices();
        String myEmail = gmailServices.readEmail();
        System.out.println(myEmail);
        Assert.assertTrue(myEmail.contains("Click on the following link to reset your password"));
        clearResetData();
    }

    @Test(priority = 5, description = "Retrieve email for password reset")
    public void retriveMail() throws IOException, ParseException {
        GmailServices gmailServices = new GmailServices();
        String myEmail = gmailServices.readEmail();
//        System.out.println(myEmail);
        Pattern pattern = Pattern.compile("(https?://\\S+)");
        Matcher matcher = pattern.matcher(myEmail);

        String resetLink = null;
        if (matcher.find()) {
            resetLink = matcher.group(1);
        }

        System.out.println("Reset Password Link: " + resetLink);
        Assert.assertNotNull(resetLink, "Reset link not found in the email!");
        driver.get(resetLink);

        String newPassword = "4321";

        ChangePasswordPage changePasswordPage = new ChangePasswordPage(driver);
        changePasswordPage.doChangePassword(newPassword);


        JSONObject userObj = Utils.readJsonData("./src/test/resources/users.json");
        String resetEmail = userObj.get("email").toString();
       updatePasswordInJSON("./src/test/resources/users.json",resetEmail, newPassword);

       String actual = driver.findElement(By.tagName("p")).getText();
       String expected = "Password reset successfully";
       Assert.assertEquals(actual,expected);
    }

    public void updatePasswordInJSON(String filepath, String email, String password) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray users = (JSONArray) jsonParser.parse(new FileReader(filepath));

        boolean updated = false;
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.get("email").equals(email)) {
                user.put("password", password);
                updated = true;
                break;
            }
        }

        try (FileWriter file = new FileWriter(filepath)) {
            file.write(users.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (updated)
            System.out.println("Password updated in JSON for user: " + email);
        else
            System.out.println(" Email not found in JSON: " + email);
    }

    public void clearResetData(){
        ResetPage resetPage = new ResetPage(driver);
        resetPage.resetEmail.sendKeys(Keys.CONTROL+"a", Keys.BACK_SPACE);
    }


}
