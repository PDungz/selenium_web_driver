package selenium_webdriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class DynamicControlsTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Automatically sets up the ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
    }

    @Test
    public void testDynamicControls() {
        // Nhấn nút Remove để xóa checkbox
        WebElement removeButton = driver.findElement(By.xpath("//button[text()='Remove']"));
        removeButton.click();
        
        // Kiểm tra thông báo hiển thị sau khi checkbox đã bị xóa
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        Assert.assertEquals(message.getText(), "It's gone!", "Thông báo không chính xác sau khi xóa checkbox.");

        // Kiểm tra checkbox không còn hiển thị
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("checkbox")));
        Assert.assertFalse(isCheckboxVisible(), "Checkbox vẫn hiển thị sau khi bị xóa.");

        // Nhấn nút Add để thêm lại checkbox
        WebElement addButton = driver.findElement(By.xpath("//button[text()='Add']"));
        addButton.click();
        
        // Kiểm tra thông báo hiển thị sau khi thêm checkbox
        WebElement addMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        Assert.assertEquals(addMessage.getText(), "It's back!", "Thông báo không chính xác sau khi thêm checkbox.");

        // Kiểm tra checkbox đã xuất hiện lại
        WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
        Assert.assertTrue(checkbox.isDisplayed(), "Checkbox không xuất hiện lại sau khi thêm.");
    }

    private boolean isCheckboxVisible() {
        try {
            return driver.findElement(By.id("checkbox")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @AfterClass
    public void tearDown() {
        // Đóng trình duyệt
        if (driver != null) {
            driver.quit();
        }
    }
}
