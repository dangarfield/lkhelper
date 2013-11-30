package com.dangarfield.lkhelper.data.historic;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricNameData implements Comparable<HistoricNameData>{

	@JsonProperty("_id")
	private int _id;
	private String name;
	private Date lastUpdate;
	
	/**
	 * 
	 */
	public HistoricNameData() {
		super();
	}
	/**
	 * @param name
	 * @param lastUpdate
	 */
	public HistoricNameData(String name, Date lastUpdate) {
		super();
		this.name = name;
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
		return name + " - " + lastUpdate;
	}
	
	public int compareTo(HistoricNameData o) {
	    return getLastUpdate().compareTo(o.getLastUpdate());
	}
}
