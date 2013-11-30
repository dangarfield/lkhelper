package com.dangarfield.lkhelper.utils.impl;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.data.users.UserData.UserRole;
import com.dangarfield.lkhelper.utils.UserUtils;

public class UserUtilsImpl implements UserUtils {
	private static Logger LOG = LogManager.getLogger("UserUtilsImpl");
	
	@Autowired
	private List<String> userRightsCrusaderAlliance;
	@Autowired
	private List<String> userRightsReporters;
	@Autowired
	private List<String> userRightsAdmins;
	

	@Override
	public void authenticateUserAndUpdateUserRights(final UserData userData) {
		updateUserRights(userData, true);
	}
	
	@Override
	public void updateUserRights(final UserData userData, final boolean authenticate) {
		List<UserRole> amendedUserRoles = userData.getUserRoles();
		final String serverId = userData.getServerId(); 
		final int allianceId = userData.getAllianceId();
		final int playerId = userData.getPlayerId();
		final String serverAlliance = serverId + "_" + String.valueOf(allianceId);
		final String serverPlayer = serverId + "_" + String.valueOf(playerId);
		
		if (authenticate) {
			authenticate(amendedUserRoles);	
		}
		authenticateCrusader(amendedUserRoles, serverAlliance);
		authenticateReporter(amendedUserRoles, serverPlayer);
		authenticateAdmin(amendedUserRoles, serverPlayer);
		userData.setUserRoleBooleanValues(amendedUserRoles);
	}
	private void authenticate(final List<UserRole> amendedUserRoles) {
		amendedUserRoles.remove(UserRole.ROLE_UNAUTHENTICATED_USER);
		amendedUserRoles.add(UserRole.ROLE_USER);
	}
	private void authenticateCrusader(final List<UserRole> amendedUserRoles, final String serverAlliance) {
		List<String> crusaderslist = getUserRightsCrusaderAlliance();
		amendedUserRoles.remove(UserRole.ROLE_CRUSADER);
		if (crusaderslist.contains(serverAlliance)) {
			amendedUserRoles.add(UserRole.ROLE_CRUSADER);
		}
		LOG.debug(crusaderslist);
	}
	private void authenticateReporter(final List<UserRole> amendedUserRoles, final String serverPlayer) {
		List<String> reporterslist = getUserRightsReporters();
		amendedUserRoles.remove(UserRole.ROLE_REPORTER);
		if (reporterslist.contains(serverPlayer)) {
			amendedUserRoles.add(UserRole.ROLE_REPORTER);
		}
		LOG.debug(reporterslist);
	}
	private void authenticateAdmin(final List<UserRole> amendedUserRoles, final String serverPlayer) {
		List<String> adminslist = getUserRightsAdmins();
		amendedUserRoles.remove(UserRole.ROLE_ADMIN);
		if (adminslist.contains(serverPlayer)) {
			amendedUserRoles.add(UserRole.ROLE_ADMIN);
		}
		LOG.debug(adminslist);
	}

	
	/**
	 * @return the userRightsCrusaderAlliance
	 */
	public List<String> getUserRightsCrusaderAlliance() {
		return userRightsCrusaderAlliance;
	}

	/**
	 * @param userRightsCrusaderAlliance the userRightsCrusaderAlliance to set
	 */
	public void setUserRightsCrusaderAlliance(
			List<String> userRightsCrusaderAlliance) {
		this.userRightsCrusaderAlliance = userRightsCrusaderAlliance;
	}

	/**
	 * @return the userRightsReporters
	 */
	public List<String> getUserRightsReporters() {
		return userRightsReporters;
	}

	/**
	 * @param userRightsReporters the userRightsReporters to set
	 */
	public void setUserRightsReporters(List<String> userRightsReporters) {
		this.userRightsReporters = userRightsReporters;
	}

	/**
	 * @return the userRightsAdmins
	 */
	public List<String> getUserRightsAdmins() {
		return userRightsAdmins;
	}

	/**
	 * @param userRightsAdmins the userRightsAdmins to set
	 */
	public void setUserRightsAdmins(List<String> userRightsAdmins) {
		this.userRightsAdmins = userRightsAdmins;
	}
	
	
}
