package com.dangarfield.lkhelper.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangarfield.lkhelper.constants.ControllerConstants;
import com.dangarfield.lkhelper.dao.AllianceDAO;
import com.dangarfield.lkhelper.dao.CastleDAO;
import com.dangarfield.lkhelper.dao.PlayerDAO;
import com.dangarfield.lkhelper.dao.ServerDAO;
import com.dangarfield.lkhelper.dao.UserDAO;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.data.users.UserData.UserRole;
import com.dangarfield.lkhelper.exceptions.LKServerNotFoundException;
import com.dangarfield.lkhelper.service.ImageProcessingService;



@Controller
public class AdminController extends AbstractLKController {
	
	//private static Logger LOG = LogManager.getLogger("AdminController");
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ServerDAO serverDAO;
	@Autowired
	private AllianceDAO allianceDAO;
	@Autowired
	private PlayerDAO playerDAO;
	@Autowired
	private CastleDAO castleDAO;
	@Autowired
	private ImageProcessingService imageProcessingService;

	@RequestMapping(value=ControllerConstants.URL.Admin.AdminHome, method = RequestMethod.GET)
	public String login(final Model model) {
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Admin Home");
		model.addAttribute("message", "Admin home");
		model.addAttribute("editServersLink", ControllerConstants.URL.Admin.AdminEditServers);
		model.addAttribute("editUsersLink", ControllerConstants.URL.Admin.AdminEditUsers);
		return ControllerConstants.Views.Admin.AdminHome;

	}
	@RequestMapping(value=ControllerConstants.URL.Admin.AdminEditServers, method = RequestMethod.GET)
	public String showServerList(final Model model) {

		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Admin Edit Servers");
		model.addAttribute("message", "Admin edit servers");
		List<ServerData> serverDatas = serverDAO.getAllServerData();
		model.addAttribute("serverDataCount", serverDatas.size());
		serverDatas.add(new ServerData());
		model.addAttribute("serverDatas", serverDatas);
		model.addAttribute("saveServerLink", ControllerConstants.URL.Admin.AdminEditServers);
		return ControllerConstants.Views.Admin.AdminEditServers;

	}
	@RequestMapping(value=ControllerConstants.URL.Admin.AdminEditServers, method = RequestMethod.POST)
	public String editServer(final Model model,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "code", required = true) int code,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "url", required = true) String url,
			@RequestParam(value = "centralX", required = true) int centralX,
			@RequestParam(value = "centralY", required = true) int centralY,
			@RequestParam(value = "country", required = true) String country,
			@RequestParam(value = "timezone", required = true) String timezone,
			@RequestParam(value = "remove", required = false) boolean remove) {

		ServerData server;
		try {
			server = serverDAO.getServerDataForId(id);	
		} catch (LKServerNotFoundException e) {
			// User does not exist, they need creating
			server = new ServerData(id, code, name, url, centralX, centralY, country, timezone);
		}

		if (code > 0) {
			server.setCode(code);
		}
		if (StringUtils.isNotBlank(name)) {
			server.setName(name);
		}
		if (StringUtils.isNotBlank(url)) {
			server.setUrl(url);
		}
		if (centralX > 0) {
			server.setCentralX(centralX);
		}
		if (centralY > 0) {
			server.setCentralY(centralY);
		}
		if (StringUtils.isNotBlank(country)) {
			server.setCountry(country);
		}
		if (StringUtils.isNotBlank(timezone)) {
			server.setTimezone(timezone);
		}
		
		if (remove) {
			serverDAO.removeServerData(server);
		} else {
			serverDAO.saveServerData(server);
		}
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Admin Edit Servers");
		model.addAttribute("message", "Admin editted servers");
		List<ServerData> serverDatas = serverDAO.getAllServerData();
		model.addAttribute("serverDataCount", serverDatas.size());
		serverDatas.add(new ServerData());
		model.addAttribute("serverDatas", serverDatas);
		model.addAttribute("saveServerLink", ControllerConstants.URL.Admin.AdminEditServers);
		return ControllerConstants.Views.Admin.AdminEditServers;

	}
	

	@RequestMapping(value=ControllerConstants.URL.Admin.AdminEditUsers, method = RequestMethod.GET)
	public String showUserList(final Model model) {
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Admin Edit Users");
		model.addAttribute("message", "Admin edit users");
		List<UserData> userDatas = userDAO.getAllUserData();
		model.addAttribute("userDataCount", userDatas.size());
		userDatas.add(new UserData());
		model.addAttribute("userDatas", userDatas);
		model.addAttribute("allUserRoles", userDAO.getAllUserRoles());
		return ControllerConstants.Views.Admin.AdminEditUsers;

	}
	@RequestMapping(value=ControllerConstants.URL.Admin.AdminEditUsers, method = RequestMethod.POST)
	public String editUser(final Model model, final HttpServletRequest request,
				@RequestParam(value = "email", required = true) String email,
				@RequestParam(value = "password", required = true) String password,
				@RequestParam(value = "name", required = false) String name,
				@RequestParam(value = "serverId", required = true) String serverId,
				@RequestParam(value = "playerId", required = false) String playerId,
				@RequestParam(value = "allianceId", required = false) String allianceId,
				@RequestParam(value = "lastLogin", required = false) String lastLogin,
				@RequestParam(value = "remove", required = false) boolean remove) throws ParseException {

		UserData user;
		try {
			user = userDAO.getUser(email);	
		} catch (UsernameNotFoundException e) {
			// User does not exist, they need creating
			user = userDAO.createAndSaveNewUser(email, password, serverId);
		}
		
		if (StringUtils.isNotBlank(password)) {
			user.setPassword(password);
		}
		if (StringUtils.isNotBlank(name)) {
			user.setName(name);
		}
		if (StringUtils.isNotBlank(serverId)) {
			user.setServerId(serverId);
		}
		if (StringUtils.isNotBlank(playerId)) {
			user.setPlayerId(Integer.valueOf(playerId).intValue());
		}
		if (StringUtils.isNotBlank(allianceId)) {
			user.setAllianceId(Integer.valueOf(allianceId).intValue());
		}
		if (StringUtils.isNotBlank(lastLogin)) {
			SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
			user.setLastLogin(formatter.parse(lastLogin));
		}
		
		
		List<UserRole> possibleUserRoles = userDAO.getAllUserRoles();
		List<UserRole> userRoles = new ArrayList<UserRole>();
		
		for (UserRole possibleUserRole : possibleUserRoles) {
			final String userRole = request.getParameter(possibleUserRole.toString());
			if(StringUtils.isNotBlank(userRole)) {
				if (userRole.equals("on")) {
					userRoles.add(possibleUserRole);
				}
			}
		}
		user.setUserRoles(userRoles);
		
		if (remove) {
			userDAO.removeUserData(user);	
		} else {
			userDAO.saveUser(user);
		}
		
		
		model.addAttribute("message", "Admin editted users");
		
		//Duplicate code below
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Admin Edit Users");
		List<UserData> userDatas = userDAO.getAllUserData();
		model.addAttribute("userDataCount", userDatas.size());
		userDatas.add(new UserData());
		model.addAttribute("userDatas", userDatas);
		model.addAttribute("saveUserLink", ControllerConstants.URL.Admin.AdminEditUsers);
		model.addAttribute("allUserRoles", userDAO.getAllUserRoles());
		return ControllerConstants.Views.Admin.AdminEditUsers;
	}
	
	@RequestMapping(value=ControllerConstants.URL.Admin.AdminTriggerEnsureIndexes, method = RequestMethod.GET)
	public String ensureIndexes(final Model model) {
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Ensure Indexes");
		model.addAttribute("message", "Ensure index complete");
		serverDAO.ensureIndexes();
		userDAO.ensureIndexes();
		final List<ServerData> serverDatas = serverDAO.getAllServerData();
		for (ServerData serverData : serverDatas) {
			final String serverId = serverData.getId();
			castleDAO.ensureIndexes(serverId);
			allianceDAO.ensureIndexes(serverId);
			playerDAO.ensureIndexes(serverId);
		}
		
		return ControllerConstants.Views.Admin.AdminTriggerEnsureIndexes;

	}
	
	@RequestMapping(value=ControllerConstants.URL.Admin.AdminCreateCSVData, method = RequestMethod.GET)
	public String createImages(final Model model) {
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Admin Create CSV Data");
		model.addAttribute("message", "Image Creation complete");
		imageProcessingService.generateAllRawCSVFiles();
		
		return ControllerConstants.Views.Admin.AdminCreateCSVData;

	}
}
