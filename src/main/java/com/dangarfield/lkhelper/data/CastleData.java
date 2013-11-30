package com.dangarfield.lkhelper.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.dangarfield.lkhelper.data.historic.HistoricAllianceData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;
import com.dangarfield.lkhelper.data.historic.HistoricPlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricPointsData;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property="_class")
public class CastleData implements Comparable<CastleData> {

	@JsonProperty("_id")
	private int id;
	private String name;
	private String normalisedName;
	private List<HistoricNameData> historicCastleName;
	private int playerId;
	private String playerName;
	private List<HistoricPlayerData> historicPlayerName;
	private int allianceId;
	private String allianceName;
	private List<HistoricAllianceData> historicAllianceName;
	private int points;
	private List<HistoricPointsData> historicPoints;
	private int mapX;
	private int mapY;
	private Date lastUpdate;
	private String link;

	/**
	 * 
	 */
	public CastleData() {
		super();
		this.historicPoints = new ArrayList<HistoricPointsData>();
		this.historicCastleName = new ArrayList<HistoricNameData>();
		this.historicPlayerName = new ArrayList<HistoricPlayerData>();
		this.historicAllianceName = new ArrayList<HistoricAllianceData>();
	}
	/**
	 * 
	 */
	public CastleData(int id) {
		super();
		this.id = id;
		this.historicPoints = new ArrayList<HistoricPointsData>();
		this.historicCastleName = new ArrayList<HistoricNameData>();
		this.historicPlayerName = new ArrayList<HistoricPlayerData>();
		this.historicAllianceName = new ArrayList<HistoricAllianceData>();
	}
	/**
	 * 
	 */
	public CastleData(String name) {
		super();
		this.name = name;
		this.historicPoints = new ArrayList<HistoricPointsData>();
		this.historicCastleName = new ArrayList<HistoricNameData>();
		this.historicPlayerName = new ArrayList<HistoricPlayerData>();
		this.historicAllianceName = new ArrayList<HistoricAllianceData>();
	}

	/**
	 * @param id
	 * @param name
	 * @param playerId
	 * @param allianceId
	 * @param points
	 * @param mapX
	 * @param mapY
	 * @param lastUpdate
	 */
	public CastleData(int id, String name, int playerId, int allianceId,
			int points, int mapX, int mapY, List<HistoricPointsData> historicPoints, Date lastUpdate, String link) {
		super();
		this.id = id;
		this.name = name;
		this.playerId = playerId;
		this.allianceId = allianceId;
		this.points = points;
		this.mapX = mapX;
		this.mapY = mapY;
		this.setHistoricPoints(historicPoints);
		this.lastUpdate = lastUpdate;
		this.historicPoints = new ArrayList<HistoricPointsData>();
		this.link = link;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the normalisedName
	 */
	public String getNormalisedName() {
		return normalisedName;
	}
	/**
	 * @param normalisedName the normalisedName to set
	 */
	public void setNormalisedName(String normalisedName) {
		this.normalisedName = normalisedName;
	}
	/**
	 * @return the historicCastleName
	 */
	public List<HistoricNameData> getHistoricCastleName() {
		return historicCastleName;
	}
	/**
	 * @param historicCastleName the historicCastleName to set
	 */
	public void setHistoricCastleName(List<HistoricNameData> historicCastleName) {
		this.historicCastleName = historicCastleName;
	}
	/**
	 * @param newHistoricCastleName the newHistoricCastleName to add
	 */
	public void addHistoricCastleName(HistoricNameData newHistoricCastleName) {
		List<HistoricNameData> historicCastleNames = this.historicCastleName;
		if (historicCastleNames == null) {
			historicCastleNames = new ArrayList<HistoricNameData>();
		}
		
		// check to see if the list is empty, if it is, add the new data
		if (historicCastleNames.isEmpty()) {
			historicCastleNames.add(newHistoricCastleName);
		} else {
			// if the list is not empty, find the last record in the list (in terms of date)
			final HistoricNameData existingHistoricCastleName = historicCastleNames.get(historicCastleNames.size()-1);
			// if the matching attribute is the same, remove the last item in the list (to ensure lastUpdate date is correct)
			if(existingHistoricCastleName.getName().equals(newHistoricCastleName.getName())) {
				historicCastleNames.remove(existingHistoricCastleName);
			}
			// Add the new entry to the list
			historicCastleNames.add(newHistoricCastleName);
		}
		
		// Sort the list
		Collections.sort(historicCastleNames);
		// Set the new list to replace the existing list
		this.historicCastleName = historicCastleNames;
	}
	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	/**
	 * @return the historicPlayerName
	 */
	public List<HistoricPlayerData> getHistoricPlayerName() {
		return historicPlayerName;
	}
	/**
	 * @param historicPlayerName the historicPlayerName to set
	 */
	public void setHistoricPlayerName(List<HistoricPlayerData> historicPlayerName) {
		this.historicPlayerName = historicPlayerName;
	}
	/**
	 * @param newHistoricPlayerName the newHistoricPlayerName to add
	 */
	public void addHistoricPlayerName(HistoricPlayerData newHistoricPlayerName) {
		List<HistoricPlayerData> historicPlayerNames = this.historicPlayerName;
		if (historicPlayerNames == null) {
			historicPlayerNames = new ArrayList<HistoricPlayerData>();
		}
		
		// check to see if the list is empty, if it is, add the new data
		if (historicPlayerNames.isEmpty()) {
			historicPlayerNames.add(newHistoricPlayerName);
		} else {
			// if the list is not empty, find the last record in the list (in terms of date)
			final HistoricPlayerData existingHistoricCastleName = historicPlayerNames.get(historicPlayerNames.size()-1);
			// if the matching attribute is the same, remove the last item in the list (to ensure lastUpdate date is correct)
			if(existingHistoricCastleName.getPlayerName().equals(newHistoricPlayerName.getPlayerName())) {
				historicPlayerNames.remove(existingHistoricCastleName);
			}
			// Add the new entry to the list
			historicPlayerNames.add(newHistoricPlayerName);
		}
		
		// Sort the list
		Collections.sort(historicPlayerNames);
		// Set the new list to replace the existing list
		this.historicPlayerName = historicPlayerNames;
	}
	/**
	 * @return the allianceId
	 */
	public int getAllianceId() {
		return allianceId;
	}

	/**
	 * @param allianceId the allianceId to set
	 */
	public void setAllianceId(int allianceId) {
		this.allianceId = allianceId;
	}

	public String getAllianceName() {
		return allianceName;
	}
	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}
	/**
	 * @return the historicAllianceName
	 */
	public List<HistoricAllianceData> getHistoricAllianceName() {
		return historicAllianceName;
	}
	/**
	 * @param historicAllianceName the historicAllianceName to set
	 */
	public void setHistoricAllianceName(List<HistoricAllianceData> historicAllianceName) {
		this.historicAllianceName = historicAllianceName;
	}
	/**
	 * @param newHistoricAllianceName the newHistoricAllianceName to add
	 */
	public void addHistoricAllianceName(HistoricAllianceData newHistoricAllianceName) {
		List<HistoricAllianceData> historicAllianceNames = this.historicAllianceName;
		if (historicAllianceNames == null) {
			historicAllianceNames = new ArrayList<HistoricAllianceData>();
		}
		
		// check to see if the list is empty, if it is, add the new data
		if (historicAllianceNames.isEmpty()) {
			historicAllianceNames.add(newHistoricAllianceName);
		} else {
			// if the list is not empty, find the last record in the list (in terms of date)
			final HistoricAllianceData existingHistoricCastleName = historicAllianceNames.get(historicAllianceNames.size()-1);
			// if the matching attribute is the same, remove the last item in the list (to ensure lastUpdate date is correct)
			if(existingHistoricCastleName.getAllianceId() == newHistoricAllianceName.getAllianceId()) {
				historicAllianceNames.remove(existingHistoricCastleName);
			}
			// Add the new entry to the list
			historicAllianceNames.add(newHistoricAllianceName);
		}
		
		// Sort the list
		Collections.sort(historicAllianceNames);
		// Set the new list to replace the existing list
		this.historicAllianceName = historicAllianceNames;
	}
	
	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the mapX
	 */
	public int getMapX() {
		return mapX;
	}

	/**
	 * @param mapX the mapX to set
	 */
	public void setMapX(int mapX) {
		this.mapX = mapX;
	}

	/**
	 * @return the mapY
	 */
	public int getMapY() {
		return mapY;
	}

	/**
	 * @param mapY the mapY to set
	 */
	public void setMapY(int mapY) {
		this.mapY = mapY;
	}

	/**
	 * @return the historicPoints
	 */
	public List<HistoricPointsData> getHistoricPoints() {
		return historicPoints;
	}
	/**
	 * @param historicPoints the historicPoints to set
	 */
	public void setHistoricPoints(List<HistoricPointsData> historicPoints) {
		this.historicPoints = historicPoints;
	}
	/**
	 * @param newHistoricPoint the newHistoricPoint to add
	 */
	public void addHistoricPoint(HistoricPointsData newHistoricPoint) {
		
		List<HistoricPointsData> historicPointsDatas = new ArrayList<HistoricPointsData>();
		// Iterate through existing list
		for (HistoricPointsData existingHistoricPointsData : this.historicPoints) {
			// Identify existing items that are not the same date as new item
			if(!existingHistoricPointsData.getLastUpdate().equals(newHistoricPoint.getLastUpdate())){
				// If this is the case, add the existing item to the new list
				historicPointsDatas.add(existingHistoricPointsData);
        	}
		}
		// Add the new item to the new list
		historicPointsDatas.add(newHistoricPoint);
		// Sort the list
		Collections.sort(historicPointsDatas);
		// Set the new list to replace the existing list
		this.historicPoints = historicPointsDatas;
	}
	
	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return name + " - " + id;
	}
	
	public int compareTo(CastleData o) {
		return Integer.valueOf(getId()).compareTo(Integer.valueOf(o.getId()));
	}
	
	public static Comparator<CastleData> DescendingLastUpdateComparator = new Comparator<CastleData>() {
		public int compare(CastleData date1, CastleData date2) {
			return date1.getLastUpdate().compareTo(date2.getLastUpdate());
		}
	};
}
