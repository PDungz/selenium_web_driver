package selenium_webdriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.ArrayList;
import java.util.Set;

public class WindowHandlesTest {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Automatically sets up the ChromeDriver
        driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/windows");
    }

    @Test
    public void testWindowHandles() {
        // Nhấn nút "Click Here" để mở cửa sổ mới
        driver.findElement(By.linkText("Click Here")).click();

        // Lấy danh sách các cửa sổ
        Set<String> windowHandles = driver.getWindowHandles();
        Assert.assertEquals(windowHandles.size(), 2, "Số cửa sổ không chính xác!"); // Kiểm tra số lượng cửa sổ mở

        // Chuyển đổi đến cửa sổ mới
        ArrayList<String> windows = new ArrayList<>(windowHandles);
        driver.switchTo().window(windows.get(1));
        String newWindowTitle = driver.getTitle();
        
        Assert.assertEquals(newWindowTitle, "New Window", "Tiêu đề cửa sổ mới không chính xác!");

        // Chuyển về cửa sổ chính
        driver.switchTo().window(windows.get(0));
        String mainWindowTitle = driver.getTitle();
        
        Assert.assertEquals(mainWindowTitle, "The Internet", "Tiêu đề cửa sổ chính không chính xác!");
    }

    @AfterClass
    public void tearDown() {
        // Đóng trình duyệt
        if (driver != null) {
            driver.quit();
        }
    }
}
