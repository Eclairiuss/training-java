package fr.ebiz.nurdiales.trainingJava.servlet;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by ebiz on 24/04/17.
 */
public class SeleniumCreateComputer {
    private WebDriver driver;
    private String baseUrl;
    private JavascriptExecutor jse;

    private static String ID_NAME = "computerName", ID_INTRODUCED = "introduced", ID_DISCONTINUED = "discontinued", ID_COMPANY = "companyId", ID_ADD = "add", ID_CANCEL = "cancel";

    private WebElement name, introduced, discontinued, company, add, cancel;
    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver");
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/add_computer";
        jse = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);
        name=driver.findElement(By.id(ID_NAME));
        introduced=driver.findElement(By.id(ID_INTRODUCED));
        discontinued=driver.findElement(By.id(ID_DISCONTINUED));
        company=driver.findElement(By.id(ID_COMPANY));
        add=driver.findElement(By.id(ID_ADD));
        cancel=driver.findElement(By.id(ID_CANCEL));
    }

    @Test
    public void testAddFullComputer() throws Exception {
        name.click();
        name.sendKeys("nameTestSelenium");

        Thread.sleep(100);

        introduced.click();
        introduced.sendKeys("1980-01-01");

        Thread.sleep(100);

        discontinued.click();
        discontinued.sendKeys("1981-01-01");

        Thread.sleep(100);
        add.click();
    }
}
