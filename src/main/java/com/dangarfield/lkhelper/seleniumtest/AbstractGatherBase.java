package com.dangarfield.lkhelper.seleniumtest;

import org.openqa.selenium.WebDriver;

import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.dangarfield.lkhelper.exceptions.LKServerNotFoundException;

public abstract interface AbstractGatherBase {
	
	public void loginAsDefaultUser(final ServerData serverData) throws LKServerNotFoundException, GatherDataException;
	
	public void login(final WebDriver driver, final String loginEmail, final String loginPassword, final String loginWorld) throws GatherDataException;
	
	public String calculateTimeDifference(final long startTime, final long endTime);
	public void closeDriver();
}
