package com.dangarfield.lkhelper.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResourcesData {
	
	@JsonProperty("_id")
	private int _id;
	private int wood;
	private int stone;
	private int ore;
	private int people;
	private int copper;
	private int silver;
	
	/**
	 * 
	 */
	public ResourcesData() {
		super();
	}
	
	/**
	 * @param wood
	 * @param stone
	 * @param ore
	 * @param people
	 * @param copper
	 * @param silver
	 */
	public ResourcesData(int wood, int stone, int ore, int people,
			int copper, int silver) {
		super();
		this.wood = wood;
		this.stone = stone;
		this.ore = ore;
		this.people = people;
		this.copper = copper;
		this.silver = silver;
	}
	/**
	 * @return the wood
	 */
	public int getWood() {
		return wood;
	}
	/**
	 * @param wood the wood to set
	 */
	public void setWood(int wood) {
		this.wood = wood;
	}
	/**
	 * @return the stone
	 */
	public int getStone() {
		return stone;
	}
	/**
	 * @param stone the stone to set
	 */
	public void setStone(int stone) {
		this.stone = stone;
	}
	/**
	 * @return the ore
	 */
	public int getOre() {
		return ore;
	}
	/**
	 * @param ore the ore to set
	 */
	public void setOre(int ore) {
		this.ore = ore;
	}
	/**
	 * @return the people
	 */
	public int getPeople() {
		return people;
	}
	/**
	 * @param people the people to set
	 */
	public void setPeople(int people) {
		this.people = people;
	}
	/**
	 * @return the copper
	 */
	public int getCopper() {
		return copper;
	}
	/**
	 * @param copper the copper to set
	 */
	public void setCopper(int copper) {
		this.copper = copper;
	}
	/**
	 * @return the silver
	 */
	public int getSilver() {
		return silver;
	}
	/**
	 * @param silver the silver to set
	 */
	public void setSilver(int silver) {
		this.silver = silver;
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
	
}
