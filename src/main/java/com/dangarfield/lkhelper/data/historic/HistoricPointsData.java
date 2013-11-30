package com.dangarfield.lkhelper.data.historic;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricPointsData implements Comparable<HistoricPointsData>{

	@JsonProperty("_id")
	private int _id;
	private int points;
	private Date lastUpdate;
	
	/**
	 * 
	 */
	public HistoricPointsData() {
		super();
	}
	/**
	 * @param points
	 * @param lastUpdate
	 */
	public HistoricPointsData(int points, Date lastUpdate) {
		super();
		this.points = points;
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
		return points + " - " + lastUpdate;
	}
	
	public int compareTo(HistoricPointsData o) {
	    return getLastUpdate().compareTo(o.getLastUpdate());
	}
}
