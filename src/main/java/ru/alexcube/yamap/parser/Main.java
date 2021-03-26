package ru.alexcube.yamap.parser;

import functional.CommonFunctions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import yandex.test1.ConfProperties;

import java.util.concurrent.TimeUnit;

public class Main {
    public static ChromeOptions option;
    public static WebDriver driver;
    public static WebDriverWait wait;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Старт парсера.");
        setup();
        step1();
        step2();


        stopService();
    }

    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        option = new ChromeOptions();
        option.setBinary(ConfProperties.getProperty("chromebin"));
        option.addArguments("user-data-dir=C:\\Browsers\\otr.chrome85");
        option.addArguments("chrome-version=85.0.4183.83");
        //Открыть Хром-браузер на весь экран
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    public static void step1() {
        //Перейти на  www.yandex.ru
        CommonFunctions.printStep();
        driver.get(ConfProperties.getProperty("url"));
    }

    public static void step2() {

    }


    public static void stopService() throws InterruptedException {
        Thread.sleep(10000);
        driver.close();
        driver.quit();
    }


}
