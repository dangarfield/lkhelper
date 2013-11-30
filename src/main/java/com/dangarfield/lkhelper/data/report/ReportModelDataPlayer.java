package com.dangarfield.lkhelper.data.report;

import java.util.List;

import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricAllianceData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;

public class ReportModelDataPlayer {
	
	private List<CastleData> castleDatas;
	private int castleDatasCount;
	private PlayerData playerData;
	private AllianceData allianceData;
	private List<CastlePointsFrequencyReport> castlePointsFrequencyReports;
	private String gameStatusForPlayer;
	

	private List<ReportCastleChangeData> newCastlesForPlayer;	
	private int newCastlesForPlayerCount;
	private List<ReportCastleChangeData> lostCastlesForPlayer;	
	private int lostCastlesForPlayerCount;
	
	private List<HistoricNameData> historicPlayerNameDatas;
	private List<HistoricAllianceData> historicPlayerAllianceDatas;
	
	private String removeCompareUrl;
	
	/**
	 * 
	 */
	public ReportModelDataPlayer() {
		super();
	}

	/**
	 * @param castleDatas
	 * @param castleDatasCount
	 * @param playerData
	 * @param allianceData
	 * @param castlePointsFrequencyReports
	 * @param gameStatusForPlayer
	 * @param newCastlesForPlayer
	 * @param newCastlesForPlayerCount
	 * @param lostCastlesForPlayer
	 * @param lostCastlesForPlayerCount
	 * @param historicPlayerNameDatas
	 * @param historicPlayerAllianceDatas
	 */
	public ReportModelDataPlayer(List<CastleData> castleDatas,
			int castleDatasCount, PlayerData playerData,
			AllianceData allianceData,
			List<CastlePointsFrequencyReport> castlePointsFrequencyReports,
			String gameStatusForPlayer,
			List<ReportCastleChangeData> newCastlesForPlayer,
			int newCastlesForPlayerCount,
			List<ReportCastleChangeData> lostCastlesForPlayer,
			int lostCastlesForPlayerCount,
			List<HistoricNameData> historicPlayerNameDatas,
			List<HistoricAllianceData> historicPlayerAllianceDatas) {
		super();
		this.castleDatas = castleDatas;
		this.castleDatasCount = castleDatasCount;
		this.playerData = playerData;
		this.allianceData = allianceData;
		this.castlePointsFrequencyReports = castlePointsFrequencyReports;
		this.gameStatusForPlayer = gameStatusForPlayer;
		this.newCastlesForPlayer = newCastlesForPlayer;
		this.newCastlesForPlayerCount = newCastlesForPlayerCount;
		this.lostCastlesForPlayer = lostCastlesForPlayer;
		this.lostCastlesForPlayerCount = lostCastlesForPlayerCount;
		this.historicPlayerNameDatas = historicPlayerNameDatas;
		this.historicPlayerAllianceDatas = historicPlayerAllianceDatas;
	}
	
	/**
	 * @return the castleDatas
	 */
	public List<CastleData> getCastleDatas() {
		return castleDatas;
	}
	/**
	 * @param castleDatas the castleDatas to set
	 */
	public void setCastleDatas(List<CastleData> castleDatas) {
		this.castleDatas = castleDatas;
	}
	/**
	 * @return the castleDatasCount
	 */
	public int getCastleDatasCount() {
		return castleDatasCount;
	}
	/**
	 * @param castleDatasCount the castleDatasCount to set
	 */
	public void setCastleDatasCount(int castleDatasCount) {
		this.castleDatasCount = castleDatasCount;
	}
	/**
	 * @return the playerData
	 */
	public PlayerData getPlayerData() {
		return playerData;
	}
	/**
	 * @param playerData the playerData to set
	 */
	public void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
	}
	/**
	 * @return the allianceData
	 */
	public AllianceData getAllianceData() {
		return allianceData;
	}
	/**
	 * @param allianceData the allianceData to set
	 */
	public void setAllianceData(AllianceData allianceData) {
		this.allianceData = allianceData;
	}
	/**
	 * @return the castlePointsFrequencyReports
	 */
	public List<CastlePointsFrequencyReport> getCastlePointsFrequencyReports() {
		return castlePointsFrequencyReports;
	}
	/**
	 * @param castlePointsFrequencyReports the castlePointsFrequencyReports to set
	 */
	public void setCastlePointsFrequencyReports(
			List<CastlePointsFrequencyReport> castlePointsFrequencyReports) {
		this.castlePointsFrequencyReports = castlePointsFrequencyReports;
	}
	/**
	 * @return the gameStatusForPlayer
	 */
	public String getGameStatusForPlayer() {
		return gameStatusForPlayer;
	}
	/**
	 * @param gameStatusForPlayer the gameStatusForPlayer to set
	 */
	public void setGameStatusForPlayer(String gameStatusForPlayer) {
		this.gameStatusForPlayer = gameStatusForPlayer;
	}
	/**
	 * @return the newCastlesForPlayer
	 */
	public List<ReportCastleChangeData> getNewCastlesForPlayer() {
		return newCastlesForPlayer;
	}
	/**
	 * @param newCastlesForPlayer the newCastlesForPlayer to set
	 */
	public void setNewCastlesForPlayer(
			List<ReportCastleChangeData> newCastlesForPlayer) {
		this.newCastlesForPlayer = newCastlesForPlayer;
	}
	/**
	 * @return the newCastlesForPlayerCount
	 */
	public int getNewCastlesForPlayerCount() {
		return newCastlesForPlayerCount;
	}
	/**
	 * @param newCastlesForPlayerCount the newCastlesForPlayerCount to set
	 */
	public void setNewCastlesForPlayerCount(int newCastlesForPlayerCount) {
		this.newCastlesForPlayerCount = newCastlesForPlayerCount;
	}
	/**
	 * @return the lostCastlesForPlayer
	 */
	public List<ReportCastleChangeData> getLostCastlesForPlayer() {
		return lostCastlesForPlayer;
	}
	/**
	 * @param lostCastlesForPlayer the lostCastlesForPlayer to set
	 */
	public void setLostCastlesForPlayer(
			List<ReportCastleChangeData> lostCastlesForPlayer) {
		this.lostCastlesForPlayer = lostCastlesForPlayer;
	}
	/**
	 * @return the lostCastlesForPlayerCount
	 */
	public int getLostCastlesForPlayerCount() {
		return lostCastlesForPlayerCount;
	}
	/**
	 * @param lostCastlesForPlayerCount the lostCastlesForPlayerCount to set
	 */
	public void setLostCastlesForPlayerCount(int lostCastlesForPlayerCount) {
		this.lostCastlesForPlayerCount = lostCastlesForPlayerCount;
	}
	/**
	 * @return the historicPlayerNameDatas
	 */
	public List<HistoricNameData> getHistoricPlayerNameDatas() {
		return historicPlayerNameDatas;
	}
	/**
	 * @param historicPlayerNameDatas the historicPlayerNameDatas to set
	 */
	public void setHistoricPlayerNameDatas(
			List<HistoricNameData> historicPlayerNameDatas) {
		this.historicPlayerNameDatas = historicPlayerNameDatas;
	}
	/**
	 * @return the historicPlayerAllianceDatas
	 */
	public List<HistoricAllianceData> getHistoricPlayerAllianceDatas() {
		return historicPlayerAllianceDatas;
	}
	/**
	 * @param historicPlayerAllianceDatas the historicPlayerAllianceDatas to set
	 */
	public void setHistoricPlayerAllianceDatas(
			List<HistoricAllianceData> historicPlayerAllianceDatas) {
		this.historicPlayerAllianceDatas = historicPlayerAllianceDatas;
	}

	/**
	 * @return the removeCompareUrl
	 */
	public String getRemoveCompareUrl() {
		return removeCompareUrl;
	}

	/**
	 * @param removeCompareUrl the removeCompareUrl to set
	 */
	public void setRemoveCompareUrl(String removeCompareUrl) {
		this.removeCompareUrl = removeCompareUrl;
	}
	
	

}
