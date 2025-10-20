package testrunner;

import com.github.javafaker.Faker;
import config.ItemModel;
import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.ItemPage;
import pages.Login;
import utils.Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class AddItemsTestRunner extends Setup {
    @Test(priority = 1, description = "User can login after reset email")
    public void userLogin() throws IOException, ParseException {
        Login login = new Login(driver);
        JSONObject userObj = Utils.readJsonData("./src/test/resources/users.json");
        String email = userObj.get("email").toString();
        String password = userObj.get("password").toString();
        login.doLogin(email, password);

    }

    @Test(priority = 2, description = "User can create Item using All fields fields")
    public void userCreateItem() throws IOException, ParseException, InterruptedException {
        ItemPage itemPage = new ItemPage(driver);
        ItemModel itemModel = new ItemModel();
        itemPage.clickAddButton();

        Faker faker = new Faker();
        String txtItem = faker.commerce().productName();
        String inputQuantity = String.valueOf(faker.number().numberBetween(10, 100));
        String txtAmount = String.valueOf(faker.number().numberBetween(200, 2000));

        Date startDate = java.sql.Date.valueOf("2020-01-01");
        Date endDate = java.sql.Date.valueOf("2025-12-31");
        Date randomDate = faker.date().between(startDate, endDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String datePurchase = sdf.format(randomDate);


        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        String monthName = monthFormat.format(randomDate);

        itemModel.setTxtItem(txtItem);
        itemModel.setInputQuantity(inputQuantity);
        itemModel.setTxtAmount(txtAmount);
        itemModel.setDatePurchase(datePurchase);
        itemModel.setDropdownMonth(monthName);
        itemModel.setTxtRemarks("This is test remarks for product " + txtItem + " purchased on " + datePurchase);

        System.out.println(txtItem);
        System.out.println(inputQuantity);
        System.out.println(txtAmount);
        System.out.println(datePurchase);
        System.out.println(monthName);

        itemPage.addItem(itemModel);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("txtItem", txtItem);
        jsonObject.put("InputQuantity", inputQuantity);
        jsonObject.put("txtAmount", txtAmount);
        jsonObject.put("datePurchase", datePurchase);
        jsonObject.put("monthName", monthName);
        Utils.saveJsonData(jsonObject, "./src/test/resources/items.json");
       Utils.handleAlert(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody tr")));
        itemAssertions(driver,txtItem);
    }

    @Test(priority = 3, description = "User can create Item using mandatory fields")
    public void userCreateItemOptional() throws IOException, ParseException, InterruptedException {
        ItemPage itemPage = new ItemPage(driver);
        ItemModel itemModel = new ItemModel();
        itemPage.clickAddButton();

        Faker faker = new Faker();
        String txtItem = faker.commerce().productName();
        String txtAmount = String.valueOf(faker.number().numberBetween(200, 2000));

        itemModel.setTxtItem(txtItem);
        itemModel.setTxtAmount(txtAmount);

        System.out.println("Item: " + txtItem);
        System.out.println("Amount: " + txtAmount);

        itemPage.addItem(itemModel);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("txtItem", txtItem);
        jsonObject.put("txtAmount", txtAmount);
        Utils.saveJsonData(jsonObject, "./src/test/resources/items.json");

       Utils.handleAlert(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody tr")));
        itemAssertions(driver, txtItem);

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.doLogout();
    }



    public void itemAssertions(WebDriver driver, String itemName){
        List<WebElement> itemNames = driver.findElements(By.cssSelector("table tbody tr td:first-child"));
        boolean found = itemNames.stream()
                .map(WebElement::getText)
                .anyMatch(name -> name.equalsIgnoreCase(itemName));

        System.out.println("Item found in table: " + found);
        Assert.assertTrue(found, "Newly added item '" + itemName + "' not found in table!");
    }



}


