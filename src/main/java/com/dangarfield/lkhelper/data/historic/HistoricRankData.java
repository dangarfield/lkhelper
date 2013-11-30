package com.dangarfield.lkhelper.data.historic;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricRankData implements Comparable<HistoricRankData>{

	@JsonProperty("_id")
	private int _id;
	private int rank;
	private Date lastUpdate;
	
	/**
	 * 
	 */
	public HistoricRankData() {
		super();
	}
	/**
	 * @param rank
	 * @param lastUpdate
	 */
	public HistoricRankData(int rank, Date lastUpdate) {
		super();
		this.rank = rank;
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
		return rank + " - " + lastUpdate;
	}
	
	public int compareTo(HistoricRankData o) {
	    return getLastUpdate().compareTo(o.getLastUpdate());
	}
}
