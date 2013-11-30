package com.dangarfield.lkhelper.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangarfield.lkhelper.constants.ControllerConstants;
import com.dangarfield.lkhelper.dao.AllianceDAO;
import com.dangarfield.lkhelper.dao.ServerDAO;
import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.users.UserData;


@Controller
public class UserHomeController extends AbstractLKController {	

	@Autowired
	private AllianceDAO allianceDAO;
	@Autowired
	private ServerDAO serverDAO;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RequestMapping(value=ControllerConstants.URL.User.UserHome, method = RequestMethod.GET)
	public String useHomePage(Model model, Principal principal) {
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Hi " + principal.getName());
		
		final UserData userData = getUserData(principal);
		model.addAttribute("userData", userData);
		
		model.addAttribute("gatherSpecificPlayerLink", ControllerConstants.URL.Gather.GatherSpecificPlayerCastleData.replace("{"+ControllerConstants.URL.PathVariable.UserEmailMatchAll+"}", principal.getName()));
		model.addAttribute("editDetailsLink", ControllerConstants.URL.User.UserEditDetails);
		return ControllerConstants.Views.User.UserHome;

	}

	@RequestMapping(value=ControllerConstants.URL.User.UserEditDetails, method = RequestMethod.GET)
	public String editDetails(Model model, Principal principal) {
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Edit User Details");
		final UserData userData = getUserData(principal);
		if (userData.getPlayerId() > 0) {
			final PlayerData playerData = playerDAO.getPlayerForId(userData.getServerId(), userData.getPlayerId());
			model.addAttribute("playerName", playerData.getName());
			if(playerData.getAllianceId() > 0) {
				final AllianceData allianceData = allianceDAO.getAllianceForId(userData.getServerId(), playerData.getAllianceId());
				model.addAttribute("allianceName", allianceData.getName());
			}
		}
		model.addAttribute("userData", userData);
		model.addAttribute("allServers",serverDAO.getAllServerData());
		return ControllerConstants.Views.User.UserEditDetails;

	}
	
	@RequestMapping(value=ControllerConstants.URL.User.UserEditDetails, method = RequestMethod.POST)
	public String saveDetails(@RequestParam(value="email", required=true) String email, @RequestParam(value="password", required=true) String password, @RequestParam(value="serverId", required=true) String serverId, Model model, Principal principal) {
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Edit User Details");
		model.addAttribute("allServers",serverDAO.getAllServerData());
		
		try {
			final UserData userData = getUserData(principal);
			userData.setEmail(email);
			userData.setPassword(password);
			userData.setServerId(serverId);
			userDAO.saveUser(userData);
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken (userDetails, password, userDetails.getAuthorities());
			authenticationManager.authenticate(auth);
			if(auth.isAuthenticated()) {
			    SecurityContextHolder.getContext().setAuthentication(auth);
			}
			

			return "redirect:" + ControllerConstants.URL.Login.Authenticate;
			
		} catch (UsernameNotFoundException e) {
			model.addAttribute("errorMessage", "User does not exist, no password to update. Please register");
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Error editing user");
			
		}
		model.addAttribute("error", "true");
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Edit User Details Failed");
		return ControllerConstants.Views.Login.Login;

	}
	
}
