package com.dangarfield.lkhelper.data.historic;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricCastleCountData implements Comparable<HistoricCastleCountData>{

	@JsonProperty("_id")
	private int _id;
	private int castleCount;
	private Date lastUpdate;
	
	/**
	 * 
	 */
	public HistoricCastleCountData() {
		super();
	}
	/**
	 * @param castleCount
	 * @param lastUpdate
	 */
	public HistoricCastleCountData(int castleCount, Date lastUpdate) {
		super();
		this.castleCount = castleCount;
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
		return String.valueOf(castleCount) + " - " + lastUpdate;
	}
	
	public int compareTo(HistoricCastleCountData o) {
	    return getLastUpdate().compareTo(o.getLastUpdate());
	}
}
