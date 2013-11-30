package com.dangarfield.lkhelper.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.dangarfield.lkhelper.data.historic.HistoricAllianceData;
import com.dangarfield.lkhelper.data.historic.HistoricCastleCountData;
import com.dangarfield.lkhelper.data.historic.HistoricCastleData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;
import com.dangarfield.lkhelper.data.historic.HistoricPointsData;
import com.dangarfield.lkhelper.data.historic.HistoricRankData;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property="_class")
public class PlayerData implements Comparable<PlayerData> {
	
	@JsonProperty("_id")
	private int id; // Set when gather castle data
	private String name; // Set when gather castle data
	private String normalisedName; // Set when gather castle data
	private List<HistoricNameData> historicPlayerName; // Set when gather castle data
	private int points; // Set when gather castle data
	private List<HistoricPointsData> historicPoints; // Set when gather castle data
	private int rank; // Set when gather castle data
	private List<HistoricRankData> historicRank; // Set when gather castle data
	private int allianceRank; // Calculated after castle data is gathered
	private List<HistoricRankData> historicAllianceRank; // Calculated after castle data is gathered
	private int allianceId; // Calculated after castle data is gathered
	private String allianceName; // Calculated after castle data is gathered
	private List<HistoricAllianceData> historicAllianceId; // Calculated after castle data is gathered
	private int castleCount; // Calculated after castle data is gathered
	private List<HistoricCastleCountData> historicCastleCount; // Calculated after castle data is gathered
	private List<HistoricCastleData> historicLostCastles; // Calculated after castle data is gathered
	private boolean onVacation; // Set when gather castle data
	private Date lastUpdate; // Set when gather castle data
	private String link;
	
	/**
	 * 
	 */
	public PlayerData() {
		super();
		this.historicPlayerName = new ArrayList<HistoricNameData>();
		this.historicPoints = new ArrayList<HistoricPointsData>();
		this.historicRank = new ArrayList<HistoricRankData>();
		this.historicAllianceRank = new ArrayList<HistoricRankData>();
		this.historicAllianceId = new ArrayList<HistoricAllianceData>();
		this.historicCastleCount = new ArrayList<HistoricCastleCountData>();
		this.historicLostCastles = new ArrayList<HistoricCastleData>();
	}
	/**
	 * 
	 */
	public PlayerData(int id) {
		super();
		this.id = id;
		this.historicPlayerName = new ArrayList<HistoricNameData>();
		this.historicPoints = new ArrayList<HistoricPointsData>();
		this.historicRank = new ArrayList<HistoricRankData>();
		this.historicAllianceRank = new ArrayList<HistoricRankData>();
		this.historicAllianceId = new ArrayList<HistoricAllianceData>();
		this.historicCastleCount = new ArrayList<HistoricCastleCountData>();
		this.historicLostCastles = new ArrayList<HistoricCastleData>();
	}
	/**
	 * 
	 */
	public PlayerData(String name) {
		super();
		this.name = name;
		this.historicPlayerName = new ArrayList<HistoricNameData>();
		this.historicPoints = new ArrayList<HistoricPointsData>();
		this.historicRank = new ArrayList<HistoricRankData>();
		this.historicAllianceRank = new ArrayList<HistoricRankData>();
		this.historicAllianceId = new ArrayList<HistoricAllianceData>();
		this.historicCastleCount = new ArrayList<HistoricCastleCountData>();
		this.historicLostCastles = new ArrayList<HistoricCastleData>();
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
	 * @return the historicPlayerName
	 */
	public List<HistoricNameData> getHistoricPlayerName() {
		return historicPlayerName;
	}
	/**
	 * @param historicPlayerName the historicPlayerName to set
	 */
	public void setHistoricPlayerName(List<HistoricNameData> historicPlayerName) {
		this.historicPlayerName = historicPlayerName;
	}
	/**
	 * @param newHistoricPlayerName the newHistoricPlayerName to add
	 */
	public void addHistoricPlayerName(HistoricNameData newHistoricPlayerName) {
		List<HistoricNameData> historicPlayerName = this.historicPlayerName;
		if (historicPlayerName == null) {
			historicPlayerName = new ArrayList<HistoricNameData>();
		}
		
		// check to see if the list is empty, if it is, add the new data
		if (historicPlayerName.isEmpty()) {
			historicPlayerName.add(newHistoricPlayerName);
		} else {
			// if the list is not empty, find the last record in the list (in terms of date)
			final HistoricNameData existingHistoricPlayerName = historicPlayerName.get(historicPlayerName.size()-1);
			// if the matching attribute is the same, remove the last item in the list (to ensure lastUpdate date is correct)
			if(existingHistoricPlayerName.getName().equals(newHistoricPlayerName.getName())) {
				historicPlayerName.remove(existingHistoricPlayerName);
			}
			// Add the new entry to the list
			historicPlayerName.add(newHistoricPlayerName);
		}
		
		// Sort the list
		Collections.sort(historicPlayerName);
		// Set the new list to replace the existing list
		this.historicPlayerName = historicPlayerName;
				
//		// Remove duplicates, comment this code once data is clean
//		List<HistoricNameData> dedupedData = new ArrayList<HistoricNameData>();
//		
//		for (HistoricNameData historicNameData : historicPlayerNames) {
//			boolean add = false;
//			if (dedupedData.isEmpty()) {
//				add = true;
//			} else {
//				HistoricNameData lastAddedItem = dedupedData.get(dedupedData.size()-1);
//				
//					if(!historicNameData.getName().equals(lastAddedItem.getName())) {
//						add = true;
//					}
//				
//				
//			}
//			if (add) {
//				dedupedData.add(historicNameData);
//			}
//		}
//		historicPlayerNames = dedupedData;
				
		
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
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}


	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}


	/**
	 * @return the historicRank
	 */
	public List<HistoricRankData> getHistoricRank() {
		return historicRank;
	}


	/**
	 * @param historicRank the historicRank to set
	 */
	public void setHistoricRank(List<HistoricRankData> historicRank) {
		this.historicRank = historicRank;
	}

	/**
	 * @param newHistoricRank the newHistoricRank to add
	 */
	public void addHistoricRank(HistoricRankData newHistoricRank) {
		
		List<HistoricRankData> historicRankDatas = new ArrayList<HistoricRankData>();
		
		// Iterate through existing list
		for (HistoricRankData existingHistoricRankData : this.historicRank) {
			// Identify existing items that are not the same date as new item
			if(!existingHistoricRankData.getLastUpdate().equals(newHistoricRank.getLastUpdate())){
				// If this is the case, add the existing item to the new list
				historicRankDatas.add(existingHistoricRankData);
        	}
		}
		// Add the new item to the new list
		historicRankDatas.add(newHistoricRank);
		// Sort the list
		Collections.sort(historicRankDatas);
		// Set the new list to replace the existing list
		this.historicRank = historicRankDatas;
	}


	/**
	 * @return the allianceRank
	 */
	public int getAllianceRank() {
		return allianceRank;
	}
	/**
	 * @param allianceRank the allianceRank to set
	 */
	public void setAllianceRank(int allianceRank) {
		this.allianceRank = allianceRank;
	}
	/**
	 * @return the historicAllianceRank
	 */
	public List<HistoricRankData> getHistoricAllianceRank() {
		return historicAllianceRank;
	}
	/**
	 * @param newHistoricAllianceRank the newHistoricAllianceRank to add
	 */
	public void addHistoricAllianceRank(HistoricRankData newHistoricAllianceRank) {
		
		List<HistoricRankData> historicAllianceRankDatas = new ArrayList<HistoricRankData>();
		// Iterate through existing list
		for (HistoricRankData existingHistoricAllianceRankData : this.historicAllianceRank) {
			// Identify existing items that are not the same date as new item
			if(!existingHistoricAllianceRankData.getLastUpdate().equals(newHistoricAllianceRank.getLastUpdate())){
				// If this is the case, add the existing item to the new list
				historicAllianceRankDatas.add(existingHistoricAllianceRankData);
        	}
		}
		// Add the new item to the new list
		historicAllianceRankDatas.add(newHistoricAllianceRank);
		// Sort the list
		Collections.sort(historicAllianceRankDatas);
		// Set the new list to replace the existing list
		this.historicAllianceRank = historicAllianceRankDatas;
	}
	/**
	 * @param historicAllianceRank the historicAllianceRank to set
	 */
	public void setHistoricAllianceRank(List<HistoricRankData> historicAllianceRank) {
		this.historicAllianceRank = historicAllianceRank;
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
	 * @return the historicAllianceId
	 */
	public List<HistoricAllianceData> getHistoricAllianceId() {
		return historicAllianceId;
	}
	/**
	 * @param historicAllianceId the historicAllianceId to set
	 */
	public void setHistoricAllianceId(List<HistoricAllianceData> historicAllianceId) {
		this.historicAllianceId = historicAllianceId;
	}

	/**
	 * @param newHistoricAllianceRank the newHistoricAllianceRank to add
	 */
	public void addHistoricAllianceId(HistoricAllianceData newHistoricAllianceId) {
		List<HistoricAllianceData> historicAllianceId = this.historicAllianceId;
		if (historicAllianceId == null) {
			historicAllianceId = new ArrayList<HistoricAllianceData>();
		}
		
		// check to see if the list is empty, if it is, add the new data
		if (historicAllianceId.isEmpty()) {
			historicAllianceId.add(newHistoricAllianceId);
		} else {
			// if the list is not empty, find the last record in the list (in terms of date)
			final HistoricAllianceData existingHistoricAllianceId = historicAllianceId.get(historicAllianceId.size()-1);
			// if the matching attribute is the same, remove the last item in the list (to ensure lastUpdate date is correct)
			if(existingHistoricAllianceId.getAllianceId() == newHistoricAllianceId.getAllianceId()) {
				historicAllianceId.remove(existingHistoricAllianceId);
			}
			// Add the new entry to the list
			historicAllianceId.add(newHistoricAllianceId);
		}
		
		// Sort the list
		Collections.sort(historicAllianceId);
		// Set the new list to replace the existing list
		this.historicAllianceId = historicAllianceId;
				
//		// Remove duplicates, comment this code once data is clean
//		List<HistoricAllianceData> dedupedData = new ArrayList<HistoricAllianceData>();
//		for (HistoricAllianceData historicAllianceData : historicAllianceIds) {
//			boolean add = false;
//			if (dedupedData.isEmpty()) {
//				add = true;
//			} else {
//				for (HistoricAllianceData dedupedItem : dedupedData) {
//					if(historicAllianceData.getAllianceId() != dedupedItem.getAllianceId()) {
//						add = true;
//					}
//				}
//			}
//			if (add) {
//				dedupedData.add(historicAllianceData);
//			}
//		}
//		historicAllianceIds = dedupedData;
		
		
		
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
	 * @return the castleCount
	 */
	public int getCastleCount() {
		return castleCount;
	}
	/**
	 * @param castleCount the castleCount to set
	 */
	public void setCastleCount(int castleCount) {
		this.castleCount = castleCount;
	}
	/**
	 * @return the historicCastleCount
	 */
	public List<HistoricCastleCountData> getHistoricCastleCount() {
		return historicCastleCount;
	}
	/**
	 * @param historicCastleCount the historicCastleCount to set
	 */
	public void setHistoricCastleCount(List<HistoricCastleCountData> historicCastleCount) {
		this.historicCastleCount = historicCastleCount;
	}

	/**
	 * @param newHistoricCastleCount the newHistoricCastleCount to add
	 */
	public void addHistoricCastleCount(HistoricCastleCountData newHistoricCastleCount) {
		
		List<HistoricCastleCountData> historicCastleCountDatas = new ArrayList<HistoricCastleCountData>();
		// Iterate through existing list
		for (HistoricCastleCountData existingHistoricCastleCountData : this.historicCastleCount) {
			// Identify existing items that are not the same date as new item
			if(!existingHistoricCastleCountData.getLastUpdate().equals(newHistoricCastleCount.getLastUpdate())){
				// If this is the case, add the existing item to the new list
				historicCastleCountDatas.add(existingHistoricCastleCountData);
        	}
		}
		// Add the new item to the new list
		historicCastleCountDatas.add(newHistoricCastleCount);
		// Sort the list
		Collections.sort(historicCastleCountDatas);
		// Set the new list to replace the existing list
		this.historicCastleCount = historicCastleCountDatas;
	}
	/**
	 * @return the historicLostCastles
	 */
	public List<HistoricCastleData> getHistoricLostCastles() {
		return historicLostCastles;
	}
	/**
	 * @param historicLostCastles the historicLostCastles to set
	 */
	public void setHistoricLostCastles(List<HistoricCastleData> historicLostCastles) {
		this.historicLostCastles = historicLostCastles;
	}
	/**
	 * @param newLostCastle the newHistoricPoint to add
	 */
	public void addLostCastle(HistoricCastleData newHistoricLostCastle) {
		List<HistoricCastleData> historicLostCastles = this.historicLostCastles;
		if (historicLostCastles == null) {
			historicLostCastles = new ArrayList<HistoricCastleData>();
		}
		
		// check to see if the list is empty, if it is, add the new data
		if (historicLostCastles.isEmpty()) {
			historicLostCastles.add(newHistoricLostCastle);
		} else {
			// if the list is not empty, find the last record in the list (in terms of date)
			final HistoricCastleData existingHistoricLostCastle = historicLostCastles.get(historicLostCastles.size()-1);
			// if the matching attribute is the same, remove the last item in the list (to ensure lastUpdate date is correct)
			if(existingHistoricLostCastle.getCastleId() == newHistoricLostCastle.getCastleId()) {
				historicLostCastles.remove(existingHistoricLostCastle);
			}
			// Add the new entry to the list
			historicLostCastles.add(newHistoricLostCastle);
		}
		
		// Sort the list
		Collections.sort(historicLostCastles);
		// Set the new list to replace the existing list
		this.historicLostCastles = historicLostCastles;
	}
	
	/**
	 * @return the onVacation
	 */
	public boolean isOnVacation() {
		return onVacation;
	}
	/**
	 * @param onVacation the onVacation to set
	 */
	public void setOnVacation(boolean onVacation) {
		this.onVacation = onVacation;
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
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof PlayerData)) {
			return false;
		}
		PlayerData other = (PlayerData) obj;
		
		if (this.getId() == other.getId()){
			return true;	
		} else {
			return false;
		}
		
	}
	@Override
	public int compareTo(PlayerData o) {
		return Integer.valueOf(getId()).compareTo(Integer.valueOf(o.getId()));
	}
	public static Comparator<PlayerData> DescendingLastUpdateComparator = new Comparator<PlayerData>() {
		public int compare(PlayerData date1, PlayerData date2) {
			return date1.getLastUpdate().compareTo(date2.getLastUpdate());
		}
	};
}
