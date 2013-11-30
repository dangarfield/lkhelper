package com.dangarfield.lkhelper.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TroopsData {
	
	@JsonProperty("_id")
	private int _id;
	private int spearman;
	private int swordsman;
	private int archer;
	private int crossbowman;
	private int armouredHorse;
	private int lancer;
	private int cart;
	private int oxcart;
	
	/**
	 * 
	 */
	public TroopsData() {
		super();
	}
	/**
	 * @param spearman
	 * @param swordsman
	 * @param archer
	 * @param crossbowman
	 * @param armouredHorse
	 * @param lancer
	 * @param cart
	 * @param oxcart
	 */
	public TroopsData(int spearman, int swordsman, int archer,
			int crossbowman, int armouredHorse, int lancer,
			int cart, int oxcart) {
		super();
		this.spearman = spearman;
		this.swordsman = swordsman;
		this.archer = archer;
		this.crossbowman = crossbowman;
		this.armouredHorse = armouredHorse;
		this.lancer = lancer;
		this.cart = cart;
		this.oxcart = oxcart;
	}
	/**
	 * @return the spearman
	 */
	public int getSpearman() {
		return spearman;
	}
	/**
	 * @param spearman the spearman to set
	 */
	public void setSpearman(int spearman) {
		this.spearman = spearman;
	}
	/**
	 * @param spearman the spearman to add
	 */
	public void addSpearman(int spearman) {
		this.spearman = this.spearman + spearman;
	}
	/**
	 * @return the swordsman
	 */
	public int getSwordsman() {
		return swordsman;
	}
	/**
	 * @param swordsman the swordsman to set
	 */
	public void setSwordsman(int swordsman) {
		this.swordsman = swordsman;
	}
	/**
	 * @param swordsman the swordsman to add
	 */
	public void addSwordsman(int swordsman) {
		this.swordsman = this.swordsman + swordsman;
	}
	/**
	 * @return the archer
	 */
	public int getArcher() {
		return archer;
	}
	/**
	 * @param archer the archer to set
	 */
	public void setArcher(int archer) {
		this.archer = archer;
	}
	/**
	 * @param archer the archer to add
	 */
	public void addArcher(int archer) {
		this.archer = this.archer + archer;
	}
	/**
	 * @return the crossbowman
	 */
	public int getCrossbowman() {
		return crossbowman;
	}
	/**
	 * @param crossbowman the crossbowman to set
	 */
	public void setCrossbowman(int crossbowman) {
		this.crossbowman = crossbowman;
	}
	/**
	 * @param crossbowman the crossbowman to add
	 */
	public void addCrossbowman(int crossbowman) {
		this.crossbowman = this.crossbowman + crossbowman;
	}
	/**
	 * @return the armouredHorse
	 */
	public int getArmouredHorse() {
		return armouredHorse;
	}
	/**
	 * @param armouredHorse the armouredHorse to set
	 */
	public void setArmouredHorse(int armouredHorse) {
		this.armouredHorse = armouredHorse;
	}
	/**
	 * @param armouredHorse the armouredHorse to add
	 */
	public void addArmouredHorse(int armouredHorse) {
		this.armouredHorse = this.armouredHorse + armouredHorse;
	}
	/**
	 * @return the lancer
	 */
	public int getLancer() {
		return lancer;
	}
	/**
	 * @param lancer the lancer to set
	 */
	public void setLancer(int lancer) {
		this.lancer = lancer;
	}
	/**
	 * @param lancer the lancer to add
	 */
	public void addLancer(int lancer) {
		this.lancer = this.lancer + lancer;
	}
	/**
	 * @return the cart
	 */
	public int getCart() {
		return cart;
	}
	/**
	 * @param cart the cart to set
	 */
	public void setCart(int cart) {
		this.cart = cart;
	}
	/**
	 * @param cart the cart to add
	 */
	public void addCart(int cart) {
		this.cart = this.cart + cart;
	}
	/**
	 * @return the oxcart
	 */
	public int getOxcart() {
		return oxcart;
	}
	/**
	 * @param oxcart the oxcart to set
	 */
	public void setOxcart(int oxcart) {
		this.oxcart = oxcart;
	}
	/**
	 * @param oxcart the oxcart to add
	 */
	public void addOxcart(int oxcart) {
		this.oxcart = this.oxcart + oxcart;
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
