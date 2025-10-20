package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DashboardPage {
    @FindBy(css = "[type=button]")
    public List<WebElement> btnUserProfileIcon;
    @FindBy(css = "[role=menuitem]")
    public List<WebElement> btnMenuItem;
    @FindBy(css = "a[href='/forgot-password']")
    public WebElement btnReset;


    public DashboardPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void doLogout(){
        btnUserProfileIcon.get(0).click(); //click on profile menu item
        btnMenuItem.get(1).click(); //click on logout button
    }

    public void goToProfile(){
        btnUserProfileIcon.get(0).click();
        btnMenuItem.get(0).click();
    }

    public void clickReset(){
        btnReset.click();
    }


}
