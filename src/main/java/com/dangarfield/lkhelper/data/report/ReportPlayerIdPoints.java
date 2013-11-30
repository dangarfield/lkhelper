package com.dangarfield.lkhelper.data.report;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportPlayerIdPoints {

	@JsonProperty("_id")
	private int playerId;
	@JsonProperty("castlePointsCount")
	private int points;
	@JsonProperty("castleCount")
	private int castles;
	
	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}
	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	/**
	 * @return the castles
	 */
	public int getCastles() {
		return castles;
	}
	/**
	 * @param castles the castles to set
	 */
	public void setCastles(int castles) {
		this.castles = castles;
	}
}
