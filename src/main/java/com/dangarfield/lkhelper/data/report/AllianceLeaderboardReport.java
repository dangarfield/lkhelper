package com.dangarfield.lkhelper.data.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllianceLeaderboardReport {

	private int playerCount;
	private List<AllianceLeaderboardData> points;
	private List<AllianceLeaderboardData> pointsGrowth;
	private List<AllianceLeaderboardData> castleCount;
	private List<AllianceLeaderboardData> castleCountGrowth;
	private List<AllianceLeaderboardData> rank;
	private List<AllianceLeaderboardData> rankGrowth;
	private List<AllianceLeaderboardData> allianceRank;
	private List<AllianceLeaderboardData> allianceRankGrowth;
	
	/**
	 * 
	 */
	public AllianceLeaderboardReport(final List<AllianceLeaderboardData> allianceLeaderboardDatas) {
		super();
		playerCount = allianceLeaderboardDatas.size();
		
		List<AllianceLeaderboardData> pointsList = copyList(allianceLeaderboardDatas);
		Collections.sort(pointsList, AllianceLeaderboardData.Points);
		points = pointsList;
		List<AllianceLeaderboardData> pointsGrowthList = copyList(allianceLeaderboardDatas);
		Collections.sort(pointsGrowthList, AllianceLeaderboardData.PointsGrowth);
		pointsGrowth = pointsGrowthList;

		List<AllianceLeaderboardData> castleCountList = copyList(allianceLeaderboardDatas);
		Collections.sort(castleCountList, AllianceLeaderboardData.CastleCount);
		castleCount = castleCountList;
		List<AllianceLeaderboardData> castleCountGrowthList = copyList(allianceLeaderboardDatas);
		Collections.sort(castleCountGrowthList, AllianceLeaderboardData.CastleCountGrowth);
		castleCountGrowth = castleCountGrowthList;

		List<AllianceLeaderboardData> rankList = copyList(allianceLeaderboardDatas);
		Collections.sort(rankList, AllianceLeaderboardData.Rank);
		rank = rankList;
		List<AllianceLeaderboardData> rankGrowthList = copyList(allianceLeaderboardDatas);
		Collections.sort(rankGrowthList, AllianceLeaderboardData.RankGrowth);
		rankGrowth = rankGrowthList;
		
		List<AllianceLeaderboardData> allianceRankList = copyList(allianceLeaderboardDatas);
		Collections.sort(allianceRankList, AllianceLeaderboardData.AllianceRank);
		allianceRank = allianceRankList;
		List<AllianceLeaderboardData> allianceRankGrowthList = copyList(allianceLeaderboardDatas);
		Collections.sort(allianceRankGrowthList, AllianceLeaderboardData.AllianceRankGrowth);
		allianceRankGrowth = allianceRankGrowthList;
	}
	
	private List<AllianceLeaderboardData> copyList(final List<AllianceLeaderboardData> allianceLeaderboardDatas) {
		final List<AllianceLeaderboardData> newAllianceLeaderboardData = new ArrayList<AllianceLeaderboardData>();
		for (AllianceLeaderboardData allianceLeaderboardData : allianceLeaderboardDatas) {
			newAllianceLeaderboardData.add(allianceLeaderboardData);
		}
		return newAllianceLeaderboardData;
	}
	/**
	 * @return the playerCount
	 */
	public int getPlayerCount() {
		return playerCount;
	}

	/**
	 * @param playerCount the playerCount to set
	 */
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	/**
	 * @return the points
	 */
	public List<AllianceLeaderboardData> getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(List<AllianceLeaderboardData> points) {
		this.points = points;
	}
	/**
	 * @return the pointsGrowth
	 */
	public List<AllianceLeaderboardData> getPointsGrowth() {
		return pointsGrowth;
	}
	/**
	 * @param pointsGrowth the pointsGrowth to set
	 */
	public void setPointsGrowth(List<AllianceLeaderboardData> pointsGrowth) {
		this.pointsGrowth = pointsGrowth;
	}
	/**
	 * @return the castleCount
	 */
	public List<AllianceLeaderboardData> getCastleCount() {
		return castleCount;
	}
	/**
	 * @param castleCount the castleCount to set
	 */
	public void setCastleCount(List<AllianceLeaderboardData> castleCount) {
		this.castleCount = castleCount;
	}
	/**
	 * @return the castleCountGrowth
	 */
	public List<AllianceLeaderboardData> getCastleCountGrowth() {
		return castleCountGrowth;
	}
	/**
	 * @param castleCountGrowth the castleCountGrowth to set
	 */
	public void setCastleCountGrowth(List<AllianceLeaderboardData> castleCountGrowth) {
		this.castleCountGrowth = castleCountGrowth;
	}
	/**
	 * @return the rank
	 */
	public List<AllianceLeaderboardData> getRank() {
		return rank;
	}
	/**
	 * @param rank the rank to set
	 */
	public void setRank(List<AllianceLeaderboardData> rank) {
		this.rank = rank;
	}
	/**
	 * @return the rankGrowth
	 */
	public List<AllianceLeaderboardData> getRankGrowth() {
		return rankGrowth;
	}
	/**
	 * @param rankGrowth the rankGrowth to set
	 */
	public void setRankGrowth(List<AllianceLeaderboardData> rankGrowth) {
		this.rankGrowth = rankGrowth;
	}
	/**
	 * @return the allianceRank
	 */
	public List<AllianceLeaderboardData> getAllianceRank() {
		return allianceRank;
	}
	/**
	 * @param allianceRank the allianceRank to set
	 */
	public void setAllianceRank(List<AllianceLeaderboardData> allianceRank) {
		this.allianceRank = allianceRank;
	}
	/**
	 * @return the allianceRankGrowth
	 */
	public List<AllianceLeaderboardData> getAllianceRankGrowth() {
		return allianceRankGrowth;
	}
	/**
	 * @param allianceRankGrowth the allianceRankGrowth to set
	 */
	public void setAllianceRankGrowth(
			List<AllianceLeaderboardData> allianceRankGrowth) {
		this.allianceRankGrowth = allianceRankGrowth;
	}
	
	
}
