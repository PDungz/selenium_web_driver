package selenium_webdriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class IFrameTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Automatically sets up the ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://the-internet.herokuapp.com/iframe");
    }

    @Test
    public void testIFrame() {
        // Chuyển đến iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("mce_0_ifr")));

        // Chờ cho text area hiển thị
        WebElement textArea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tinymce")));

        // Xóa và thiết lập nội dung mới bằng JavaScript
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].innerHTML = '';", textArea); // Xóa nội dung
        js.executeScript("arguments[0].innerHTML = 'Nội dung mới';", textArea); // Thiết lập nội dung mới

        // Xác minh văn bản đã nhập
        String enteredText = textArea.getText();
        Assert.assertEquals(enteredText, "Nội dung mới", "Nội dung không chính xác!"); // Sử dụng assertion từ TestNG
    }

    @AfterClass
    public void tearDown() {
        // Quay lại khung chính
        driver.switchTo().defaultContent();
        
        // Đóng trình duyệt
        if (driver != null) {
            driver.quit();
        }
    }
}
