package com.dangarfield.lkhelper.converter.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangarfield.lkhelper.converter.CastleDataJsonConverter;
import com.dangarfield.lkhelper.dao.AllianceDAO;
import com.dangarfield.lkhelper.dao.CastleDAO;
import com.dangarfield.lkhelper.dao.PlayerDAO;
import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricAllianceData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;
import com.dangarfield.lkhelper.data.historic.HistoricPlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricPointsData;
import com.dangarfield.lkhelper.data.historic.HistoricRankData;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.dangarfield.lkhelper.utils.GeneralUtils;
import com.fasterxml.jackson.databind.JsonNode;

public class CastleDataJsonConverterImpl implements CastleDataJsonConverter {
	
	private static Logger LOG = LogManager.getLogger("CastleDataJsonConverter");

	@Autowired
	protected CastleDAO castleDAO;
	@Autowired
	protected AllianceDAO allianceDAO;
	@Autowired
	protected PlayerDAO playerDAO;
	@Autowired
	protected GeneralUtils generalUtils;

	
	@Override
	public List<JsonNode> convertJsonMapToJsonHabitat(final ServerData serverData, final JsonNode rootJson) {
		
//		  	json > map > tileArray[x] > habitatDictionary[172450] >
//		  		creationDate
//		  		id
//		  		mapX
//		  		mapY
//		  		name
//		  		player >
//		  			alliance > 
//		 				id
//		  				name
//		  				points
//		  				pointsAverage
//		  				rank
//		  				rankAverage
//		  			alliancePermission
//		  			id
//		  			isOnVacation
//		  			nick
//		  			points
//		  			rank
//		  		points
		
		final List<JsonNode> jsonHabitats = new ArrayList<JsonNode>();
		
		LOG.debug("Attempting to convert the following JSON document:");
		LOG.debug(rootJson);
		
		
		final JsonNode mapNode = rootJson.get("map");
		LOG.debug(mapNode);
		final JsonNode tileArrayNodes = mapNode.get("tileArray");
		LOG.debug(tileArrayNodes);
		
		final Iterator<JsonNode> tileArrayNodesIterator = tileArrayNodes.elements();
		while( tileArrayNodesIterator.hasNext() ){
			final JsonNode tileArrayNode = tileArrayNodesIterator.next();
			final JsonNode habitatDictionaryNode = tileArrayNode.get("habitatDictionary");
			
			final Iterator<JsonNode> habitatDictionaryNodeIterator = habitatDictionaryNode.elements();
			while( habitatDictionaryNodeIterator.hasNext() ){
				final JsonNode habitatDictionary = habitatDictionaryNodeIterator.next();
				
				LOG.debug("Adding the following JSON document to the habitat list");
				LOG.debug(habitatDictionary);
				jsonHabitats.add(habitatDictionary);
	        }
		}
		LOG.debug("JSON habitat list is as follows:");
		LOG.debug(jsonHabitats);
		return jsonHabitats;
	}

	@Override
	public Map<String, List<? extends Object>> convertJsonHabitatsToCastleAndAllianceDatas(final ServerData serverData, final List<JsonNode> jsonHabitats) throws GatherDataException {
		Map<String, List<? extends Object>> castleAllianceMap = new HashMap<String, List<? extends Object>>();
    	
    	final List<CastleData> castleDatas = new ArrayList<CastleData>();
    	final List<AllianceData> allianceDatas = new ArrayList<AllianceData>();
    	final List<PlayerData> playerDatas = new ArrayList<PlayerData>();
		
		for (JsonNode jsonHabitat : jsonHabitats) {
			Map<String, ? extends Object> hashMapSingleCastleAlliance = convertJsonHabitatToCastleData(serverData, jsonHabitat);
			final CastleData castleData = (CastleData) hashMapSingleCastleAlliance.get(HASHMAP_CASTLE_DATA_KEY);
			castleDatas.add(castleData);

			final AllianceData allianceData = (AllianceData) hashMapSingleCastleAlliance.get(HASHMAP_ALLIANCE_DATA_KEY);
			if (allianceData != null) {
				allianceDatas.add(allianceData);	
			}
			final PlayerData playerData = (PlayerData) hashMapSingleCastleAlliance.get(HASHMAP_PLAYER_DATA_KEY);
			if (playerData != null) {
				playerDatas.add(playerData);	
			}
		}
		castleAllianceMap.put(HASHMAP_CASTLE_DATA_KEY, castleDatas);
		castleAllianceMap.put(HASHMAP_ALLIANCE_DATA_KEY, allianceDatas);
		castleAllianceMap.put(HASHMAP_PLAYER_DATA_KEY, playerDatas);
		
		return castleAllianceMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, ? extends Object> convertJsonHabitatToCastleData(final ServerData serverData, final JsonNode habitatDictionaryNode) throws GatherDataException {
		
		Map hashMapSingleCastleAlliance = new HashMap();
		
		LOG.debug("Attempting to convert the following JSON habitatDictionaryNode:");
		LOG.debug(habitatDictionaryNode);
		
		final Date latestDate =  generalUtils.newDateForDay();
		final int serverCode = serverData.getCode();
		
		final int castleId = habitatDictionaryNode.get("id").asInt();
		
		
		try {
			
			if(castleId==14906) {
				LOG.info("Found problem castle");
			}
			final CastleData castleData = castleDAO.getCastleForId(serverData.getId(),castleId);
			
			castleData.setId(castleId);
			
			String castleName = "";
			if (habitatDictionaryNode.has("name")) {
				castleName = habitatDictionaryNode.get("name").asText();
			} else {
				castleName = "Free castle " + String.valueOf(castleId);
			}
			
			
			castleData.setName(castleName);
			castleData.setNormalisedName(generalUtils.normaliseString(castleName));
			castleData.addHistoricCastleName(new HistoricNameData(castleName, latestDate));
			
			final int points = habitatDictionaryNode.get("points").asInt();
			castleData.setPoints(points);
			castleData.addHistoricPoint(new HistoricPointsData(points, latestDate));
			
			final int mapX = habitatDictionaryNode.get("mapX").asInt();
			final int mapY = habitatDictionaryNode.get("mapY").asInt();
			castleData.setMapX(mapX);
			castleData.setMapY(mapY);
			
			castleData.setLastUpdate(latestDate);
			castleData.setLink(generalUtils.createLinkForCastle(serverCode, mapX, mapY));
			
			if (habitatDictionaryNode.has("player")) {
				final JsonNode playerNode = habitatDictionaryNode.get("player");
				final int playerId = playerNode.get("id").asInt();
				castleData.setPlayerId(playerId);
				// More can be done with players and alliances
				final PlayerData playerData = playerDAO.getPlayerForId(serverData.getId(), playerId);
				final String playerName = playerNode.get("nick").asText();
				playerData.setName(playerName);
				playerData.setNormalisedName(generalUtils.normaliseString(playerName));
				playerData.addHistoricPlayerName(new HistoricNameData(playerName, latestDate));
				
				castleData.setPlayerName(playerName);
				castleData.addHistoricPlayerName(new HistoricPlayerData(playerId, playerName, latestDate));
				
				final int playerPoints = playerNode.get("points").asInt();
				playerData.setPoints(playerPoints);
				playerData.addHistoricPoint(new HistoricPointsData(playerPoints, latestDate));
				
				playerData.setOnVacation(playerNode.get("isOnVacation").asBoolean());
	
				final int playerRank = playerNode.get("rank").asInt();
				playerData.setRank(playerRank);
				playerData.addHistoricRank(new HistoricRankData(playerRank, latestDate));
				hashMapSingleCastleAlliance.put(HASHMAP_PLAYER_DATA_KEY, playerData);
				
				playerData.setLink(generalUtils.createLinkForPlayer(serverCode, playerId));
				
				if (playerNode.has("alliance")) {
					final JsonNode allianceNode = playerNode.get("alliance");
					final int allianceId = allianceNode.get("id").asInt();
					castleData.setAllianceId(allianceId);
					final AllianceData allianceData = allianceDAO.getAllianceForId(serverData.getId(), allianceId);
					allianceData.setId(allianceId);
					final String allianceName = allianceNode.get("name").asText();
					allianceData.setName(allianceName);
					allianceData.setNormalisedName(generalUtils.normaliseString(allianceName));
					allianceData.addHistoricAllianceName(new HistoricNameData(allianceName, latestDate));
					castleData.addHistoricAllianceName(new HistoricAllianceData(allianceId, allianceName, latestDate));
					castleData.setAllianceName(allianceName);
					playerData.addHistoricAllianceId(new HistoricAllianceData(allianceId, allianceName, latestDate));
					playerData.setAllianceName(allianceName);
					
					//final int memberCount = allianceNode.get("memberCount").asInt();
					//allianceData.setMemberCount(memberCount);
					//allianceData.addHistoricMemberCount(new HistoricMemberCountData(memberCount, generalUtils.newDateForDay()));
					
					final int alliancePoints = allianceNode.get("points").asInt(); 
					allianceData.setPoints(alliancePoints);
					allianceData.setPointsAverage(allianceNode.get("pointsAverage").asInt());
					allianceData.addHistoricPoint(new HistoricPointsData(alliancePoints, latestDate));
					
					final int allianceRank = allianceNode.get("rank").asInt();
					allianceData.setRank(allianceRank);
					allianceData.setRankAverage(allianceNode.get("rankAverage").asInt());
					allianceData.addHistoricRank(new HistoricRankData(allianceRank, latestDate));
					
					allianceData.setLastUpdate(generalUtils.newDateForDay());
					allianceData.setLink(generalUtils.createLinkForAlliance(serverCode, allianceId));
					hashMapSingleCastleAlliance.put(HASHMAP_ALLIANCE_DATA_KEY, allianceData);
				} else {
					//If not in alliance, set historic alliance to 0
					final HistoricAllianceData noAllianceData = new HistoricAllianceData(0, "No alliance", latestDate);
					playerData.setAllianceId(0);
					playerData.addHistoricAllianceId(noAllianceData);
					playerData.setAllianceName("No alliance");
					castleData.setAllianceId(0);
					castleData.addHistoricAllianceName(noAllianceData);
					castleData.setAllianceName("No alliance");
				}
			} else {
				castleData.addHistoricPlayerName(new HistoricPlayerData(0, "No player", latestDate));
				castleData.setPlayerId(0);
				castleData.setPlayerName("No player");
				castleData.addHistoricAllianceName(new HistoricAllianceData(0, "No alliance", latestDate));
				castleData.setAllianceId(0);
				castleData.setAllianceName("No alliance");
			}
			
			
			
			LOG.debug("Successfully converted the following castle:" + castleId);
			LOG.debug(castleData);
	
			hashMapSingleCastleAlliance.put(HASHMAP_CASTLE_DATA_KEY, castleData);
			
			return hashMapSingleCastleAlliance;
		
		} catch (Exception e) {
			final String error = "Problem converting data for castle: " + castleId;
			LOG.error(error);
			
			throw new GatherDataException(error);
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> convertHashMapList(List list, T t){
	    return list;
	}
	
	/**
	 * @return the castleDAO
	 */
	public CastleDAO getCastleDAO() {
		return castleDAO;
	}

	/**
	 * @param castleDAO the castleDAO to set
	 */
	public void setCastleDAO(CastleDAO castleDAO) {
		this.castleDAO = castleDAO;
	}

	/**
	 * @return the allianceDAO
	 */
	public AllianceDAO getAllianceDAO() {
		return allianceDAO;
	}

	/**
	 * @param allianceDAO the allianceDAO to set
	 */
	public void setAllianceDAO(AllianceDAO allianceDAO) {
		this.allianceDAO = allianceDAO;
	}
	/**
	 * @return the generalUtils
	 */
	public GeneralUtils getGeneralUtils() {
		return generalUtils;
	}

	/**
	 * @param generalUtils the generalUtils to set
	 */
	public void setGeneralUtils(GeneralUtils generalUtils) {
		this.generalUtils = generalUtils;
	}
}
