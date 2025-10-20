package testrunner;

import config.Setup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.Login;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UserTableScrapTestRunner extends Setup {
    @Test(priority = 1, description = "Get all the users from user table and write them in a text file")
    public void getTableData() throws IOException {
        Login login = new Login(driver);
        if (System.getProperty("email") != null && System.getProperty("password") != null) {
            String adminEmail = System.getProperty("email");
            String adminPassword = System.getProperty("password");
            login.doLogin(adminEmail, adminPassword);
        } else {
            login.doLogin("admin@test.com", "admin123");
        }

        WebElement div = driver.findElement(By.cssSelector(".admin-dashboard"));
        WebElement table = div.findElement(By.tagName("table"));
        List<WebElement> allTbody = table.findElements(By.tagName("tbody"));

        try (FileWriter writer = new FileWriter("./src/test/resources/tableData.txt")) {

            for (WebElement tbody : allTbody) {
                List<WebElement> rows = tbody.findElements(By.tagName("tr"));

                for (WebElement row : rows) {
                    List<WebElement> cells = row.findElements(By.tagName("td"));

                    // Exclude last column
                    int totalCells = cells.size();
                    for (int i = 0; i < totalCells - 1; i++) {
                        String cellText = cells.get(i).getText();
                        System.out.print(cellText + " ");
                        writer.write(cellText + " | ");
                    }

                    System.out.println();
                    writer.write("\n");
                }
            }
        }

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.doLogout();
    }
}
