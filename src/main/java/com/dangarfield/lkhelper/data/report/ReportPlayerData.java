package com.dangarfield.lkhelper.data.report;

import java.util.Comparator;
import java.util.Date;

import com.dangarfield.lkhelper.data.historic.HistoricAllianceData;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportPlayerData implements Comparable<ReportPlayerData> {

	final public static String ACTIVE = "active";
	final public static String LOSING_POINTS = "losing points";
	final public static String INACTIVE = "zero growth";
	final public static String POSSIBLY_INACTIVE = "possibly inactive";
	
	
	@JsonProperty("_id")
	private int _id;
	private int playerId;
	private String playerName;
	private int points;
	private String gameStatus;
	private HistoricAllianceData previousAlliance;
	private Date lastUpdate;

	/**
	 * 
	 */
	public ReportPlayerData() {
		super();
	}

	/**
	 * @param playerId
	 * @param playerName
	 * @param lastUpdate
	 */
	public ReportPlayerData(int playerId, String playerName, int points, String gameStatus, HistoricAllianceData previousAlliance, Date lastUpdate) {
		super();
		this.playerId = playerId;
		this.playerName = playerName;
		this.points = points;
		this.gameStatus = gameStatus;
		this.previousAlliance = previousAlliance;
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return the _id
	 */
	public int get_id() {
		return _id;
	}

	/**
	 * @param _id
	 *            the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}

	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId
	 *            the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName
	 *            the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the gameStatus
	 */
	public String getGameStatus() {
		return gameStatus;
	}

	/**
	 * @param gameStatus
	 *            the gameStatus to set
	 */
	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate
	 *            the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return the previousAlliance
	 */
	public HistoricAllianceData getPreviousAlliance() {
		return previousAlliance;
	}

	/**
	 * @param previousAlliance the previousAlliance to set
	 */
	public void setPreviousAlliance(HistoricAllianceData previousAlliance) {
		this.previousAlliance = previousAlliance;
	}

	public int compareTo(ReportPlayerData o) {
		return getLastUpdate().compareTo(o.getLastUpdate());
	}

	public static Comparator<ReportPlayerData> InactivityComparator = new Comparator<ReportPlayerData>() {

		public int compare(ReportPlayerData report1, ReportPlayerData report2) {

			Integer gameStatus1Priority = getPriority(report1.getGameStatus());
			Integer gameStatus2Priority = getPriority(report2.getGameStatus());
			
			return gameStatus1Priority.compareTo(gameStatus2Priority);

		}

		private Integer getPriority(final String gameStatus) {
			if (gameStatus.equals(LOSING_POINTS)) {
				return Integer.valueOf(1);
			} else if (gameStatus.equals(INACTIVE)) {
				return Integer.valueOf(2);
			} else if (gameStatus.equals(POSSIBLY_INACTIVE)) {
				return Integer.valueOf(3);
			} else {
				return Integer.valueOf(4);
			}
		}
	};

	public static Comparator<ReportPlayerData> NewMembersComparator = new Comparator<ReportPlayerData>() {

		public int compare(ReportPlayerData report1, ReportPlayerData report2) {

			Date previousJoinDate1 = report1.getPreviousAlliance().getLastUpdate();
			Date previousJoinDate2 = report2.getPreviousAlliance().getLastUpdate();
			
			return previousJoinDate2.compareTo(previousJoinDate1);

		}
	};
	public static Comparator<ReportPlayerData> PointsComparator = new Comparator<ReportPlayerData>() {

		public int compare(ReportPlayerData report1, ReportPlayerData report2) {

			Integer points1 = report1.getPoints();
			Integer points2 = report2.getPoints();
			
			return points2.compareTo(points1);

		}
	};
}
