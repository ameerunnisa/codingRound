import com.sun.javafx.PlatformUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;

import java.util.List;

public class FlightBookingTest {

    WebDriver driver = new ChromeDriver();


    @Test
    public void testThatResultsAppearForAOneWayJourney() {

        setDriverPath();
        driver.get("https://www.cleartrip.com/");
        
        /* Instead of using Thread.Sleep we can using Explici or Implicit Wait for Synchronizing so that we can reduce execution time*/
        //waitFor(2000);
        waitForWebElement(driver.findElement(By.id("OneWay")));
        driver.findElement(By.id("OneWay")).click();

        driver.findElement(By.id("FromTag")).clear();
        /*Any how we are going to select first option so we can directly use Keys.ENTER in sendkeys*/
        driver.findElement(By.id("FromTag")).sendKeys("Bangalore",Keys.ENTER);

        //wait for the auto complete options to appear for the origin
        
        //Or we can use this way using Xpaths

        /*waitFor(2000);
        List<WebElement> originOptions = driver.findElements(By.xpath("//*[@id='ui-id-1']/li/a"));
        originOptions.get(0).click();*/

        driver.findElement(By.id("toTag")).clear();
        driver.findElement(By.id("toTag")).sendKeys("Delhi");

        //wait for the auto complete options to appear for the destination

        waitFor(2000);
        //select the first item from the destination auto complete list
        List<WebElement> destinationOptions = driver.findElement(By.id("ui-id-2")).findElements(By.tagName("li"));
        destinationOptions.get(0).click();
         
        //To select date from datepicker first we need to open datepicker
        
        //driver.findElement(By.xpath("//*[@class='icon ir datePicker']")).click();
        
        driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[1]/table/tbody/tr[3]/td[7]/a")).click();

        //all fields filled in. Now click on search
        driver.findElement(By.id("SearchBtn")).click();

        waitFor(5000);
        //verify that result appears for the provided journey search
        Assert.assertTrue(isElementPresent(By.className("searchSummary")));

        //close the browser
        driver.quit();

    }

private void waitForWebElement(WebElement ele) {        
WebDriverWait wait=new WebDriverWait(driver,this);
    wait.until(ExpectedConditions.visibilityOfElement(ele));
}
    private void waitFor(int durationInMilliSeconds) {
        try {
            Thread.sleep(durationInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void setDriverPath() {
        if (PlatformUtil.isMac()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
        }
        if (PlatformUtil.isWindows()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        }
        if (PlatformUtil.isLinux()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
        }
    }
}
