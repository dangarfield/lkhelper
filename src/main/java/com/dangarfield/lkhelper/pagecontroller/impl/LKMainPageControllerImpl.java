package com.dangarfield.lkhelper.pagecontroller.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dangarfield.lkhelper.pagecontroller.LKMainPageController;

public class LKMainPageControllerImpl implements LKMainPageController {
	private static Logger LOG = LogManager.getLogger("LKMainPageController");
	
	@Override
	public int getHabitatArrayCount(final WebDriver driver) {
	
		LOG.debug("  Fetching Habitat Array Count: Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		Long habitatArrayLength = (Long) jsDriver.executeScript("return _habitatArray.length");
		
		LOG.debug("  Fetching Habitat Array Count: End");
        return habitatArrayLength.intValue();
	}

	@Override
	public String getHabitatName(final WebDriver driver, final int habitatArrayNo) {
		
		LOG.debug("  Fetching Habitat Name for Habitat " + habitatArrayNo + ": Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		String name = (String) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].name");
		
		LOG.debug("  Fetching Habitat Name for Habitat " + habitatArrayNo + ": End");
        return name;
	}
	@Override
	public int getHabitatId(final WebDriver driver, final int habitatArrayNo) {
		
		LOG.debug("  Fetching Habitat ID for Habitat " + habitatArrayNo + ": Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		Long id = (Long) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].id");
		
		LOG.debug("  Fetching Habitat ID for Habitat " + habitatArrayNo + ": End");
        return id.intValue();
	}
	@Override
	public int getHabitatMapX(final WebDriver driver, final int habitatArrayNo) {
		
		LOG.debug("  Fetching Habitat mapX for Habitat " + habitatArrayNo + ": Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		Long mapX = (Long) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].mapX");
		
		LOG.debug("  Fetching Habitat mapX for Habitat " + habitatArrayNo + ": End");
        return mapX.intValue();
	}
	@Override
	public int getHabitatMapY(final WebDriver driver, final int habitatArrayNo) {
		
		LOG.debug("  Fetching Habitat mapY for Habitat " + habitatArrayNo + ": Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		Long mapY = (Long) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].mapY");
		
		LOG.debug("  Fetching Habitat mapY for Habitat " + habitatArrayNo + ": End");
        return mapY.intValue();
	}
	@Override
	public int getHabitatPoints(final WebDriver driver, final int habitatArrayNo) {
		
		LOG.debug("  Fetching Habitat points for Habitat " + habitatArrayNo + ": Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		Long points = (Long) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].points");
		
		LOG.debug("  Fetching Habitat points for Habitat " + habitatArrayNo + ": End");
        return points.intValue();
	}
	@Override
	public int getHabitatPlayerId(final WebDriver driver) {
		
		LOG.debug("  Fetching Habitat playerId for Habitat: Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		String playerId = (String) jsDriver.executeScript("return _habitatArray[0].player.id");
		
		LOG.debug("  Fetching Habitat playerId for Habitat: End");
        return Integer.valueOf(playerId).intValue();
	}
	@Override
	public int getHabitatAllianceId(final WebDriver driver) {
		
		LOG.debug("  Fetching Habitat alliaceId for Habitat: Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		Long alliaceId = (Long) jsDriver.executeScript("return _habitatArray[0].player.alliance.id");
		
		LOG.debug("  Fetching Habitat alliaceId for Habitat: End");
        return alliaceId.intValue();
	}
	
	@Override
	public int getHabitatResourceArrayCount(final WebDriver driver, final int habitatArrayNo) {
		
		LOG.debug("  Fetching Habitat Resource Array for Habitat " + habitatArrayNo + ": Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		
		StringBuilder jsForHabitatResourceCount = new StringBuilder();
		
		jsForHabitatResourceCount.append("Object.size = function(obj) {");
		jsForHabitatResourceCount.append("    var size = 0, key;");
		jsForHabitatResourceCount.append("    for (key in obj) {");
		jsForHabitatResourceCount.append("        if (obj.hasOwnProperty(key)) size++;");
		jsForHabitatResourceCount.append("    }");
		jsForHabitatResourceCount.append("    return size;");
		jsForHabitatResourceCount.append("};");
		jsForHabitatResourceCount.append("var domObj = _habitatArray["+habitatArrayNo+"].habitatResources;");
		jsForHabitatResourceCount.append("return Object.size(domObj);");
		
		Long resourceArrayLength = (Long) jsDriver.executeScript(jsForHabitatResourceCount.toString());
		
		LOG.debug("  Fetching Habitat Resource Array for Habitat " + habitatArrayNo + ": End");
        return resourceArrayLength.intValue();
	}
	@Override
	public Map<String, Long> getHabitatResources(final WebDriver driver, final int habitatArrayNo, final int resourceArrayNo) {
		
		LOG.debug("  Fetching Habitat Resource Array for Habitat " + habitatArrayNo + ", resource space + " + resourceArrayNo + ": Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		Long resourceId = (Long) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].habitatResources["+resourceArrayNo+"].resourceID");
		
		Long amount = (Long) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].habitatResources["+resourceArrayNo+"].amount");
		
		Map<String, Long> resourceMap = new HashMap<String, Long>();
		
		resourceMap.put("resourceId", resourceId);
		resourceMap.put("amount", amount);
		
		LOG.debug("  Fetching Habitat Resource Array for Habitat " + habitatArrayNo + ", resource space + " + resourceArrayNo + ": End");
		
		return resourceMap;
        
	}
	
	@Override
	public int getHabitatTroopArrayCount(final WebDriver driver, final int habitatArrayNo) {
		
		LOG.debug("  Fetching Habitat Troop Array for Habitat " + habitatArrayNo + ": Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		
		Long troopArrayLength = (Long) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].habitatUnits.length");
		
		LOG.debug("  Fetching Habitat Troop Array for Habitat " + habitatArrayNo + ": End");
        return troopArrayLength.intValue();
	}
	@Override
	public Map<String, Long> getHabitatTroops(final WebDriver driver, final int habitatArrayNo, final int troopArrayNo) {
		
		LOG.debug("  Fetching Habitat Troop Array for Habitat " + habitatArrayNo + ", troop space + " + troopArrayNo + ": Start");
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		Long sourceHabitatId = (Long) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].habitatUnits["+troopArrayNo+"].sourceHabitat.id");
		
		Map<String, Long> troopMap = new HashMap<String, Long>();
		
		troopMap.put("sourceHabitatId", sourceHabitatId);
		
		Long troopUnitsFromCastleCount = (Long) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].habitatUnits["+troopArrayNo+"].units.length");
		for (int troopType = 0; troopType < troopUnitsFromCastleCount; troopType++) {
			Long troopUnitType = (Long) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].habitatUnits["+troopArrayNo+"].units["+troopType+"].primaryKey");
			Long troopUnitCount = (Long) jsDriver.executeScript("return _habitatArray["+habitatArrayNo+"].habitatUnits["+troopArrayNo+"].units["+troopType+"].count");
			
			if(troopUnitCount > 0) {
				troopMap.put(String.valueOf(troopUnitType), troopUnitCount);
			}
			
		}
		
		LOG.debug("  Fetching Habitat Troop Array for Habitat " + habitatArrayNo + ", troop space + " + troopArrayNo + ": End");
		
		return troopMap;
	}
	@Override
	public void keepSessionAlive(final WebDriver driver){
		final WebElement bottombar = driver.findElement(By.className("bottombar"));
		final WebElement container = bottombar.findElement(By.className("container"));
		final WebElement centerWrapper = container.findElement(By.className("centerWrapper"));
		final WebElement controls = centerWrapper.findElement(By.className("controls"));
		final WebElement player = controls.findElement(By.className("Player"));
		final WebElement imageElement = player.findElement(By.className("imageElement"));
		final WebElement img = imageElement.findElement(By.xpath("//div/img"));
		img.click();
		img.click();
		img.click();
		img.click();
	}
	@Override
	public void openNewTab(final WebDriver driver) {
		driver.findElement(By.xpath("html")).sendKeys(Keys.CONTROL +"t");
	}
	@Override
	public String openNewWindow(final WebDriver driver, final String baseWindow) {
		driver.findElement(By.xpath("html")).sendKeys(Keys.CONTROL +"n");
		Set<String> windowHandles = driver.getWindowHandles();
		for (String windowHandle : windowHandles) {
			if(!windowHandle.equals(baseWindow)) {
				return windowHandle;
			}
		}
		return "error";
	}
	@Override
	public void selectWindow(final WebDriver driver, final String windowHandle) {
		driver.switchTo().window(windowHandle);
	}
	@Override
	public void closeTab(final WebDriver driver, final String url) {
		final String currentURL = driver.getCurrentUrl();
		if (currentURL.equals(url)) {
			LOG.info("Closing tab for URL: " + url);
			driver.findElement(By.xpath("html")).sendKeys(Keys.CONTROL +"w");
		} else {
			LOG.info("NOT Closing tab page as URL does not match. Expected: " + url + ". Actual: " + currentURL);
		}
		
	}
}
