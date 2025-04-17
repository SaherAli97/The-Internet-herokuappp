import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class javaScript {
    ChromeDriver driver;



    @BeforeTest
    public void prepareDriver() throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.amazon.eg/?language=en_AE");
        Thread.sleep(2000);
    }

    @AfterTest
    public void CloseDriver() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }

    @Test(priority = 1)
    public void testJavaScript () {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String title = (String) js.executeScript("return document.title");

        Assert.assertTrue(title.contains("Amazon"));
    }

    @Test(priority = 2)
    public void scrollDown () throws InterruptedException {
        Actions act = new Actions(driver);
        WebElement searchBar = driver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys("Monitors");
        searchBar.submit();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("scrollBy(0,2000)");
        Thread.sleep(3000);

    }

}
