package com.dangarfield.lkhelper.seleniumtest.impl;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.dangarfield.lkhelper.seleniumtest.AuthenticateUser;
import com.dangarfield.lkhelper.data.users.JMSJobData;
import com.dangarfield.lkhelper.data.users.JMSJobData.JMSJobStatus;

public class AuthenticateUserImpl extends AbstractGatherBaseImpl implements AuthenticateUser {
	
	private static Logger LOG = LogManager.getLogger("AuthenticateUserImpl");
	
	private void before(final UserData user, final JMSJobData jmsJob) {
		LOG.info("------------------------------------------------------------------------");
        LOG.info("--------------------      AUTHENTICATING USER       --------------------");
        LOG.info("------------------------------------------------------------------------");
        
        driver = driverUtils.startSeleniumWebDriver();
	}
	
	@Override
	public void authenticateUser(final UserData user, final JMSJobData jmsJob) throws GatherDataException {
		
		boolean success = true;
		String errorMessage = "";
		
		LOG.info("Attempting to authenticate user: Start");
		
		jmsJob.setDetail("Authenticating User");
		jmsJob.setStep(3);
		jmsJobDAO.saveJob(jmsJob);
		
		before(user, jmsJob);
		try {
			final ServerData status = serverDAO.getServerDataForId(user.getServerId());
			login(driver, user.getEmail(), user.getPassword(), status.getName());
			
			// Need to get playerName, playerID and allianceId to user data
			
			jmsJob.setDetail("Fetching data");
			jmsJob.setStep(4);
			jmsJobDAO.saveJob(jmsJob);
			
			int playerId = lkMainPageController.getHabitatPlayerId(driver);
			int allianceId = lkMainPageController.getHabitatAllianceId(driver);
			
			user.setPlayerId(playerId);
			user.setAllianceId(allianceId);
			userUtils.authenticateUserAndUpdateUserRights(user);
			
			userDAO.saveUser(user);
			
		} catch (Exception e) {
			success = false;
			errorMessage = e.getMessage();
			LOG.error(e);
		} finally {
			after();
		}
		
		final Date endDate = new Date();
		if(success) {
			jmsJob.setDetail("Complete");
			jmsJob.setEndTime(endDate);
			jmsJob.setStatus(JMSJobStatus.COMPLETE);
			jmsJobDAO.saveJob(jmsJob);
			LOG.info("Authentication passed for user: " + user.getEmail() + " on server: " + user.getServerId());	
		} else {
			jmsJob.setDetail("Error: " + errorMessage);
			jmsJob.setStatus(JMSJobStatus.ERROR);
			jmsJobDAO.saveJob(jmsJob);
			LOG.info("Authentication failed for user: " + user.getEmail() + " on server: " + user.getServerId());
		}
		
	}
	
	private void after() {
		closeDriver();
	}
}
