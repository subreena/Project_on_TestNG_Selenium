package testrunner;

import com.github.javafaker.Faker;
import config.Setup;
import config.UserModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.Login;
import pages.SignUpPage;
import services.GmailServices;
import utils.Utils;

import java.io.IOException;


public class SignUpTestRunner extends Setup {
    @Test(priority = 1, description = "Verify User can register")
    public void userReg() throws IOException {
        SignUpPage signUpPage = new SignUpPage(driver);
        GmailServices gmailServices = new GmailServices();
        Utils.scroller(driver,500);
        signUpPage.linkRegister.click();
        Faker faker = new Faker();
        String fName = faker.name().firstName();
        String lName= faker.name().lastName();
        String email= "teachercopilot123+"+Utils.generateNumber(10000,99999)+"@gmail.com";
        String pass="1234";
        String pNm="0190"+Utils.generateNumber(1000000,9999999);
        String addr=faker.country().capital();

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
            Utils.saveJsonData(jsonObject, "./src/test/resources/users.json");
            System.out.println("Successful!" + email);
            Thread.sleep(5000); //if ager email ashe
            String myEmail = gmailServices.readEmail();
            System.out.println(myEmail);
            Assert.assertTrue(myEmail.contains("Welcome to our platform!"));
        }
        catch (Exception e){
            System.out.println("Failed "+e);
        }
    }


}
