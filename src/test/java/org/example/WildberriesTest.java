package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class WildberriesTest {
    WebDriver driver;
    WebDriverWait webDriverWait;
    Actions actions;
    private final static String WILDBERRIES_BASE_URL = "https://wildberries.ru";

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupDriver() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        actions = new Actions(driver);
        driver.get(WILDBERRIES_BASE_URL);
    }

    @Test
    void presenceGoodsInCart() {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.findElement(By.xpath("//button[@class='nav-element__burger j-menu-burger-btn']")).click();
        driver.findElement(By.xpath("//a[contains(@href,'https://www.wildberries.ru/catalog/zhenshchinam')]")).click();
        driver.findElement(By.xpath("//a[@class='j-menu-item']")).click();
        driver.findElement(By.xpath("//img[@class='j-thumbnail thumbnail']")).click();
        driver.findElement(By.xpath("//li[@class='sizes-list__item']/label[contains(@class,'j-size')]")).click();
        driver.findElement(By.xpath("//button[@class='btn-main']")).click();

       Assertions.assertEquals(driver.findElement(By.xpath("//span[@class='navbar-pc__notify']")).getText(),"1");
    }

    @Test
    void hoverMenuCountry(){
        actions.moveToElement(driver.findElement(By.xpath("//span[@class='simple-menu__link simple-menu__link--country']")))
                .build()
                .perform();
        driver.findElement(By.xpath("//span[.='Беларусь']")).click();

        Assertions.assertEquals(driver.getCurrentUrl(), "https://by.wildberries.ru/");
    }

    @Test
    void presencePickUpPoints() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        driver.findElement(By.xpath("//div[@class='navbar-pc__item j-item-addresses']/a[@class='navbar-pc__link']")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,'YMapsID')]/descendant::input")));
        driver.findElement(By.xpath("//div[contains(@id,'YMapsID')]/descendant::input")).sendKeys("Москва");
        driver.findElement(By.xpath("//div[contains(@id,'YMapsID')]/descendant::ymaps[@class='ymaps-2-1-79-searchbox-button-text']")).click();
        driver.findElement(By.xpath("//div[@class='swiper-slide']"));
    }

    @AfterEach
    void tearDown(){
        driver.quit();
    }

}

