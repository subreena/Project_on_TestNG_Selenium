package testrunner;

import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.Login;
import pages.UserProfilePage;
import utils.Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserGmailTestRunner extends Setup {

    @Test(priority = 1, description = "Verify user can update gmail and login with updated gmail")
    public void updateUserGmail() throws IOException, ParseException {
        Login login = new Login(driver);
        JSONObject userObj = Utils.readJsonData("./src/test/resources/users.json");
        String oldEmail = userObj.get("email").toString();
        String password = userObj.get("password").toString();
        login.doLogin(oldEmail, password);

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.goToProfile();
        UserProfilePage userProfilePage = new UserProfilePage(driver);
        userProfilePage.clickEdit(driver);

        String newEmail= "teachercopilot123+"+Utils.generateNumber(10000,99999)+"@gmail.com";
        userProfilePage.updateEmail(newEmail);

        System.out.println(oldEmail +"\n"+ newEmail);
        updateEmailInJson("./src/test/resources/users.json",oldEmail,newEmail);
        Utils.handleAlert(driver);
        dashboardPage.doLogout();

        //assert user cannot login with old email;
        login.doLogin(oldEmail,password);
        String oldActual = driver.findElement(By.tagName("p")).getText();
        String oldExpected = "Invalid email or password";
        Assert.assertEquals(oldActual, oldExpected, "User cannot login with old email");
        Utils.clearData(driver);

        //assert use can login with new email;
        login.doLogin(newEmail,password);
        String newActual = driver.findElement(By.tagName("h2")).getText();
        String newExpected = "User Daily Costs";
        Assert.assertEquals(newActual, newExpected, "User can login with new email");

        dashboardPage.doLogout();

    }




    public void updateEmailInJson(String filepath, String oldEmail, String newEmail) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray users = (JSONArray) jsonParser.parse(new FileReader(filepath));

        boolean updated = false;
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (oldEmail.equals(user.get("email"))) { // safer null check
                user.put("email", newEmail);
                updated = true;
                break;
            }
        }

        try (FileWriter file = new FileWriter(filepath)) {
            file.write(users.toJSONString()); // note: all in one line
            file.flush();
        }

        if (!updated) {
            System.out.println("Old email not found in JSON file.");
        } else {
            System.out.println("Email updated successfully.");
        }
    }
}
