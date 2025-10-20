package pages;

import config.UserModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SignUpPage {
    @FindBy(partialLinkText = "Register")
    public WebElement linkRegister;

    @FindBy(id="firstName")
    public WebElement txtFname;

    @FindBy(id="lastName")
    public WebElement txtLname;

    @FindBy(id="email")
    public WebElement txtEmail;

    @FindBy(id="password")
    public WebElement txtPassword;
    @FindBy(id="phoneNumber")
    public WebElement txtPhoneNumber;
    @FindBy(id="address")
    public WebElement txtAddress;
    @FindBy(css="[type=radio]")
    public List <WebElement> rbGender;
    @FindBy(css="[type=checkbox]")
    public WebElement checkbox;
    @FindBy(id="register")
    public WebElement btnRegister;



    public SignUpPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }


    public  void doSignup(UserModel userModel){
        txtFname.sendKeys(userModel.getFirstname());
        txtLname.sendKeys(userModel.getLastname()!=null?userModel.getLastname(): "");
        txtEmail.sendKeys(userModel.getEmail());
        txtPassword.sendKeys(userModel.getPassword());
        txtPhoneNumber.sendKeys(userModel.getPhonenumber());
        txtAddress.sendKeys(userModel.getAddress()!=null?userModel.getAddress(): "");
        rbGender.get(1).click();
        checkbox.click();
        btnRegister.click();

    }


}
