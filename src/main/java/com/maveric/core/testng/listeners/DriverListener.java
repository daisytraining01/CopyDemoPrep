package com.maveric.core.testng.listeners;

import com.maveric.core.config.ConfigProperties;
import com.maveric.core.cucumber.CucumberListener;
import com.maveric.core.driver.BrowserStackClient;
import com.maveric.core.driver.Driver;
import com.maveric.core.driver.DriverFactory;
import com.maveric.core.driver.SauceClient;
import com.maveric.core.utils.web.WebActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

public class DriverListener implements ISuiteListener, ITestListener, IInvokedMethodListener {
    private static final Logger logger = LogManager.getLogger();
    private DriverFactory driverFactory;
    public static ThreadLocal<String> tests = new ThreadLocal<>();

    @Override
    public void onStart(ISuite suite) {
        DriverFactory.downloadDriver();
        driverFactory = new DriverFactory();
        BrowserStackClient.startBrowserStackLocal();
        BrowserStackClient.uploadFile();
        SauceClient.uploadFile();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() && ConfigProperties.BROWSER_INITIATE.getBoolean()) {
            tests.set(method.getTestMethod().getMethodName());
            driverFactory.driverSetup();
            logger.info("Driver Setup Completed");
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

        if (method.isTestMethod() && ConfigProperties.BROWSER_INITIATE.getBoolean()) {
            if ((!testResult.isSuccess()) && CucumberListener.getScenario() == null) {
                takeFailureScreenshot();
            }
            Driver.quitDriver();
            logger.info("Driver Quit Completed");
        }
    }

    @Override
    public void onFinish(ISuite suite) {

        BrowserStackClient.stopBrowserStackLocal();
        BrowserStackClient.deleteFiles();
    }

    private void takeFailureScreenshot() {
        if (!Driver.isWebDriverEmpty() || !Driver.isAppiumDriverEmpty()) {
            WebActions actions = new WebActions();
            actions.logScreenshot("Failure Screenshot");
        }
    }

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}


}
