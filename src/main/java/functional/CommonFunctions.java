package functional;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CommonFunctions {

    /**
     * Ожидание
     */
    public static void wait(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void wait(double seconds) {
        long milliseconds = (long) (seconds * 1000);
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void waitForElement(WebDriver driver, By locator, int seconds) {
        new WebDriverWait(driver, seconds).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForElementDisplayed(WebDriver driver, By locator, int seconds) {
        new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
        highlightWebElement(driver, locator);
    }

    public static void highlightWebElement(WebDriver driver, By locator) {
        WebElement webElement = driver.findElement(locator);
        for (int i = 0; i <= 1; i++) {
            // draw a border around the found element
            ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", webElement);
            CommonFunctions.wait(0.3);
            ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='none'", webElement);
            CommonFunctions.wait(0.3);
        }
    }

    /**
     * Дата сегодня ("dd.MM.yyyy")
     */
    public static String dateToday(String format) {
        String date = new SimpleDateFormat(format).format(new Date());
        return date;
    }

    /**
     * Дата со сдвигом на нужное число дней вперед(+) / назад(-)
     *      @param format - формат даты
     *      @param dateShift -  сдвиг даты в календарных днях вперед(+) / назад(-)
     */
    public static String dateShift(String format, Integer dateShift) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, dateShift);
        return dateFormat.format(cal.getTime());
    }

    /**
     * Рандомный номер
     * @param min - минимальное значение
     * @param max - максимальное значение
     */
    public static String randomNumber(int min, int max) {
        Random rnd = new Random(System.currentTimeMillis());
        int num = min + rnd.nextInt(max - min + 1);
        String numString = Integer.toString(num);
        return numString;
    }

    /**
     * Номер со счетчиком вида dMM## (с использованием файла)
     * @param docNumCounterPath - путь к файлу счетчика (должен быть создан и заполнен вручную)
     * @param docNumPath - путь к файлу генерируемого номера документа
     */
    public static String generateNiceDocNum(String docNumCounterPath, String docNumPath) throws Exception {
        //Создание объекта BufferedReader
        BufferedReader reader = new BufferedReader(new FileReader(docNumCounterPath));
        //Читаем и разделяем номер на части, где docNum_a = ДДММ, а docNum_b - двузначный счетчик в разрезе дня
        String docNumCounter = reader.readLine();
        String docNum_a = docNumCounter.substring(0, 4);
        String docNum_b = docNumCounter.substring(4);

        String docNum;

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMM");

        if (docNum_a.equals(dateFormat.format(new Date()))) {
            if (Integer.valueOf(docNum_b) < 9) {
                docNum = docNum_a + 0 + (Integer.valueOf(docNum_b) + 1);
            } else {
                docNum = docNum_a + (Integer.valueOf(docNum_b) + 1);
            }
        } else {
            docNum_a = dateFormat.format(new Date());
            docNum_b = "01";
            docNum = docNum_a + docNum_b;
        }
        //System.out.println(docNum);
        //Создание объекта BufferedWriter
        BufferedWriter writer = new BufferedWriter(new FileWriter(docNumCounterPath));
        BufferedWriter writer_2 = new BufferedWriter(new FileWriter(docNumPath));
        //Запишем в файл
        writer.write(docNum);
        writer_2.write(docNum);

        reader.close();
        writer.close();
        writer_2.close();

        return docNum;
    }

    /**
     * Вывести в консоль выполняемый шаг
     */
    public static void printStep() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        System.out.println((char) 27 + "[33m***" + stack[2].getClassName() + " --> " + stack[2].getMethodName() + "***" + (char)27 + "[0m");
    }

    /**
     * Сделать скрин
     */
    public static void screenShot(WebDriver driver, String way){
        File screen= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screen, new File(way));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Счеткик времени
     */
    public static long StartTransaction(){
        return System.nanoTime();
    }

    public static double TimeTransaction(long startTransaction){
        long endTransaction = System.nanoTime();
        double timeTransaction  = TimeUnit.MILLISECONDS.convert(endTransaction - startTransaction, TimeUnit.NANOSECONDS)/1000.0;
        return timeTransaction;
    }

    /**
     * SQL-запрос к БД
     * @param classBD - класс БД ("oracle.jdbc.OracleDriver", "org.postgresql.Driver", "com.mysql.jdbc.Driver")
     * @param typeRequest  - тип запроса ("update", "remove", "insert", "select")
     * @param url url - адрес
     * @param login  - логин
     * @param password - пароль
     * @param request - SQL-запрос
     */
    public static String SQLRequest (String classBD, String typeRequest, String url, String login, String password, String request) {

        String answer = "";

        try {
            Class.forName(classBD);
            Connection connection = DriverManager.getConnection(url, login, password);
            Statement statement = connection.createStatement();

            if(typeRequest.equals("insert")){
                statement.executeUpdate(request);
            } else if(typeRequest.equals("remove")||typeRequest.equals("update")){
                statement.execute(request);
            } else if(typeRequest.equals("select")){
                ResultSet result = statement.executeQuery(request);
                while (result.next()) {
                    if(answer.isEmpty()){
                        answer = answer + result.getString(1);
                    }else {
                        answer = answer + " " + result.getString(1);
                    }
                }
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        return answer;
    }
}
