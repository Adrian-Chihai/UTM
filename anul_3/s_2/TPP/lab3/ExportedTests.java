// Generated by Selenium IDE

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Test1Test {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void test1() {
        driver.get("https://www.mortgagecalculator.org/");
        driver.manage().window().setSize(new Dimension(1586, 866));
        driver.switchTo().frame(0);
        js.executeScript("window.scrollTo(0,0)");
        driver.switchTo().defaultContent();
        {
            WebElement element = driver.findElement(By.id("homeval"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.id("homeval"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.id("homeval"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.id("homeval")).click();
        driver.findElement(By.id("homeval")).sendKeys("250000");
        driver.findElement(By.id("intrstsrate")).click();
        driver.findElement(By.name("cal")).click();
        driver.findElement(By.cssSelector(".cal-right-box")).click();
        driver.findElement(By.cssSelector(".rw-box:nth-child(1) > .left-cell > h3")).click();
        driver.findElement(By.cssSelector(".cal-right-box")).click();
        driver.findElement(By.cssSelector(".rw-box:nth-child(1) > .left-cell > h3")).click();
        driver.findElement(By.cssSelector(".rw-box:nth-child(1) > .left-cell > h3")).click();
        vars.put("monthlyPayment", driver.findElement(By.cssSelector(".rw-box:nth-child(1) > .left-cell > h3")).getText());
        System.out.println("999,24");
        System.out.println(vars.get("monthlyPayment").toString());
    }
}
