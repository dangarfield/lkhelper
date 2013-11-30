package com.dangarfield.lkhelper.data.users;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerData {

	@JsonProperty("_id")
	private String id;
	private int code;
	private String name;
	private String url;
	private int centralX;
	private int centralY;
	private String country;
	private String timezone;
	private Date lastCaptured;
	
	/**
	 * 
	 */
	public ServerData() {
		super();
	}
	
	/**
	 * @param id
	 * @param name
	 * @param url
	 * @param centralX
	 * @param centralY
	 * @param country
	 * @param timezone
	 */
	public ServerData(String id, int code, String name, String url, int centralX,
			int centralY, String country, String timezone) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.url = url;
		this.centralX = centralX;
		this.centralY = centralY;
		this.country = country;
		this.timezone = timezone;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the centralX
	 */
	public int getCentralX() {
		return centralX;
	}

	/**
	 * @param centralX the centralX to set
	 */
	public void setCentralX(int centralX) {
		this.centralX = centralX;
	}

	/**
	 * @return the centralY
	 */
	public int getCentralY() {
		return centralY;
	}

	/**
	 * @param centralY the centralY to set
	 */
	public void setCentralY(int centralY) {
		this.centralY = centralY;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the lastCaptured
	 */
	public Date getLastCaptured() {
		return lastCaptured;
	}

	/**
	 * @param lastCaptured the lastCaptured to set
	 */
	public void setLastCaptured(Date lastCaptured) {
		this.lastCaptured = lastCaptured;
	}

	@Override
	public String toString() {
		if (name == null) {
			return "new empty server";
		}
		return name;
	}
	
}
