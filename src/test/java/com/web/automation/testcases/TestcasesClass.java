package com.web.automation.testcases;

import com.web.automation.genericUtilities.Constants;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TestcasesClass extends BaseClass{
    String taskId = null;

    @Test(priority = 1)
    public void login_invalidCredentials(){
        loginPage.do_login(Constants.INVALID_USERID,Constants.INVALID_PASSWORD);
        boolean flag = loginPage.getWrongIdPasswordErrorMessageTxt().isDisplayed();
        Assert.assertTrue(flag,"Login successful with invalid credentials");
    }

    @Test(priority = 2)
    public void login_validCredentials() throws Exception {
        loginPage.do_login(Constants.VALID_USERID,Constants.VALID_PASSWORD);
        portalUtils.waitForElementToBePresent(homepage.getSearchIcon(),"SearchIcon",10);
        String pageUrl = portalUtils.getCurrentPageUrl();
        Assert.assertEquals(pageUrl,"https://test-hiring.locus-dashboard.com/#/client/test-hiring/live_view","url mismatch");
    }

    @Test(priority = 3)
    public void personalProfile_test() throws Exception {
        loginPage.do_login(Constants.VALID_USERID,Constants.VALID_PASSWORD);
        portalUtils.waitForElementToBePresent(homepage.getPersonalMenuIcon(),"Search icon",10);
        portalUtils.actionMoveToElement(homepage.getPersonalMenuIcon());
        boolean flag = homepage.getWebTestText().isDisplayed();
        Assert.assertTrue(flag,"Web test is not displaying");
    }

    @Test(priority = 4)
    public void searchForTask_test(){
        loginPage.do_login(Constants.VALID_USERID,Constants.VALID_PASSWORD);
        homepage.searchForTask(Constants.SEARCH_TASK);
        List<WebElement> tasksList = homepage.getSearchResult();
        int noOfTasks = tasksList.size();
        Assert.assertEquals(noOfTasks,1,"Tasks search result mismatch");
    }

    @Test(priority = 5)
    public void createServiceTask_test(){
        loginPage.do_login(Constants.VALID_USERID,Constants.VALID_PASSWORD);
        homepage.addTaskAndSelectTeam("897465");
        boolean flag = homepage.createServiceTask(Constants.LOCATION_ID,"praveen","9584736476",120);
        taskId = (String) portalUtils.getValue("taskId");
        Assert.assertTrue(flag,"Service Task creation failure");
    }

    @Test(priority = 6)
    public void searchForCreatedTask_test(){
        loginPage.do_login(Constants.VALID_USERID,Constants.VALID_PASSWORD);
        homepage.searchForTask(taskId);
        List<WebElement> tasksList = homepage.getSearchResult();
        int noOfTasks = tasksList.size();
        Assert.assertEquals(noOfTasks,1,"Tasks search result mismatch error");
    }


}
