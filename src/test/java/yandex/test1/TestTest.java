package yandex.test1;

import functional.CommonFunctions;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class TestTest {

    public static ChromeOptions option;
    public static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        option = new ChromeOptions();
        option.addArguments("user-data-dir=C:\\Browsers\\otr.chrome85");
        option.addArguments("chrome-version=85.0.4183.102");
    }

    @Before
    public void setUp() {

    }


    @Test
    public void step1() {
        //Открыть Хром-браузер на весь экран
        CommonFunctions.printStep();
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void step2() {
        //Перейти на  www.yandex.ru
        CommonFunctions.printStep();
        driver.get(ConfProperties.getProperty("url"));
    }

    @Test
    public void step3() {
        //Перейти в раздел "Маркет"
        CommonFunctions.printStep();
        WebElement yaMarkerButton = driver.findElement(By.xpath("//ul[contains(@class, \"services-new__list\")]/li[contains(., \"Маркет\")]/a"));
        yaMarkerButton.click();
        String[] browsersTabs = driver.getWindowHandles().toArray(new String[0]);
        driver.switchTo().window(browsersTabs[1]);
    }

    @Test
    public void step4() {
        //В Маркете , в поисковой строке ввести "ноутбук xiaomi redmibook"
        CommonFunctions.printStep();
        driver.findElement(By.xpath("//input[@id=\"header-search\"]")).sendKeys("ноутбук xiaomi redmibook");
    }

    @Test
    public void step5() {
        //Произвести поиск нажатием кнопки "Найти"
        CommonFunctions.printStep();
        WebElement yaMarketSearchButton = driver.findElement(By.xpath("//form[@action=\"/search\"]/div/div/button[@type=\"submit\"]"));
        CommonFunctions.waitForElement(driver, By.xpath("//form[@action=\"/search\"]/div/div/button[@type=\"submit\"]"), 10);
        yaMarketSearchButton.click();
    }

    /*
    @Test
    public void step6() {
        //Поставить галочку "Сначала предложения в моем регионе"
        CommonFunctions.printStep();
        driver.get("https://passport.yandex.ru/auth");
    }
    */

    @Test
    public void step7() {
        //Сделать скриншот и сохранить в *.jpg формате в папке с тестом
        CommonFunctions.printStep();
        CommonFunctions.screenShot(driver, "c:\\WebDriverServer\\screens\\screenshot.jpg");
    }

    @After
    public void tearDown() {

    }

    @AfterClass
    public static void createAndStopService() {
        driver.close();
        driver.quit();
    }

}
