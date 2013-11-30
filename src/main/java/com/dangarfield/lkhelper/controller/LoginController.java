package com.dangarfield.lkhelper.controller;

import java.security.Principal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
import com.dangarfield.lkhelper.dao.JMSJobDAO;
import com.dangarfield.lkhelper.dao.ServerDAO;
import com.dangarfield.lkhelper.dao.UserDAO;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.jms.GatherMessageSender;
import com.dangarfield.lkhelper.jms.event.AuthenticateUserEvent;
import com.dangarfield.lkhelper.data.users.JMSJobData;



@Controller
public class LoginController extends AbstractLKController {
	
	private static Logger LOG = LogManager.getLogger("LoginController");

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ServerDAO serverDAO;
	@Autowired
	private JMSJobDAO jmsJobDAO;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private GatherMessageSender messageProducer;
	
	@RequestMapping(value=ControllerConstants.URL.Login.Login, method = RequestMethod.GET)
	public String login(final Model model) {
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Login");
		model.addAttribute("allServers",serverDAO.getAllServerData());
		return ControllerConstants.Views.Login.Login;

	}
	
	@RequestMapping(value=ControllerConstants.URL.Login.LoginFailed, method = RequestMethod.GET)
	public String loginError(final Model model) {

		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Login Failed");
		model.addAttribute("error", "true");
		model.addAttribute("allServers",serverDAO.getAllServerData());
		return ControllerConstants.Views.Login.Login;

	}

	@RequestMapping(value=ControllerConstants.URL.Login.Logout, method = RequestMethod.GET)
	public String logout(final Model model) {
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Logout");
		model.addAttribute("allServers",serverDAO.getAllServerData());
		return ControllerConstants.Views.Login.Login;

	}

	@RequestMapping(value=ControllerConstants.URL.Login.Register, method = RequestMethod.POST)
	public String register(@RequestParam(value="email", required=true) String email, @RequestParam(value="password", required=true) String password,@RequestParam(value="server", required=true) String serverId, final Model model) {

		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Register");
		model.addAttribute("allServers",serverDAO.getAllServerData());
		
		try {
			try {
				userDAO.getUser(email);
				// user already exists
				model.addAttribute("error", "true");
				model.addAttribute("errorMessage", "User already exists");
				return ControllerConstants.Views.Login.Login;
			} catch (UsernameNotFoundException e) {
				// Allowing to progress
				LOG.debug("User does not exists, creating new user; " + email);
			}
			
			
			userDAO.createAndSaveNewUser(email, password, serverId);
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken (userDetails, password, userDetails.getAuthorities());
			authenticationManager.authenticate(auth);
			if(auth.isAuthenticated()) {
			    SecurityContextHolder.getContext().setAuthentication(auth);
			}
			

			return "redirect:" + ControllerConstants.URL.Login.Authenticate;
			
		} catch (Exception e) {
			model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Register Failed");
			model.addAttribute("error", "true");
			model.addAttribute("errorMessage", "Error creating user");
			
			return ControllerConstants.Views.Login.Login;
		}

	};
	
	@RequestMapping(value=ControllerConstants.URL.Login.Authenticate, method = { RequestMethod.GET, RequestMethod.POST })
	public String authenticate(final Principal principal, final Model model) {
		
		try {
			model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Authenticate User");
			String email = principal.getName();
			UserData userData = userDAO.getUser(email);

			final String jmsJobId = messageProducer.send(new AuthenticateUserEvent(userData.getEmail(), userData.getServerId()));
			
			final JMSJobData jmsJob = jmsJobDAO.getRunningJobData(jmsJobId);
			
			model.addAttribute("jmsJob", jmsJob);
			
			return ControllerConstants.Views.Login.Authenticate;
			
			
		} catch (Exception e) {
			model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Authentication Failed");
			model.addAttribute("error", "true");
			model.addAttribute("errorMessage", "Error authenticating user");
			model.addAttribute("allServers",serverDAO.getAllServerData());
			return ControllerConstants.Views.Login.Login;
		}
	}
	
	@RequestMapping(value=ControllerConstants.URL.Login.LoginUpdatePassword, method = RequestMethod.POST)
	public String loginUpdatePassword(@RequestParam(value="j_username", required=true) String email, @RequestParam(value="j_password", required=true) String password, final Model model) {

		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Update Password");
		model.addAttribute("allServers",serverDAO.getAllServerData());
		
		try {
			final UserData userData = userDAO.getUser(email);
			userData.setPassword(password);
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
		return ControllerConstants.Views.Login.Login;
	};
	
}
