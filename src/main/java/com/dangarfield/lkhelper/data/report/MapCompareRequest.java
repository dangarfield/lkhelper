package com.dangarfield.lkhelper.data.report;

import java.util.List;

public class MapCompareRequest {

	private String type;
	private int id;
	private int thickness;
	private String color;
	private String textValue;
	private List<MapCoordinates> allCoords;
	private String path;
	private String removeURL;
	
	/**
	 * @param type
	 * @param id
	 * @param thickness
	 * @param color
	 */
	public MapCompareRequest(String type, int id, int thickness, String color) {
		super();
		this.type = type;
		this.id = id;
		this.thickness = thickness;
		this.color = color;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	/**
	 * @return the thickness
	 */
	public int getThickness() {
		return thickness;
	}
	/**
	 * @param thickness the thickness to set
	 */
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the textValue
	 */
	public String getTextValue() {
		return textValue;
	}
	/**
	 * @param textValue the textValue to set
	 */
	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	/**
	 * @return the allCoords
	 */
	public List<MapCoordinates> getAllCoords() {
		return allCoords;
	}
	/**
	 * @param allCoords the allCoords to set
	 */
	public void setAllCoords(List<MapCoordinates> allCoords) {
		this.allCoords = allCoords;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the removeURL
	 */
	public String getRemoveURL() {
		return removeURL;
	}
	/**
	 * @param removeURL the removeURL to set
	 */
	public void setRemoveURL(String removeURL) {
		this.removeURL = removeURL;
	}
	
}
