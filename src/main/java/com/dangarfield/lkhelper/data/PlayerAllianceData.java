package com.dangarfield.lkhelper.data;

import java.util.Date;

public class PlayerAllianceData extends AllianceData {

	private Date lastPlayerUpdate;

	
	public PlayerAllianceData() {
		super();
	}
	public PlayerAllianceData(int id) {
		super(id);
	}
	public PlayerAllianceData(String name) {
		super(name);
	}
	
	/**
	 * @return the lastPlayerUpdate
	 */
	public Date getLastPlayerUpdate() {
		return lastPlayerUpdate;
	}

	/**
	 * @param lastPlayerUpdate the lastPlayerUpdate to set
	 */
	public void setLastPlayerUpdate(Date lastPlayerUpdate) {
		this.lastPlayerUpdate = lastPlayerUpdate;
	}
}
