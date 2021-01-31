package com.web.automation.applicationPages;

import com.web.automation.genericUtilities.PortalUtils;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


@Getter
public class Homepage {
    private static Logger log = Logger.getLogger(Homepage.class);
    private WebDriver driver;
    private PortalUtils portalUtils;

    public Homepage(WebDriver driver){
        this.driver = driver;
        portalUtils = new PortalUtils(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//button[contains(@class,'SearchTheme')]")
    private WebElement searchIcon;

    @FindBy(xpath = "//div[@data-test-id='personnelMenu-accountIcon']")
    private WebElement personalMenuIcon;

    @FindBy(xpath = "//div[text()='web-test']")
    private WebElement webTestText;

    @FindBy(xpath = "//input[contains(@placeholder,'Search Tasks by ID')]")
    private WebElement searchTaskInput;

    @FindBy(xpath = "//button[contains(@class,'TaskSearchInput')]")
    private WebElement searchTaskInputSearchBtn;

    @FindBys({@FindBy(xpath = "//div[@class='fixedDataTableCellGroupLayout_cellGroup']")})
    private List<WebElement> searchResult;

    @FindBy(xpath = "//button/span[text()='Add Task']")
    private WebElement addTaskBtn;

    @FindBy(xpath = "//input[@testid='enterVistId']")
    private WebElement enterTaskIdInput;

    @FindBy(xpath = "//div[text()='Select Team']")
    private WebElement selectTeamDropdown;

    @FindBy(xpath = "//*[text()='spmd']")
    private WebElement selectTeamDropdownValue;

    @FindBy(xpath = "//button[@testid='proceedTaskCreation']")
    private WebElement proceedButton;

    @FindBy(xpath = "//div[contains(@class,'ListCard__left')]/div[text()='Pickup']")
    private WebElement leftCardPickup;

    @FindBy(xpath = "//div[contains(text(),'Enter Location ID, Name')]")
    private WebElement locationIdInput;

    @FindBy(xpath = "//input[@placeholder='Enter Customer Name']")
    private WebElement customerNameInput;

    @FindBy(xpath = "//input[@placeholder='Enter Phone Number']")
    private WebElement phoneNumberInput;

    @FindBy(xpath = "//button[span[text()='Choose Slot']]")
    private WebElement chooseTimeSlotButton;

    @FindBy(xpath = "//input[@placeholder='Enter SLA (min)']")
    private WebElement enterSlaInput;

    @FindBy(xpath = "//button[@testid='save']")
    private WebElement saveBtn;

    @FindBy(xpath = "//button[@testid='createTask']")
    private WebElement createTaskBtn;

    @FindBy(xpath = "//div[contains(@class,'TaskDetails__title')]/span[contains(text(),'TASK')]")
    private WebElement TaskHeader;

    @FindBy(xpath = "//span[text()='close']")
    private WebElement closeBtn;


    /**
     *
     * @param taskName
     */
    public void searchForTask(String taskName){
        try{
            portalUtils.waitForElementToBePresent(this.searchIcon,"Search icon",30);
            this.searchIcon.click();
            portalUtils.waitForElementToBePresent(this.searchTaskInput,"Search task input",30);
           //portalUtils.javaScriptSendKeys(this.searchTaskInput,taskName);
            this.searchTaskInput.sendKeys(taskName);
            this.searchTaskInputSearchBtn.click();
            Thread.sleep(3000);
            log.info(taskName+" task search was successful...");
        }catch (Exception e){
            log.error("Unable to search task :"+taskName+" --->",e);
        }
    }


    public void addTaskAndSelectTeam(String taskId){
        try{
            portalUtils.waitForElementToBePresent(this.addTaskBtn,"add Task Btn",30);
            portalUtils.javaScriptClick(this.addTaskBtn);
            //this.addTaskBtn.click();
            portalUtils.waitForElementToBePresent(this.enterTaskIdInput,"Task Id Input",3);
            this.enterTaskIdInput.sendKeys(taskId);
            portalUtils.waitForElementToBePresent(this.selectTeamDropdown,"Select team dropdown",5);
            this.selectTeamDropdown.click();
            portalUtils.waitForElementToBePresent(this. selectTeamDropdownValue,"selectTeamDropdownValue",2);
            this.selectTeamDropdownValue.click();
            while(true){
                if(this.proceedButton.isEnabled()) break;
            }
            this.proceedButton.click();
            portalUtils.waitForElementToBePresent(this.locationIdInput,"location Id Input",5);
            log.info("team selected successfully");
        }catch (Exception e){
            log.error("Unable to select team --->",e);
        }
    }

    /**
     *
     * @param locationId
     * @param customerName
     * @param phoneNo
     * @param minForSla
     * @return
     */
    public boolean createServiceTask(String locationId, String customerName, String phoneNo, int minForSla){
        boolean isTaskCreated = false;
        try{
            portalUtils.waitForElementToBePresent(this.leftCardPickup,"left Card Pickup",5);
            this.leftCardPickup.click();
            Actions a = portalUtils.getActionObject();
            a.sendKeys(Keys.ARROW_DOWN).build().perform();
            a.sendKeys(Keys.ARROW_DOWN).build().perform();
            a.sendKeys(Keys.ENTER).build().perform();
            portalUtils.waitForElementToBePresent(this.locationIdInput,"locationIdInput",5);
            this.locationIdInput.sendKeys(locationId);
            portalUtils.getActionObject().sendKeys(Keys.ENTER).build().perform();
            this.customerNameInput.sendKeys(customerName);
            this.phoneNumberInput.sendKeys(phoneNo);
            this.chooseTimeSlotButton.click();
            portalUtils.waitForElementToBePresent(this.enterSlaInput,"enter SLA Input",1);
            this.enterSlaInput.sendKeys(String.valueOf(minForSla));
            this.saveBtn.click();
            this.createTaskBtn.click();
            portalUtils.waitForElementToBePresent(this.TaskHeader,"Task Header",20);
            String taskId = this.TaskHeader.getText().split(": ")[1];
            log.info("Task Id is :"+taskId);
            portalUtils.setValue("taskId",taskId);
            log.info("Task created successfully");
            this.closeBtn.click();
            portalUtils.waitForElementToBePresent(this.searchTaskInput,"searchTaskInput",10);
            log.info("successful task creation modal has been closed");
            isTaskCreated = true;
        }catch (Exception e){
            log.error("Unable to create task --->",e);
        }
        return isTaskCreated;
    }

}
