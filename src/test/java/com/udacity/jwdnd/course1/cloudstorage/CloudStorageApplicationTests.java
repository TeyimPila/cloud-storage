package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.repositories.CredentialRepository;
import com.udacity.jwdnd.course1.cloudstorage.repositories.NoteRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    CredentialRepository credentialRepository;

    @LocalServerPort
    private int port;
    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get(getLoginUrl());
        Assertions.assertEquals("Login", driver.getTitle());
    }


    WebDriverWait getWait() {
        return new WebDriverWait(driver, 100);
    }

    @Test
    public void testRegistration() {

        String username = "user@gmail.com";
        String passwordVal = "Test@12345";
        String homeUrl = "http://localhost:" + this.port + "/home";
        String loginurl = getLoginUrl();

        registerUser(username, passwordVal);

        driver.get(homeUrl);
        String destination = driver.getCurrentUrl();

        //Attempting to go to the home url should redirect the user to the login page
        Assertions.assertEquals(destination, loginurl);
        Assertions.assertNotEquals(homeUrl, destination);

        loginUser(username, passwordVal);

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals(currentUrl, homeUrl);

        // After login, user can access home
        driver.get(homeUrl);
        destination = driver.getCurrentUrl();
        Assertions.assertEquals(homeUrl, destination);

        logoutUser();

        driver.navigate().to(homeUrl);
        destination = driver.getCurrentUrl();
        Assertions.assertNotEquals(homeUrl, destination);

    }

    @Test
    public void testUserLogin() {

        String username = "user@gmail.com";
        String passwordVal = "Test@12345";
        String homeUrl = "http://localhost:" + this.port + "/home";
        String loginurl = getLoginUrl();

        registerUser(username, passwordVal);

        driver.get(homeUrl);
        String destination = driver.getCurrentUrl();

        //Attempting to go to the home url should redirect the user to the login page
        Assertions.assertNotEquals(homeUrl, destination);
        Assertions.assertEquals(destination, loginurl);

        loginUser(username, passwordVal);

        //After registration and login, the user should be able to visit home url
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals(currentUrl, homeUrl);

        // After login, user can access home
        driver.get(homeUrl);
        destination = driver.getCurrentUrl();
        Assertions.assertEquals(homeUrl, destination);

        logoutUser();

        driver.navigate().to(homeUrl);

        //After logging out, attempting to visit home should redirect user to login page
        destination = driver.getCurrentUrl();
        Assertions.assertNotEquals(homeUrl, destination);
        Assertions.assertEquals(loginurl, destination);

        //Attempting to log a user in with wrong credentials...
        loginUser("wrong@username.com", "WrongPasswordVal");


        currentUrl = driver.getCurrentUrl();

        //The user should not be redirected to home, but stay on login page.
        Assertions.assertNotEquals(currentUrl, homeUrl);
        Assertions.assertNotEquals(currentUrl, loginurl);

    }

    @Test
    public void testAccessRestriction() {

        String username = "user@gmail.com";
        String passwordVal = "Test@12345";
        String homeUrl = "http://localhost:" + this.port + "/home";
        String loginurl = getLoginUrl();

        registerUser(username, passwordVal);

        driver.get(homeUrl);
        String destination = driver.getCurrentUrl();

        //Attempting to go to the home url should redirect the user to the login page
        Assertions.assertEquals(destination, loginurl);
        Assertions.assertNotEquals(homeUrl, destination);

        loginUser(username, passwordVal);

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals(currentUrl, homeUrl);

        // After login, user can access home
        driver.get(homeUrl);
        destination = driver.getCurrentUrl();
        Assertions.assertEquals(homeUrl, destination);

        logoutUser();

        driver.navigate().to(homeUrl);
        destination = driver.getCurrentUrl();
        Assertions.assertNotEquals(homeUrl, destination);

    }

    @Test
    public void testNoteCreation() {

        this.noteRepository.deleteAll();

        String username = "user@gmail.com";
        String passwordVal = "Test@12345";

        String newNoteTitle = "New Note Title 1";
        String newNoteDescription = "New Note Description";

        registerUser(username, passwordVal);
        loginUser(username, passwordVal);

        // User is navigated to the home page
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());

        goToNotesTab();

        //Check the number of notes before a note is created.
        WebElement notesTable = driver.findElement(By.id("notes-table"));
        List<WebElement> tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertEquals(0, tableRows.size());

        createNote(newNoteTitle, newNoteDescription);

        driver.navigate().to(getHomeUrl());

        goToNotesTab();

        WebDriverWait wait = getWait();

        notesTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notes-table")));
        tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        WebElement noteTitleCell = notesTable.findElement(By.xpath(".//tbody/tr[1]/th"));
        WebElement noteDescriptionCell = notesTable.findElement(By.xpath(".//tbody/tr[1]/td[2]"));

        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(noteTitleCell.getText(), newNoteTitle);
        Assertions.assertEquals(noteDescriptionCell.getText(), newNoteDescription);

    }

    @Test
    public void testNoteEdit() {

        this.noteRepository.deleteAll();

        String username = "user@gmail.com";
        String passwordVal = "Test@12345";

        String newNoteTitle = "New Note Title 1";
        String newNoteDescription = "New Note Description";

        registerUser(username, passwordVal);
        loginUser(username, passwordVal);

        // User is navigated to the home page after login
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());

        goToNotesTab();

        // Check the number of notes before a note is created.
        WebElement notesTable = driver.findElement(By.id("notes-table"));
        List<WebElement> tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertEquals(0, tableRows.size());

        createNote(newNoteTitle, newNoteDescription);

        driver.navigate().to(getHomeUrl());

        goToNotesTab();

        WebDriverWait wait = getWait();

        notesTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notes-table")));

        tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        WebElement newNoteRow = notesTable.findElement(By.xpath(".//tbody/tr[1]"));
        WebElement noteTitleCell = newNoteRow.findElement(By.xpath(".//th"));
        WebElement noteDescriptionCell = newNoteRow.findElement(By.xpath(".//td[2]"));

        // Check that the note is successfully created.
        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(noteTitleCell.getText(), newNoteTitle);
        Assertions.assertEquals(noteDescriptionCell.getText(), newNoteDescription);

        WebElement editButton = newNoteRow.findElement(By.xpath(".//td/button"));
        editButton.click();

        newNoteTitle = "Edited Note Title 1";
        newNoteDescription = "Edited Note Description";

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("noteTitle"))).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), newNoteTitle);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("noteDescription"))).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), newNoteDescription);

        WebElement saveNote = driver.findElement(By.id("save-note-button"));
        saveNote.click();

        driver.navigate().to(getHomeUrl());

        goToNotesTab();

        notesTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notes-table")));

        tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        newNoteRow = notesTable.findElement(By.xpath(".//tbody/tr[1]"));
        noteTitleCell = newNoteRow.findElement(By.xpath(".//th"));
        noteDescriptionCell = newNoteRow.findElement(By.xpath(".//td[2]"));

        // Check that the note is successfully created.
        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(noteTitleCell.getText(), newNoteTitle);
        Assertions.assertEquals(noteDescriptionCell.getText(), newNoteDescription);
    }

    @Test
    public void testNoteDelete() {

        this.noteRepository.deleteAll();

        String username = "user@gmail.com";
        String passwordVal = "Test@12345";

        String newNoteTitle = "New Note Title 1";
        String newNoteDescription = "New Note Description";

        registerUser(username, passwordVal);
        loginUser(username, passwordVal);

        // User is navigated to the home page after login
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());

        goToNotesTab();

        // Check the number of notes before a note is created.
        WebElement notesTable = driver.findElement(By.id("notes-table"));
        List<WebElement> tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertEquals(0, tableRows.size());

        createNote(newNoteTitle, newNoteDescription);

        driver.navigate().to(getHomeUrl());
        goToNotesTab();
        WebDriverWait wait = getWait();

        notesTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notes-table")));
        tableRows = notesTable.findElements(By.xpath(".//tbody/tr"));
        WebElement newNoteRow = notesTable.findElement(By.xpath(".//tbody/tr[1]"));
        WebElement noteTitleCell = newNoteRow.findElement(By.xpath(".//th"));
        WebElement noteDescriptionCell = newNoteRow.findElement(By.xpath(".//td[2]"));

        // Check that the note is successfully created.
        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(noteTitleCell.getText(), newNoteTitle);
        Assertions.assertEquals(noteDescriptionCell.getText(), newNoteDescription);

        // Locate and click the "Delete Note" button
        WebElement deleteButton = newNoteRow.findElement(By.xpath(".//td/form/button"));
        deleteButton.click();

        driver.navigate().to(getHomeUrl());
        goToNotesTab();

        notesTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notes-table")));
        List<WebElement> tableRowsNow = notesTable.findElements(By.xpath(".//tbody/tr"));

        // Check that the note is successfully deleted.
        Assertions.assertTrue(tableRows.size() > tableRowsNow.size());
        Assertions.assertEquals(0, tableRowsNow.size());
    }


    @Test
    public void testCredentialCreation() {

        this.credentialRepository.deleteAll();

        String username = "user@gmail.com";
        String passwordVal = "Test@12345";

        String credUrl = "https://udacity.com/login";
        String credUsername = "my.username@udacity.com";
        String credPassword = "MySup3rComplexP455w0rD";

        registerUser(username, passwordVal);
        loginUser(username, passwordVal);

        // User is navigated to the home page
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());

        goToCredentialsTab();

        //Check the number of notes before a note is created.
        WebElement credentialTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> tableRows = credentialTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertEquals(0, tableRows.size());

        createCredential(credUrl, credUsername, credPassword);

        driver.navigate().to(getHomeUrl());

        goToCredentialsTab();

        WebDriverWait wait = getWait();

        credentialTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        tableRows = credentialTable.findElements(By.xpath(".//tbody/tr"));
        WebElement credentialUrlCell = credentialTable.findElement(By.xpath(".//tbody/tr[1]/th"));
        WebElement credentialUsernameCell = credentialTable.findElement(By.xpath(".//tbody/tr[1]/td[2]"));
        WebElement credentialPasswordCell = credentialTable.findElement(By.xpath(".//tbody/tr[1]/td[3]"));

        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(credUrl, credentialUrlCell.getText());
        Assertions.assertEquals(credUsername, credentialUsernameCell.getText());
        // The password should be encrypted
        Assertions.assertNotEquals(credPassword, credentialPasswordCell.getText());

    }

    @Test
    public void testCredentialEdit() {
        this.credentialRepository.deleteAll();

        String username = "user@gmail.com";
        String passwordVal = "Test@12345";

        String credUrl = "https://udacity.com/login";
        String credUsername = "my.username@udacity.com";
        String credPassword = "MySup3rComplexP455w0rD";

        registerUser(username, passwordVal);
        loginUser(username, passwordVal);

        // User is navigated to the home page
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());

        goToCredentialsTab();

        //Check the number of notes before a note is created.
        WebElement credentialTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> tableRows = credentialTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertEquals(0, tableRows.size());

        createCredential(credUrl, credUsername, credPassword);

        driver.navigate().to(getHomeUrl());

        goToCredentialsTab();

        WebDriverWait wait = getWait();

        credentialTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        tableRows = credentialTable.findElements(By.xpath(".//tbody/tr"));

        WebElement newCredentialRow = credentialTable.findElement(By.xpath(".//tbody/tr[1]"));

        WebElement credentialUrlCell = newCredentialRow.findElement(By.xpath(".//th"));
        WebElement credentialUsernameCell = newCredentialRow.findElement(By.xpath(".//td[2]"));
        WebElement credentialPasswordCell = newCredentialRow.findElement(By.xpath(".//td[3]"));

        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(credUrl, credentialUrlCell.getText());
        Assertions.assertEquals(credUsername, credentialUsernameCell.getText());
        // The password should be encrypted
        Assertions.assertNotEquals(credPassword, credentialPasswordCell.getText());

        WebElement editButton = newCredentialRow.findElement(By.xpath(".//td/button"));
        editButton.click();

        credUrl = "https://wwww.coursera.org/login";
        credUsername = "my.username@coursera.com";
        String newCredPassword = "MyNewStrongPassword";

        WebElement passwordFormField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));

        // Check that when the credential model is open, the password is the raw password
        Assertions.assertEquals(credPassword, passwordFormField.getAttribute("value"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("url"))).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), credUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), credUsername);
        passwordFormField.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), newCredPassword);

        WebElement saveCredential = driver.findElement(By.id("save-credential-button"));
        saveCredential.click();

        driver.navigate().to(getHomeUrl());

        goToCredentialsTab();

        credentialTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));

        tableRows = credentialTable.findElements(By.xpath(".//tbody/tr"));
        newCredentialRow = credentialTable.findElement(By.xpath(".//tbody/tr[1]"));
        credentialUrlCell = newCredentialRow.findElement(By.xpath(".//th"));
        credentialUsernameCell = newCredentialRow.findElement(By.xpath(".//td[2]"));
        credentialPasswordCell = newCredentialRow.findElement(By.xpath(".//td[3]"));

        // Check that the credential is successfully saved.
        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(credUrl, credentialUrlCell.getText());
        Assertions.assertEquals(credUsername, credentialUsernameCell.getText());
        // The password should be encrypted
        Assertions.assertNotEquals(credPassword, credentialPasswordCell.getText());
    }

    @Test
    public void testCredentialDelete() {

        this.credentialRepository.deleteAll();

        String username = "user@gmail.com";
        String passwordVal = "Test@12345";

        String credUrl = "https://udacity.com/login";
        String credUsername = "my.username@udacity.com";
        String credPassword = "MySup3rComplexP455w0rD";

        registerUser(username, passwordVal);
        loginUser(username, passwordVal);

        // User is navigated to the home page
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());

        goToCredentialsTab();

        //Check the number of notes before a note is created.
        WebElement credentialTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> tableRows = credentialTable.findElements(By.xpath(".//tbody/tr"));
        Assertions.assertEquals(0, tableRows.size());

        createCredential(credUrl, credUsername, credPassword);

        driver.navigate().to(getHomeUrl());

        goToCredentialsTab();

        WebDriverWait wait = getWait();

        credentialTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        tableRows = credentialTable.findElements(By.xpath(".//tbody/tr"));

        WebElement newCredentialRow = credentialTable.findElement(By.xpath(".//tbody/tr[1]"));

        WebElement credentialUrlCell = newCredentialRow.findElement(By.xpath(".//th"));
        WebElement credentialUsernameCell = newCredentialRow.findElement(By.xpath(".//td[2]"));
        WebElement credentialPasswordCell = newCredentialRow.findElement(By.xpath(".//td[3]"));

        Assertions.assertTrue(tableRows.size() >= 1);
        Assertions.assertEquals(credUrl, credentialUrlCell.getText());
        Assertions.assertEquals(credUsername, credentialUsernameCell.getText());
        // The password should be encrypted
        Assertions.assertNotEquals(credPassword, credentialPasswordCell.getText());

        // Locate and click the "Delete Note" button
        WebElement deleteButton = newCredentialRow.findElement(By.xpath(".//td/form/button"));
        deleteButton.click();

        driver.navigate().to(getHomeUrl());
        goToCredentialsTab();

        credentialTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        List<WebElement> tableRowsNow = credentialTable.findElements(By.xpath(".//tbody/tr"));

        // Check that the note is successfully deleted.
        Assertions.assertTrue(tableRows.size() > tableRowsNow.size());
        Assertions.assertEquals(0, tableRowsNow.size());
    }

    /**
     * Given a note title and description, this method opens the new
     * note modal, fills the form and submits the form
     *
     * @param newNoteTitle       The title of the new note to be created
     * @param newNoteDescription The Description of the new note to be created
     */
    private void createNote(String newNoteTitle, String newNoteDescription) {

        WebDriverWait wait = getWait();

        WebElement newNoteButton = driver.findElement(By.id("new-note-button"));

        wait.until(ExpectedConditions.elementToBeClickable(newNoteButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("noteTitle"))).sendKeys(newNoteTitle);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("noteDescription"))).sendKeys(newNoteDescription);

        WebElement saveNote = driver.findElement(By.id("save-note-button"));
        saveNote.click();

    }


    /**
     * Given a note title and description, this method opens the new
     * note modal, fills the form and submits the form
     *
     * @param url      The url of the new credential to be created
     * @param username The username of the credential note to be created
     * @param password The password of the new credential to be created
     */
    private void createCredential(String url, String username, String password) {

        WebDriverWait wait = getWait();

        WebElement newCredentialButton = driver.findElement(By.id("new-credential-button"));

        wait.until(ExpectedConditions.elementToBeClickable(newCredentialButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("url"))).sendKeys(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys(password);

        WebElement saveNote = driver.findElement(By.id("save-credential-button"));
        saveNote.click();

    }

    public String getLoginUrl() {
        return "http://localhost:" + this.port + "/login";
    }

    public String getHomeUrl() {
        return "http://localhost:" + this.port + "/home";
    }

    public String getSignupUrl() {
        return "http://localhost:" + this.port + "/signup";
    }

    /**
     * Navigates to the Notes tab from home
     */
    public void goToNotesTab() {
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();
    }

    /**
     * Navigates to the Credentials tab from home
     */
    public void goToCredentialsTab() {
        Assertions.assertEquals(driver.getCurrentUrl(), getHomeUrl());
        WebElement noteTab = driver.findElement(By.id("nav-credentials-tab"));
        noteTab.click();
    }

    /**
     * Given a username and password, this method fills in the sign up form and submits it.
     *
     * @param username    The username (email) of the user
     * @param passwordVal The password of the user
     */
    public void registerUser(String username, String passwordVal) {

        driver.get(getSignupUrl());
        WebElement companyName = driver.findElement(By.name("firstName"));
        companyName.sendKeys("FName");

        WebElement fullName = driver.findElement(By.name("lastName"));
        fullName.sendKeys("LName");

        WebElement email = driver.findElement(By.name("username"));
        email.sendKeys(username);

        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(passwordVal);

        WebElement signUp = driver.findElement(By.xpath("//button[text()='Register User']"));
        signUp.click();

    }

    /**
     * Given a username and password, this method fills in the login form and submits it.
     *
     * @param username    The username (email) of the user
     * @param passwordVal The password of the user
     */
    public void loginUser(String username, String passwordVal) {

        driver.get(getLoginUrl());

        WebElement email = driver.findElement(By.name("username"));
        email.sendKeys(username);

        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(passwordVal);

        WebElement login = driver.findElement(By.xpath("//button[text()='Login']"));
        login.click();
    }

    /**
     * Logs a user out
     */
    void logoutUser() {
        WebElement logout = driver.findElement(By.xpath("//button[text()='Logout']"));
        logout.click();
    }

}
