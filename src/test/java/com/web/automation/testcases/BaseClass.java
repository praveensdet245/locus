package com.web.automation.testcases;

import com.web.automation.applicationPages.Homepage;
import com.web.automation.applicationPages.LoginPage;
import com.web.automation.genericUtilities.Constants;
import com.web.automation.genericUtilities.Driver;
import com.web.automation.genericUtilities.PortalUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;


public class BaseClass {
    private static Logger log = Logger.getLogger(BaseClass.class);
    public static WebDriver driver;
    public static PortalUtils portalUtils;
    public static LoginPage loginPage;
    public static Homepage homepage;


    @BeforeMethod
    public void initiate(){
        driver = Driver.getWebDriverInstance();
        driver.get(Constants.WEB_URL);
        portalUtils = new PortalUtils(driver);
        loginPage = new LoginPage(driver);
        homepage = new Homepage(driver);
        //close that popup on homepage
        portalUtils.waitForPageToLoad();

    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
