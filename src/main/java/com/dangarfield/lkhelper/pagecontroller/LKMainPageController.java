package com.dangarfield.lkhelper.pagecontroller;

import java.util.Map;

import org.openqa.selenium.WebDriver;

public interface LKMainPageController {
	
	public int getHabitatArrayCount(final WebDriver driver);

	public String getHabitatName(final WebDriver driver, final int habitatArrayNo);
	
	public int getHabitatId(final WebDriver driver, final int habitatArrayNo);
	
	public int getHabitatMapX(final WebDriver driver, final int habitatArrayNo);
	
	public int getHabitatMapY(final WebDriver driver, final int habitatArrayNo);
	
	public int getHabitatPoints(final WebDriver driver, final int habitatArrayNo);
	
	public int getHabitatPlayerId(final WebDriver driver);
	
	public int getHabitatAllianceId(final WebDriver driver);
	
	public int getHabitatResourceArrayCount(final WebDriver driver, final int habitatArrayNo);
	
	public Map<String, Long> getHabitatResources(final WebDriver driver, final int habitatArrayNo, final int resourceArrayNo);

	public int getHabitatTroopArrayCount(final WebDriver driver, final int habitatArrayNo);
	
	public Map<String, Long> getHabitatTroops(final WebDriver driver, final int habitatArrayNo, final int troopArrayNo);
	
	public void keepSessionAlive(final WebDriver driver);
	
	public void selectWindow(final WebDriver driver, final String windowHandle);
	public String openNewWindow(final WebDriver driver, final String baseWindow);
	public void openNewTab(final WebDriver driver);
	
	public void closeTab(final WebDriver driver, final String url);
}
