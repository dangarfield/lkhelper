package com.dangarfield.lkhelper.exceptions;

@SuppressWarnings("serial")
public class EmptyTileException extends Exception {

	private int mapX;
	private int mapY;
	private int tileWidth;
	private int tileHeight;
	

	public EmptyTileException(String message) {
        super(message);
    }
	
	/**
	 * @param mapX
	 * @param mapY
	 * @param tileWidth
	 * @param tileHeight
	 */
	public EmptyTileException(String message, int mapX, int mapY, int tileWidth, int tileHeight ) {
		super(message);
		this.mapX = mapX;
		this.mapY = mapY;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	/**
	 * @return the mapX
	 */
	public int getMapX() {
		return mapX;
	}

	/**
	 * @param mapX the mapX to set
	 */
	public void setMapX(int mapX) {
		this.mapX = mapX;
	}

	/**
	 * @return the mapY
	 */
	public int getMapY() {
		return mapY;
	}

	/**
	 * @param mapY the mapY to set
	 */
	public void setMapY(int mapY) {
		this.mapY = mapY;
	}

	/**
	 * @return the tileHeight
	 */
	public int getTileHeight() {
		return tileHeight;
	}

	/**
	 * @param tileHeight the tileHeight to set
	 */
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	/**
	 * @return the tileWidth
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * @param tileWidth the tileWidth to set
	 */
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	
}
