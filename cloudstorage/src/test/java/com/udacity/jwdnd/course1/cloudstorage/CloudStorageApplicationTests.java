package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;
    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private WebElement inputElement;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 3);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

//		/* Check that the sign up was successful.
//		// You may have to modify the element "success-msg" and the sign-up
//		// success message below depening on the rest of your code.
//		*/
//        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a successful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */

    // Do log out
    private void doLogOut() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("log-out")));
        WebElement logoutButton = driver.findElement(By.id("log-out"));
        logoutButton.click();
    }

    @Test
    public void testRedirection() {
        driver.get("http://localhost:" + this.port + "/signup");

        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");

        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123");
        doLogIn("UT", "123");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }

    @Test
    public void testUnauthorizedUserPermission() {

        // Try to access login URL
        driver.get("http://localhost:" + this.port + "/login");
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

        // Try to access signup URL
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
        Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());

        // Try to access home URL
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    @Test
    public void testAuthorizedUserPermission() {

        String username = "testAuthorizedUserPermission";
        String password = "123123";

        // Create a test account
        doMockSignUp("Vu", "Trung", username, password);
        doLogIn(username, password);

        // Try to access home URL
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

        // Log out
        doLogOut();

        // Try to access home URL
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    private void doCheckSuccess() {

        webDriverWait.until(ExpectedConditions.titleContains("Result"));
        inputElement = driver.findElement(By.id("success-continue-link"));
        Assertions.assertTrue(inputElement.isDisplayed());
    }

    private void doClickContinue() {

        webDriverWait.until(ExpectedConditions.titleContains("Result"));
        inputElement = driver.findElement(By.id("success-continue-link"));
        inputElement.click();
    }

    public void doCreateNote(String noteTitle, String noteDescription) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        inputElement = driver.findElement(By.id("nav-notes-tab"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-a-new-note")));
        inputElement = driver.findElement(By.id("add-a-new-note"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        inputElement = driver.findElement(By.id("note-title"));
        inputElement.sendKeys(noteTitle);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        inputElement = driver.findElement(By.id("note-description"));
        inputElement.sendKeys(noteDescription);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-button")));
        inputElement = driver.findElement(By.id("save-note-button"));
        inputElement.click();

    }

    @Test
    public void testCreateNote() {

        String username = "testCreateNote";
        String password = "123123";

        // Create a test account
        doMockSignUp("Vu", "Trung", username, password);
        doLogIn(username, password);

        String noteTitle = "Note title";
        String noteDescription = "Note description example";

        // Create note
        doCreateNote(noteTitle, noteDescription);

        // Check success
        doCheckSuccess();
    }

    private void doEditNote(String newNoteTitle, String newNoteDescription) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        inputElement = driver.findElement(By.id("nav-notes-tab"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note-button")));
        inputElement = driver.findElement(By.id("edit-note-button"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        inputElement = driver.findElement(By.id("note-title"));
        inputElement.clear();
        inputElement.sendKeys(newNoteTitle);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        inputElement = driver.findElement(By.id("note-description"));
        inputElement.clear();
        inputElement.sendKeys(newNoteDescription);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-button")));
        inputElement = driver.findElement(By.id("save-note-button"));
        inputElement.click();
    }

    @Test
    public void testEditNote() {

        String username = "testEditNote";
        String password = "123123";

        // Create a test account
        doMockSignUp("Vu", "Trung", username, password);
        doLogIn(username, password);

        String noteTitle = "Note title";
        String noteDescription = "Note description example";
        String newNoteTitle = "New note title";
        String newNoteDescription = "New note description example";

        // Create note
        doCreateNote(noteTitle, noteDescription);

        // Click continue
        doClickContinue();

        // Edit note
        doEditNote(newNoteTitle, newNoteDescription);

        // Check success
        doCheckSuccess();
    }

    private void doDeleteNote() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        inputElement = driver.findElement(By.id("nav-notes-tab"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note-button")));
        inputElement = driver.findElement(By.id("delete-note-button"));
        inputElement.click();
    }

    @Test
    public void testDeleteNote() {

        String username = "testDeleteNote";
        String password = "123123";

        // Create a test account
        doMockSignUp("Vu", "Trung", username, password);
        doLogIn(username, password);

        String noteTitle = "Note title";
        String noteDescription = "Note description example";

        // Create note
        doCreateNote(noteTitle, noteDescription);

        // Click continue
        doClickContinue();

        // Delete note
        doDeleteNote();

        // Check success
        doCheckSuccess();
    }

    public void doCreateCredential(String credentialUrl, String credentialUsername, String credentialPassword) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        inputElement = driver.findElement(By.id("nav-credentials-tab"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-a-new-credential")));
        inputElement = driver.findElement(By.id("add-a-new-credential"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        inputElement = driver.findElement(By.id("credential-url"));
        inputElement.sendKeys(credentialUrl);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        inputElement = driver.findElement(By.id("credential-username"));
        inputElement.sendKeys(credentialUsername);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        inputElement = driver.findElement(By.id("credential-password"));
        inputElement.sendKeys(credentialPassword);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credential-button")));
        inputElement = driver.findElement(By.id("save-credential-button"));
        inputElement.click();

    }

    @Test
    public void testCreateCredential() {

        String username = "testCreateCredential";
        String password = "123123";

        // Create a test account
        doMockSignUp("Vu", "Trung", username, password);
        doLogIn(username, password);

        // Try to access home URL
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

        String credentialUrl = "http://www.facebook.com";
        String credentialUsername = "username";
        String credentialPassword = "password";

        // Create credential
        doCreateCredential(credentialUrl, credentialUsername, credentialPassword);

        // Check success
        doCheckSuccess();
    }

    private void doEditCredential(String newCredentialUrl, String newCredentialUsername, String newCredentialPassword) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        inputElement = driver.findElement(By.id("nav-credentials-tab"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-credential-button")));
        inputElement = driver.findElement(By.id("edit-credential-button"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        inputElement = driver.findElement(By.id("credential-url"));
        inputElement.sendKeys(newCredentialUrl);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        inputElement = driver.findElement(By.id("credential-username"));
        inputElement.sendKeys(newCredentialUsername);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        inputElement = driver.findElement(By.id("credential-password"));
        inputElement.sendKeys(newCredentialPassword);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credential-button")));
        inputElement = driver.findElement(By.id("save-credential-button"));
        inputElement.click();
    }

    @Test
    public void testEditCredential() {

        String username = "testEditCredential";
        String password = "123123";

        // Create a test account
        doMockSignUp("Vu", "Trung", username, password);
        doLogIn(username, password);

        String credentialUrl = "http://www.facebook.com";
        String credentialUsername = "username";
        String credentialPassword = "password";

        String newCredentialUrl = "http://www.google.com";
        String newCredentialUsername = "newUsername";
        String newCredentialPassword = "newPassword";

        // Create credential
        doCreateCredential(credentialUrl, credentialUsername, credentialPassword);

        // Click continue
        doClickContinue();

        // Edit credential
        doEditCredential(newCredentialUrl, newCredentialUsername, newCredentialPassword);

        // Check success
        doCheckSuccess();
    }

    private void doDeleteCredential() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        inputElement = driver.findElement(By.id("nav-credentials-tab"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-credential-button")));
        inputElement = driver.findElement(By.id("delete-credential-button"));
        inputElement.click();
    }

    @Test
    public void testDeleteCredential() {

        String username = "testDeleteCredential";
        String password = "123123";

        // Create a test account
        doMockSignUp("Vu", "Trung", username, password);
        doLogIn(username, password);

        String credentialUrl = "http://www.facebook.com";
        String credentialUsername = "username";
        String credentialPassword = "password";

        // Create note
        doCreateCredential(credentialUrl, credentialUsername, credentialPassword);

        // Click continue
        doClickContinue();

        // Delete note
        doDeleteCredential();

        // Check success
        doCheckSuccess();
    }


    public void doUploadFile(String fileName) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-files-tab")));
        inputElement = driver.findElement(By.id("nav-files-tab"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        inputElement = driver.findElement(By.id("fileUpload"));
        inputElement.sendKeys(new File(fileName).getAbsolutePath());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uploadButton")));
        inputElement = driver.findElement(By.id("uploadButton"));
        inputElement.click();

    }

    @Test
    public void testUploadFile() {

        String username = "testUploadFile";
        String password = "123123";

        // Create a test account
        doMockSignUp("Vu", "Trung", username, password);
        doLogIn(username, password);

        // Try to access home URL
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

        String fileName = "image-sample.jpg";

        // Upload file
        doUploadFile(fileName);

        // Check success
        doCheckSuccess();
    }

    private void doDownloadFile() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-files-tab")));
        inputElement = driver.findElement(By.id("nav-files-tab"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("download-file-button")));
        inputElement = driver.findElement(By.id("download-file-button"));
        inputElement.click();
    }
    @Test
    public void testDownloadFile() {

        String username = "testDownloadFile";
        String password = "123123";

        // Create a test account
        doMockSignUp("Vu", "Trung", username, password);
        doLogIn(username, password);

        // Try to access home URL
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

        String fileName = "image-sample.jpg";

        // Upload file
        doUploadFile(fileName);

        // Check success
        doCheckSuccess();

        // Click continue
        doClickContinue();

        // Download file
        doDownloadFile();
    }

    private void doDeleteFile() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-files-tab")));
        inputElement = driver.findElement(By.id("nav-files-tab"));
        inputElement.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-file-button")));
        inputElement = driver.findElement(By.id("delete-file-button"));
        inputElement.click();
    }

    @Test
    public void testDeleteFile() {

        String username = "testDeleteFile";
        String password = "123123";

        // Create a test account
        doMockSignUp("Vu", "Trung", username, password);
        doLogIn(username, password);

        // Try to access home URL
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

        String fileUrl = "C:\\Users\\Admin\\Desktop\\zihan-al.jpg";

        // Upload file
        doUploadFile(fileUrl);

        // Check success
        doCheckSuccess();

        // Click continue
        doClickContinue();

        // Delete file
        doDeleteFile();

        // Check success
        doCheckSuccess();
    }


}
