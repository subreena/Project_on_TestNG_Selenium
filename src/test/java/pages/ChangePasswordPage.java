package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChangePasswordPage {
    @FindBy(id=":r1:")
    WebElement newPassword;

    @FindBy(id= ":r3:")
    WebElement confirmPassword;

    @FindBy(css="[type='submit']")
    public WebElement btnChangePassword;

    public ChangePasswordPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void doChangePassword(String password){
        newPassword.sendKeys(password);
        confirmPassword.sendKeys(password);
        btnChangePassword.click();
    }
}
