package com.dangarfield.lkhelper.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dangarfield.lkhelper.constants.ControllerConstants;
import com.dangarfield.lkhelper.dao.CastleDAO;
import com.dangarfield.lkhelper.dao.JMSJobDAO;
import com.dangarfield.lkhelper.dao.ServerDAO;
import com.dangarfield.lkhelper.dao.UserDAO;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.historic.HistoricAllianceData;
import com.dangarfield.lkhelper.data.historic.HistoricPlayerData;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.exceptions.JMSJobNotFoundException;
import com.dangarfield.lkhelper.exceptions.LKServerNotFoundException;
import com.dangarfield.lkhelper.jms.GatherMessageSender;
import com.dangarfield.lkhelper.jms.event.GatherAllCastlesEvent;
import com.dangarfield.lkhelper.jms.event.GatherPlayerCastlesEvent;
import com.dangarfield.lkhelper.data.users.JMSJobData;



@Controller
public class GatherController extends AbstractLKController {


	@Autowired
	private CastleDAO castleDAO;
	@Autowired
	private ServerDAO serverDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private JMSJobDAO jmsJobDAO;
	@Autowired
	private GatherMessageSender messageProducer;
	@Resource(name="gatherCastleDataDefaultUser")
	private Map<String,String> gatherCastleDataDefaultUser;

	@RequestMapping(value=ControllerConstants.URL.Gather.GatherHome, method = RequestMethod.GET)
	public String gatherPage(Model model, Principal principal) throws Exception {
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Gather Data");

		final List<UserData> allUserDatas = userDAO.getAllUserData();
		model.addAttribute("allUserDatas", allUserDatas);
		
		final List<ServerData> allServerDatas = serverDAO.getAllServerData();
		model.addAttribute("allServerDatas", allServerDatas);
		
		return ControllerConstants.Views.Gather.GatherHome;

	}

	@RequestMapping(value=ControllerConstants.URL.Gather.GatherAllCastleData, method = RequestMethod.GET)
	public String gatherAllCastleData(Model model, Principal principal) {
		
		//TODO check is user is allowed access
		//final UserData userData = getUserData(principal);
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Gather All Castle Data");
		List<ServerData> allServers = serverDAO.getAllServerData();
		for (ServerData serverData : allServers) {
			final String defaultUserIdForServer = gatherCastleDataDefaultUser.get(serverData.getId());
			final UserData defaultUserForServer = userDAO.getUser(defaultUserIdForServer);
			messageProducer.send(new GatherAllCastlesEvent(defaultUserForServer.getEmail(), defaultUserForServer.getPlayerId(), serverData.getId()));
		}
		
		model.addAttribute("message", "Gathering triggered for all castle data for the following servers:");
		model.addAttribute("allServers", allServers);
		
		return ControllerConstants.Views.Gather.GatherAllCastleData;

	}
	
	@RequestMapping(value=ControllerConstants.URL.Gather.GatherSpecificServerCastleData, method = RequestMethod.GET)
	public String gatherSpecificServerCastleData(Model model, @PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId, Principal principal) throws LKServerNotFoundException, JMSJobNotFoundException {
		
		//TODO check is user is allowed access
		//final UserData userData = getUserData(principal);
		
		
		ServerData serverData = serverDAO.getServerDataForId(serverId);
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Gather Castle Data for " +  serverData.getName() + " server");
		final String defaultUserIdForServer = gatherCastleDataDefaultUser.get(serverData.getId());
		final UserData defaultUserForServer = userDAO.getUser(defaultUserIdForServer);
		final String jmsJobId = messageProducer.send(new GatherAllCastlesEvent(defaultUserForServer.getEmail(), defaultUserForServer.getPlayerId(), serverData.getId()));
		
		model.addAttribute("serverData", serverData);
		final JMSJobData jmsJob = jmsJobDAO.getRunningJobData(jmsJobId);
		
		model.addAttribute("jmsJob", jmsJob);
		
		return ControllerConstants.Views.Gather.GatherAllCastleData;

	}

	@RequestMapping(value=ControllerConstants.URL.Gather.GatherAllPlayerCastleData, method = RequestMethod.GET)
	public String gatherAllPlayerCastleData(Model model, Principal principal) throws LKServerNotFoundException {
		
		final UserData mainUserData = getUserData(principal);
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Gather All Player Data");
		//TODO check is user is allowed access
		List<UserData> allUsers = userDAO.getAllUserData();
		for (UserData userData : allUsers) {
			String serverId = userData.getServerId();
			messageProducer.send(new GatherPlayerCastlesEvent(mainUserData.getEmail(), userData, serverId));
		}
		
		model.addAttribute("message", "Gathering triggered for all castle data for the following servers:");
		model.addAttribute("allServers", allUsers);
		return ControllerConstants.Views.Gather.GatherAllPlayerCastleData;

	}
	
	@RequestMapping(value=ControllerConstants.URL.Gather.GatherSpecificPlayerCastleData, method = RequestMethod.GET)
	public String gatherSpecificPlayerCastleData(Model model, Principal principal,
			 @PathVariable(ControllerConstants.URL.PathVariable.UserEmail) String email) throws LKServerNotFoundException {
		
		final UserData userData = getUserData(principal);
		if(!userData.getEmail().equals(email)) {
			if(!userData.getIsAdmin()) {
				return "forward:" + ControllerConstants.URL.Error.AccessDenied403;	
			}
		}
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Gather All Player Data");
		
		//String serverId = userData.getServerId();
		//This is not being invoked in this release
		//messageProducer.send(new GatherPlayerCastlesEvent(userData.getEmail(), userData, serverId));
		
		
		model.addAttribute("message", "Gathering triggered for all castle data for the following servers:");
		model.addAttribute("userData", userData);
		return ControllerConstants.Views.Gather.GatherSpecificPlayerCastleData;

	}
	
	@RequestMapping(value=ControllerConstants.URL.Gather.GatherLogs, method = RequestMethod.GET)
	public @ResponseBody JMSJobData gatherSpecificPlayerCastleData(Model model, @PathVariable(ControllerConstants.URL.PathVariable.LogID) String logId, Principal principal) {
		
		try {
			final JMSJobData log = jmsJobDAO.getRunningJobData(logId);
			return log;
		} catch (JMSJobNotFoundException e) {
			return null;
		}
	}

	@RequestMapping(value="/gather-data/fix-castles", method = RequestMethod.GET)
	public String fixData(Model model, Principal principal) {
		final String serverId = "uk3";
		
		final List<Integer> playerIds = playerDAO.getAllPlayerIds(serverId);
		for (Integer playerId : playerIds) {
			List<CastleData> castleDatas = castleDAO.getCastlesForPlayer(serverId, playerId);
			for (CastleData castleData : castleDatas) {
				
				List<HistoricAllianceData> historicAllianceNames = castleData.getHistoricAllianceName();
				Collections.sort(historicAllianceNames);
				castleData.setHistoricAllianceName(historicAllianceNames);
				
				List<HistoricPlayerData> historicPlayerNames = castleData.getHistoricPlayerName();
				Collections.sort(historicPlayerNames);
				castleData.setHistoricPlayerName(historicPlayerNames);
				
				castleDAO.saveCastle(serverId, castleData);
			}
		}
		
		return "";
	}
	
	
}
