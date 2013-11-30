package com.dangarfield.lkhelper.service;

import java.util.Date;
import java.util.List;

import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.report.AllianceLeaderboardReport;
import com.dangarfield.lkhelper.data.report.AllianceCastleChangeReport;
import com.dangarfield.lkhelper.data.report.CastlePointsFrequencyReport;
import com.dangarfield.lkhelper.data.report.ReportCastleChangeData;
import com.dangarfield.lkhelper.data.report.ReportPieChartData;
import com.dangarfield.lkhelper.data.report.ReportPlayerData;

public interface ReportService {

	List<CastlePointsFrequencyReport> createCastlePointsFrequecyReport(final List<CastleData> castleDatas);

	String createGameStatusForPlayer(final PlayerData playerData);
	String createGameStatusForCastle(final CastleData castleData);
	String createGameStatusForAlliance(final AllianceData allianceData);
	
	List<ReportPlayerData> createInactiveReportForAlliance(final List<ReportPlayerData> reportPlayerDatas);
	List<ReportPieChartData> createInactivePieChartReportForAlliance(final List<ReportPlayerData> reportPlayerDatas);
	List<ReportPlayerData> createNewPlayersReportForAlliance(final List<ReportPlayerData> reportPlayerDatas);
	List<PlayerData> createExPlayersReportForAlliance(final String serverId, final int allianceId);
	
	List<ReportCastleChangeData> createNewCastlesReportForPlayer(final String serverId, final PlayerData playerData);
	AllianceCastleChangeReport createNewCastlesReportForAlliance(final String serverId, final AllianceData allianceData, final List<ReportPlayerData> newMembers);
	
	List<ReportCastleChangeData> createLostCastlesReportForPlayer(final String serverId, final PlayerData playerData);
	AllianceCastleChangeReport createLostCastlesReportForAlliance(final String serverId, final AllianceData allianceData, final List<PlayerData> exMembers);
	
	AllianceLeaderboardReport createAllianceLeaderboardReport(final String serverId, final int allianceId);
	AllianceLeaderboardReport createAllianceLeaderboardReport(final String serverId, final int allianceId, final Date startDate, final Date endDate);
}
