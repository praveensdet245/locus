package com.web.automation.genericUtilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author praveenkumar created on 26th Jan 2021
 */
public class Driver {
    private static Logger log = Logger.getLogger(Driver.class.getName());
    private static WebDriver webDriver;

    /**
     * to get webdriver instance
     * @return chrome driver instance
     */
    public static WebDriver getWebDriverInstance(){
        try {
            WebDriverManager.chromedriver().setup();
            webDriver = new ChromeDriver();
            webDriver.manage().window().maximize();
            log.info("Chrome browser launched..");
        }catch(Exception e){
            log.error("Unable to launch chrome instance..",e);
        }
        return webDriver;
    }
}
