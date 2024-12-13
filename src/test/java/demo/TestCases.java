// package demo;

// import org.openqa.selenium.By;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeDriverService;
// import org.openqa.selenium.chrome.ChromeOptions;
// import org.openqa.selenium.logging.LogType;
// import org.openqa.selenium.logging.LoggingPreferences;
// import org.testng.annotations.AfterTest;
// import org.testng.annotations.BeforeTest;
// import org.testng.annotations.Test;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.logging.Level;

// // import io.github.bonigarcia.wdm.WebDriverManager;
// import demo.wrappers.Wrappers;

// public class TestCases {
//     ChromeDriver driver;

//     /*
//      * TODO: Write your tests here with testng @Test annotation. 
//      * Follow `testCase01` `testCase02`... format or what is provided in instructions
//      */

//     /*
//      * Do not change the provided methods unless necessary, they will help in automation and assessment
//      */
//     @BeforeTest
//     public void startBrowser()
//     {
//         System.setProperty("java.util.logging.config.file", "logging.properties");

//         // NOT NEEDED FOR SELENIUM MANAGER
//         // WebDriverManager.chromedriver().timeout(30).setup();

//         ChromeOptions options = new ChromeOptions();
//         LoggingPreferences logs = new LoggingPreferences();

//         logs.enable(LogType.BROWSER, Level.ALL);
//         logs.enable(LogType.DRIVER, Level.ALL);
//         options.setCapability("goog:loggingPrefs", logs);
//         options.addArguments("--remote-allow-origins=*");

//         System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

//         driver = new ChromeDriver(options);

//         driver.manage().window().maximize();
//     }

//     @AfterTest
//     public void endTest()
//     {
//         driver.close();
//         driver.quit();

//     }
// }

package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow testCase01 testCase02... format or what is provided in instructions
     */

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start Test case 01");
        driver.get("https://www.flipkart.com/");

        WebElement inputBox = driver.findElement(By.xpath("//input[@title = 'Search for Products, Brands and More']"));
        inputBox.click();
        inputBox.clear();
        inputBox.sendKeys("Washing Machine");
        inputBox.sendKeys(Keys.ENTER);

        Thread.sleep(3000);

        Wrappers.clickElement(driver, By.xpath("//div[text()='Popularity']"));
        Thread.sleep(2000);

        List<WebElement> starRatingElements = driver
                .findElements(By.xpath("//span[contains(@id , 'productRating')]/div"));
        for (WebElement ratingElement : starRatingElements) {
            double rating = Double.parseDouble(ratingElement.getText());
            if (rating <= 4.0) {
                System.out.println(ratingElement.getText());
            }
        }
        System.out.println("End Test case 01");

    }

    @Test
    public void testCase02() throws InterruptedException {
        System.out.println("Start Test case 02");
        driver.get("https://www.flipkart.com/");
        Thread.sleep(3000);

        // click on cancel icon
        Wrappers.clickElement(driver, By.xpath("//span[@role='button']"));
        WebElement inputBox = driver.findElement(By.xpath("//input[@title = 'Search for Products, Brands and More']"));

        inputBox.click();
        inputBox.clear();
        inputBox.sendKeys("iPhone");
        inputBox.sendKeys(Keys.ENTER);
        Thread.sleep(3000);

        Boolean status = Wrappers.printTitleAndDiscount(driver, By.xpath("//div[contains(@class, 'yKfJKb')]"), 17);
        Assert.assertTrue(status);
        System.out.println("End Test case 02");

    }

    @Test
    public void testCase03() throws InterruptedException {
        System.out.println("Start Test case 03");
        driver.get("https://www.flipkart.com/");
        // click on cancel icon
        Wrappers.clickElement(driver, By.xpath("//span[@role='button']"));
        Wrappers.searchProduct(driver, By.xpath("//input[@title = 'Search for Products, Brands and More']"),
                "Coffee Mug");
        Thread.sleep(3000);

        Wrappers.clickElement(driver, By.xpath("//div[text() = '4★ & above']"));
        Thread.sleep(3000);

        Boolean status = Wrappers.printTitleAndImageUrl(driver,
                By.xpath("//div[@class = 'slAVV4']//span[@class = 'Wphh3N']"));
        Assert.assertTrue(status);
        System.out.println("End Test case 03");

    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}