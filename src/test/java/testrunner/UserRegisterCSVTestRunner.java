package testrunner;

import config.RegisterDataset;
import config.Setup;
import config.UserModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.Login;
import pages.SignUpPage;
import services.GmailServices;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

public class UserRegisterCSVTestRunner extends Setup {
    @Test(priority = 1,description = "Check if user can register from csv", dataProvider = "RegisterDataset", dataProviderClass = RegisterDataset.class)
    public void userRegisterCSV(String fName, String lName, String email, String pass, String pNm, String addr) throws IOException, ParseException, InterruptedException {

        SignUpPage signUpPage  = new SignUpPage(driver);
        Utils.scroller(driver,500);
        signUpPage.linkRegister.click();

        UserModel userModel = new UserModel();
        userModel.setFirstname(fName);
        userModel.setLastname(lName);
        userModel.setEmail(email);
        userModel.setPassword(pass);
        userModel.setPhonenumber(pNm);
        userModel.setAddress(addr);
        signUpPage.doSignup(userModel);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", fName);
        jsonObject.put("lastName", lName);
        jsonObject.put("email", email);
        jsonObject.put("password", pass);
        try {
//            Utils.saveJsonData(jsonObject, "./src/test/resources/users.json");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));


        }
        catch (Exception e){
            System.out.println("Failed "+e);
        }


    }

}
