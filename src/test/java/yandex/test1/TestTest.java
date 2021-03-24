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
        //driver = new ChromeDriver(option);
        //driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //driver.get(ConfProperties.getProperty("url"));
    }

    @Before
    public void setUp() {

    }


    @Test
    public void step1() {
        //Открыть Хром-браузер на весь экран
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void step2() {
        //Перейти на  www.yandex.ru
        driver.get(ConfProperties.getProperty("url"));
    }

    @Test
    public void step3() {
        //Перейти в раздел "Маркет"
        WebElement yaMarkerButton = driver.findElement(By.xpath("//ul[contains(@class, \"services-new__list\")]/li[contains(., \"Маркет\")]/a"));
        yaMarkerButton.click();
        String[] browsersTabs = driver.getWindowHandles().toArray(new String[0]);
        driver.switchTo().window(browsersTabs[1]);
    }

    @Test
    public void step4() {
        //В Маркете , в поисковой строке ввести "ноутбук xiaomi redmibook"
        driver.findElement(By.xpath("//input[@id=\"header-search\"]")).sendKeys("ноутбук xiaomi redmibook");
    }

    @Test
    public void step5() {
        //Произвести поиск нажатием кнопки "Найти"
        //WebElement yaMarkerSearchButton = driver.findElement(By.xpath("//form[@action=\"/search\"]/div/div/button[@type=\"submit\"]"));

        WebElement yaMarkerSearchButton = (new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@action=\"/search\"]/div/div/button[@type=\"submit\"]"))));

        yaMarkerSearchButton.click();
    }

    /*
    @Test
    public void step6() {
        //Поставить галочку "Сначала предложения в моем регионе"
        driver.get("https://passport.yandex.ru/auth");
    }
*/
    @Test
    public void step7() throws IOException {
        //Сделать скриншот и сохранить в *.jpg формате в папке с тестом
        //File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //FileUtils.copyFile(scrFile, new File("c:\\WebDriverServer\\screens\\screenshot.jpg"));
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
