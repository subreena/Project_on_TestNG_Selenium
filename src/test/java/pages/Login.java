package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
    @FindBy(id="email")
   public WebElement txtEmail;
    @FindBy(id="password")
    public WebElement txtPassword;
    @FindBy(css="[type='submit']")
    public WebElement btnSubmit;

    public Login(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void doLogin(String email, String password){
        txtEmail.sendKeys(email);
        txtPassword.sendKeys(password);
        btnSubmit.click();
    }


}
