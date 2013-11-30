package com.dangarfield.lkhelper.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dangarfield.lkhelper.constants.ControllerConstants;
import com.dangarfield.lkhelper.dao.AllianceDAO;
import com.dangarfield.lkhelper.dao.CastleDAO;
import com.dangarfield.lkhelper.dao.PlayerDAO;
import com.dangarfield.lkhelper.dao.ServerDAO;
import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricAllianceData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;
import com.dangarfield.lkhelper.data.historic.HistoricPlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricPointsData;
import com.dangarfield.lkhelper.data.report.AllianceCastleChangeReport;
import com.dangarfield.lkhelper.data.report.AllianceLeaderboardReport;
import com.dangarfield.lkhelper.data.report.CastlePointsFrequencyReport;
import com.dangarfield.lkhelper.data.report.ReportCastleChangeData;
import com.dangarfield.lkhelper.data.report.ReportModelDataAlliance;
import com.dangarfield.lkhelper.data.report.ReportModelDataCastle;
import com.dangarfield.lkhelper.data.report.ReportModelDataPlayer;
import com.dangarfield.lkhelper.data.report.ReportPieChartData;
import com.dangarfield.lkhelper.data.report.ReportPlayerData;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.exceptions.LKServerNotFoundException;
import com.dangarfield.lkhelper.service.ReportService;
import com.dangarfield.lkhelper.utils.GeneralUtils;


@Controller
public class ReportController extends AbstractLKController {

	@Autowired
	protected CastleDAO castleDAO;
	@Autowired
	protected AllianceDAO allianceDAO;
	@Autowired
	protected PlayerDAO playerDAO;
	@Autowired
	protected ServerDAO serverDAO;
	@Autowired
	protected ReportService reportService;
	@Autowired
	protected GeneralUtils generalUtils;
	
	private static final int GRAPH_REPORT_DAYS = 7;
	
	@RequestMapping(value=ControllerConstants.URL.Report.ReportHome, method = RequestMethod.GET)
	public String reportHome(Model model, Principal principal) throws LKServerNotFoundException {
		final UserData userData = getUserData(principal);
		final int playerId = userData.getPlayerId();
		final String serverId = userData.getServerId();
		final ServerData serverData = serverDAO.getServerDataForId(serverId);
		model.addAttribute("serverData", serverData);
		
		// Compare example - rival players in your alliance
		PlayerData playerData = playerDAO.getPlayerForId(serverId, playerId);
		final List<PlayerData> rivalPlayers = playerDAO.getRivalPlayersInAlliance(serverId, playerData);
		List<String> rivalPlayersIds = new ArrayList<String>();
		for (PlayerData rivalPlayer : rivalPlayers) {
			rivalPlayersIds.add(String.valueOf(rivalPlayer.getId()));
		}
		final String rivalPlayersComparePath = StringUtils.join(rivalPlayersIds,ControllerConstants.URL.Report.CompareDivider);
		model.addAttribute("rivalPlayersComparePath", rivalPlayersComparePath);
		
		// Compare example - rival alliances 
		AllianceData allianceData = allianceDAO.getAllianceForId(serverId, playerData.getAllianceId());
		final List<AllianceData> rivalAlliances = allianceDAO.getRivalAlliances(serverId, allianceData);
		List<String> rivalAlliancesIds = new ArrayList<String>();
		for (AllianceData rivalAlliance : rivalAlliances) {
			rivalAlliancesIds.add(String.valueOf(rivalAlliance.getId()));
		}
		final String rivalAlliancesComparePath = StringUtils.join(rivalAlliancesIds,ControllerConstants.URL.Report.CompareDivider);
		model.addAttribute("rivalAlliancesComparePath", rivalAlliancesComparePath);
		
		// Compare example - rival castles created at a similar time to you
		List<CastleData> castleDatas = castleDAO.getCastlesForPlayer(serverId, playerId);
		CastleData castleData = castleDatas.iterator().next();
		final List<CastleData> rivalCastles = castleDAO.getRivalCastles(serverId, castleData);
		List<String> rivalCastlesIds = new ArrayList<String>();
		for (CastleData rivalCastle : rivalCastles) {
			rivalCastlesIds.add(String.valueOf(rivalCastle.getId()));
		}
		final String rivalCastlesComparePath = StringUtils.join(rivalCastlesIds,ControllerConstants.URL.Report.CompareDivider);
		model.addAttribute("rivalCastlesComparePath", rivalCastlesComparePath);
		
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Reports");
		
		return ControllerConstants.Views.Report.ReportHome;
	}

	
	

	
	/* PLAYERS */
	
	@RequestMapping(value=ControllerConstants.URL.Report.OwnPlayerReportScorecard, method = RequestMethod.GET)
	public String playerReportOwnScorecard(Model model, Principal principal) throws LKServerNotFoundException {
		final UserData userData = getUserData(principal);
		final int playerId = userData.getPlayerId();
		final String serverId = userData.getServerId();
		return playerReportScorecard(model, serverId, playerId);
	}
		
	@RequestMapping(value=ControllerConstants.URL.Report.PlayerReportScorecard, method = RequestMethod.GET)
	public String playerReportScorecard(Model model, @PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId, @PathVariable(ControllerConstants.URL.PathVariable.PlayerID) int playerId) throws LKServerNotFoundException {
		//TODO need to check that the current user is allowed to access this report
		
		final ServerData serverData = serverDAO.getServerDataForId(serverId);
		
		final ReportModelDataPlayer reportModelPlayerData = createReportModelDataForPlayer(serverId,playerId);
		
		model.addAttribute("serverData", serverData);
		model.addAttribute("reportModelPlayerData", reportModelPlayerData);
		model.addAttribute("currentCompare", reportModelPlayerData.getPlayerData().getId());
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Player Report for " + reportModelPlayerData.getPlayerData().getName());
		return ControllerConstants.Views.Report.PlayerScorecard;
	}
	
	@RequestMapping(value=ControllerConstants.URL.Report.PlayerReportCompare, method = RequestMethod.GET)
	public String comparePlayers(Model model, Principal principal,
			 @PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			 @PathVariable(ControllerConstants.URL.PathVariable.CompareItems) String compareItems) throws LKServerNotFoundException {
		
		final ServerData serverData = serverDAO.getServerDataForId(serverId);
		
		final List<Integer> compareIDs = new ArrayList<Integer>();
		final List<ReportModelDataPlayer> reportModelPlayerDatas = new ArrayList<ReportModelDataPlayer>();
		
		try {
			final List<String> stringIdSplitList = Arrays.asList(compareItems.split(ControllerConstants.URL.Report.CompareDivider));
			for (String stringId : stringIdSplitList) {
				final Integer integerId = Integer.valueOf(stringId);
				compareIDs.add(integerId);
			}
		} catch (Exception e) {
			//TODO: do something with catching exceptions and displaying output
			return ControllerConstants.Views.Report.PlayerCompare;
		}
		if (compareIDs.size() == 1) {
			return "redirect:" + ControllerConstants.URL.Report.OwnPlayerReportScorecard + "/" + serverId + "/" + compareIDs.iterator().next().intValue();
		}
		
		for (Integer compareID : compareIDs) {
			final ReportModelDataPlayer reportModelPlayerData = createReportModelDataForPlayer(serverId, compareID.intValue());
			reportModelPlayerDatas.add(reportModelPlayerData);
		}
		for (ReportModelDataPlayer reportModelDataPlayer : reportModelPlayerDatas) {
			List<Integer> removeCompareUrlList = new ArrayList<Integer>();
			for (Integer compareID : compareIDs) {
				if(!compareID.equals(Integer.valueOf(reportModelDataPlayer.getPlayerData().getId()))) {
					removeCompareUrlList.add(compareID);
				}
			}
			final String removeCompareUrl = StringUtils.join(removeCompareUrlList,ControllerConstants.URL.Report.CompareDivider);
			reportModelDataPlayer.setRemoveCompareUrl(removeCompareUrl);
		}
		
		final Map<String, String> mapMap = createCompareMapImagePathForPlayers(reportModelPlayerDatas);
		
		model.addAttribute("serverData", serverData);
		model.addAttribute("reportModelPlayerDatas", reportModelPlayerDatas);
		model.addAttribute("mapMap", mapMap);
		
		final StringBuilder pageTitle = new StringBuilder();
		pageTitle.append(PAGE_TITLE_PREPEND).append("Comparing Players");
		for (ReportModelDataPlayer reportModelPlayerData : reportModelPlayerDatas) {
			pageTitle.append(" - ").append(reportModelPlayerData.getPlayerData().getName());
		}
		model.addAttribute("currentCompare", compareItems);
		
		model.addAttribute("pageTitle", pageTitle);
		
		return ControllerConstants.Views.Report.PlayerCompare;
	}
	
	private ReportModelDataPlayer createReportModelDataForPlayer(final String serverId, final int playerId) {
		
		final List<CastleData> castleDatas = castleDAO.getCastlesForPlayer(serverId, playerId);
		final int castleDatasCount = castleDatas.size();
		final PlayerData playerData = playerDAO.getPlayerForId(serverId, playerId);
		playerData.setNormalisedName(generalUtils.normaliseString(playerData.getName()));
		
		playerData.setHistoricPoints(limitItemsInList(playerData.getHistoricPoints()));
		playerData.setHistoricRank(limitItemsInList(playerData.getHistoricRank()));
		playerData.setHistoricCastleCount(limitItemsInList(playerData.getHistoricCastleCount()));
		playerData.setHistoricAllianceRank(limitItemsInList(playerData.getHistoricAllianceRank()));
		
		final AllianceData allianceData = allianceDAO.getAllianceForId(serverId, playerData.getAllianceId());
		
		
		final List<CastlePointsFrequencyReport> castlePointsFrequencyReports = reportService.createCastlePointsFrequecyReport(castleDatas);
		final String gameStatusForPlayer = reportService.createGameStatusForPlayer(playerData);
		
		final List<ReportCastleChangeData> newCastlesForPlayer = reportService.createNewCastlesReportForPlayer(serverId, playerData);
		final int newCastlesForPlayerCount = newCastlesForPlayer.size();
		final List<ReportCastleChangeData> lostCastlesForPlayer = reportService.createLostCastlesReportForPlayer(serverId, playerData);
		final int lostCastlesForPlayerCount = lostCastlesForPlayer.size();
		
		List<HistoricNameData> historicPlayerNameDatas = playerData.getHistoricPlayerName();
		Collections.reverse(historicPlayerNameDatas);
		
		List<HistoricAllianceData> historicPlayerAllianceDatas = playerData.getHistoricAllianceId();
		Collections.reverse(historicPlayerAllianceDatas);
		
		final ReportModelDataPlayer reportModelPlayerData = new ReportModelDataPlayer(castleDatas, castleDatasCount, playerData, allianceData, castlePointsFrequencyReports, gameStatusForPlayer, newCastlesForPlayer, newCastlesForPlayerCount, lostCastlesForPlayer, lostCastlesForPlayerCount, historicPlayerNameDatas, historicPlayerAllianceDatas);
		
		return reportModelPlayerData;
	}
	

	
	/* ALLIANCES */
	
	@RequestMapping(value=ControllerConstants.URL.Report.OwnAllianceReportScorecard, method = RequestMethod.GET)
	public String allianceReportOwnScorecard(Model model, Principal principal) throws LKServerNotFoundException {
		final UserData userData = getUserData(principal);
		final String serverId = userData.getServerId();
		return allianceReportScorecard(model, serverId, userData.getAllianceId());
	}
		
	@RequestMapping(value=ControllerConstants.URL.Report.AllianceReportScorecard, method = RequestMethod.GET)
	public String allianceReportScorecard(Model model, @PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId, @PathVariable(ControllerConstants.URL.PathVariable.AllianceID) int allianceId) throws LKServerNotFoundException {
		//TODO need to check that the current user is allowed to access this report
		
		final ServerData serverData = serverDAO.getServerDataForId(serverId);
		
		final ReportModelDataAlliance reportModelAllianceData = createReportModelDataForAlliance(serverId, allianceId);
		
		model.addAttribute("serverData", serverData);
		model.addAttribute("reportModelAllianceData", reportModelAllianceData);
		model.addAttribute("currentCompare", reportModelAllianceData.getAllianceData().getId());
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Alliance Report for " + reportModelAllianceData.getAllianceData().getName());
		
		return ControllerConstants.Views.Report.AllianceScorecard;
	}
	

	@RequestMapping(value=ControllerConstants.URL.Report.AllianceReportCompare, method = RequestMethod.GET)
	public String compareAlliances(Model model, Principal principal,
			 @PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			 @PathVariable(ControllerConstants.URL.PathVariable.CompareItems) String compareItems) throws LKServerNotFoundException {
		
		final ServerData serverData = serverDAO.getServerDataForId(serverId);
		
		final List<Integer> compareIDs = new ArrayList<Integer>();
		final List<ReportModelDataAlliance> reportModelAllianceDatas = new ArrayList<ReportModelDataAlliance>();
		
		try {
			final List<String> stringIdSplitList = Arrays.asList(compareItems.split(ControllerConstants.URL.Report.CompareDivider));
			for (String stringId : stringIdSplitList) {
				final Integer integerId = Integer.valueOf(stringId);
				compareIDs.add(integerId);
			}
		} catch (Exception e) {
			//TODO: do something with catching exceptions and displaying output
			return ControllerConstants.Views.Report.AllianceCompare;
		}
		if (compareIDs.size() == 1) {
			return "redirect:" + ControllerConstants.URL.Report.OwnAllianceReportScorecard + "/" + serverId + "/" + compareIDs.iterator().next().intValue();
		}
		
		for (Integer compareID : compareIDs) {
			final ReportModelDataAlliance reportModelAllianceData = createReportModelDataForAlliance(serverId, compareID.intValue());
			reportModelAllianceDatas.add(reportModelAllianceData);
		}
		
		for (ReportModelDataAlliance reportModelDataAlliance : reportModelAllianceDatas) {
			List<Integer> removeCompareUrlList = new ArrayList<Integer>();
			for (Integer compareID : compareIDs) {
				if(!compareID.equals(Integer.valueOf(reportModelDataAlliance.getAllianceData().getId()))) {
					removeCompareUrlList.add(compareID);
				}
			}
			final String removeCompareUrl = StringUtils.join(removeCompareUrlList,ControllerConstants.URL.Report.CompareDivider);
			reportModelDataAlliance.setRemoveCompareUrl(removeCompareUrl);
		}
		
		final Map<String, String> mapMap = createCompareMapImagePathForAlliances(reportModelAllianceDatas);
		
		model.addAttribute("serverData", serverData);
		model.addAttribute("reportModelAllianceDatas", reportModelAllianceDatas);
		model.addAttribute("mapMap", mapMap);
		
		final StringBuilder pageTitle = new StringBuilder();
		pageTitle.append(PAGE_TITLE_PREPEND).append("Comparing Alliances");
		for (ReportModelDataAlliance reportModelAllianceData : reportModelAllianceDatas) {
			pageTitle.append(" - ").append(reportModelAllianceData.getAllianceData().getName());
		}
		model.addAttribute("currentCompare", compareItems);
		model.addAttribute("pageTitle", pageTitle);
		
		return ControllerConstants.Views.Report.AllianceCompare;
	}
	
	private ReportModelDataAlliance createReportModelDataForAlliance(final String serverId, final int allianceId) {
		
		final List<CastleData> castleDatas = castleDAO.getCastlesForAlliance(serverId, allianceId);
		final List<PlayerData> playerDatas = playerDAO.getAllPlayersForAlliance(serverId, allianceId);
		final AllianceData allianceData = allianceDAO.getAllianceForId(serverId, allianceId);
		allianceData.setNormalisedName(generalUtils.normaliseString(allianceData.getName()));
		allianceData.setHistoricPoints(limitItemsInList(allianceData.getHistoricPoints()));
		allianceData.setHistoricRank(limitItemsInList(allianceData.getHistoricRank()));
		allianceData.setHistoricCastleCount(limitItemsInList(allianceData.getHistoricCastleCount()));
		
		final String gameStatusForAlliance = reportService.createGameStatusForAlliance(allianceData);
		
		final List<ReportPlayerData> inactiveList = reportService.createInactiveReportForAlliance(allianceData.getMemberList());
		final int inactiveListCount = inactiveList.size();
		final List<ReportPieChartData> inactivePieChart = reportService.createInactivePieChartReportForAlliance(allianceData.getMemberList());
		
		final List<ReportPlayerData> newMembers = reportService.createNewPlayersReportForAlliance(allianceData.getMemberList());
		final int newMembersCount = newMembers.size();
		final List<PlayerData> exMembers = reportService.createExPlayersReportForAlliance(serverId, allianceId);
		final int exMembersCount = exMembers.size();
		
		final AllianceCastleChangeReport allNewCastlesForAlliance = reportService.createNewCastlesReportForAlliance(serverId, allianceData, newMembers);
		final List<ReportCastleChangeData> newCastlesForAllianceMainPlayers = allNewCastlesForAlliance.getMainPlayers();
		final int newCastlesForAllianceMainPlayersCount = newCastlesForAllianceMainPlayers.size();
		final List<ReportCastleChangeData> newCastlesForAllianceNewPlayers = allNewCastlesForAlliance.getOtherPlayers();
		final int newCastlesForAllianceNewPlayersCount = newCastlesForAllianceNewPlayers.size();
		
		final AllianceCastleChangeReport allLostCastlesForAlliance = reportService.createLostCastlesReportForAlliance(serverId, allianceData, exMembers);
		final List<ReportCastleChangeData> lostCastlesForAllianceMainPlayers = allLostCastlesForAlliance.getMainPlayers();
		final int lostCastlesForAllianceMainPlayersCount = lostCastlesForAllianceMainPlayers.size();
		final List<ReportCastleChangeData> lostCastlesForAllianceExPlayers = allLostCastlesForAlliance.getOtherPlayers();
		final int lostCastlesForAllianceExPlayersCount = lostCastlesForAllianceExPlayers.size();
		
		List<HistoricNameData> historicAllianceNameDatas = allianceData.getHistoricAllianceName();
		Collections.reverse(historicAllianceNameDatas);
		
		final AllianceLeaderboardReport allianceLeaderboardReport = reportService.createAllianceLeaderboardReport(serverId, allianceId);
		
		final ReportModelDataAlliance reportModelAllianceData = new ReportModelDataAlliance(castleDatas, playerDatas, allianceData, gameStatusForAlliance, newCastlesForAllianceMainPlayers, newCastlesForAllianceMainPlayersCount, newCastlesForAllianceNewPlayers, newCastlesForAllianceNewPlayersCount, lostCastlesForAllianceMainPlayers, lostCastlesForAllianceMainPlayersCount, lostCastlesForAllianceExPlayers, lostCastlesForAllianceExPlayersCount, inactiveList, inactiveListCount, inactivePieChart, newMembers, newMembersCount, exMembers, exMembersCount, historicAllianceNameDatas, allianceLeaderboardReport);
		return reportModelAllianceData;
	}
	
	
	/* CASTLES */
	
	
	@RequestMapping(value=ControllerConstants.URL.Report.OwnCastleReportScorecard, method = RequestMethod.GET)
	public String castleReportOwnScorecard(Model model, Principal principal) throws LKServerNotFoundException {
		final UserData userData = getUserData(principal);
		
		final List<CastleData> allPlayerCastles = castleDAO.getCastlesForPlayer(userData.getServerId(), userData.getPlayerId());
		final CastleData mainPlayerCastle = allPlayerCastles.iterator().next();
		
		//TODO - Deal with if they have no castles??
		
		return castleReportScorecard(model, userData.getServerId(), mainPlayerCastle.getId());
	}
		
	@RequestMapping(value=ControllerConstants.URL.Report.CastleReportScorecard, method = RequestMethod.GET)
	public String castleReportScorecard(Model model, @PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId, @PathVariable(ControllerConstants.URL.PathVariable.CastleID) int castleId) throws LKServerNotFoundException {
		//TODO need to check that the current user is allowed to access this report
		

		final ServerData serverData = serverDAO.getServerDataForId(serverId);
		final ReportModelDataCastle reportModelCastleData = createReportModelDataForCastle(serverId, castleId);
		
		model.addAttribute("serverData", serverData);
		model.addAttribute("reportModelCastleData", reportModelCastleData);
		model.addAttribute("currentCompare", reportModelCastleData.getCastleData().getId());
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Castle Report for " + reportModelCastleData.getCastleData().getName());
		
		return ControllerConstants.Views.Report.CastleScorecard;
	}
	
	@RequestMapping(value=ControllerConstants.URL.Report.CastleReportCompare, method = RequestMethod.GET)
	public String compareCastles(Model model, Principal principal,
			 @PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			 @PathVariable(ControllerConstants.URL.PathVariable.CompareItems) String compareItems) throws LKServerNotFoundException {
		
		final ServerData serverData = serverDAO.getServerDataForId(serverId);
		
		final List<Integer> compareIDs = new ArrayList<Integer>();
		final List<ReportModelDataCastle> reportModelCastleDatas = new ArrayList<ReportModelDataCastle>();
		
		try {
			final List<String> stringIdSplitList = Arrays.asList(compareItems.split(ControllerConstants.URL.Report.CompareDivider));
			for (String stringId : stringIdSplitList) {
				final Integer integerId = Integer.valueOf(stringId);
				compareIDs.add(integerId);
			}
		} catch (Exception e) {
			//TODO: do something with catching exceptions and displaying output
			return ControllerConstants.Views.Report.CastleCompare;
		}
		if (compareIDs.size() == 1) {
			return "redirect:" + ControllerConstants.URL.Report.OwnCastleReportScorecard + "/" + serverId + "/" + compareIDs.iterator().next().intValue();
		}
		
		for (Integer compareID : compareIDs) {
			final ReportModelDataCastle reportModelCastleData = createReportModelDataForCastle(serverId, compareID.intValue());
			reportModelCastleDatas.add(reportModelCastleData);
		}
		for (ReportModelDataCastle reportModelDataCastle : reportModelCastleDatas) {
			List<Integer> removeCompareUrlList = new ArrayList<Integer>();
			for (Integer compareID : compareIDs) {
				if(!compareID.equals(Integer.valueOf(reportModelDataCastle.getCastleData().getId()))) {
					removeCompareUrlList.add(compareID);
				}
			}
			final String removeCompareUrl = StringUtils.join(removeCompareUrlList,ControllerConstants.URL.Report.CompareDivider);
			reportModelDataCastle.setRemoveCompareUrl(removeCompareUrl);
		}
		
		final Map<String, String> mapMap = createCompareMapImagePathForCastles(reportModelCastleDatas);
		
		model.addAttribute("serverData", serverData);
		model.addAttribute("reportModelCastleDatas", reportModelCastleDatas);
		model.addAttribute("mapMap", mapMap);
		
		final StringBuilder pageTitle = new StringBuilder();
		pageTitle.append(PAGE_TITLE_PREPEND).append("Comparing Castles");
		for (ReportModelDataCastle reportModelCastleData : reportModelCastleDatas) {
			pageTitle.append(" - ").append(reportModelCastleData.getCastleData().getName());
		}
		model.addAttribute("currentCompare", compareItems);
		model.addAttribute("pageTitle", pageTitle);
		
		return ControllerConstants.Views.Report.CastleCompare;
	}
	
	private ReportModelDataCastle createReportModelDataForCastle(final String serverId, final int castleId) {
		final CastleData castleData = castleDAO.getCastleForId(serverId, castleId);
		castleData.setNormalisedName(generalUtils.normaliseString(castleData.getName()));
		final PlayerData playerData = playerDAO.getPlayerForId(serverId, castleData.getPlayerId());
		final AllianceData allianceData = allianceDAO.getAllianceForId(serverId, castleData.getAllianceId());
		

		List<HistoricPointsData> historicPointsDatas = limitItemsInList(castleData.getHistoricPoints());

		List<HistoricNameData> historicCastleNameDatas = castleData.getHistoricCastleName();
		Collections.reverse(historicCastleNameDatas);
		
		List<HistoricPlayerData> historicPlayerNameDatas = castleData.getHistoricPlayerName();
		Collections.reverse(historicPlayerNameDatas);
		
		List<HistoricAllianceData> historicAllianceNameDatas = castleData.getHistoricAllianceName();
		Collections.reverse(historicAllianceNameDatas);
		
		final ReportModelDataCastle reportModelCastleData = new ReportModelDataCastle(castleData, playerData, allianceData, historicPointsDatas, historicCastleNameDatas, historicPlayerNameDatas, historicAllianceNameDatas);
		return reportModelCastleData;
	}

	private Map<String,String> createCompareMapImagePathForCastles(final List<ReportModelDataCastle> reportModelCastleDatas) {
		final List<String> castlesList = new ArrayList<String>();
		final List<String> playersList = new ArrayList<String>();
		final List<String> alliancesList = new ArrayList<String>();
		
		for (ReportModelDataCastle reportModelDataCastle : reportModelCastleDatas) {
			castlesList.add(String.valueOf(reportModelDataCastle.getCastleData().getId()));
			playersList.add(String.valueOf(reportModelDataCastle.getPlayerData().getId()));
			alliancesList.add(String.valueOf(reportModelDataCastle.getAllianceData().getId()));
		}
		final String castles = StringUtils.join(castlesList,ControllerConstants.URL.Report.CompareDivider);
		final String players = StringUtils.join(playersList,ControllerConstants.URL.Report.CompareDivider);
		final String alliances = StringUtils.join(alliancesList,ControllerConstants.URL.Report.CompareDivider);
		Map<String,String> mapMap = new HashMap<String,String>();
		mapMap.put("castles", castles);
		mapMap.put("players", players);
		mapMap.put("alliances", alliances);
		return mapMap;
	}

	private Map<String,String> createCompareMapImagePathForPlayers(final List<ReportModelDataPlayer> reportModelPlayerDatas) {
		final List<String> playersList = new ArrayList<String>();
		final List<String> alliancesList = new ArrayList<String>();
		
		for (ReportModelDataPlayer reportModelDataPlayer : reportModelPlayerDatas) {
			playersList.add(String.valueOf(reportModelDataPlayer.getPlayerData().getId()));
			alliancesList.add(String.valueOf(reportModelDataPlayer.getAllianceData().getId()));
		}
		final String players = StringUtils.join(playersList,ControllerConstants.URL.Report.CompareDivider);
		final String alliances = StringUtils.join(alliancesList,ControllerConstants.URL.Report.CompareDivider);
		Map<String,String> mapMap = new HashMap<String,String>();
		mapMap.put("players", players);
		mapMap.put("alliances", alliances);
		return mapMap;
	}
	private Map<String,String> createCompareMapImagePathForAlliances(final List<ReportModelDataAlliance> reportModelAllianceDatas) {
		final List<String> alliancesList = new ArrayList<String>();
		
		for (ReportModelDataAlliance reportModelDataAlliance : reportModelAllianceDatas) {
			alliancesList.add(String.valueOf(reportModelDataAlliance.getAllianceData().getId()));
		}
		final String alliances = StringUtils.join(alliancesList,ControllerConstants.URL.Report.CompareDivider);
		Map<String,String> mapMap = new HashMap<String,String>();
		mapMap.put("alliances", alliances);
		return mapMap;
	}
	
	private <T extends Object> List<T> limitItemsInList(List<T> list) {
		if(list.size() > GRAPH_REPORT_DAYS) {
			return list.subList(list.size()-GRAPH_REPORT_DAYS, list.size());	
		} else {
			return list;
		}
	}
	
}
