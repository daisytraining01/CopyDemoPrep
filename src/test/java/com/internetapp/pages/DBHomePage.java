package com.internetapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.maveric.core.utils.web.WebActions;

public class DBHomePage extends WebActions {

    private final By txt_pagetitle = By.xpath("//div[@class='page-title']//li");
    private final By txt_password = By.name("password");
    private final By txt_pin = By.name("accpin");
    
    private final By link_savings = By.id("savings-menu");
    private final By link_savingsacc_view = By.xpath("//a[contains(@href,'savings-view')]");
    private final By acc1_balance=By.xpath("//form[contains(@action,'savings-view')]//div[text()='Daisy']//following-sibling::div[last()]");
    
    private final By link_Transfer = By.xpath("//a[text()='Transfer']");
    private final By link_btw_accounts = By.xpath("//a[contains(@href,'xfer-between')]");
    private final By txt_trxferamount = By.name("amount");
    private final By select_fromAcc = By.name("fromAccount");
    private final By select_toAcc = By.name("toAccount");
    
    private final By button_Submit =  By.xpath("//button[@type='submit']");
    
    WebDriverWait wait;
    WebDriver driver;
    float balance;

    public DBHomePage(WebDriver driver) {
     this.driver=driver;  
     wait=new WebDriverWait(driver,20);
    }

    public void checkSavingsBalance(String accHolderName) {
        
    	driver.findElement(link_savings).click();
    	wait.until(ExpectedConditions.elementToBeClickable(link_savingsacc_view)).click();
    	System.out.println("-----------"+wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form[contains(@action,'savings-view')]//div[text()='"+accHolderName+"']//following-sibling::div[last()]"))).getText());
    	balance=Float.valueOf(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form[contains(@action,'savings-view')]//div[text()='"+accHolderName+"']//following-sibling::div[last()]"))).getText().split("\\$")[1]);
    	
    }

    public void transferAmount(String amount,String fromAcc,String toAcc) {
        
    	driver.findElement(link_Transfer).click();
    	wait.until(ExpectedConditions.elementToBeClickable(link_btw_accounts)).click();
    	wait.until(ExpectedConditions.elementToBeClickable(txt_trxferamount));
    	
      	Select option=new Select(driver.findElement(select_fromAcc));
    	option.selectByValue(option.getOptions().stream().filter(x->x.getText().contains(fromAcc)).findAny().get().getAttribute("value"));
    	
    	
    	Select option2=new Select(driver.findElement(select_toAcc));
    	option2.selectByValue(option.getOptions().stream().filter(x->x.getText().contains(toAcc)).findAny().get().getAttribute("value"));
    	
    	
    	driver.findElement(txt_trxferamount).sendKeys(amount);
    	driver.findElement(txt_trxferamount).sendKeys(Keys.ENTER);
    	
    	//driver.findElement(button_Submit).click();
    	
    	wait.until(ExpectedConditions.elementToBeClickable(By.id("transactionTable")));
    }
    
    public void verifyCreditTransactionSuccess(String amount) {
        
    	if(driver.findElement(By.xpath("//tr[1]//td[text()='Income']//following-sibling::td[text()='$"+amount+"']")).isDisplayed()) {
    		logScreenshot("Transaction Success");
    	}else {
    		logScreenshot("Transaction Failed");
    	}
    }
  
    public void verifyTransactionSuccess(String amount,String accHolderName,String transType) {
    	driver.findElement(link_savings).click();
    	wait.until(ExpectedConditions.elementToBeClickable(link_savingsacc_view)).click();
        
    	driver.findElement(By.xpath("//div[text()='"+accHolderName+"']/preceding-sibling::label//span[@class='switch-label']")).click();
    	
    	if(transType.equalsIgnoreCase("debited")) {
		    	if(driver.findElement(By.xpath("//tr[1]//td[text()='Misc']//following-sibling::td[text()='$-"+amount+"']")).isDisplayed()) {
		    		logScreenshot("Transaction Success");
		    	}else {
		    		logScreenshot("Transaction Failed");
		    	}
    	}
    	
    	if(transType.equalsIgnoreCase("credited")) {
	        	if(driver.findElement(By.xpath("//tr[1]//td[text()='Income']//following-sibling::td[text()='$"+amount+"']")).isDisplayed()) {
	        		logScreenshot("Transaction Success");
	        	}else {
	        		logScreenshot("Transaction Failed");
	        	}
    	}
    	
    }
    
        
}
