package com.dangarfield.lkhelper.data.historic;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricCastleData implements Comparable<HistoricCastleData>{

	@JsonProperty("_id")
	private int _id;
	private int castleId;
	private String castleName;
	private Date lastUpdate;
	
	/**
	 * 
	 */
	public HistoricCastleData() {
		super();
	}
	/**
	 * @param castleId
	 * @param castleName
	 * @param lastUpdate
	 */
	public HistoricCastleData(int castleId, String castleName, Date lastUpdate) {
		super();
		this.castleId = castleId;
		this.castleName = castleName;
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
	 * @return the castleId
	 */
	public int getCastleId() {
		return castleId;
	}
	/**
	 * @param castleId the castleId to set
	 */
	public void setCastleId(int castleId) {
		this.castleId = castleId;
	}
	/**
	 * @return the castleName
	 */
	public String getCastleName() {
		return castleName;
	}
	/**
	 * @param castleName the castleName to set
	 */
	public void setCastleName(String castleName) {
		this.castleName = castleName;
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
		return castleName + " - " + castleId + " - " + lastUpdate;
	}
	
	public int compareTo(HistoricCastleData o) {
	    return getLastUpdate().compareTo(o.getLastUpdate());
	}
}
