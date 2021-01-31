package com.web.automation.applicationPages;


import com.web.automation.genericUtilities.PortalUtils;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author praveenkumar created on 30th Jan 2021
 */
@Getter
public class LoginPage {
    private static Logger log = Logger.getLogger(LoginPage.class);
    private WebDriver driver;
    private PortalUtils portalUtils;
    private Homepage homepage;

    public LoginPage(WebDriver driver){
        portalUtils = new PortalUtils(driver);
        homepage = new Homepage(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginBtn;

    public WebElement getElementOfTest(String aa){
        return driver.findElement(By.xpath("//div[@class='aa' and text()='"+aa+"']"));
    }

    @FindBy(xpath = "//input[@placeholder='User ID']")
    private WebElement userIdInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement continueBtn;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//p[contains(text(),'Wrong ID')]")
    private WebElement wrongIdPasswordErrorMessageTxt;

    /**
     *  @param username
     * @param password
     * @return false if login fails, true if login successful
     */
    public void do_login(String username, String password){
        try{
            portalUtils.waitForElementToBePresent(this.loginBtn,"Login button",30);
            this.loginBtn.click();
            portalUtils.waitForElementToBePresent(this.userIdInput,"User Id input",20);
            this.userIdInput.sendKeys(username);
            portalUtils.waitForElementToBePresent(this.continueBtn,"Continue button",5);
            this.continueBtn.click();
            portalUtils.waitForElementToBePresent(this.passwordInput,"Password input",10);
            this.passwordInput.sendKeys(password);
            this.loginBtn.click();
            Thread.sleep(3000);

        }catch (Exception e){
            log.error("Unable to perform login with given credentials {username:"+username+",password:"+password+"} --->",e);
        }
    }

}
