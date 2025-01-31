package com.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

public class GoogleLoginTest {

    WebDriver driver;

    @BeforeTest
    @Parameters("browser")
    public void setup(String browser) throws Exception {
        // Initialize the WebDriver based on the browser parameter passed (Chrome, Firefox, or Edge)
        if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("Edge")) {
            driver = new EdgeDriver();
        } else {
            throw new Exception("Incorrect Browser specified in test parameters");
        }

        // Set the implicit wait for element search
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void googleLoginTest() {
        // Navigate to Google login page
        driver.get("https://accounts.google.com/signin");

        // Step 1: Enter email
        WebElement emailField = driver.findElement(By.id("identifierId"));
        emailField.sendKeys("your-email@gmail.com"); // Use a valid email for testing
        driver.findElement(By.id("identifierNext")).click();

        // Step 2: Wait until the password field is visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));

        // Step 3: Enter password
        passwordField.sendKeys("your-password"); // Use a valid password for testing
        driver.findElement(By.id("passwordNext")).click();

        // Step 4: Wait for the user to be redirected to the account page
        wait.until(ExpectedConditions.urlContains("myaccount.google.com"));

        // Step 5: Assert that the user is logged in (URL should contain 'myaccount.google.com')
        Assert.assertTrue(driver.getCurrentUrl().contains("myaccount.google.com"));

        // Optionally, print the page title to confirm successful login
        System.out.println("Page title: " + driver.getTitle());
    }

    // Clean up method to close the browser after the test
    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser and end the session
        }
    }
}








