package utils;

import com.github.javafaker.Faker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Login;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

public class Utils {
    public static int generateNumber(int min, int max){
        double randomNumber = Math.random()*(max-min)+min;
        return (int) randomNumber;
    }

    public static void scroller(WebDriver driver, int px){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,"+px+")");
    }

    public static void saveJsonData(JSONObject jsnObject, String filePath) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;

        try (FileReader reader = new FileReader(filePath)) {
            Object obj = jsonParser.parse(reader);
            jsonArray = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            // If file doesn't exist or is empty, start a new JSON array
            jsonArray = new JSONArray();
        }

        // Add the new object
        jsonArray.add(jsnObject);

        // Save back to file
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
        }
    }
    public static JSONObject readJsonData(String filepath) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(filepath));

        JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.size()-1);
        return jsonObject;
    }

    public static void getToken(WebDriver driver) throws IOException {
        //wait until the authToken is available
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until((ExpectedCondition<Boolean>) wd -> js.executeScript("return window.localStorage.getItem('authToken')") != null);

        //get the authToken from the localstorage
        String authToken = (String) js.executeScript("return window.localStorage.getItem('authToken');");
        String authTokenData=(String) js.executeScript("return window.localStorage.getItem('authTokenData');");
        System.out.println("Auth Token Retrieved: " + authToken);
        System.out.println("Auth Token Retrieved: " + authTokenData);

        // Save the auth token to a localstorage.json file
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authToken", authToken);
        jsonObject.put("authTokenData", authTokenData);
        FileWriter writer=new FileWriter("./src/test/resources/localstorage.json");
        writer.write(jsonObject.toJSONString());
        writer.flush();
        writer.close();
    }
    public static void setAuth(WebDriver driver) throws ParseException, InterruptedException, IOException {
        JSONParser jsonParser=new JSONParser();
        JSONObject authObj= (JSONObject) jsonParser.parse(new FileReader( "./src/test/resources/localstorage.json"));
        String authToken= authObj.get("authToken").toString();
        String authTokenData= authObj.get("authTokenData").toString();
        System.out.println(authToken);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.setItem('authToken', arguments[0]);", authToken);
        js.executeScript("window.localStorage.setItem('authTokenData', arguments[0]);", authTokenData);
        Thread.sleep(2000);
    }

    public static void handleAlert(WebDriver driver) {
        try {
            // Wait for the alert to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            // Print alert text (optional)
            System.out.println("Alert text: " + alert.getText());

            // Click OK
            alert.accept();
            System.out.println("Alert accepted successfully.");
        } catch (Exception e) {
            System.out.println("No alert found: " + e.getMessage());
        }

    }

    public static void clearData(WebDriver driver){
        Login login = new Login(driver);
        login.txtEmail.sendKeys(Keys.CONTROL+"a", Keys.BACK_SPACE);
        login.txtPassword.sendKeys(Keys.CONTROL+"a", Keys.BACK_SPACE);
    }

    public static void generateRandomUsersCsv(String filePath, int numberOfUsers) {
        Faker faker = new Faker();
        String header = "firstName,lastName,email,password,phoneNumber,address";

        try (FileWriter writer = new FileWriter(filePath, false)) { // false = overwrite
            // Write header
            writer.append(header).append("\n");

            // Generate each user
            for (int i = 0; i < numberOfUsers; i++) {
                String firstName = faker.name().firstName();
                String lastName= faker.name().lastName();
                String email= "teachercopilot123+"+Utils.generateNumber(10000,99999)+"@gmail.com";
                String password ="1234";
                String phoneNumber ="0190"+Utils.generateNumber(1000000,9999999);
                String address =faker.country().capital();

                // Write CSV row
                String row = String.join(",", firstName, lastName, email, password, phoneNumber, address);
                writer.append(row).append("\n");
            }

            System.out.println("Random CSV file generated successfully: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//    public static void main(String[] args) {
//        System.out.println(generateNumber(1000,9999));
//    }
}
