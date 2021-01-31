package com.web.automation.genericUtilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.log4j.Logger;
import org.testng.*;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Listeners implements ITestListener, IRetryAnalyzer {
    private static ExtentReports report;
    private static ExtentTest logger;
    private static ExtentHtmlReporter htmlReporter;
    private static Logger log = Logger.getLogger(Listeners.class.getName());

    public void onFinish(ISuite iSuite) {
            report.flush();
    }

    public void onTestStart(ITestResult iTestResult) {
        log.info("-------------------"+iTestResult.getMethod().getMethodName()+"------------------");
        logger = report.createTest(iTestResult.getName().trim());
    }

    public void onTestSuccess(ITestResult iTestResult) {
        long totalExecutedTimeMillis = iTestResult.getEndMillis()-iTestResult.getStartMillis();
        long totalExecutedTimeSec = TimeUnit.MILLISECONDS.toSeconds(totalExecutedTimeMillis);
        logger.log(Status.PASS,iTestResult.getName().trim()+" Executed Successfully with in "+totalExecutedTimeSec+" seconds");
        log.info("-------------------"+iTestResult.getMethod().getMethodName()+"------------------ Success");
    }

    public void onTestFailure(ITestResult iTestResult) {
         log.error(iTestResult.getMethod().getMethodName()+" failed..");
         logger.log(Status.FAIL,iTestResult.getThrowable().fillInStackTrace());
         log.info("-------------------"+iTestResult.getMethod().getMethodName()+"------------------ Failed");
    }

    public void onTestSkipped(ITestResult iTestResult) {
           logger.log(Status.SKIP,iTestResult.getThrowable());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {

    }

    public void onStart(ISuite iSuite) {
        InetAddress localhost = null;
        try {
             localhost = InetAddress.getLocalHost();
        }catch(Exception e){
            e.printStackTrace();
        }
        String testCaseName=iSuite.getName().trim();
        String currDir = System.getProperty("user.dir");
        String lTimeStamp = getTimeAsString();
        String reportPath = currDir +
                "/" + testCaseName + "_" + lTimeStamp + ".html";
        htmlReporter=new ExtentHtmlReporter(reportPath);
        report = new ExtentReports();
        report.setSystemInfo("Host IP",localhost.getHostAddress().trim());
        report.setSystemInfo("Hostname",System.getProperty("user.name"));
        report.setSystemInfo("Environment",System.getProperty("os.name"));
        report.attachReporter(htmlReporter);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Report");
        htmlReporter.config().setReportName("BOSS Automation Report");

    }

    private static String getTimeAsString(){
        java.util.Date date= new java.util.Date();
        Timestamp currentTimestamp= new Timestamp(date.getTime());
        String currentTimeStampAsString = currentTimestamp.toString();
        currentTimeStampAsString = currentTimeStampAsString.replace("-", "");
        currentTimeStampAsString = currentTimeStampAsString.replace(":", "");
        currentTimeStampAsString = currentTimeStampAsString.replace(".", "");
        currentTimeStampAsString = currentTimeStampAsString.replace(" ", "");
        return currentTimeStampAsString.substring(0,12);
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        int retryCount=0;
        if(retryCount < 2)
        {
            retryCount++;
            log.info("Retrying Test method : "+iTestResult.getName() + " for " + retryCount +" times. ");
            return true;
        }
        return false;
    }
}
