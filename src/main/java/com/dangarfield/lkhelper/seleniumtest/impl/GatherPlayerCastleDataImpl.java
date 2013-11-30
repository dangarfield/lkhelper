package com.dangarfield.lkhelper.seleniumtest.impl;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dangarfield.lkhelper.data.PlayerCastleData;
import com.dangarfield.lkhelper.data.historic.HistoricPointsData;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.dangarfield.lkhelper.seleniumtest.GatherPlayerCastleData;
import com.dangarfield.lkhelper.data.users.JMSJobData;


public class GatherPlayerCastleDataImpl extends AbstractGatherBaseImpl implements GatherPlayerCastleData {
	
	private static Logger LOG = LogManager.getLogger("GatherPlayerData");
	
	private void before(final ServerData serverData, final UserData userData) throws GatherDataException {
		LOG.info("------------------------------------------------------------------------");
        LOG.info("--------------------       GATHER PLAYER DATA       --------------------");
        LOG.info("------------------------------------------------------------------------");
        
        driver = driverUtils.startSeleniumWebDriver();
        //TODO Need to login as specific user
        login(driver, userData.getEmail(), userData.getPassword(), serverData.getName());
	}
	
	@Override
	public void getHabitatData(final ServerData serverData, final UserData userData, final JMSJobData jmsJob) throws Exception {
		before(serverData, userData);
		
		LOG.info("Creating Habitat Data: Start");
		int habitatArrayCount = lkMainPageController.getHabitatArrayCount(driver);
		LOG.info(habitatArrayCount + " player castles to populate data");

		int playerId = lkMainPageController.getHabitatPlayerId(driver);
		int allianceId = lkMainPageController.getHabitatAllianceId(driver);
		
		for (int habitatArrayNo = 0; habitatArrayNo < habitatArrayCount; habitatArrayNo++) {
			
			final int id = lkMainPageController.getHabitatId(driver, habitatArrayNo);
			final PlayerCastleData playerCastle = castleDAO.getPlayerCastleForId(null,id);
			
			final String name = lkMainPageController.getHabitatName(driver, habitatArrayNo);
			LOG.info("habitat " + habitatArrayNo + " name: " + name + " (id = " + id + ")");
			
			playerCastle.setName(name);
			final Date updateDate = generalUtils.newDateForDay();
			playerCastle.setLastUpdate(updateDate);
			playerCastle.setLastPlayerUpdate(updateDate);
			playerCastle.setMapX(lkMainPageController.getHabitatMapX(driver, habitatArrayNo));
			playerCastle.setMapY(lkMainPageController.getHabitatMapY(driver, habitatArrayNo));
			final int points = lkMainPageController.getHabitatPoints(driver, habitatArrayNo);
			playerCastle.setPoints(points);
			playerCastle.addHistoricPoint(new HistoricPointsData(points, generalUtils.newDateForDay()));
			playerCastle.setPlayerId(playerId);
			playerCastle.setAllianceId(allianceId);
			
			final int habitatResourceArrayCount = lkMainPageController.getHabitatResourceArrayCount(driver, habitatArrayNo);
			LOG.debug("habitat " + habitatArrayNo + " name: " + name + " has " + habitatResourceArrayCount + " different resources");
			
			lkUtils.resetResourceCount(playerCastle);
			for (int habitatResourceArrayNo = 1; habitatResourceArrayNo < habitatResourceArrayCount; habitatResourceArrayNo++) {
				Map<String, Long> resourceMap = lkMainPageController.getHabitatResources(driver, habitatArrayNo, habitatResourceArrayNo);
				
				final int resourceId = resourceMap.get("resourceId").intValue();
				final int amount = resourceMap.get("amount").intValue();
				lkUtils.addResourceToPlayer(playerCastle, resourceId, amount);
			}
			
			final int habitatTroopArrayCount = lkMainPageController.getHabitatTroopArrayCount(driver, habitatArrayNo);
			LOG.debug("habitat " + habitatArrayNo + " name: " + name + " has " + habitatTroopArrayCount + " different troops");
			
			lkUtils.resetTroopCount(playerCastle);
			for (int habitatTroopArrayNo = 0; habitatTroopArrayNo < habitatTroopArrayCount; habitatTroopArrayNo++) {
				final Map<String, Long> troopMap = lkMainPageController.getHabitatTroops(driver, habitatArrayNo, habitatTroopArrayNo);
				lkUtils.addTroopsToPlayer(playerCastle, troopMap);
			}
			LOG.debug("habitat " + habitatArrayNo + " name: " + name + " is being saved to db");
			castleDAO.savePlayerCastle(null,playerCastle);
		}
		
		
		// Player Data
		
		// Alliance Data
		
		
		LOG.info("Creating Habitat Data: End");
		LOG.info("------------------------------------------------------------------------");
		
		after();
	}
	
	private void after() {
		closeDriver();
	}
}
