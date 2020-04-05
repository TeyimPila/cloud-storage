package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

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
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void validRegistrationTest() {

        String username = "user@gmail.com";
        String passwordVal = "Test@12345";
        String homeUrl = "http://localhost:" + this.port + "/home";
        String signupUrl = "http://localhost:" + this.port + "/signup";
        String loginurl = "http://localhost:" + this.port + "/login";

        registerUser(username, passwordVal);

        driver.get(homeUrl);
        String destination = driver.getCurrentUrl();
        Assertions.assertNotEquals(homeUrl, destination);


        driver.navigate().to(loginurl);

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

    public void registerUser(String username, String passwordVal) {

        String signupUrl = "http://localhost:" + this.port + "/signup";

        driver.get(signupUrl);
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


    public void loginUser(String username, String passwordVal) {
        WebElement email = driver.findElement(By.name("username"));
        email.sendKeys(username);

        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(passwordVal);

        WebElement login = driver.findElement(By.xpath("//button[text()='Login']"));
        login.click();
    }

    void logoutUser() {
        WebElement logout = driver.findElement(By.xpath("//button[text()='Logout']"));
        logout.click();
    }

}
