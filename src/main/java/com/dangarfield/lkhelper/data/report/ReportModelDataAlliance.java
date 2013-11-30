package com.dangarfield.lkhelper.data.report;

import java.util.List;

import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;

public class ReportModelDataAlliance {
	
	private List<CastleData> castleDatas;
	private List<PlayerData> playerDatas;
	private AllianceData allianceData;
	private String gameStatusForAlliance;
	
	private List<ReportCastleChangeData> newCastlesForAllianceMainPlayers;	
	private int newCastlesForAllianceMainPlayersCount;
	private List<ReportCastleChangeData> newCastlesForAllianceNewPlayers;	
	private int newCastlesForAllianceNewPlayersCount;
	
	private List<ReportCastleChangeData> lostCastlesForAllianceMainPlayers;	
	private int lostCastlesForAllianceMainPlayersCount;
	private List<ReportCastleChangeData> lostCastlesForAllianceExPlayers;	
	private int lostCastlesForAllianceExPlayersCount;
	
	private List<ReportPlayerData> inactiveList;
	private int inactiveListCount;
	private List<ReportPieChartData> inactivePieChart;
	
	private List<ReportPlayerData> newMembers;
	private int newMembersCount;
	private List<PlayerData> exMembers;
	private int exMembersCount;
	
	private List<HistoricNameData> historicAllianceNameDatas;
	private AllianceLeaderboardReport allianceLeaderboardReport;
	
	private String removeCompareUrl;
	
	/**
	 * 
	 */
	public ReportModelDataAlliance() {
		super();
	}
	/**
	 * @param castleDatas
	 * @param playerDatas
	 * @param allianceData
	 * @param gameStatusForAlliance
	 * @param newCastlesForAllianceMainPlayers
	 * @param newCastlesForAllianceMainPlayersCount
	 * @param newCastlesForAllianceNewPlayers
	 * @param newCastlesForAllianceNewPlayersCount
	 * @param lostCastlesForAllianceMainPlayers
	 * @param lostCastlesForAllianceMainPlayersCount
	 * @param lostCastlesForAllianceExPlayers
	 * @param lostCastlesForAllianceExPlayersCount
	 * @param inactiveList
	 * @param inactiveListCount
	 * @param inactivePieChart
	 * @param newMembers
	 * @param newMembersCount
	 * @param exMembers
	 * @param exMembersCount
	 * @param historicAllianceNameDatas
	 * @param allianceLeaderboardReport
	 */
	public ReportModelDataAlliance(List<CastleData> castleDatas,
			List<PlayerData> playerDatas, AllianceData allianceData,
			String gameStatusForAlliance,
			List<ReportCastleChangeData> newCastlesForAllianceMainPlayers,
			int newCastlesForAllianceMainPlayersCount,
			List<ReportCastleChangeData> newCastlesForAllianceNewPlayers,
			int newCastlesForAllianceNewPlayersCount,
			List<ReportCastleChangeData> lostCastlesForAllianceMainPlayers,
			int lostCastlesForAllianceMainPlayersCount,
			List<ReportCastleChangeData> lostCastlesForAllianceExPlayers,
			int lostCastlesForAllianceExPlayersCount,
			List<ReportPlayerData> inactiveList, int inactiveListCount,
			List<ReportPieChartData> inactivePieChart,
			List<ReportPlayerData> newMembers, int newMembersCount,
			List<PlayerData> exMembers, int exMembersCount,
			List<HistoricNameData> historicAllianceNameDatas,
			AllianceLeaderboardReport allianceLeaderboardReport) {
		super();
		this.castleDatas = castleDatas;
		this.playerDatas = playerDatas;
		this.allianceData = allianceData;
		this.gameStatusForAlliance = gameStatusForAlliance;
		this.newCastlesForAllianceMainPlayers = newCastlesForAllianceMainPlayers;
		this.newCastlesForAllianceMainPlayersCount = newCastlesForAllianceMainPlayersCount;
		this.newCastlesForAllianceNewPlayers = newCastlesForAllianceNewPlayers;
		this.newCastlesForAllianceNewPlayersCount = newCastlesForAllianceNewPlayersCount;
		this.lostCastlesForAllianceMainPlayers = lostCastlesForAllianceMainPlayers;
		this.lostCastlesForAllianceMainPlayersCount = lostCastlesForAllianceMainPlayersCount;
		this.lostCastlesForAllianceExPlayers = lostCastlesForAllianceExPlayers;
		this.lostCastlesForAllianceExPlayersCount = lostCastlesForAllianceExPlayersCount;
		this.inactiveList = inactiveList;
		this.inactiveListCount = inactiveListCount;
		this.inactivePieChart = inactivePieChart;
		this.newMembers = newMembers;
		this.newMembersCount = newMembersCount;
		this.exMembers = exMembers;
		this.exMembersCount = exMembersCount;
		this.historicAllianceNameDatas = historicAllianceNameDatas;
		this.allianceLeaderboardReport = allianceLeaderboardReport;
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
	 * @return the playerDatas
	 */
	public List<PlayerData> getPlayerDatas() {
		return playerDatas;
	}
	/**
	 * @param playerDatas the playerDatas to set
	 */
	public void setPlayerDatas(List<PlayerData> playerDatas) {
		this.playerDatas = playerDatas;
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
	 * @return the gameStatusForAlliance
	 */
	public String getGameStatusForAlliance() {
		return gameStatusForAlliance;
	}
	/**
	 * @param gameStatusForAlliance the gameStatusForAlliance to set
	 */
	public void setGameStatusForAlliance(String gameStatusForAlliance) {
		this.gameStatusForAlliance = gameStatusForAlliance;
	}
	/**
	 * @return the newCastlesForAllianceMainPlayers
	 */
	public List<ReportCastleChangeData> getNewCastlesForAllianceMainPlayers() {
		return newCastlesForAllianceMainPlayers;
	}
	/**
	 * @param newCastlesForAllianceMainPlayers the newCastlesForAllianceMainPlayers to set
	 */
	public void setNewCastlesForAllianceMainPlayers(
			List<ReportCastleChangeData> newCastlesForAllianceMainPlayers) {
		this.newCastlesForAllianceMainPlayers = newCastlesForAllianceMainPlayers;
	}
	/**
	 * @return the newCastlesForAllianceMainPlayersCount
	 */
	public int getNewCastlesForAllianceMainPlayersCount() {
		return newCastlesForAllianceMainPlayersCount;
	}
	/**
	 * @param newCastlesForAllianceMainPlayersCount the newCastlesForAllianceMainPlayersCount to set
	 */
	public void setNewCastlesForAllianceMainPlayersCount(
			int newCastlesForAllianceMainPlayersCount) {
		this.newCastlesForAllianceMainPlayersCount = newCastlesForAllianceMainPlayersCount;
	}
	/**
	 * @return the newCastlesForAllianceNewPlayers
	 */
	public List<ReportCastleChangeData> getNewCastlesForAllianceNewPlayers() {
		return newCastlesForAllianceNewPlayers;
	}
	/**
	 * @param newCastlesForAllianceNewPlayers the newCastlesForAllianceNewPlayers to set
	 */
	public void setNewCastlesForAllianceNewPlayers(
			List<ReportCastleChangeData> newCastlesForAllianceNewPlayers) {
		this.newCastlesForAllianceNewPlayers = newCastlesForAllianceNewPlayers;
	}
	/**
	 * @return the newCastlesForAllianceNewPlayersCount
	 */
	public int getNewCastlesForAllianceNewPlayersCount() {
		return newCastlesForAllianceNewPlayersCount;
	}
	/**
	 * @param newCastlesForAllianceNewPlayersCount the newCastlesForAllianceNewPlayersCount to set
	 */
	public void setNewCastlesForAllianceNewPlayersCount(
			int newCastlesForAllianceNewPlayersCount) {
		this.newCastlesForAllianceNewPlayersCount = newCastlesForAllianceNewPlayersCount;
	}
	/**
	 * @return the lostCastlesForAllianceMainPlayers
	 */
	public List<ReportCastleChangeData> getLostCastlesForAllianceMainPlayers() {
		return lostCastlesForAllianceMainPlayers;
	}
	/**
	 * @param lostCastlesForAllianceMainPlayers the lostCastlesForAllianceMainPlayers to set
	 */
	public void setLostCastlesForAllianceMainPlayers(
			List<ReportCastleChangeData> lostCastlesForAllianceMainPlayers) {
		this.lostCastlesForAllianceMainPlayers = lostCastlesForAllianceMainPlayers;
	}
	/**
	 * @return the lostCastlesForAllianceMainPlayersCount
	 */
	public int getLostCastlesForAllianceMainPlayersCount() {
		return lostCastlesForAllianceMainPlayersCount;
	}
	/**
	 * @param lostCastlesForAllianceMainPlayersCount the lostCastlesForAllianceMainPlayersCount to set
	 */
	public void setLostCastlesForAllianceMainPlayersCount(
			int lostCastlesForAllianceMainPlayersCount) {
		this.lostCastlesForAllianceMainPlayersCount = lostCastlesForAllianceMainPlayersCount;
	}
	/**
	 * @return the lostCastlesForAllianceExPlayers
	 */
	public List<ReportCastleChangeData> getLostCastlesForAllianceExPlayers() {
		return lostCastlesForAllianceExPlayers;
	}
	/**
	 * @param lostCastlesForAllianceExPlayers the lostCastlesForAllianceExPlayers to set
	 */
	public void setLostCastlesForAllianceExPlayers(
			List<ReportCastleChangeData> lostCastlesForAllianceExPlayers) {
		this.lostCastlesForAllianceExPlayers = lostCastlesForAllianceExPlayers;
	}
	/**
	 * @return the lostCastlesForAllianceExPlayersCount
	 */
	public int getLostCastlesForAllianceExPlayersCount() {
		return lostCastlesForAllianceExPlayersCount;
	}
	/**
	 * @param lostCastlesForAllianceExPlayersCount the lostCastlesForAllianceExPlayersCount to set
	 */
	public void setLostCastlesForAllianceExPlayersCount(
			int lostCastlesForAllianceExPlayersCount) {
		this.lostCastlesForAllianceExPlayersCount = lostCastlesForAllianceExPlayersCount;
	}
	/**
	 * @return the inactiveList
	 */
	public List<ReportPlayerData> getInactiveList() {
		return inactiveList;
	}
	/**
	 * @param inactiveList the inactiveList to set
	 */
	public void setInactiveList(List<ReportPlayerData> inactiveList) {
		this.inactiveList = inactiveList;
	}
	/**
	 * @return the inactiveListCount
	 */
	public int getInactiveListCount() {
		return inactiveListCount;
	}
	/**
	 * @param inactiveListCount the inactiveListCount to set
	 */
	public void setInactiveListCount(int inactiveListCount) {
		this.inactiveListCount = inactiveListCount;
	}
	/**
	 * @return the inactivePieChart
	 */
	public List<ReportPieChartData> getInactivePieChart() {
		return inactivePieChart;
	}
	/**
	 * @param inactivePieChart the inactivePieChart to set
	 */
	public void setInactivePieChart(List<ReportPieChartData> inactivePieChart) {
		this.inactivePieChart = inactivePieChart;
	}
	/**
	 * @return the newMembers
	 */
	public List<ReportPlayerData> getNewMembers() {
		return newMembers;
	}
	/**
	 * @param newMembers the newMembers to set
	 */
	public void setNewMembers(List<ReportPlayerData> newMembers) {
		this.newMembers = newMembers;
	}
	/**
	 * @return the newMembersCount
	 */
	public int getNewMembersCount() {
		return newMembersCount;
	}
	/**
	 * @param newMembersCount the newMembersCount to set
	 */
	public void setNewMembersCount(int newMembersCount) {
		this.newMembersCount = newMembersCount;
	}
	/**
	 * @return the exMembers
	 */
	public List<PlayerData> getExMembers() {
		return exMembers;
	}
	/**
	 * @param exMembers the exMembers to set
	 */
	public void setExMembers(List<PlayerData> exMembers) {
		this.exMembers = exMembers;
	}
	/**
	 * @return the exMembersCount
	 */
	public int getExMembersCount() {
		return exMembersCount;
	}
	/**
	 * @param exMembersCount the exMembersCount to set
	 */
	public void setExMembersCount(int exMembersCount) {
		this.exMembersCount = exMembersCount;
	}
	/**
	 * @return the historicAllianceNameDatas
	 */
	public List<HistoricNameData> getHistoricAllianceNameDatas() {
		return historicAllianceNameDatas;
	}
	/**
	 * @param historicAllianceNameDatas the historicAllianceNameDatas to set
	 */
	public void setHistoricAllianceNameDatas(
			List<HistoricNameData> historicAllianceNameDatas) {
		this.historicAllianceNameDatas = historicAllianceNameDatas;
	}
	/**
	 * @return the allianceLeaderboardReport
	 */
	public AllianceLeaderboardReport getAllianceLeaderboardReport() {
		return allianceLeaderboardReport;
	}
	/**
	 * @param allianceLeaderboardReport the allianceLeaderboardReport to set
	 */
	public void setAllianceLeaderboardReport(
			AllianceLeaderboardReport allianceLeaderboardReport) {
		this.allianceLeaderboardReport = allianceLeaderboardReport;
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
