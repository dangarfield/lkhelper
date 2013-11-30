package com.dangarfield.lkhelper.data.historic;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricAllianceData implements Comparable<HistoricAllianceData>{

	@JsonProperty("_id")
	private int _id;
	private int allianceId;
	private String allianceName;
	private Date lastUpdate;
	
	/**
	 * 
	 */
	public HistoricAllianceData() {
		super();
	}
	/**
	 * @param allianceId
	 * @param allianceName
	 * @param lastUpdate
	 */
	public HistoricAllianceData(int allianceId, String allianceName, Date lastUpdate) {
		super();
		this.allianceId = allianceId;
		this.allianceName = allianceName;
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
	/**
	 * @return the allianceName
	 */
	public String getAllianceName() {
		return allianceName;
	}
	/**
	 * @param allianceName the allianceName to set
	 */
	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
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
		return allianceName + " - " + allianceId + " - " + lastUpdate;
	}
	
	public int compareTo(HistoricAllianceData o) {
	    return getLastUpdate().compareTo(o.getLastUpdate());
	}
}
