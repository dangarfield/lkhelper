package com.dangarfield.lkhelper.data.search;

public abstract class AbstractTypeaheadData {
	
	private String value;
	private int id;
	
	/**
	 * 
	 */
	public AbstractTypeaheadData() {
		super();
	}
	/**
	 * @param value
	 * @param name
	 */
	public AbstractTypeaheadData(String value, int id) {
		super();
		this.value = value;
		this.id = id;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
