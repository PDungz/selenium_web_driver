package selenium_webdriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;

public class HandlePopupsTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Automatically sets up the ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait for 10 seconds
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
    }

    @Test
    public void testJavaScriptAlerts() {
        // Handle alert popup
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Click for JS Alert']"))).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent()); // Wait for alert to be present
        String alertText = alert.getText();
        System.out.println("Alert text: " + alertText);
        alert.accept();
        Assert.assertEquals(alertText, "I am a JS Alert");

        // Handle confirm popup
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Click for JS Confirm']"))).click();
        Alert confirm = wait.until(ExpectedConditions.alertIsPresent()); // Wait for confirm alert to be present
        String confirmText = confirm.getText();
        System.out.println("Confirm text: " + confirmText);
        confirm.dismiss(); // Click Cancel
        Assert.assertEquals(confirmText, "I am a JS Confirm");

        // Handle prompt popup
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Click for JS Prompt']"))).click();
        Alert prompt = wait.until(ExpectedConditions.alertIsPresent()); // Wait for prompt alert to be present
        String promptText = prompt.getText();
        System.out.println("Prompt text: " + promptText);
        prompt.sendKeys("Testing Selenium");
        prompt.accept();
        // Check if the input value was set correctly
        Assert.assertEquals(promptText, "I am a JS prompt"); // Update expected value
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
