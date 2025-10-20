package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminDashboard {
    public AdminDashboard(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "search-box")
    public WebElement searchBox;

    @FindBy(className = "total-count")
    public WebElement totalCount;

    public String searchBox(WebDriver driver, String email){
        searchBox.sendKeys(email);
        String count = totalCount.getText();
        String numberOnly = count.replaceAll("\\D+", "");

        return numberOnly;
    }
}
