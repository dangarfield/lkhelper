package com.dangarfield.lkhelper.data.report;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportCastleChangeData implements Comparable<ReportCastleChangeData>{

	@JsonProperty("_id")
	private int id;
	private Date lastUpdate;
	private int points;

	private String allianceNameStart;
	private int allianceIdStart;
	private String playerNameStart;
	private int playerIdStart;
	private String castleNameStart;
	
	private String allianceNameEnd;
	private int allianceIdEnd;
	private String playerNameEnd;
	private int playerIdEnd;
	private String castleNameEnd;

	private boolean newlyJoinedAlliancePlayer;
	
	/**
	 * 
	 */
	public ReportCastleChangeData() {
		super();
	}

	/**
	 * @return the _id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the allianceNameStart
	 */
	public String getAllianceNameStart() {
		return allianceNameStart;
	}

	/**
	 * @param allianceNameStart the allianceNameStart to set
	 */
	public void setAllianceNameStart(String allianceNameStart) {
		this.allianceNameStart = allianceNameStart;
	}

	/**
	 * @return the allianceIdStart
	 */
	public int getAllianceIdStart() {
		return allianceIdStart;
	}

	/**
	 * @param allianceIdStart the allianceIdStart to set
	 */
	public void setAllianceIdStart(int allianceIdStart) {
		this.allianceIdStart = allianceIdStart;
	}

	/**
	 * @return the playerNameStart
	 */
	public String getPlayerNameStart() {
		return playerNameStart;
	}

	/**
	 * @param playerNameStart the playerNameStart to set
	 */
	public void setPlayerNameStart(String playerNameStart) {
		this.playerNameStart = playerNameStart;
	}

	/**
	 * @return the playerIdStart
	 */
	public int getPlayerIdStart() {
		return playerIdStart;
	}

	/**
	 * @param playerIdStart the playerIdStart to set
	 */
	public void setPlayerIdStart(int playerIdStart) {
		this.playerIdStart = playerIdStart;
	}

	/**
	 * @return the castleNameStart
	 */
	public String getCastleNameStart() {
		return castleNameStart;
	}

	/**
	 * @param castleNameStart the castleNameStart to set
	 */
	public void setCastleNameStart(String castleNameStart) {
		this.castleNameStart = castleNameStart;
	}

	/**
	 * @return the allianceNameEnd
	 */
	public String getAllianceNameEnd() {
		return allianceNameEnd;
	}

	/**
	 * @param allianceNameEnd the allianceNameEnd to set
	 */
	public void setAllianceNameEnd(String allianceNameEnd) {
		this.allianceNameEnd = allianceNameEnd;
	}

	/**
	 * @return the allianceIdEnd
	 */
	public int getAllianceIdEnd() {
		return allianceIdEnd;
	}

	/**
	 * @param allianceIdEnd the allianceIdEnd to set
	 */
	public void setAllianceIdEnd(int allianceIdEnd) {
		this.allianceIdEnd = allianceIdEnd;
	}

	/**
	 * @return the playerNameEnd
	 */
	public String getPlayerNameEnd() {
		return playerNameEnd;
	}

	/**
	 * @param playerNameEnd the playerNameEnd to set
	 */
	public void setPlayerNameEnd(String playerNameEnd) {
		this.playerNameEnd = playerNameEnd;
	}

	/**
	 * @return the playerIdEnd
	 */
	public int getPlayerIdEnd() {
		return playerIdEnd;
	}

	/**
	 * @param playerIdEnd the playerIdEnd to set
	 */
	public void setPlayerIdEnd(int playerIdEnd) {
		this.playerIdEnd = playerIdEnd;
	}

	/**
	 * @return the castleNameEnd
	 */
	public String getCastleNameEnd() {
		return castleNameEnd;
	}

	/**
	 * @param castleNameEnd the castleNameEnd to set
	 */
	public void setCastleNameEnd(String castleNameEnd) {
		this.castleNameEnd = castleNameEnd;
	}

	/**
	 * @return the newlyJoinedAlliancePlayer
	 */
	public boolean isNewlyJoinedAlliancePlayer() {
		return newlyJoinedAlliancePlayer;
	}

	/**
	 * @param newlyJoinedAlliancePlayer the newlyJoinedAlliancePlayer to set
	 */
	public void setNewlyJoinedAlliancePlayer(boolean newlyJoinedAlliancePlayer) {
		this.newlyJoinedAlliancePlayer = newlyJoinedAlliancePlayer;
	}

	@Override
	public int compareTo(ReportCastleChangeData o) {
		return getLastUpdate().compareTo(o.getLastUpdate());
	}
	
	@Override
	public String toString() {
		return "From name: " +this.castleNameStart + " to: " + this.castleNameEnd + " (" + String.valueOf(this.id) + ")"; 
	}
	
}
