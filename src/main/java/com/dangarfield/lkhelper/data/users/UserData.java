package com.dangarfield.lkhelper.data.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData {

	@JsonProperty("_id")
	private String email;
	private String password;
	private String name;
	private String serverId;
	private int playerId;
	private int allianceId;
	private Date lastLogin;
	private List<UserRole> userRoles;
	
	private boolean isUnauthenticatedUser;
	private boolean isUser;
	private boolean isCrusader;
	private boolean isReporter;
	private boolean isAdmin;

	public enum UserRole {
		ROLE_UNAUTHENTICATED_USER,
		ROLE_USER,
		ROLE_CRUSADER,
		ROLE_REPORTER,
		ROLE_ADMIN
	}
	public void setUserRoleBooleanValues(List<UserRole> userRoles) {
		if (userRoles.contains(UserRole.ROLE_UNAUTHENTICATED_USER)) {
			this.isUnauthenticatedUser = true;
			this.isUser = false;
		}
		if (userRoles.contains(UserRole.ROLE_USER)) {
			this.isUnauthenticatedUser = false;
			this.isUser = true;
		}
		if (userRoles.contains(UserRole.ROLE_CRUSADER)) {
			this.isCrusader = true;
		}
		if (userRoles.contains(UserRole.ROLE_REPORTER)) {
			this.isReporter = true;
		}
		if (userRoles.contains(UserRole.ROLE_ADMIN)) {
			this.isAdmin = true;
		}
	}
	
	public UserData() {
	}
	
	/**
	 * @param email
	 * @param password
	 */
	public UserData(String email, String password, String serverId) {
		super();
		this.email = email;
		this.password = password;
		this.serverId = serverId;
		final ArrayList<UserRole> roles = new ArrayList<UserRole>();
		roles.add(UserRole.ROLE_UNAUTHENTICATED_USER);
		this.setUserRoles(roles);
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the allianceId
	 */
	public int getAllianceId() {
		return allianceId;
	}

	/**
	 * @param allianceId the allianceId to set
	 */
	public void setAllianceId(int allianceId) {
		this.allianceId = allianceId;
	}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	
	/**
	 * @return the userRoles
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
		setUserRoleBooleanValues(userRoles);
	}

	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}

	/**
	 * @param serverId the mainServer to set
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	/**
	 * @return the isUnauthenticatedUser
	 */
	public boolean getIsUnauthenticatedUser() {
		return isUnauthenticatedUser;
	}

	/**
	 * @param isUnauthenticatedUser the isUnauthenticatedUser to set
	 */
	public void setIsUnauthenticatedUser(boolean isUnauthenticatedUser) {
		this.isUnauthenticatedUser = isUnauthenticatedUser;
	}
	
	/**
	 * @return the isUser
	 */
	public boolean getIsUser() {
		return isUser;
	}

	/**
	 * @param isUser the isUser to set
	 */
	public void setIsUser(boolean isUser) {
		this.isUser = isUser;
	}

	/**
	 * @return the isCrusader
	 */
	public boolean getIsCrusader() {
		return isCrusader;
	}

	/**
	 * @param isCrusader the isCrusader to set
	 */
	public void setIsCrusader(boolean isCrusader) {
		this.isCrusader = isCrusader;
	}

	/**
	 * @return the isReporter
	 */
	public boolean getIsReporter() {
		return isReporter;
	}

	/**
	 * @param isReporter the isReporter to set
	 */
	public void setIsReporter(boolean isReporter) {
		this.isReporter = isReporter;
	}

	/**
	 * @return the isAdmin
	 */
	public boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		if (email == null) {
			return "new empty user";
		}
		return email;
	}


}
