package com.dangarfield.lkhelper.seleniumtest.impl;

import static com.dangarfield.lkhelper.pagecontroller.impl.LKHttpRequestControllerImpl.BASE_MAP_URL_PARAM_CALLBACK_DEFAULT_VALUE;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangarfield.lkhelper.converter.CastleDataJsonConverter;
import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricAllianceData;
import com.dangarfield.lkhelper.data.historic.HistoricCastleCountData;
import com.dangarfield.lkhelper.data.historic.HistoricMemberCountData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;
import com.dangarfield.lkhelper.data.historic.HistoricRankData;
import com.dangarfield.lkhelper.data.report.ReportPlayerData;
import com.dangarfield.lkhelper.data.report.ReportPlayerIdPoints;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.exceptions.EmptyTileException;
import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.dangarfield.lkhelper.exceptions.LKServerNotFoundException;
import com.dangarfield.lkhelper.seleniumtest.GatherAllCastleData;
import com.dangarfield.lkhelper.service.ReportService;
import com.dangarfield.lkhelper.data.users.JMSJobData;
import com.dangarfield.lkhelper.data.users.JMSJobData.JMSJobStatus;
import com.fasterxml.jackson.databind.JsonNode;

public class GatherAllCastleDataImpl extends AbstractGatherBaseImpl implements GatherAllCastleData {

	private static Logger LOG = LogManager.getLogger("GatherAllCastleData");

	private static String BASE_MAP_URL_A = "http://ios-hh.lordsandknights.com/XYRALITY/WebObjects/";
	private static String BASE_MAP_URL_B = ".woa/wa/MapAction/map";
	private static String BASE_MAP_URL_PARAM_MAPX_KEY = "mapX";
	private static String BASE_MAP_URL_PARAM_MAPY_KEY = "mapY";
	private static String BASE_MAP_URL_PARAM_MAPWIDTH_KEY = "mapWidth";
	private static String BASE_MAP_URL_PARAM_MAPHEIGHT_KEY = "mapHeight";
	private static String BASE_MAP_URL_PARAM_PROPERTYLISTVERSION_KEY = "PropertyListVersion";
	private static String BASE_MAP_URL_PARAM_PROPERTYLISTVERSION_DEFAULT_VALUE = "3";
	private static String BASE_MAP_URL_PARAM_CALLBACK_KEY = "callback";
	
	private static String DRIVER_WINDOW_MAIN= "";
	private static String DRIVER_WINDOW_JSON= "jsonWindow";
	
	private static int MAP_WIDTH = 64;
	private static int MAP_HEIGHT = 64;
	private static int MAX_TILE_LOOP_CATCH = 100;
	
	private static int CASTLE_COUNT = 0;
	
	private static int KEEP_ALIVE_COUNT = 0;
	private static int KEEP_ALIVE_LIMIT = 10;
	
	
	private static List<TilePair> COMPLETED_TILES = new ArrayList<TilePair>();
	private static List<TilePair> EMPTY_TILES = new ArrayList<TilePair>();
	
	@Autowired
	private ReportService reportService;
	
	private void before(final ServerData serverData) throws GatherDataException {
		LOG.info("------------------------------------------------------------------------");
        LOG.info("-----------      GATHER ALL CASTLE DATA FOR SERVER: "+serverData.getName()+"     -----------");
        LOG.info("------------------------------------------------------------------------");
        
        driver = driverUtils.startSeleniumWebDriver();
        DRIVER_WINDOW_MAIN = driver.getWindowHandle();

		try {
			loginAsDefaultUser(serverData);
		} catch (LKServerNotFoundException e) {
			throw new GatherDataException(e.getMessage());
		}
		
		DRIVER_WINDOW_JSON = lkMainPageController.openNewWindow(driver, DRIVER_WINDOW_MAIN);
		lkMainPageController.selectWindow(driver, DRIVER_WINDOW_MAIN);
	}
	
	
	@Override
	public int getAllCastleData(final ServerData serverData, final JMSJobData jmsJob) throws GatherDataException {
		
		//updateAllianceAndPlayerDataFromNewCastleData(serverData);
		
		boolean success = true;
		
		final long startTime = jmsJob.getStartTime().getTime();
		
		jmsJob.setDetail("Logging into server");
		jmsJob.setStep(3);
		jmsJobDAO.saveJob(jmsJob);
		
		before(serverData);
		
		jmsJob.setDetail("0 of 4 boundaries found");
		jmsJob.setStep(4);
		jmsJobDAO.saveJob(jmsJob);
		
		try {
			
		
			// min and max X and Y values will be ascertained and used to populate master hashmap to get all tiles
			int minX = 0;
			int centreX = 0;
			int maxX = 0;
			int minY = 0;
			int centreY = 0;
			int maxY = 0;
			
			//start from any central point that is definitely on the towards the centre of the hexagon (eg, will be able to find maxX and minX coords)
			LOG.info("Validate the intial position of coordinates for the serverData:" + serverData.getCentralX() + " - " + serverData.getCentralY());
			checkServerDataStartPoint(serverData);
			
	
			LOG.info("Locate min, centre and max X and Y co-ordinates");
			minX = locateMinX(serverData);
			
			jmsJob.setDetail("1 of 4 boundaries found");
			jmsJob.setStep(4);
			jmsJobDAO.saveJob(jmsJob);
			
			maxX = locateMaxX(serverData);
			
			jmsJob.setDetail("2 of 4 boundaries found");
			jmsJob.setStep(4);
			jmsJobDAO.saveJob(jmsJob);
			
			centreX = calculateCentreCoords(minX, maxX);
			LOG.info("minX: " + minX + ". maxX: " + maxX + ". centreX:" + centreX);
			
			minY = locateMinY(serverData, centreX);
			
			jmsJob.setDetail("3 of 4 boundaries found");
			jmsJob.setStep(4);
			jmsJobDAO.saveJob(jmsJob);
			
			maxY = locateMaxY(serverData, centreX);
			
			jmsJob.setDetail("4 of 4 boundaries found");
			jmsJob.setStep(4);
			jmsJobDAO.saveJob(jmsJob);
			
			centreY = calculateCentreCoords(minX, maxX);
			LOG.info("minY: " + minY + ". maxY: " + maxY + ". centreY:" + centreY);
			
			LOG.info("Validating min, centre and max X and Y co-ordinates");
			validatePositions(minX, centreX, maxX, minY, centreY, maxY);
	
			jmsJob.setDetail("Calculating");
			jmsJob.setStep(5);
			jmsJobDAO.saveJob(jmsJob);
			
			LOG.info("Create a list of all possible tiles within min and max boundaries");
			List<TilePair> possibleTiles = createPossibleTileList(minX, maxX, minY, maxY);
			LOG.info("Created " + possibleTiles.size() + " possible tiles");
			
			LOG.info("De-dupe any previously collected tiles (successful or empty) from the possible list");
			dedupePossibleTiles(possibleTiles);
			LOG.info("After de-dupe there remains " + possibleTiles.size() + " possible tiles");
			
			jmsJob.setDetail("Capturing");
			jmsJob.setStep(6);
			jmsJobDAO.saveJob(jmsJob);
			
			LOG.info("Capture all remaining possible tile data");
			captureRemainingPossibleTiles(jmsJob, serverData, possibleTiles);

			
			jmsJob.setDetail("Updating alliance and player data based on new castle data");
			jmsJob.setStep(7);
			jmsJobDAO.saveJob(jmsJob);
			
			LOG.info("Updating alliance and player data based on new castle data: START");
			updateAllianceAndPlayerDataFromNewCastleData(serverData);
			LOG.info("Updating alliance and player data based on new castle data: COMPLETE");
			
		} catch (Exception e) {
			success = false;
			LOG.error(e);
		} finally {
			after();
		}
		
		
		
		final Date endDate = new Date();
		final long endTime = endDate.getTime();
		
		final String timeDifference = calculateTimeDifference(startTime, endTime);
		if(success) {
			final String successText = "Sucessfully captured all castle data on server " + serverData.getName() + ". " + CASTLE_COUNT + " castles. In " + timeDifference;
			
			jmsJob.setDetail(successText);
			jmsJob.setEndTime(endDate);
			jmsJob.setStatus(JMSJobStatus.COMPLETE);
			jmsJobDAO.saveJob(jmsJob);
			
			LOG.info(successText);	
		} else {
			final String errorText = "There was an error, however the process managed to captured partial castle data on server " + serverData.getName() + ". " + CASTLE_COUNT + " castles. In " + timeDifference; 
			
			jmsJob.setDetail(errorText);
			jmsJob.setEndTime(endDate);
			jmsJob.setStatus(JMSJobStatus.ERROR);
			jmsJobDAO.saveJob(jmsJob);
			
			LOG.info(errorText);
		}
		
		serverData.setLastCaptured(generalUtils.newDateForDay());
		serverDAO.saveServerData(serverData);
		return CASTLE_COUNT;
	}

	private void checkServerDataStartPoint(final ServerData serverData) throws GatherDataException {
		try {
			final int mapX = serverData.getCentralX();
			final int mapY = serverData.getCentralY();
			LOG.info("Getting tile from centre xy coords: " + mapX + " - " + mapY);
			CASTLE_COUNT += getAllCastlesForSingleTile(serverData,mapX,mapY);
			recordTileResult(COMPLETED_TILES, mapX, mapY);
		} catch (EmptyTileException e) {
			final String error = "No Data held in centre point, please add another"; 
			LOG.error(error);
			throw new GatherDataException(error);
		}
	}

	private int locateMinX(final ServerData serverData) throws GatherDataException {
		int minX = 0;
		try {
			LOG.info("Attempting to locate minX");
			//reset x and y to centre
			final int mapX = serverData.getCentralX();
			final int mapY = serverData.getCentralY();
			
			//loop for until EmptyTileException is caught or 100+ tiles are used
			for (int x = 1;x<=MAX_TILE_LOOP_CATCH;x++) {
				// these multiplications should not not floating point adjustment
				final int testX = mapX - (x * MAP_WIDTH);
				LOG.info("Fetching tile from xy coords: " + testX + " - " + mapY);
				CASTLE_COUNT += getAllCastlesForSingleTile(serverData,testX,mapY);
				recordTileResult(COMPLETED_TILES, testX, mapY);
				minX = testX;
		    } 
		} catch (EmptyTileException e) {
			LOG.info("Empty Tile found at xy coords: " + e.getMapX() + " - " + e.getMapY());
			recordTileResult(EMPTY_TILES, e.getMapX(), e.getMapY());
			LOG.info("Found minX: " + minX);
		}
		return minX;
	}
	private int locateMaxX(final ServerData serverData) throws GatherDataException {
		int maxX = 0;
		try {
			LOG.info("Attempting to locate maxX");
			//reset x and y to centre
			final int mapX = serverData.getCentralX();
			final int mapY = serverData.getCentralY();
			
			//loop for until EmptyTileException is caught or 100+ tiles are used
			for (int x = 1;x<=MAX_TILE_LOOP_CATCH;x++) {
				// these multiplications should not not floating point adjustment
				final int testX = mapX + (x * MAP_HEIGHT);
				LOG.info("Fetching tile from xy coords: " + testX + " - " + mapY);
				CASTLE_COUNT += getAllCastlesForSingleTile(serverData,testX,mapY);
				recordTileResult(COMPLETED_TILES, testX, mapY);
				maxX = testX;
		    } 
		} catch (EmptyTileException e) {
			LOG.info("Empty Tile found at xy coords: " + e.getMapX() + " - " + e.getMapY());
			recordTileResult(EMPTY_TILES, e.getMapX(), e.getMapY());
			LOG.info("Found maxX: " + maxX);
		}
		return maxX;
	}
	private int locateMinY(final ServerData serverData, final int centreX) throws GatherDataException {
		int minY = 0;
		try {
			LOG.info("Attempting to locate minY");
			//reset x and y to centre, override if a more accurate centreX is known
			final int mapX = centreX;
			final int mapY = serverData.getCentralY();
			
			//loop for until EmptyTileException is caught or 100+ tiles are used
			for (int x = 1;x<=MAX_TILE_LOOP_CATCH;x++) {
				// these multiplications should not not floating point adjustment
				final int testY = mapY - (x * MAP_WIDTH);
				LOG.info("Fetching tile from xy coords: " + mapX + " - " + testY);
				CASTLE_COUNT += getAllCastlesForSingleTile(serverData,mapX,testY);
				recordTileResult(COMPLETED_TILES, mapX, testY);
				minY = testY;
		    } 
		} catch (EmptyTileException e) {
			LOG.info("Empty Tile found at xy coords: " + e.getMapX() + " - " + e.getMapY());
			recordTileResult(EMPTY_TILES, e.getMapX(), e.getMapY());
			LOG.info("Found minY: " + minY);
		}
		return minY;
	}
	private int locateMaxY(final ServerData serverData, final int centreX) throws GatherDataException {
		int maxY = 0;
		try {
			LOG.info("Attempting to locate maxY");
			//reset x and y to centre, override if a more accurate centreX is known
			final int mapX = centreX;
			final int mapY = serverData.getCentralY();
			
			//loop for until EmptyTileException is caught or 100+ tiles are used
			for (int x = 1;x<=MAX_TILE_LOOP_CATCH;x++) {
				// these multiplications should not not floating point adjustment
				final int testY = mapY + (x * MAP_HEIGHT);
				LOG.info("Fetching tile from xy coords: " + mapX + " - " + testY);
				CASTLE_COUNT += getAllCastlesForSingleTile(serverData,mapX,testY);
				recordTileResult(COMPLETED_TILES, mapX, testY);
				maxY = testY;
		    } 
		} catch (EmptyTileException e) {
			LOG.info("Empty Tile found at xy coords: " + e.getMapX() + " - " + e.getMapY());
			recordTileResult(EMPTY_TILES, e.getMapX(), e.getMapY());
			LOG.info("Found maxY: " + maxY);
		}
		return maxY;
	}

	private int calculateCentreCoords(int minX, int maxX) {
		return minX + ((maxX - minX) / 2);
	}

	private void validatePositions(int minX, int centreX, int maxX, int minY,
			int centreY, int maxY) throws GatherDataException {
		
		//TODO test min and max x and y are set, add a few (multiple of MAP_WIDTH/MAP_HEIGHT to be sure
		
		if (minX == 0) {
			final String error = "Error validating minX position, shouldn't be zero: " + String.valueOf(minX);
			LOG.error(error);
			throw new GatherDataException(error);
		} else if (centreX == 0) {
			final String error = "Error validating centreX position, shouldn't be zero: " + String.valueOf(minX);
			LOG.error(error);
			throw new GatherDataException(error);
		} else if (maxX == 0) {
			final String error = "Error validating maxX position, shouldn't be zero: " + String.valueOf(minX);
			LOG.error(error);
			throw new GatherDataException(error);
		} else if (minY == 0) {
			final String error = "Error validating minY position, shouldn't be zero: " + String.valueOf(minX);
			LOG.error(error);
			throw new GatherDataException(error);
		} else if (centreY == 0) {
			final String error = "Error validating centreY position, shouldn't be zero: " + String.valueOf(minX);
			LOG.error(error);
			throw new GatherDataException(error);
		} else if (maxY == 0) {
			final String error = "Error validating maxY position, shouldn't be zero: " + String.valueOf(minX);
			LOG.error(error);
			throw new GatherDataException(error);
		}
		LOG.info("Successfully validated min, centre and max X and Y co-ordinates");
	}
	

	private List<TilePair> createPossibleTileList(int minX, int maxX, int minY,
			int maxY) {

		final List<TilePair> possibleTiles = new ArrayList<TilePair>();
		for (int potentialX = minX ; potentialX < maxX ; potentialX += MAP_WIDTH) {
			for (int potentialY = minY ; potentialY < maxY ; potentialY += MAP_HEIGHT) {
				possibleTiles.add(new TilePair(potentialX, potentialY));
			}
		}
		return possibleTiles;
	}


	private void dedupePossibleTiles(final List<TilePair> possibleTiles) {
		final int originalPossibleTilesCount = possibleTiles.size();
		final int alreadyCompletedTilesCount = COMPLETED_TILES.size();
		final int alreadyEmptyTilesCount = EMPTY_TILES.size();
		
		final List<TilePair> removeFromPossibles = new ArrayList<TilePair>();

		LOG.debug("Completed Tiles");
		LOG.debug(COMPLETED_TILES);
		LOG.debug("Empty Tiles");
		LOG.debug(EMPTY_TILES);
		LOG.debug("Possible Tiles");
		LOG.debug(possibleTiles);
		
		for (TilePair possible : possibleTiles) {
			for (TilePair completed : COMPLETED_TILES) {
				if(possible.equals(completed)) {
					removeFromPossibles.add(possible);
				}
			}
			for (TilePair empty : EMPTY_TILES) {
				if(possible.equals(empty)) {
					removeFromPossibles.add(possible);
				}
			}
		}

		possibleTiles.removeAll(removeFromPossibles);
		
		LOG.debug("Post dedupe possible Tiles");
		LOG.debug(possibleTiles);
		
		final int dedupedActualPossibleTilesCount = possibleTiles.size();
	
		LOG.info("Original possible: " + String.valueOf(originalPossibleTilesCount) + ". Already completed: " + String.valueOf(alreadyCompletedTilesCount) + " Already empty: " + String.valueOf(alreadyEmptyTilesCount) + ". Post dedupe: " + String.valueOf(dedupedActualPossibleTilesCount));
	}
	private void dedupeAllianceData(final List<AllianceData> possibleAlliances) {
		final int originalPossibleAlliancesCount = possibleAlliances.size();
		
		final List<AllianceData> uniqueAlliances = new ArrayList<AllianceData>();

		LOG.debug("Possible Alliances");
		LOG.debug(originalPossibleAlliancesCount);

		
		for (AllianceData possible : possibleAlliances) {
			
			boolean isAlreadyAdded = false;
			for (AllianceData unique : uniqueAlliances) {
				if(possible.equals(unique)) {
					isAlreadyAdded = true;
				}
			}
			if (!isAlreadyAdded) {
				uniqueAlliances.add(possible);
			}
		}

		LOG.debug("Post dedupe unique alliances");
		LOG.debug(uniqueAlliances);
		
		possibleAlliances.clear();
		possibleAlliances.addAll(uniqueAlliances);
		
		final int uniqueAlliancesCount = possibleAlliances.size();
		
		LOG.debug("Original Possible Alliances: " + String.valueOf(originalPossibleAlliancesCount) + ". Unique alliances: " + String.valueOf(uniqueAlliancesCount));
		
	}
	private void dedupePlayerData(final List<PlayerData> possiblePlayers) {
		final int originalPossiblePlayersCount = possiblePlayers.size();
		
		final List<PlayerData> uniquePlayers = new ArrayList<PlayerData>();

		LOG.debug("Possible Players");
		LOG.debug(originalPossiblePlayersCount);

		
		for (PlayerData possible : possiblePlayers) {
			
			boolean isAlreadyAdded = false;
			for (PlayerData unique : uniquePlayers) {
				if(possible.equals(unique)) {
					isAlreadyAdded = true;
				}
			}
			if (!isAlreadyAdded) {
				uniquePlayers.add(possible);
			}
		}

		LOG.debug("Post dedupe unique players");
		LOG.debug(uniquePlayers);
		
		possiblePlayers.clear();
		possiblePlayers.addAll(uniquePlayers);
		
		final int uniquePlayersCount = possiblePlayers.size();
		
		LOG.debug("Original Possible Plaers: " + String.valueOf(originalPossiblePlayersCount) + ". Unique players: " + String.valueOf(uniquePlayersCount));
		
	}
	
	private void captureRemainingPossibleTiles(final JMSJobData jmsJob, final ServerData serverData, final List<TilePair> possibleTiles) throws GatherDataException {
		
		final int possibleTilesCount = possibleTiles.size();
		for (int i = 0; i < possibleTilesCount; i++) {
			TilePair tilePair = possibleTiles.get(i);
			try {
				final int mapX = tilePair.getX();
				final int mapY = tilePair.getY();
				
				jmsJob.setDetail("Fetching map tiles: " + (i+1) + " of " + possibleTilesCount);
				jmsJob.setStep(6);
				jmsJobDAO.saveJob(jmsJob);
				
				LOG.info( (i+1) + " of " + possibleTilesCount + ". Fetching tile from xy coords: " + mapX + " - " + mapY);
				CASTLE_COUNT += getAllCastlesForSingleTile(serverData,mapX,mapY);
				recordTileResult(COMPLETED_TILES, mapX, mapY);
			} catch (EmptyTileException e) {
				LOG.info("Empty Tile found at xy coords: " + e.getMapX() + " - " + e.getMapY());
				recordTileResult(EMPTY_TILES, e.getMapX(), e.getMapY());
			}	
		}
		
	}
	
	private void recordTileResult(final List<TilePair> tileMap, final int mapX, final int mapY){
		tileMap.add(new TilePair(mapX, mapY));
	}
	
	private int getAllCastlesForSingleTile(final ServerData serverData, final int mapX, final int mapY) throws EmptyTileException, GatherDataException {

		KEEP_ALIVE_COUNT += 1;
		if(KEEP_ALIVE_COUNT > KEEP_ALIVE_LIMIT) {
			KEEP_ALIVE_COUNT = 0;
			LOG.info("Danger of session ending, touching page to keep session alive");
			lkMainPageController.selectWindow(driver, DRIVER_WINDOW_MAIN);
			lkMainPageController.keepSessionAlive(driver);
		}
        final URL mapUrl = createMapRequestUrl(serverData, mapX, mapY, MAP_WIDTH, MAP_HEIGHT);

        lkMainPageController.selectWindow(driver, DRIVER_WINDOW_JSON);
        
        JsonNode jsonResponse;
        try {
        	jsonResponse = lkHttpRequestController.getMap(driver, mapUrl);
        } catch (Exception e) {
        	final String error = "Error fetching URL map and parsing into generic JSON for tile xy:" + mapX + " - " + mapY;
			LOG.error(error);
			throw  new GatherDataException(error);
		}
        
        lkMainPageController.selectWindow(driver, DRIVER_WINDOW_MAIN);
        
        List<JsonNode> jsonHabitats;
        try {
        	jsonHabitats = castleDataJsonConverter.convertJsonMapToJsonHabitat(serverData, jsonResponse);
        } catch (Exception e) {
        	final String error = "Error converting generic JSON into a list of JSON habitats ready to be converted to CastleData objects for tile xy:" + mapX + " - " + mapY;
			LOG.error(error);
			throw  new GatherDataException(error);
		}
        
        List<CastleData> castleDatas;
        List<AllianceData> allianceDatas;
        List<PlayerData> playerDatas;
        try {
        	
        	if(mapX==16235 && mapY==16401) {
				LOG.info("Found problem tile");
			}
        	
        	Map<String, List<? extends Object>> castleAllianceMap = castleDataJsonConverter.convertJsonHabitatsToCastleAndAllianceDatas(serverData, jsonHabitats);
        	
        	castleDatas = castleDataJsonConverter.convertHashMapList(castleAllianceMap.get(CastleDataJsonConverter.HASHMAP_CASTLE_DATA_KEY),new CastleData());
        	allianceDatas = castleDataJsonConverter.convertHashMapList(castleAllianceMap.get(CastleDataJsonConverter.HASHMAP_ALLIANCE_DATA_KEY),new AllianceData());
        	playerDatas = castleDataJsonConverter.convertHashMapList(castleAllianceMap.get(CastleDataJsonConverter.HASHMAP_PLAYER_DATA_KEY),new PlayerData());
        	// Need to dedupe the allianceDatas and playerDatas
        } catch (Exception e) {
        	final String error = "Error converting JSON habitats into CastleData, AllianceData and PlayerData objects for tile xy:" + mapX + " - " + mapY;
			LOG.error(error);
			throw  new GatherDataException(error);
		}
        dedupeAllianceData(allianceDatas);
        dedupePlayerData(playerDatas);
        castleDAO.saveCastles(serverData.getId(), castleDatas);
        allianceDAO.saveAlliances(serverData.getId(), allianceDatas);
        playerDAO.savePlayers(serverData.getId(), playerDatas);
        
        final int completedCastleCount = castleDatas.size();
        LOG.info("Gathered data from " + completedCastleCount + " castles");
        
        if(castleDatas.size() == 0) {
        	final String error = "0 castles were identified, throwing an EmptyTileException for tile xy:" + mapX + " - " + mapY;
        	LOG.info(error);
        	throw new EmptyTileException(error, mapX, mapY, MAP_WIDTH, MAP_HEIGHT);
        }
		return completedCastleCount;
	}
	
	
	
	private URL createMapRequestUrl(final ServerData serverData, final int mapX, final int mapY, final int mapWidth, final int mapHeight) throws GatherDataException {

		try {
			//ios-hh.lordsandknights.com/XYRALITY/WebObjects/LKWorldServer-UK-3.woa/wa/MapAction/map?mapX=16000&mapY=16000&mapWidth=64&mapHeight=64&PropertyListVersion=3
			
			//ios-hh.lordsandknights.com/XYRALITY/WebObjects/LKWorldServer-UK-3.woa/wa/MapAction/map
			//	mapX=16000
			//	mapY=16000
			//	mapWidth=64
			//	mapHeight=64
			//	PropertyListVersion=3
			
			final StringBuilder createMapURL = new StringBuilder();
			//Base URL
			createMapURL.append(BASE_MAP_URL_A + serverData.getUrl() + BASE_MAP_URL_B);
			//mapX param
			createMapURL.append("?" + BASE_MAP_URL_PARAM_MAPX_KEY + "=" + String.valueOf(mapX));
			//mapY param
			createMapURL.append("&" + BASE_MAP_URL_PARAM_MAPY_KEY + "=" + String.valueOf(mapY));
			//mapWidth param
			createMapURL.append("&" + BASE_MAP_URL_PARAM_MAPWIDTH_KEY + "=" + String.valueOf(mapWidth));
			//mapHeight param
			createMapURL.append("&" + BASE_MAP_URL_PARAM_MAPHEIGHT_KEY + "=" + String.valueOf(mapHeight));
			//PropertyListVersion param
			createMapURL.append("&" + BASE_MAP_URL_PARAM_PROPERTYLISTVERSION_KEY + "=" + String.valueOf(BASE_MAP_URL_PARAM_PROPERTYLISTVERSION_DEFAULT_VALUE));
			//callback param
			createMapURL.append("&" + BASE_MAP_URL_PARAM_CALLBACK_KEY + "=" + String.valueOf(BASE_MAP_URL_PARAM_CALLBACK_DEFAULT_VALUE));
			
			final URL mapURL = new URL(createMapURL.toString());
			
			LOG.debug("URL Created: " + mapURL);
			return mapURL;
			
		} catch (MalformedURLException e) {
			final String error = "Error creating map request URL: "  + e.getMessage();
			LOG.error(error);
			throw new GatherDataException(error);
		}
	}


	private void updateAllianceAndPlayerDataFromNewCastleData(final ServerData serverData) {
		
		final String serverId = serverData.getId();
		final List<Integer> allAllianceIds = allianceDAO.getAllAllianceIds(serverId);
		
		final Date updateDate = generalUtils.newDateForDay();
		
		final int totalAlliancesCount = allAllianceIds.size();
		for (int a = 0; a < totalAlliancesCount; a++) {
			final Integer allianceId = allAllianceIds.get(a); 
		
			final AllianceData allianceData = allianceDAO.getAllianceForId(serverId, allianceId);
			
			LOG.info("Updating: " + (a+1) + " of " + totalAlliancesCount + " alliances - " + allianceData.getName() + ". Updating Alliance and Player Data");
			
			// get a count of all castles in that alliance
			final int castleCount = castleDAO.getCastleCountForAlliance(serverId, allianceId);
			allianceData.setCastleCount(castleCount);
			allianceData.addHistoricCastleCount(new HistoricCastleCountData(castleCount, updateDate));
			
			// get all members and their points as part of that alliance
			List<ReportPlayerIdPoints> memberPointsReport = castleDAO.getMemberPointsForAlliance(serverId, allianceId);
			final int memberCount = memberPointsReport.size();
			allianceData.setMemberCount(memberCount);
			allianceData.addHistoricMemberCount(new HistoricMemberCountData(memberCount, updateDate));
			
			final List<ReportPlayerData> memberList = new ArrayList<ReportPlayerData>();
			
			//allianceData.setHistoricLostCastles(castleDAO.getLostCastlesForAlliance(serverId, allianceId));
			
			
			for (int p = 0; p < memberPointsReport.size(); p++) {
				final ReportPlayerIdPoints reportPlayerIdPoints = memberPointsReport.get(p);
				final int playerId = reportPlayerIdPoints.getPlayerId();
				final int playerCastleCount = reportPlayerIdPoints.getCastles();
				final int rank = p + 1;
				
				if(playerId==5826) {
					//LOG.info("Found problem player");
				}
				final PlayerData playerData = playerDAO.getPlayerForId(serverId, playerId);
				playerData.setAllianceRank(rank);
				playerData.addHistoricAllianceRank(new HistoricRankData(rank, updateDate));
				playerData.setAllianceId(allianceId);
				playerData.addHistoricAllianceId(new HistoricAllianceData(allianceId, allianceData.getName(), updateDate));
				//Delete in a sec
				//playerData.addHistoricPlayerName(new HistoricNameData(playerData.getName(), updateDate));
				//Delete in a sec
				playerData.setCastleCount(playerCastleCount);
				playerData.addHistoricCastleCount(new HistoricCastleCountData(playerCastleCount, updateDate));
				playerDAO.savePlayer(serverId, playerData);
				
				String playerGameStatus = reportService.createGameStatusForPlayer(playerData);
				
				HistoricAllianceData previousAlliance = null;
				List<HistoricAllianceData> historicAllianceDatas = playerData.getHistoricAllianceId();
				

				for (int i=historicAllianceDatas.size()-1; i >= 0; i--){
					HistoricAllianceData historicAllianceData = (HistoricAllianceData) historicAllianceDatas.get(i);
					if (historicAllianceData.getAllianceId() != allianceId.intValue()) {
						previousAlliance = historicAllianceData;
						break;
					}
				}
				
				memberList.add(new ReportPlayerData(playerId, playerData.getName(), playerData.getPoints(), playerGameStatus, previousAlliance, updateDate));
			}
			Collections.sort(memberList, ReportPlayerData.PointsComparator);
			allianceData.setMemberList(memberList);
			String allianceGameStatus = reportService.createGameStatusForAlliance(allianceData);
			allianceData.setGameStatus(allianceGameStatus);
			allianceDAO.saveAlliance(serverId, allianceData);
		}
		
		//TODO: Do something the fetching of data for players not in an alliance
		
		final Date latestDate = generalUtils.newDateForDay();
		
		final List<Integer> noAlliancePlayerIds = playerDAO.getAllPlayerIdsWithNoAlliance(serverId);
		final int noAlliancePlayerIdsCount = noAlliancePlayerIds.size();
		
		for (int b = 0; b < totalAlliancesCount; b++) {
			LOG.debug("Updating: " + (b+1) + " of " + noAlliancePlayerIdsCount + " players without an alliance");
			final Integer playerId = noAlliancePlayerIds.get(b);
			final PlayerData playerData = playerDAO.getPlayerForId(serverId, playerId);
			
			final int castleCount = castleDAO.getCastleCountForPlayer(serverId, playerId);
			//castle count
			playerData.setCastleCount(castleCount);
			//historic castle count
			playerData.addHistoricCastleCount(new HistoricCastleCountData(castleCount, latestDate));
			
			//historic player name // I think this should work when gathering, but apparently not for old data
			playerData.addHistoricPlayerName(new HistoricNameData(playerData.getName(), latestDate));
			//historic alliance id // I think this should work when gathering, but apparently not for old data
			playerData.addHistoricAllianceId(new HistoricAllianceData(0, "No alliance", latestDate));
			
			playerDAO.savePlayer(serverId, playerData);
		}
		
		
	}
	// Dan - playerId = 108147
	// Crusaders - allianceId = 15029



	private void after() {
		driver.quit();
	}

	private class TilePair {
		private int x;
		private int y;
		
		/**
		 * @param x
		 * @param y
		 */
		public TilePair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		/**
		 * @return the x
		 */
		public int getX() {
			return x;
		}
		/**
		 * @return the y
		 */
		public int getY() {
			return y;
		}
		
		@Override
		public String toString() {
			return "Co-ordinates xy - " + x + " : " + y;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
	            return true;
	        }
	        if (!(obj instanceof TilePair)) {
	            return false;
	        }
	        TilePair other = (TilePair) obj;
	        
	        if ((this.getX() == other.getX()) && (this.getY() == other.getY())) {
	        	return true;	
	        } else {
	        	return false;
	        }
			
		}
	}
	
}
