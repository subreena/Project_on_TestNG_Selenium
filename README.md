# TestNG & Selenium Automation Project

**Website:** [Daily Finance](https://dailyfinance.roadtocareer.net/)

This project demonstrates **web automation testing** using **Selenium** and **TestNG**. The tests cover **user registration, login, password reset, item management, profile updates, and admin operations**, including verification of emails through **Gmail API**.

---

## Test Scenarios Executed
**Test Case Link:** [link](https://docs.google.com/spreadsheets/d/162dH23vwkQQnjU0h7DjUhXx4tTh_9IuO/edit?usp=sharing&ouid=114143908846834533694&rtpof=true&sd=true)
### 1. User Registration and Email Verification
- Automates registration of a new Gmail-based account using **Faker-generated data**.  
- Stores registered user details in a **JSON file**.  
- Verifies that a **“Congratulations on Registering!”** email is received using Gmail API.  

### 2. Reset Password - Negative Test Cases
Two negative scenarios:  
1. Reset password using **blank email**.  
2. Reset password using **unregistered email**.  
- Assertions confirm that appropriate error messages are displayed.  

### 3. Reset Password - Positive Flow
- Input **registered email** and click **Send Reset Link**.  
- Assert that confirmation text matches the registered email.  

### 4. Password Update via Email
- Retrieve reset link from Gmail.  
- Update password for the requested email.  
- Dynamically update the password in the **JSON file**.  

### 5. Login with Updated Password
- Logs in with the **new password**.  
- Verifies **successful dashboard access**.  

### 6. Adding Items
- Add **2 items**:  
  1. With **all fields filled**.  
  2. With **only mandatory fields** (e.g., item name and amount).  
- Assert that both items appear in the **user’s item list**.  

### 7. Update User Gmail
- Update user email in the **profile section**.  
- Alerts handled, and **JSON file updated dynamically**.  

### 8. Login Verification After Email Update
- Login with **new email** → success.  
- Login with **old email** → failure.  

### 9. Admin Login
- Admin credentials securely provided via command line:  
```bash gradle clean test -Pemail="admin@test.com" -Ppassword="admin123" ```
- Successfully logs in and navigates to admin dashboard.
- ## 10. Admin Email Search
- Search for the **updated user email** in the admin dashboard.  
- Assert that the email is **displayed and counted correctly**.  

### 11. Bulk User Registration from CSV
- Register **3 users** from a CSV file.  
- CSV is dynamically updated each time the function is executed.  

## 12. Export Users to Text File
- Login as admin.  
- Retrieve all user data from the **admin user table**.  
- Save user data in a **text file**.  

---

### Gmail Integration
- Used [Google OAuth Playground](https://developers.google.com/oauthplayground/) to access Gmail API:  
  1. Selected **Gmail API v1**.  
  2. Exchanged authorization code for a token.  
  3. Copied the **access token** and saved it in `config.properties`.  

---

### Prerequisites
- **Selenium** for automation.  
- **TestNG** as testing framework.  
- **IDE:** IntelliJ IDEA.  
- **Gradle** for dependency management.  
- **Data Manipulation:** JSON and CSV parser.  

---

### How to Run the Project
1. Clone the project repository.  
2. Open in **IntelliJ IDEA**: File > Open > Select project folder > Open as Project
3. 3. Run the test suite with Gradle:  
```bash gradle clean test -Pemail="admin@test.com" -Ppassword="admin123" -PsuiteName="regressionSuite.xml" ```
4. Generate and serve Allure report:
allure generate allure-results --clean -o allure-report
allure serve allure-results


 ##Output
 ---
 ###Allure Reports:
 <img width="1366" height="653" alt="img1" src="https://github.com/user-attachments/assets/ed98fa4e-6b75-459d-a63f-dd014f91c5b3" />
<img width="1366" height="821" alt="img2" src="https://github.com/user-attachments/assets/33d640ce-f990-4607-8685-5c9b9a623a9b" />

 ###Full Automation Process:
 https://github.com/user-attachments/assets/f0f3be68-96c5-47ff-8b29-609fde0938fb


