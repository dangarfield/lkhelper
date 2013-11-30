package com.dangarfield.lkhelper.seleniumtest.impl;

import java.util.Map;

import javax.annotation.Resource;

import net.lightbody.bmp.proxy.ProxyServer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangarfield.lkhelper.converter.CastleDataJsonConverter;
import com.dangarfield.lkhelper.dao.AllianceDAO;
import com.dangarfield.lkhelper.dao.CastleDAO;
import com.dangarfield.lkhelper.dao.JMSJobDAO;
import com.dangarfield.lkhelper.dao.PlayerDAO;
import com.dangarfield.lkhelper.dao.ServerDAO;
import com.dangarfield.lkhelper.dao.UserDAO;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.dangarfield.lkhelper.exceptions.LKServerNotFoundException;
import com.dangarfield.lkhelper.pagecontroller.LKHttpRequestController;
import com.dangarfield.lkhelper.pagecontroller.LKMainPageController;
import com.dangarfield.lkhelper.pagecontroller.LKLoginPageController;
import com.dangarfield.lkhelper.seleniumtest.AbstractGatherBase;
import com.dangarfield.lkhelper.utils.ConfigUtils;
import com.dangarfield.lkhelper.utils.DriverUtils;
import com.dangarfield.lkhelper.utils.GeneralUtils;
import com.dangarfield.lkhelper.utils.LKUtils;
import com.dangarfield.lkhelper.utils.UserUtils;

public abstract class AbstractGatherBaseImpl implements AbstractGatherBase {
	
	private static Logger LOG = LogManager.getLogger("AbstractGatherBase");
	
	protected WebDriver driver;
	protected ProxyServer proxyServer;

	@Autowired
	protected CastleDAO castleDAO;
	@Autowired
	protected AllianceDAO allianceDAO;
	@Autowired
	protected PlayerDAO playerDAO;
	@Autowired
	protected UserDAO userDAO;
	@Autowired
	protected ServerDAO serverDAO;
	@Autowired
	protected JMSJobDAO jmsJobDAO;
	
	@Autowired
	protected LKLoginPageController lkLoginPageController;
	@Autowired
	protected LKMainPageController lkMainPageController;
	@Autowired
	protected LKHttpRequestController lkHttpRequestController;
	
	@Autowired
	protected CastleDataJsonConverter castleDataJsonConverter;
	@Autowired
	protected ConfigUtils configUtils;
	@Autowired
	protected UserUtils userUtils;
	@Autowired
	protected LKUtils lkUtils;
	@Autowired
	protected GeneralUtils generalUtils;
	@Autowired
	protected DriverUtils driverUtils;
	@Resource(name="gatherCastleDataDefaultUser")
	private Map<String,String> gatherCastleDataDefaultUser;
	
	@Override
	public void loginAsDefaultUser(final ServerData serverData) throws LKServerNotFoundException, GatherDataException {
		final String defaultUserIdForServer = gatherCastleDataDefaultUser.get(serverData.getId());
		final UserData defaultUserForServer = userDAO.getUser(defaultUserIdForServer);
		login(driver, defaultUserForServer.getEmail(), defaultUserForServer.getPassword(), serverData.getName());
	}
	@Override
	public void login(final WebDriver driver, final String loginEmail, final String loginPassword, final String loginWorld) throws GatherDataException {
		lkLoginPageController.login(driver, loginEmail, loginPassword, loginWorld);
	}
	
	@Override
	public void closeDriver() {
		driver.quit();
		LOG.info("Driver closed");
		LOG.info("------------------------------------------------------------------------");
	}

	@Override
	public String calculateTimeDifference(final long startTime, final long endTime) {
		long diff = endTime - startTime;
		 
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		final StringBuilder diffStringBuilder = new StringBuilder();
		
		if (diffDays > 0) {
			diffStringBuilder.append(diffDays + " days, ");
		}
		if (diffHours > 0) {
			diffStringBuilder.append(diffDays + " hours, ");
		}
		if (diffMinutes > 0) {
			diffStringBuilder.append(diffMinutes + " minutes, ");
		}
		if (diffSeconds > 0) {
			diffStringBuilder.append(diffSeconds + " seconds.");
		}
		return diffStringBuilder.toString();
	}
	
	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * @return the proxyServer
	 */
	public ProxyServer getProxyServer() {
		return proxyServer;
	}

	/**
	 * @param proxyServer the proxyServer to set
	 */
	public void setProxyServer(ProxyServer proxyServer) {
		this.proxyServer = proxyServer;
	}

	/**
	 * @return the castleDAO
	 */
	public CastleDAO getCastleDAO() {
		return castleDAO;
	}

	/**
	 * @param castleDAO the castleDAO to set
	 */
	public void setCastleDAO(CastleDAO castleDAO) {
		this.castleDAO = castleDAO;
	}

	/**
	 * @return the lkLoginPageController
	 */
	public LKLoginPageController getLkLoginPageController() {
		return lkLoginPageController;
	}

	/**
	 * @param lkLoginPageController the lkLoginPageController to set
	 */
	public void setLkLoginPageController(LKLoginPageController lkLoginPageController) {
		this.lkLoginPageController = lkLoginPageController;
	}

	/**
	 * @return the lkMainPageController
	 */
	public LKMainPageController getLkMainPageController() {
		return lkMainPageController;
	}

	/**
	 * @param lkMainPageController the lkMainPageController to set
	 */
	public void setLkMainPageController(LKMainPageController lkMainPageController) {
		this.lkMainPageController = lkMainPageController;
	}

	/**
	 * @return the lkHttpRequestController
	 */
	public LKHttpRequestController getLkHttpRequestController() {
		return lkHttpRequestController;
	}

	/**
	 * @param lkHttpRequestController the lkHttpRequestController to set
	 */
	public void setLkHttpRequestController(
			LKHttpRequestController lkHttpRequestController) {
		this.lkHttpRequestController = lkHttpRequestController;
	}

	/**
	 * @return the castleDataJsonConverter
	 */
	public CastleDataJsonConverter getCastleDataJsonConverter() {
		return castleDataJsonConverter;
	}

	/**
	 * @param castleDataJsonConverter the castleDataJsonConverter to set
	 */
	public void setCastleDataJsonConverter(
			CastleDataJsonConverter castleDataJsonConverter) {
		this.castleDataJsonConverter = castleDataJsonConverter;
	}

	/**
	 * @return the configUtils
	 */
	public ConfigUtils getConfigUtils() {
		return configUtils;
	}

	/**
	 * @param configUtils the configUtils to set
	 */
	public void setConfigUtils(ConfigUtils configUtils) {
		this.configUtils = configUtils;
	}

	/**
	 * @return the lkUtils
	 */
	public LKUtils getLkUtils() {
		return lkUtils;
	}

	/**
	 * @param lkUtils the lkUtils to set
	 */
	public void setLkUtils(LKUtils lkUtils) {
		this.lkUtils = lkUtils;
	}

	/**
	 * @return the generalUtils
	 */
	public GeneralUtils getGeneralUtils() {
		return generalUtils;
	}

	/**
	 * @param generalUtils the generalUtils to set
	 */
	public void setGeneralUtils(GeneralUtils generalUtils) {
		this.generalUtils = generalUtils;
	}

	/**
	 * @return the driverUtils
	 */
	public DriverUtils getDriverUtils() {
		return driverUtils;
	}

	/**
	 * @param driverUtils the driverUtils to set
	 */
	public void setDriverUtils(DriverUtils driverUtils) {
		this.driverUtils = driverUtils;
	}
}
