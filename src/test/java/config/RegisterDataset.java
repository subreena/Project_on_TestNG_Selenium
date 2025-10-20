package config;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;
import utils.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RegisterDataset {
    @DataProvider(name = "RegisterDataset")
    public Object[][] getCSVData() throws IOException {
        ArrayList<Object[]> data=new ArrayList();
        Utils.generateRandomUsersCsv("./src/test/resources/userData.csv",3);
        CSVParser csvParser=new CSVParser(new FileReader("./src/test/resources/userData.csv"), CSVFormat.DEFAULT.withFirstRecordAsHeader());
        for(CSVRecord csvRecord:csvParser){
            String fName = csvRecord.get("firstName");
            String lName= csvRecord.get("lastName");
            String email = csvRecord.get("email");
            String password = csvRecord.get("password");
            String phoneNumber = csvRecord.get("phoneNumber");
            String address = csvRecord.get("address");
            data.add(new Object[]{fName,lName,email,password,phoneNumber,address});
        }
        return data.toArray(new Object[0][]);
    }
}
