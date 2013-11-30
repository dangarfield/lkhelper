package com.dangarfield.lkhelper.pagecontroller.impl;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.dangarfield.lkhelper.pagecontroller.LKLoginPageController;

public class LKLoginPageControllerImpl implements LKLoginPageController {
	private static Logger LOG = LogManager.getLogger("LoginPageController");
	
	public void login(final WebDriver driver, final String loginEmail, final String loginPassword, final String loginWorld) throws GatherDataException {
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        LOG.info("Logging in");
        driver.get("http://lordsandknights.com/en/");
        
        try {
	        WebElement loginEmailElement = driver.findElement(By.name("loginEmail"));
	        loginEmailElement.sendKeys(loginEmail);
        } catch (Exception e) {
			throw new GatherDataException("Unable to enter email");
		}
        
        try {
        	WebElement loginPasswordElement = driver.findElement(By.name("loginPassword"));
        	loginPasswordElement.sendKeys(loginPassword);
        } catch (Exception e) {
			throw new GatherDataException("Unable to enter password");
		}
        
        try {
        	WebElement loginButtonElement = driver.findElement(By.xpath("//form[@id='login']/button[@name='login']"));
        	loginButtonElement.click();
        } catch (Exception e) {
			throw new GatherDataException("Unable to click login");
		}
        
        
        
        
        try {
        	LOG.info("Selecting game world");
        	WebElement worldLinkElement = driver.findElement(By.partialLinkText(loginWorld)); //world id = 42
        	worldLinkElement.click();
        } catch (Exception e) {
        	if(!driver.getCurrentUrl().contains("game-login")) {
        		final String error = "Authentication failed. Username and password incorrect"; 
            	LOG.info(error);
            	throw new GatherDataException(error);
        	} else {
        		final String error = "Authentication failed. Unable to select server: " + loginWorld; 
            	LOG.info(error);
            	throw new GatherDataException(error);	
        	}
        	
		}
        LOG.info("Authentication passed");
        
        LOG.info("Waiting for main page to load");
        
   
        try {
	        WebDriverWait wait = new WebDriverWait(driver, 20);
	        wait.until(
	    		ExpectedConditions.visibilityOfElementLocated(By.className("bottombar"))
	        );
        } catch (Exception e) {
			throw new GatherDataException("Server is not loading");
		}        
        LOG.info("------------------------------------------------------------------------");
        LOG.info("-----  Successfully logged into " + loginWorld + " as " + loginEmail + " -----");
        LOG.info("------------------------------------------------------------------------");
        
	}
	
}
