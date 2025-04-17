import com.github.dockerjava.core.util.FilePathUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DurationUtils;
import org.apache.hc.core5.util.Timeout;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class handlingIssues {
    ChromeDriver driver;



    @BeforeTest
    public void prepareDriver() throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://the-internet.herokuapp.com/");
        Thread.sleep(2000);
    }

    @AfterTest
    public void CloseDriver() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }

    @Test(priority = 1)
    public void javaScriptAlerts () {
        driver.navigate().to("https://the-internet.herokuapp.com/javascript_alerts");

        WebElement alertBtn = driver.findElement(By.xpath("//button[normalize-space()='Click for JS Alert']"));
        alertBtn.click();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();

        Assert.assertEquals("I am a JS Alert",alertText);
        alert.accept();



        WebElement promptBtn = driver.findElement(By.xpath("//button[normalize-space()='Click for JS Prompt']"));
        promptBtn.click();
        alert.sendKeys("Hello There");
        alert.accept();

        WebElement textResult = driver.findElement(By.id("result"));
        String textResultAssert = textResult.getText();
        Assert.assertEquals("You entered: Hello There",textResultAssert);
    }

    @Test(priority = 2)
    public void newWindowTab () {
        driver.navigate().to("https://the-internet.herokuapp.com/windows");

        String currentWindowID = driver.getWindowHandle();

        driver.findElement(By.xpath("//a[normalize-space()='Click Here']")).click();

        for (String windowID : driver.getWindowHandles()) {
            String title = driver.switchTo().window(windowID).getCurrentUrl();

            if (title.contains("new")) {
                Assert.assertTrue(driver.getCurrentUrl().contains("new"));
                driver.close();
                break;
            }
        }
        driver.switchTo().window(currentWindowID);
    }

    @Test(priority = 3)
    public void takeScreenshots () {
        driver.navigate().to("https://the-internet.herokuapp.com/login");

        WebElement username = driver.findElement(By.xpath("//input[@id='usernameeee']"));
        username.sendKeys("tomsmith");

        WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("SuperSecretPassword!");

        WebElement loginBtn = driver.findElement(By.xpath("//i[@class='fa fa-2x fa-sign-in']"));
        loginBtn.click();

        WebElement secureArea = driver.findElement(By.xpath("//h2[normalize-space()='Secure Area']"));
        String secureAreaText = secureArea.getText();

        Assert.assertEquals(secureAreaText, "Secure Area");
    }

    @AfterMethod
    public void failedScreenshots (ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus())
        {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File ("C:\\Users\\Elnady_2\\Desktop\\FailedTest" + result.getName()+".png"));
        }

    }

}
