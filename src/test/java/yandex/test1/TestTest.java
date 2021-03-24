package yandex.test1;

import functional.CommonFunctions;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;


public class TestTest {

    public static ChromeOptions option;
    public static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        option = new ChromeOptions();
        option.setBinary(ConfProperties.getProperty("chromebin"));
        option.addArguments("user-data-dir=C:\\Browsers\\otr.chrome85");
        option.addArguments("chrome-version=85.0.4183.83");
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testGroup1() {
        step1();
        step2();
        step3();
        step4();
        step5();
        //step6();
        step7();
    }



    public void step1() {
        //Открыть Хром-браузер на весь экран
        CommonFunctions.printStep();
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void step2() {
        //Перейти на  www.yandex.ru
        CommonFunctions.printStep();
        driver.get(ConfProperties.getProperty("url"));
    }

    public void step3() {
        //Перейти в раздел "Маркет"
        CommonFunctions.printStep();
        WebElement yaMarkerButton = driver.findElement(By.xpath("//ul[contains(@class, \"services-new__list\")]/li[contains(., \"Маркет\")]/a"));
        yaMarkerButton.click();
        String[] browsersTabs = driver.getWindowHandles().toArray(new String[0]);
        driver.switchTo().window(browsersTabs[1]);
    }

    public void step4() {
        //В Маркете , в поисковой строке ввести "ноутбук xiaomi redmibook"
        CommonFunctions.printStep();
        driver.findElement(By.xpath("//input[@id=\"header-search\"]")).sendKeys("ноутбук xiaomi redmibook");
    }

    public void step5() {
        //Произвести поиск нажатием кнопки "Найти"
        CommonFunctions.printStep();
        WebElement yaMarketSearchButton = driver.findElement(By.xpath("//form[@action=\"/search\"]/div/div/button[@type=\"submit\"]"));
        CommonFunctions.waitForElement(driver, By.xpath("//form[@action=\"/search\"]/div/div/button[@type=\"submit\"]"), 10);
        yaMarketSearchButton.click();
    }

    public void step6() {
        //Поставить галочку "Сначала предложения в моем регионе"
        CommonFunctions.printStep();
        //Нужная галка не обнаружена
    }

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
