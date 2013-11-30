package com.dangarfield.lkhelper.data;

import java.util.Date;

public class PlayerCastleData extends CastleData {
	
	private ResourcesData resources;
	private TroopsData troops;
	private TroopsData externalDefendingtroops;
	private Date lastPlayerUpdate;


	public PlayerCastleData() {
		super();
		this.resources = new ResourcesData();
		this.troops = new TroopsData();
		this.externalDefendingtroops = new TroopsData();
	}
	public PlayerCastleData(int id) {
		super(id);
		this.resources = new ResourcesData();
		this.troops = new TroopsData();
		this.externalDefendingtroops = new TroopsData();
	}
	public PlayerCastleData(String name) {
		super(name);
		this.resources = new ResourcesData();
		this.troops = new TroopsData();
		this.externalDefendingtroops = new TroopsData();
	}
	
	/**
	 * @param resources
	 * @param troops
	 * @param externalDefendingtroops
	 */
	public PlayerCastleData(ResourcesData resources, TroopsData troops,
			TroopsData externalDefendingtroops) {
		super();
		this.resources = resources;
		this.troops = troops;
		this.externalDefendingtroops = externalDefendingtroops;
	}
	/**
	 * @return the resources
	 */
	public ResourcesData getResources() {
		return resources;
	}
	/**
	 * @param resources the resources to set
	 */
	public void setResources(ResourcesData resources) {
		this.resources = resources;
	}
	/**
	 * @return the troops
	 */
	public TroopsData getTroops() {
		return troops;
	}
	/**
	 * @param troops the troops to set
	 */
	public void setTroops(TroopsData troops) {
		this.troops = troops;
	}
	/**
	 * @return the externalDefendingtroops
	 */
	public TroopsData getExternalDefendingtroops() {
		return externalDefendingtroops;
	}
	/**
	 * @param externalDefendingtroops the externalDefendingtroops to set
	 */
	public void setExternalDefendingtroops(TroopsData externalDefendingtroops) {
		this.externalDefendingtroops = externalDefendingtroops;
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
