package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResetPage {

    @FindBy(id=":r1:")
    public WebElement resetEmail;

    @FindBy(css="[type='submit']")
    public WebElement btnReset;

    public ResetPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void doReset(String email){
        resetEmail.sendKeys(email);
        btnReset.click();
    }


}
