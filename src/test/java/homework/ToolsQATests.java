package homework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ToolsQATests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser");

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "opera":
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                break;
        }

        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
    }

    @Test
    public void registrationTest() {
        String firstName = "Inna";
        String lastName = "Krukova";
        String email = "im.krukova@gmail.com";
        String gender = "Male";
        String mobile = "9134866622";
        String birthDate = "01 February,1984";
        String subjects = "History";
        String hobbies = "Sports";
        String address = "Bogatyrsky, 8";
        String state = "NCR";
        String city = "Delhi";
        String imageFileName = "smailik-ylibka-14.jpg";

        driver.findElement(By.id("close-fixedban")).click(); //закрытие рекламы

        driver.findElement(By.id("firstName")).sendKeys(firstName);
        driver.findElement(By.id("lastName")).sendKeys(lastName);
        driver.findElement(By.id("userEmail")).sendKeys(email);

        WebElement genderRradio = driver.findElement(By.cssSelector("div#genterWrapper .custom-control-label"));
        genderRradio.findElement(By.xpath("//label[text()='" + gender + "']")).click();

        driver.findElement(By.id("userNumber")).sendKeys(mobile);

        WebElement hoobiesCheckbox = driver.findElement(By.cssSelector("div#hobbiesWrapper .custom-control-label"));
        hoobiesCheckbox.findElement(By.xpath("//label[text()='" + hobbies + "']")).click();

        WebElement dateOfBirth = driver.findElement(By.id("dateOfBirthInput"));
        dateOfBirth.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        dateOfBirth.sendKeys(birthDate);
        dateOfBirth.sendKeys(Keys.ESCAPE);

        WebElement subjectsInput = driver.findElement(By.id("subjectsInput"));
        subjectsInput.sendKeys(subjects);
        subjectsInput.sendKeys(Keys.ENTER);

        String filePath = System.getProperty("user.dir") + "\\images\\" + imageFileName;
        driver.findElement(By.id("uploadPicture")).sendKeys(filePath);

        driver.findElement(By.id("currentAddress")).sendKeys(address);

        WebElement stateList = driver.findElement(By.cssSelector("#state input"));
        stateList.sendKeys(state);
        stateList.sendKeys(Keys.ENTER);

        WebElement cityList = driver.findElement(By.cssSelector("#city input"));
        cityList.sendKeys(city);
        cityList.sendKeys(Keys.ENTER);

        driver.findElement(By.cssSelector("button#submit")).click();

//Assertions
        WebDriverWait wait = new WebDriverWait(driver, 10, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("example-modal-sizes-title-lg")));

        String submittingFormTitleText = driver.findElement(By.id("example-modal-sizes-title-lg")).getText();

        Assertions.assertTrue(submittingFormTitleText.contains("Thanks for submitting the form"));

        Assertions.assertEquals(firstName + " " + lastName,
                driver.findElement(By.xpath("//td[contains(text(), 'Student Name')]//..//td[2]")).getText());
        Assertions.assertEquals(email,
                driver.findElement(By.xpath("//td[contains(text(), 'Student Email')]//..//td[2]")).getText());
        Assertions.assertEquals(gender,
                driver.findElement(By.xpath("//td[contains(text(), 'Gender')]//..//td[2]")).getText());
        Assertions.assertEquals(mobile,
                driver.findElement(By.xpath("//td[contains(text(), 'Mobile')]//..//td[2]")).getText());
        Assertions.assertEquals(birthDate,
                driver.findElement(By.xpath("//td[contains(text(), 'Date of Birth')]//..//td[2]")).getText());
        Assertions.assertEquals(subjects,
                driver.findElement(By.xpath("//td[contains(text(), 'Subjects')]//..//td[2]")).getText());
        Assertions.assertEquals(hobbies,
                driver.findElement(By.xpath("//td[contains(text(), 'Hobbies')]//..//td[2]")).getText());
        Assertions.assertEquals(imageFileName,
                driver.findElement(By.xpath("//td[contains(text(), 'Picture')]//..//td[2]")).getText());
        Assertions.assertEquals(address,
                driver.findElement(By.xpath("//td[contains(text(), 'Address')]//..//td[2]")).getText());
        Assertions.assertEquals(state + " " + city,
                driver.findElement(By.xpath("//td[contains(text(), 'State and City')]//..//td[2]")).getText());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
