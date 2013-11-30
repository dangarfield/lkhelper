package com.dangarfield.lkhelper.data.historic;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricMemberCountData implements Comparable<HistoricMemberCountData>{

	@JsonProperty("_id")
	private int _id;
	private int memberCount;
	private Date lastUpdate;
	
	/**
	 * 
	 */
	public HistoricMemberCountData() {
		super();
	}
	/**
	 * @param memberCount
	 * @param lastUpdate
	 */
	public HistoricMemberCountData(int memberCount, Date lastUpdate) {
		super();
		this.memberCount = memberCount;
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
	 * @return the memberCount
	 */
	public int getMemberCount() {
		return memberCount;
	}
	/**
	 * @param rank the rank to set
	 */
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
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
		return memberCount + " - " + lastUpdate;
	}
	
	public int compareTo(HistoricMemberCountData o) {
	    return getLastUpdate().compareTo(o.getLastUpdate());
	}
}
