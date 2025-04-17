import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.NoSuchElementException;

public class myInternetTest {

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

    private boolean isElementPresent (By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false ;
        }
    }

    @Test (priority = 1)
    public void testCommands (){
        driver.navigate().to("https://the-internet.herokuapp.com/login");

        WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
        username.sendKeys("tomsmith");

        WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("SuperSecretPassword!");

        WebElement loginBtn = driver.findElement(By.xpath("//i[@class='fa fa-2x fa-sign-in']"));
        loginBtn.click();

        WebElement secureArea = driver.findElement(By.xpath("//h2[normalize-space()='Secure Area']"));
        String secureAreaText = secureArea.getText();

        Assert.assertEquals(secureAreaText, "Secure Area");
    }

    @Test (priority = 2)
    public void dropDownList () {
        driver.navigate().to("https://the-internet.herokuapp.com/dropdown");

        WebElement optionList = driver.findElement(By.id("dropdown"));
        Select selectOption = new Select(optionList);

        Assert.assertFalse(selectOption.isMultiple());
        Assert.assertEquals(3,selectOption.getOptions().size());
    }

    @Test  (priority = 3 , enabled = false)
    public void checkbox (){
        driver.navigate().to("https://the-internet.herokuapp.com/checkboxes");

        WebElement check1= driver.findElement(By.xpath("//input[1]"));
        check1.click();

        WebElement check2= driver.findElement(By.xpath("//input[2]"));

        if (check2.isSelected()) {
            check2.click();
        }
    }

    @Test (priority = 4)
    public void testIsElementPresent () {
        driver.navigate().to("https://the-internet.herokuapp.com/checkboxes");


        if (isElementPresent(By.xpath("//input[1]")))
        {
            WebElement check1= driver.findElement(By.xpath("//input[1]"));
            if (!check1.isSelected())
            {
                check1.click();
            }
        }
        else
        {
            Assert.fail("Checkbox 1 not exist");
        }
    }



    @Test (priority = 3)
    public void webTables () {
        driver.navigate().to("https://the-internet.herokuapp.com/tables");

        WebElement simpleTable = driver.findElement(By.id("table1"));

        // Get Rows
        List<WebElement> rows = simpleTable.findElements(By.tagName("tr"));
        Assert.assertEquals(5, rows.size());

        // Get cells data
        for (WebElement row:rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            for (WebElement column : columns) {
                System.out.println(column.getText());
            }
        }
    }

    @Test (priority = 4)
    public void testRightClick () {
        Actions act = new Actions(driver);

        WebElement dropDown = driver.findElement(By.xpath("//a[normalize-space()='Dropdown']"));
        act.contextClick(dropDown).perform();
    }

    @Test (priority = 5)
    public void testDragDrop () {
        Actions act = new Actions(driver);
        driver.navigate().to("https://the-internet.herokuapp.com/drag_and_drop");

         WebElement boxA = driver.findElement(By.id("column-a"));
         WebElement boxB = driver.findElement(By.id("column-b"));

         act.dragAndDrop(boxA,boxB).perform();
    }

    @Test (priority = 6)
    public void contextMenu () throws InterruptedException {
        Actions act = new Actions(driver);

        driver.navigate().to("https://the-internet.herokuapp.com/jqueryui/menu");
        WebElement enabledBtn = driver.findElement(By.xpath("//a[normalize-space()='Enabled']"));
        act.moveToElement(enabledBtn).perform();

        WebElement downloadsBtn = driver.findElement(By.cssSelector("body > div:nth-child(2) > div:nth-child(2) > div:nth-child(9) > div:nth-child(2) > ul:nth-child(3) > li:nth-child(2) > ul:nth-child(3) > li:nth-child(1) > a:nth-child(2)"));
        act.moveToElement(downloadsBtn).perform();

        WebElement pdfBtn = driver.findElement(By.xpath("//a[normalize-space()='PDF']"));
        pdfBtn.click();

    }

}