package com.web.automation.genericUtilities;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author praveenkumar  created on 26th Jan 2021
 */
public class PortalUtils {

    private static Logger log = Logger.getLogger(PortalUtils.class.getName());
    private static WebDriver driver;
    WebDriverWait webDriverWait;
    Actions actions;
    Select select;
    private static Map<String,Object> map = new HashMap<>();

    public PortalUtils(WebDriver driver){
        this.driver = driver;
    }

    public void setValue(String key,Object value){
        map.put(key,value);
    }

    public Object getValue(String key){
        return map.get(key);
    }
    /**
     * implicit wait
     */
    public void waitForPageToLoad() {

        driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
    }

    /**
     * explicit wait for visibility of element
     * @param element
     */
    public void waitForElementToBePresent(WebElement element,String elementName,int sec) throws Exception{
        try{
                webDriverWait = new WebDriverWait(driver,sec);
                webDriverWait.until(ExpectedConditions.visibilityOf(element));
                log.info("waitForElementToBePresent executed successfully with given element :"+elementName);
        }catch(Exception e){
            log.error(e);
            throw new Exception("Unable to perform waitForElementToBePresent function for element :"+elementName,e);
        }
    }

    /**
     *
     * @throws Exception
     */
    public void closeModal() throws Exception {
        try{
            actions = new Actions(driver);
            actions.sendKeys(Keys.ESCAPE).build().perform();
            log.info("Model has closed successfully");
        }catch (Exception e){
            log.error("Unable to close modal --->");
            throw new Exception(e);
        }
    }

    public Actions getActionObject(){
        return new Actions(driver);
    }

    /**
     *
     * @return
     */
    public String getCurrentPageUrl(){
        return driver.getCurrentUrl();
    }

    /**
     * select by visible text
     * @param element
     * @param visibleText
     * @throws Exception
     */
    public void selectByVisibleText(WebElement element,String visibleText,String elementName) throws Exception{
        try {
            select = new Select(element);
            select.selectByVisibleText(visibleText);
            log.info("Selection happened successful with given params {(element:"+elementName+"),(visibleText:"+visibleText+")}");
        }catch(Exception e){
            log.error("Unable to select by visible text for given params {(element:"+elementName+"),(visibleText:"+visibleText+")}",e);
            throw new Exception("Unable to perform selectByVisibleText function for given params {(element:"+elementName+"),(visibleText:"+visibleText+")}");
        }
    }

    /**
     *
     * @param element
     * @param elementName
     * @return
     */
    public String selectGetSelectedOption(WebElement element,String elementName){
        String selectedOption = null;
        try{
            select = new Select(element);
            selectedOption = select.getFirstSelectedOption().getText();
            log.info("selected option for "+elementName+" webelement is "+selectedOption);
        }catch (Exception e){
            log.error("Unable to get selected option for "+elementName+" webelement --->",e);
        }
        return selectedOption;
    }

    /**
     * this is to get all options from dropdown
     * @param element
     * @return
     */
    public List<WebElement> getDropdownOptions(WebElement element){
            select = new Select(element);
            return select.getOptions();
    }

    /**
     * element click using actions class
     * @param element
     * @param elementName
     * @throws Exception
     */
    public void actionsClick(WebElement element,String elementName) throws Exception{
        try{
            actions = new Actions(driver);
            actions.moveToElement(element).click().build().perform();
            log.info("Element "+elementName+" clicked successfully");
        }catch (Exception e){
            log.error("Unable to click element :"+elementName);
            throw new Exception("Unable to click element :"+elementName);
        }
    }

    /**
     *
     * @param element
     */
    public void actionMoveToElement(WebElement element){
        try{
            actions = new Actions(driver);
            actions.moveToElement(element).perform();
            log.info("move to element happened successfully");
        }catch (Exception e){
            log.error("Unable to perform move to element");
        }
    }

    /**
     *
     * @param windowNumber
     */
    public void switchToChildWindow(int windowNumber){
        try{
            Set<String> windows = driver.getWindowHandles();
            Iterator < String > ite = windows.iterator();
            int i = 1;
            while (ite.hasNext() && i < 10) {
                String popupHandle = ite.next().toString();
                driver.switchTo().window(popupHandle);
                if (i == windowNumber) break;
                i++;
            }
            log.info("driver control moved to window number "+windowNumber);
        }catch (Exception e){
            log.error("Unable to switch driver control to window number :"+windowNumber);
        }
    }

    /**
     * this utility is used to capture screenshot
     * @param screenshotName
     * @return screenshot fully qualified name
     */
    public String takeScreenshot(String screenshotName){
        String filepath = System.getProperty("user.dir")+"/Portal_ScreenShots";
        screenshotName = screenshotName.replace(":", "_");

        String fullyQualifiedScreenshot = filepath+"/"+screenshotName+".png";
        try{
            File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcfile, new File(fullyQualifiedScreenshot));
            log.info("Screenshot Saved in :("+filepath+") location ,with ("+screenshotName+") name...");
        }catch(Exception e){
            log.error("Unable to Execute Screenshot method...", e);
        }
        return fullyQualifiedScreenshot;
    }

    /**
     *
     * @param script
     * @param element
     */
    public void javaScriptExecutor(WebElement element,String script){
        try{
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            javascriptExecutor.executeScript(script,element);
        }catch (Exception e){
            log.warn("JavaScript execution failed with {script : "+script+" : Element : "+element+"}");
        }
    }

    public void javaScriptSendKeys(WebElement element,String value){
        try{
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            javascriptExecutor.executeScript("arguments[0].value='"+value+"'",element);
        }catch (Exception e){
            log.warn("JavaScript execution failed with {value : "+value+" : Element : "+element+"}");
        }
    }

    public void javaScriptClick(WebElement element){
        try{
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            javascriptExecutor.executeScript("arguments[0].click();",element);
        }catch (Exception e){
            log.warn("JavaScript execution failed with click script}");
        }
    }
}