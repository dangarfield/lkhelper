package com.dangarfield.lkhelper.data.report;

import java.util.List;

public class AllianceCastleChangeReport {

	private List<ReportCastleChangeData> mainPlayers;
	private List<ReportCastleChangeData> otherPlayers;
	
	/**
	 * @param mainPlayers
	 * @param otherPlayers
	 */
	public AllianceCastleChangeReport(List<ReportCastleChangeData> mainPlayers,
			List<ReportCastleChangeData> otherPlayers) {
		super();
		this.mainPlayers = mainPlayers;
		this.otherPlayers = otherPlayers;
	}

	/**
	 * @return the mainPlayers
	 */
	public List<ReportCastleChangeData> getMainPlayers() {
		return mainPlayers;
	}

	/**
	 * @param mainPlayers the mainPlayers to set
	 */
	public void setMainPlayers(List<ReportCastleChangeData> mainPlayers) {
		this.mainPlayers = mainPlayers;
	}

	/**
	 * @return the otherPlayers
	 */
	public List<ReportCastleChangeData> getOtherPlayers() {
		return otherPlayers;
	}

	/**
	 * @param otherPlayers the otherPlayers to set
	 */
	public void setOtherPlayers(List<ReportCastleChangeData> otherPlayers) {
		this.otherPlayers = otherPlayers;
	}
	
}
