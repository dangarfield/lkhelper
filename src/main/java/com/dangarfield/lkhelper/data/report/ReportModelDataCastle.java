package com.dangarfield.lkhelper.data.report;

import java.util.List;

import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricAllianceData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;
import com.dangarfield.lkhelper.data.historic.HistoricPlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricPointsData;

public class ReportModelDataCastle {

	private CastleData castleData;
	private PlayerData playerData;
	private AllianceData allianceData;
	private List<HistoricPointsData> historicPointsDatas;
	private List<HistoricNameData> historicCastleNameDatas;
	private List<HistoricPlayerData> historicPlayerNameDatas;	
	private List<HistoricAllianceData> historicAllianceNameDatas;
	
	private String removeCompareUrl;
	
	/**
	 * 
	 */
	public ReportModelDataCastle() {
		super();
	}
	/**
	 * @param castleData
	 * @param playerData
	 * @param allianceData
	 * @param historicPointsDatas
	 * @param historicCastleNameDatas
	 * @param historicPlayerNameDatas
	 * @param historicAllianceNameDatas
	 */
	public ReportModelDataCastle(CastleData castleData, PlayerData playerData,
			AllianceData allianceData,
			List<HistoricPointsData> historicPointsDatas,
			List<HistoricNameData> historicCastleNameDatas,
			List<HistoricPlayerData> historicPlayerNameDatas,
			List<HistoricAllianceData> historicAllianceNameDatas) {
		super();
		this.castleData = castleData;
		this.playerData = playerData;
		this.allianceData = allianceData;
		this.historicPointsDatas = historicPointsDatas;
		this.historicCastleNameDatas = historicCastleNameDatas;
		this.historicPlayerNameDatas = historicPlayerNameDatas;
		this.historicAllianceNameDatas = historicAllianceNameDatas;
	}
	/**
	 * @return the castleData
	 */
	public CastleData getCastleData() {
		return castleData;
	}
	/**
	 * @param castleData the castleData to set
	 */
	public void setCastleData(CastleData castleData) {
		this.castleData = castleData;
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
	 * @return the historicPointsDatas
	 */
	public List<HistoricPointsData> getHistoricPointsDatas() {
		return historicPointsDatas;
	}
	/**
	 * @param historicPointsDatas the historicPointsDatas to set
	 */
	public void setHistoricPointsDatas(List<HistoricPointsData> historicPointsDatas) {
		this.historicPointsDatas = historicPointsDatas;
	}
	/**
	 * @return the historicCastleNameDatas
	 */
	public List<HistoricNameData> getHistoricCastleNameDatas() {
		return historicCastleNameDatas;
	}
	/**
	 * @param historicCastleNameDatas the historicCastleNameDatas to set
	 */
	public void setHistoricCastleNameDatas(
			List<HistoricNameData> historicCastleNameDatas) {
		this.historicCastleNameDatas = historicCastleNameDatas;
	}
	/**
	 * @return the historicPlayerNameDatas
	 */
	public List<HistoricPlayerData> getHistoricPlayerNameDatas() {
		return historicPlayerNameDatas;
	}
	/**
	 * @param historicPlayerNameDatas the historicPlayerNameDatas to set
	 */
	public void setHistoricPlayerNameDatas(
			List<HistoricPlayerData> historicPlayerNameDatas) {
		this.historicPlayerNameDatas = historicPlayerNameDatas;
	}
	/**
	 * @return the historicAllianceNameDatas
	 */
	public List<HistoricAllianceData> getHistoricAllianceNameDatas() {
		return historicAllianceNameDatas;
	}
	/**
	 * @param historicAllianceNameDatas the historicAllianceNameDatas to set
	 */
	public void setHistoricAllianceNameDatas(
			List<HistoricAllianceData> historicAllianceNameDatas) {
		this.historicAllianceNameDatas = historicAllianceNameDatas;
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
