package com.dangarfield.lkhelper.pagecontroller;

import org.openqa.selenium.WebDriver;

import com.dangarfield.lkhelper.exceptions.GatherDataException;

public interface LKLoginPageController {
	
	public void login(final WebDriver driver, final String loginEmail, final String loginPassword, final String loginWorld) throws GatherDataException;
}
