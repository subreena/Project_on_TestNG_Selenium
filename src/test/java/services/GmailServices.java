package services;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class GmailServices {
    Properties prop;
    public GmailServices() throws IOException {
        prop = new Properties();
        FileInputStream fs = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fs);
    }
    public String getEmailList(){
        RestAssured.baseURI = "https://gmail.googleapis.com";
        Response res = given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token"))
                .when().get("/gmail/v1/users/me/messages");
//        System.out.println(res.asString());
        JsonPath jsonObj = res.jsonPath();
        String listId = jsonObj.get("messages[0].id");
        return listId;
    }

    public String readEmail() throws IOException {
        GmailServices gmailServices = new GmailServices();
        String EmailId = gmailServices.getEmailList();

        RestAssured.baseURI = "https://gmail.googleapis.com";
        Response res = given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("token"))
                .when().get("/gmail/v1/users/me/messages/"+ EmailId);
//        System.out.println(res.asString());
        JsonPath jsonObj = res.jsonPath();
        String Email = jsonObj.get("snippet");
        return Email;
    }

    public static void main(String[] args) throws IOException {
        try {
            GmailServices gmailServices = new GmailServices();
            String readLatestEmail = gmailServices.readEmail();
            System.out.println(readLatestEmail);
        } catch (Exception e) {
            e.printStackTrace(); // show the full stack trace
        }
    }
}
