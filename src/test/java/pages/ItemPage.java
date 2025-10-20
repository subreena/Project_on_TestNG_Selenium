package pages;

import config.ItemModel;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ItemPage {
    @FindBy(className = "add-cost-button")
    public WebElement btnAdd;

    @FindBy(id = "itemName")
    public WebElement txtItem;

    @FindBy(id="amount")
    public WebElement txtAmount;

    @FindBy(css = ".quantity-container button:first-of-type")
    public WebElement btnMinus;

    @FindBy(css = ".quantity-container input[type='number']")
    public WebElement inputQuantity;

    @FindBy(css = ".quantity-container button:last-of-type")
    public WebElement btnPlus;

    @FindBy(id = "purchaseDate")
    public WebElement datePurchase;

    @FindBy(id = "month")
    public WebElement dropdownMonth;

    @FindBy(id = "remarks")
    public  WebElement txtRemarks;

    @FindBy(className = "submit-button")
    public WebElement btnSubmit;

    @FindBy(className = "reset-button")
    public WebElement btnReset;

    public ItemPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void clickAddButton(){
        btnAdd.click();
    }

    public void addItem(ItemModel itemModel) throws InterruptedException {
        // Always required
        txtItem.sendKeys(itemModel.getTxtItem());
        txtAmount.sendKeys(itemModel.getTxtAmount());

        inputQuantity.sendKeys(itemModel.getInputQuantity() != null? itemModel.getInputQuantity(): "1");

        if (itemModel.getDatePurchase() != null && !itemModel.getDatePurchase().isEmpty()) {
            datePurchase.clear();
            datePurchase.sendKeys(itemModel.getDatePurchase());
        }

        if (itemModel.getDropdownMonth() != null && !itemModel.getDropdownMonth().isEmpty()) {
            Select selectMonth = new Select(dropdownMonth);
            selectMonth.selectByVisibleText(itemModel.getDropdownMonth());
        }

        if (itemModel.getTxtRemarks() != null && !itemModel.getTxtRemarks().isEmpty()) {
            txtRemarks.clear();
            txtRemarks.sendKeys(itemModel.getTxtRemarks());
        }

        btnSubmit.click();
        Thread.sleep(2000);
    }

}
