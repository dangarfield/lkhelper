package com.dangarfield.lkhelper.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.dangarfield.lkhelper.data.historic.HistoricCastleCountData;
import com.dangarfield.lkhelper.data.historic.HistoricCastleData;
import com.dangarfield.lkhelper.data.historic.HistoricMemberCountData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;
import com.dangarfield.lkhelper.data.historic.HistoricPointsData;
import com.dangarfield.lkhelper.data.historic.HistoricRankData;
import com.dangarfield.lkhelper.data.report.ReportPlayerData;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property="_class")
public class AllianceData {
	
	@JsonProperty("_id")
	private int id; // Set when gather castle data
	private String name; // Set when gather castle data
	private String normalisedName; // Set when gather castle data
	private List<HistoricNameData> historicAllianceName; // Set when gather castle data
	private int points; // Set when gather castle data
	private int pointsAverage; // Set when gather castle data
	private List<HistoricPointsData> historicPoints; // Set when gather castle data
	private List<HistoricCastleData> historicLostCastles; // Calculated after castle data is gathered
	private int memberCount; 
	private List<HistoricMemberCountData> historicMemberCount;
	private List<ReportPlayerData> memberList;
	private int rank; // Set when gather castle data
	private List<HistoricRankData> historicRank; // Set when gather castle data
	private int rankAverage; // Set when gather castle data
	private int castleCount;
	private List<HistoricCastleCountData> historicCastleCount;
	private String gameStatus;
	private Date lastUpdate; // Set when gather castle data
	private String link;
	
	/**
	 * 
	 */
	public AllianceData() {
		super();
		this.historicPoints = new ArrayList<HistoricPointsData>();
		this.historicRank = new ArrayList<HistoricRankData>();
		this.historicMemberCount = new ArrayList<HistoricMemberCountData>();
		this.historicCastleCount = new ArrayList<HistoricCastleCountData>();
		this.historicAllianceName = new ArrayList<HistoricNameData>();
		this.historicLostCastles = new ArrayList<HistoricCastleData>();
		this.memberList = new ArrayList<ReportPlayerData>();
	}
	/**
	 * 
	 */
	public AllianceData(int id) {
		super();
		this.id = id;
		this.historicPoints = new ArrayList<HistoricPointsData>();
		this.historicRank = new ArrayList<HistoricRankData>();
		this.historicMemberCount = new ArrayList<HistoricMemberCountData>();
		this.historicCastleCount = new ArrayList<HistoricCastleCountData>();
		this.historicAllianceName = new ArrayList<HistoricNameData>();
		this.historicLostCastles = new ArrayList<HistoricCastleData>();
		this.memberList = new ArrayList<ReportPlayerData>();
	}
	/**
	 * 
	 */
	public AllianceData(String name) {
		super();
		this.name = name;
		this.historicPoints = new ArrayList<HistoricPointsData>();
		this.historicRank = new ArrayList<HistoricRankData>();
		this.historicMemberCount = new ArrayList<HistoricMemberCountData>();
		this.historicCastleCount = new ArrayList<HistoricCastleCountData>();
		this.historicAllianceName = new ArrayList<HistoricNameData>();
		this.historicLostCastles = new ArrayList<HistoricCastleData>();
		this.memberList = new ArrayList<ReportPlayerData>();
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
	 * @return the historicAllianceName
	 */
	public List<HistoricNameData> getHistoricAllianceName() {
		return historicAllianceName;
	}
	/**
	 * @param historicAllianceName the historicAllianceName to set
	 */
	public void setHistoricAllianceName(List<HistoricNameData> historicAllianceName) {
		this.historicAllianceName = historicAllianceName;
	}
	/**
	 * @param newHistoricAllianceName the newHistoricAllianceName to add
	 */
	public void addHistoricAllianceName(HistoricNameData newHistoricAllianceName) {
		List<HistoricNameData> historicAllianceNames = this.historicAllianceName;
		if (historicAllianceNames == null) {
			historicAllianceNames = new ArrayList<HistoricNameData>();
		}
		
		// check to see if the list is empty, if it is, add the new data
		if (historicAllianceNames.isEmpty()) {
			historicAllianceNames.add(newHistoricAllianceName);
		} else {
			// if the list is not empty, find the last record in the list (in terms of date)
			final HistoricNameData existingHistoricCastleName = historicAllianceNames.get(historicAllianceNames.size()-1);
			// if the matching attribute is the same, remove the last item in the list (to ensure lastUpdate date is correct)
			if(existingHistoricCastleName.getName().equals(newHistoricAllianceName.getName())) {
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
	 * @return the pointsAverage
	 */
	public int getPointsAverage() {
		return pointsAverage;
	}
	/**
	 * @param pointsAverage the pointsAverage to set
	 */
	public void setPointsAverage(int pointsAverage) {
		this.pointsAverage = pointsAverage;
	}

	/**
	 * @return the memberCount
	 */
	public int getMemberCount() {
		return memberCount;
	}
	/**
	 * @param memberCount the memberCount to set
	 */
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	/**
	 * @return the historicMemberCount
	 */
	public List<HistoricMemberCountData> getHistoricMemberCount() {
		return historicMemberCount;
	}
	/**
	 * @param historicMemberCount the historicMemberCount to set
	 */
	public void setHistoricMemberCount(List<HistoricMemberCountData> historicMemberCount) {
		this.historicMemberCount = historicMemberCount;
	}
	/**
	 * @param newHistoricMember the newHistoricMember to add
	 */
	public void addHistoricMemberCount(HistoricMemberCountData newHistoricMemberCount) {
		
		List<HistoricMemberCountData> historicMemberCountDatas = new ArrayList<HistoricMemberCountData>();
		// Iterate through existing list
		for (HistoricMemberCountData existingHistoricMemberCountData : this.historicMemberCount) {
			// Identify existing items that are not the same date as new item
			if(!existingHistoricMemberCountData.getLastUpdate().equals(newHistoricMemberCount.getLastUpdate())){
				// If this is the case, add the existing item to the new list
				historicMemberCountDatas.add(existingHistoricMemberCountData);
        	}
		}
		// Add the new item to the new list
		historicMemberCountDatas.add(newHistoricMemberCount);
		// Sort the list
		Collections.sort(historicMemberCountDatas, Collections.reverseOrder());
		// Set the new list to replace the existing list
		this.historicMemberCount = historicMemberCountDatas;
	}
	
	/**
	 * @return the memberList
	 */
	public List<ReportPlayerData> getMemberList() {
		return memberList;
	}
	/**
	 * @param memberList the memberList to set
	 */
	public void setMemberList(List<ReportPlayerData> memberList) {
		this.memberList = memberList;
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
	public void addLostCastle(HistoricCastleData newLostCastle) {
		List<HistoricCastleData> historicLostCastles = this.historicLostCastles;
		if (historicLostCastles == null) {
			historicLostCastles = new ArrayList<HistoricCastleData>();
		}
		
		// check to see if the list is empty, if it is, add the new data
		if (historicLostCastles.isEmpty()) {
			historicLostCastles.add(newLostCastle);
		} else {
			// if the list is not empty, find the last record in the list (in terms of date)
			final HistoricCastleData existingHistoricLostCastle = historicLostCastles.get(historicLostCastles.size()-1);
			// if the matching attribute is the same, remove the last item in the list (to ensure lastUpdate date is correct)
			if(existingHistoricLostCastle.getCastleId() == newLostCastle.getCastleId()) {
				historicLostCastles.remove(existingHistoricLostCastle);
			}
			// Add the new entry to the list
			historicLostCastles.add(newLostCastle);
		}
		
		// Sort the list
		Collections.sort(historicLostCastles);
		// Set the new list to replace the existing list
		this.historicLostCastles = historicLostCastles;
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
	 * @return the rankAverage
	 */
	public int getRankAverage() {
		return rankAverage;
	}


	/**
	 * @param rankAverage the rankAverage to set
	 */
	public void setRankAverage(int rankAverage) {
		this.rankAverage = rankAverage;
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
	public void setHistoricCastleCount(
			List<HistoricCastleCountData> historicCastleCount) {
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
	 * @return the gameStatus
	 */
	public String getGameStatus() {
		return gameStatus;
	}
	/**
	 * @param gameStatus the gameStatus to set
	 */
	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AllianceData)) {
			return false;
		}
		AllianceData other = (AllianceData) obj;
		
		if (this.getId() == other.getId()){
			return true;	
		} else {
			return false;
		}
		
	}
}
