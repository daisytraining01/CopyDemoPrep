package com.internetapp.pages;

import com.maveric.core.config.ConfigProperties;
import com.maveric.core.driver.Driver;
import com.maveric.core.utils.web.WebActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static com.maveric.core.utils.reporter.Report.log;

import java.util.concurrent.TimeUnit;

public class DBLoginPage extends WebActions {

    private final By txt_username = By.name("username");
    private final By txt_password = By.name("password");
    private final By txt_pin = By.name("accpin");
    private final By btn_submit = By.id("submit");
    
    WebDriverWait wait;
    WebDriver driver;

    public DBLoginPage() {
        driver = Driver.getWebDriver();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, ConfigProperties.WAIT_TIMEOUT.getInt());
    }

    public DBLoginPage navigate(String url) {
        driver.navigate().to(url);

        logScreenshot("login");
        ;
        log("sample log");

        return this;

    }

    public DBHomePage login(String userName,String password) {
        wait.until(ExpectedConditions.presenceOfElementLocated(txt_username))
                .sendKeys(userName);
        driver.findElement(txt_password).sendKeys(password);
        driver.findElement(btn_submit).click();
        logScreenshot("login successful");
        return new DBHomePage(driver);

    }
    
        
}
