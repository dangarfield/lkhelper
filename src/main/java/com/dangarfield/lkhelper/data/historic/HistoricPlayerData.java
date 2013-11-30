package com.dangarfield.lkhelper.data.historic;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricPlayerData implements Comparable<HistoricPlayerData>{

	@JsonProperty("_id")
	private int _id;
	private int playerId;
	private String playerName;
	private Date lastUpdate;
	
	/**
	 * 
	 */
	public HistoricPlayerData() {
		super();
	}
	/**
	 * @param playerId
	 * @param playerName
	 * @param lastUpdate
	 */
	public HistoricPlayerData(int playerId, String playerName, Date lastUpdate) {
		super();
		this.playerId = playerId;
		this.playerName = playerName;
		this.lastUpdate = lastUpdate;
	}
	/**
	 * @return the _id
	 */
	public int get_id() {
		return _id;
	}
	/**
	 * @param _id the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
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
	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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
	
	@Override
	public String toString() {
		return playerName + " - " + playerId + " - " + lastUpdate;
	}
	
	public int compareTo(HistoricPlayerData o) {
	    return getLastUpdate().compareTo(o.getLastUpdate());
	}
}
