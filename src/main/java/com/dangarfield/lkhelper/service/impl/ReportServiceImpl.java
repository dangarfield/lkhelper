package com.dangarfield.lkhelper.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.dangarfield.lkhelper.dao.CastleDAO;
import com.dangarfield.lkhelper.dao.PlayerDAO;
import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricPointsData;
import com.dangarfield.lkhelper.data.report.AllianceLeaderboardData;
import com.dangarfield.lkhelper.data.report.AllianceLeaderboardReport;
import com.dangarfield.lkhelper.data.report.AllianceCastleChangeReport;
import com.dangarfield.lkhelper.data.report.CastlePointsFrequencyReport;
import com.dangarfield.lkhelper.data.report.ReportCastleChangeData;
import com.dangarfield.lkhelper.data.report.ReportPieChartData;
import com.dangarfield.lkhelper.data.report.ReportPlayerData;
import com.dangarfield.lkhelper.service.ReportService;
import com.dangarfield.lkhelper.utils.GeneralUtils;

public class ReportServiceImpl implements ReportService {

	final private static int THRESHOLD_INACTIVE = 1;
	final private static int THRESHOLD_POSSIBLY_INACTIVE = 10;
	final private static int THRESHOLD_DAYS = 14;
	
	final private static int REPORT_HISTORIC_DATE = -7;
	
	@Autowired
	private CastleDAO castleDAO;
	@Autowired
	private PlayerDAO playerDAO;
	@Autowired
	private GeneralUtils generalUtils;

	
	@Override
	public List<CastlePointsFrequencyReport> createCastlePointsFrequecyReport(
			final List<CastleData> castleDatas) {

		final List<CastlePointsFrequencyReport> castlePointsFrequencyReport = new ArrayList<CastlePointsFrequencyReport>();
		final Map<String, Integer> reportMap = createEmptyPointsBandMapForCastlePointsFrequecyReport();

		for (CastleData castleData : castleDatas) {
			final Integer points = Integer.valueOf(castleData.getPoints());
			final String pointsBand = getPointsBandForCastlePointsFrequecyReport(points.intValue());

			final Integer previousFrequency = reportMap.get(pointsBand);
			final Integer newFrequency = previousFrequency + 1;
			reportMap.put(pointsBand, newFrequency);
		}

		for (Entry<String, Integer> entry : reportMap.entrySet()) {
			String pointsBand = entry.getKey();
			Integer frequency = entry.getValue();

			castlePointsFrequencyReport.add(new CastlePointsFrequencyReport(pointsBand, frequency));
		}

		Collections.sort(castlePointsFrequencyReport);

		return castlePointsFrequencyReport;
	}
	private Map<String, Integer> createEmptyPointsBandMapForCastlePointsFrequecyReport() {
		final Map<String, Integer> reportMap = new HashMap<String, Integer>();
		reportMap.put("0 - 50", 0);
		reportMap.put("51 - 100", 0);
		reportMap.put("101 - 150", 0);
		reportMap.put("151 - 200", 0);
		reportMap.put("201 - 250", 0);
		reportMap.put("251 - 287", 0);
		reportMap.put("288 max", 0);
		return reportMap;
	}
	private String getPointsBandForCastlePointsFrequecyReport(final int points) {
		if (0 <= points && points <= 50) {
			return "0 - 50";
		} else if (50 < points && points <= 100) {
			return "51 - 100";
		} else if (100 < points && points <= 150) {
			return "101 - 150";
		} else if (150 < points && points <= 200) {
			return "151 - 200";
		} else if (200 < points && points <= 250) {
			return "201 - 250";
		} else if (250 < points && points <= 287) {
			return "251 - 287";
		}
		return "288 max";
	}

	@Override
	public String createGameStatusForPlayer(final PlayerData playerData) {
		return calculateGameStatusFromHistoricPointsData(playerData.getHistoricPoints());
	}
	@Override
	public String createGameStatusForCastle(final CastleData castleData) {
		return calculateGameStatusFromHistoricPointsData(castleData.getHistoricPoints());
	}
	@Override
	public String createGameStatusForAlliance(final AllianceData allianceData) {
		return calculateGameStatusFromHistoricPointsData(allianceData.getHistoricPoints());
	}
	private String calculateGameStatusFromHistoricPointsData(final List<HistoricPointsData> historicPointsList) {
		final int beginPoints = historicPointsList.get(0).getPoints();
		int endPoints = 0;

		if (historicPointsList.size() <= THRESHOLD_DAYS) {
			endPoints = historicPointsList.get((historicPointsList.size() - 1)).getPoints();

		} else {
			endPoints = historicPointsList.get(THRESHOLD_DAYS-1).getPoints();
		}
		
		final int pointDifference = endPoints - beginPoints;
		
		if (pointDifference < 0) {
			return ReportPlayerData.LOSING_POINTS;
		} else if (pointDifference < THRESHOLD_INACTIVE) {
			return ReportPlayerData.INACTIVE;
		} else if (THRESHOLD_INACTIVE <= pointDifference && pointDifference < THRESHOLD_POSSIBLY_INACTIVE) {
			return ReportPlayerData.POSSIBLY_INACTIVE;
		} else {
			return ReportPlayerData.ACTIVE;
		}
	}
	@Override
	public List<ReportPlayerData> createInactiveReportForAlliance(final List<ReportPlayerData> reportPlayerDatas) {
		final List<ReportPlayerData> inactiveReport = new ArrayList<ReportPlayerData>();
		
		for (ReportPlayerData report : reportPlayerDatas) {
			if (!report.getGameStatus().equals(ReportPlayerData.ACTIVE)) {
				inactiveReport.add(report);
			}
		}
		Collections.sort(inactiveReport, ReportPlayerData.InactivityComparator); // with a different sorting comparator
		return inactiveReport;
	}
	@Override
	public List<ReportPieChartData> createInactivePieChartReportForAlliance(final List<ReportPlayerData> reportPlayerDatas) {
		
		final List<ReportPlayerData> active = new ArrayList<ReportPlayerData>();
		final List<ReportPlayerData> losingPoints = new ArrayList<ReportPlayerData>();
		final List<ReportPlayerData> inactive = new ArrayList<ReportPlayerData>();
		final List<ReportPlayerData> possiblyInactive = new ArrayList<ReportPlayerData>();
		
		for (ReportPlayerData report : reportPlayerDatas) {
			if (report.getGameStatus().equals(ReportPlayerData.ACTIVE)) {
				active.add(report);
			} else if (report.getGameStatus().equals(ReportPlayerData.LOSING_POINTS)) {
				losingPoints.add(report);
			} else if (report.getGameStatus().equals(ReportPlayerData.INACTIVE)) {
				inactive.add(report);
			} else if (report.getGameStatus().equals(ReportPlayerData.POSSIBLY_INACTIVE)) {
				possiblyInactive.add(report);
			}
		}
		
		final List<ReportPieChartData> inactivePieChart = new ArrayList<ReportPieChartData>();
		inactivePieChart.add(new ReportPieChartData(active.size(), ReportPlayerData.ACTIVE));
		inactivePieChart.add(new ReportPieChartData(losingPoints.size(), ReportPlayerData.LOSING_POINTS));
		inactivePieChart.add(new ReportPieChartData(inactive.size(), ReportPlayerData.INACTIVE));
		inactivePieChart.add(new ReportPieChartData(possiblyInactive.size(), ReportPlayerData.POSSIBLY_INACTIVE));
		return inactivePieChart;
	}
	@Override
	public List<ReportPlayerData> createNewPlayersReportForAlliance(final List<ReportPlayerData> reportPlayerDatas) {
		final List<ReportPlayerData> inactiveReport = new ArrayList<ReportPlayerData>();
		
		for (ReportPlayerData report : reportPlayerDatas) {
			if (report.getPreviousAlliance() != null) {
				//TODO: Check to see how recent the player joined else they are not a new player
				inactiveReport.add(report);
			}
		}
		Collections.sort(inactiveReport, ReportPlayerData.NewMembersComparator); // with a different sorting comparator
		return inactiveReport;
	}
	@Override
	public List<PlayerData> createExPlayersReportForAlliance(final String serverId, final int allianceId) {
		final Date reportCutoffDate = generalUtils.newDateForCurrentDayMinusDays(REPORT_HISTORIC_DATE);
		final List<PlayerData> reportExMembers = playerDAO.getExPlayersForAlliance(serverId, allianceId, reportCutoffDate);
		Collections.sort(reportExMembers, PlayerData.DescendingLastUpdateComparator);
		return reportExMembers;
	}

	@Override
	public List<ReportCastleChangeData> createNewCastlesReportForPlayer(final String serverId, final PlayerData playerData) {
		
		final Date reportCutoffDate = generalUtils.newDateForCurrentDayMinusDays(REPORT_HISTORIC_DATE);
		final List<ReportCastleChangeData> reportNewCastleDatas = castleDAO.getNewCastlesForPlayer(serverId, playerData, reportCutoffDate);
		//Collections.sort(reportNewCastleDatas, CastleData.DescendingLastUpdateComparator);
		return reportNewCastleDatas;
	}
	@Override
	public AllianceCastleChangeReport createNewCastlesReportForAlliance(final String serverId, final AllianceData allianceData, final List<ReportPlayerData> newMembers) {
		
		final Date reportCutoffDate = generalUtils.newDateForCurrentDayMinusDays(REPORT_HISTORIC_DATE);
		final List<ReportCastleChangeData> reportNewCastleDatas = castleDAO.getNewCastlesForAlliance(serverId, allianceData, reportCutoffDate);
		
		final List<ReportCastleChangeData> mainPlayers = new ArrayList<ReportCastleChangeData>();
		final List<ReportCastleChangeData> otherPlayers = new ArrayList<ReportCastleChangeData>();
		
		for (ReportCastleChangeData reportCastleChangeData : reportNewCastleDatas) {
			boolean isNewlyJoinedPlayer = false;
			for (ReportPlayerData newMember : newMembers) {
				if(newMember.getPlayerId() == reportCastleChangeData.getPlayerIdEnd()) {
					isNewlyJoinedPlayer = true;
				}
			}
			//Collections.sort(reportNewCastleDatas, CastleData.DescendingLastUpdateComparator);
			if(isNewlyJoinedPlayer) {
				otherPlayers.add(reportCastleChangeData);
			} else {
				mainPlayers.add(reportCastleChangeData);
			}
		}
		final AllianceCastleChangeReport castleChangeReport = new AllianceCastleChangeReport(mainPlayers, otherPlayers);

		return castleChangeReport;
	}

	@Override
	public List<ReportCastleChangeData> createLostCastlesReportForPlayer(final String serverId, final PlayerData playerData) {
		
		final Date reportCutoffDate = generalUtils.newDateForCurrentDayMinusDays(REPORT_HISTORIC_DATE);
		final List<ReportCastleChangeData> reportNewCastleDatas = castleDAO.getLostCastlesForPlayer(serverId, playerData, reportCutoffDate);
		//Collections.sort(reportNewCastleDatas, CastleData.DescendingLastUpdateComparator);
		return reportNewCastleDatas;
	}
	@Override
	public AllianceCastleChangeReport createLostCastlesReportForAlliance(final String serverId, final AllianceData allianceData, final List<PlayerData> exMembers) {
		
		final Date reportCutoffDate = generalUtils.newDateForCurrentDayMinusDays(REPORT_HISTORIC_DATE);
		final List<ReportCastleChangeData> reportLostCastleDatas = castleDAO.getLostCastlesForAlliance(serverId, allianceData, reportCutoffDate);
		
		final List<ReportCastleChangeData> mainPlayers = new ArrayList<ReportCastleChangeData>();
		final List<ReportCastleChangeData> otherPlayers = new ArrayList<ReportCastleChangeData>();
		
		for (ReportCastleChangeData reportCastleChangeData : reportLostCastleDatas) {
			boolean isRecentlyLeftPlayer = false;
			for (PlayerData exMember : exMembers) {
				if(exMember.getId() == reportCastleChangeData.getPlayerIdEnd()) {
					isRecentlyLeftPlayer = true;
				}
			}
			//Collections.sort(reportNewCastleDatas, CastleData.DescendingLastUpdateComparator);
			if(isRecentlyLeftPlayer) {
				otherPlayers.add(reportCastleChangeData);
			} else {
				mainPlayers.add(reportCastleChangeData);
			}
		}
		final AllianceCastleChangeReport castleChangeReport = new AllianceCastleChangeReport(mainPlayers, otherPlayers);

		return castleChangeReport;
	}

	@Override
	public AllianceLeaderboardReport createAllianceLeaderboardReport(final String serverId, final int allianceId) {
		final Date startDate = generalUtils.newDateForCurrentDayMinusDays(REPORT_HISTORIC_DATE);
		final Date endDate = generalUtils.newDateForDay();
		
		return createAllianceLeaderboardReport(serverId, allianceId, startDate, endDate);
	}
	
	@Override
	public AllianceLeaderboardReport createAllianceLeaderboardReport(final String serverId, final int allianceId, final Date startDate, final Date endDate) {
		final List<AllianceLeaderboardData> allianceLeaderboardData = playerDAO.getAllianceLeaderboardData(serverId, allianceId, startDate, endDate);
		final AllianceLeaderboardReport allianceLeaderboardReport = new AllianceLeaderboardReport(allianceLeaderboardData);
		return allianceLeaderboardReport;
	}
}
