package selenium_webdriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class DragAndDropTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Automatically sets up the ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait for 10 seconds
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
    }

    @Test
    public void testDragAndDrop() {
        // Chờ cho các phần tử có sẵn
        WebElement elementA = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("column-a")));
        WebElement elementB = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("column-b")));
        
        // Kéo khối A và thả vào khối B
        Actions actions = new Actions(driver);
        actions.dragAndDrop(elementA, elementB).perform();
        
        // Chờ cho văn bản đã thay đổi sau khi kéo và thả
        wait.until(ExpectedConditions.textToBePresentInElement(elementA, "B"));
        wait.until(ExpectedConditions.textToBePresentInElement(elementB, "A"));

        // Kiểm tra vị trí đã hoán đổi thành công
        String textA = elementA.getText();
        String textB = elementB.getText();
        
        Assert.assertEquals(textA, "B", "Khối A không được hoán đổi chính xác!");
        Assert.assertEquals(textB, "A", "Khối B không được hoán đổi chính xác!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
