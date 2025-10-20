package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserProfilePage {
    @FindBy(id = ":r9:")
    public WebElement updatedEmail;

    @FindBy(xpath = "//button[contains(text(),'Edit')]")
    public WebElement btnEdit;

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    public WebElement btnUpdate;

    public UserProfilePage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }



    public void clickEdit(WebDriver driver){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", btnEdit);
        btnEdit.click();
    }


    public void updateEmail(String email){
        updatedEmail.sendKeys(Keys.CONTROL+"a", Keys.BACK_SPACE);
        updatedEmail.sendKeys(email);
        btnUpdate.click();
    }
}
