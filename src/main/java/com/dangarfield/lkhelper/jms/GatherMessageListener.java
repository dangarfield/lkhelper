package com.dangarfield.lkhelper.jms;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangarfield.lkhelper.dao.JMSJobDAO;
import com.dangarfield.lkhelper.dao.ServerDAO;
import com.dangarfield.lkhelper.dao.UserDAO;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.dangarfield.lkhelper.exceptions.JMSJobNotFoundException;
import com.dangarfield.lkhelper.exceptions.LKServerNotFoundException;
import com.dangarfield.lkhelper.jms.event.AuthenticateUserEvent;
import com.dangarfield.lkhelper.jms.event.GatherAllCastlesEvent;
import com.dangarfield.lkhelper.jms.event.GatherPlayerCastlesEvent;
import com.dangarfield.lkhelper.seleniumtest.AuthenticateUser;
import com.dangarfield.lkhelper.seleniumtest.GatherAllCastleData;
import com.dangarfield.lkhelper.data.users.JMSJobData;
import com.dangarfield.lkhelper.data.users.JMSJobData.JMSJobStatus;


public class GatherMessageListener implements MessageListener {
	
	private static final Logger LOG = LogManager.getLogger("GatherMessageListener");

	@Autowired
	private GatherAllCastleData gatherAllCastleData;
	@Autowired
	private AuthenticateUser authenticateUser;
	@Autowired
	private ServerDAO serverDAO;
	@Autowired
	private JMSJobDAO jmsJobDAO;
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public void onMessage(Message message) {
		
		try {
			
		
			if (message instanceof ObjectMessage) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				Serializable objectMessagePojo = objectMessage.getObject();
				
				if (objectMessagePojo instanceof GatherAllCastlesEvent) {
					final GatherAllCastlesEvent gatherAllCastlesEvent = (GatherAllCastlesEvent) objectMessagePojo;
					
					JMSJobData jmsJob = null;
					try {
						final String serverId = gatherAllCastlesEvent.getServerId();
						final ServerData serverData = serverDAO.getServerDataForId(serverId);
						
						LOG.info("GatherAllCastlesEvent captured - Triggering for server:" + serverData);
						//TODO log to database - status: In Progress
						
						jmsJob = jmsJobDAO.getRunningJobData(message.getJMSCorrelationID());
						jmsJob.setStatus(JMSJobStatus.RUNNING);
						jmsJob.setDetail("Starting processing from queue");
						jmsJob.setStep(2);
						jmsJobDAO.saveJob(jmsJob);
						
						gatherAllCastleData.getAllCastleData(serverData, jmsJob);
						//TODO log to database - status: Completed
						
						LOG.info("GatherAllCastlesEvent finished for server:" + serverData);
					} catch (GatherDataException e) {
						final String error = "GatherDataException caught: " + e.getMessage();

						jmsJob.setStatus(JMSJobStatus.ERROR);
						jmsJob.setDetail(error);
						jmsJobDAO.saveJob(jmsJob);
						
						LOG.error(error);
					} catch (LKServerNotFoundException e) {
						final String error = "LKServerNotFoundException caught: " + e.getMessage();
						LOG.error(error);
					} catch (JMSJobNotFoundException e) {
						final String error = "JMSJobNotFoundException caught: " + e.getMessage();
						LOG.error(error);
					}
					
					
					
					
				} else if (objectMessagePojo instanceof GatherPlayerCastlesEvent) {
					
					
				} else if (objectMessagePojo instanceof AuthenticateUserEvent) {
					final AuthenticateUserEvent authenticateUserEvent = (AuthenticateUserEvent) objectMessagePojo;
					
					JMSJobData jmsJob = null;
					try {
						final String serverId = authenticateUserEvent.getServerId();
						final ServerData serverData = serverDAO.getServerDataForId(serverId);
						final String userId = authenticateUserEvent.getUserId();
						final UserData userData = userDAO.getUser(userId);
						LOG.info("AuthenticateUserEvent captured - Triggering for user:" + userData);
						//TODO log to database - status: In Progress
						
						jmsJob = jmsJobDAO.getRunningJobData(message.getJMSCorrelationID());
						jmsJob.setStatus(JMSJobStatus.RUNNING);
						jmsJob.setDetail("Starting processing from queue");
						jmsJob.setStep(2);
						jmsJobDAO.saveJob(jmsJob);
						
						authenticateUser.authenticateUser(userData,jmsJob);
						//TODO log to database - status: Completed
						
						LOG.info("GatherAllCastlesEvent finished for server:" + serverData);
					} catch (GatherDataException e) {
						final String error = "GatherDataException caught: " + e.getMessage();

						jmsJob.setStatus(JMSJobStatus.ERROR);
						jmsJob.setDetail(error);
						jmsJobDAO.saveJob(jmsJob);
						
						LOG.error(error);
					} catch (LKServerNotFoundException e) {
						final String error = "LKServerNotFoundException caught: " + e.getMessage();
						LOG.error(error);
					} catch (JMSJobNotFoundException e) {
						final String error = "JMSJobNotFoundException caught: " + e.getMessage();
						LOG.error(error);
					}
					
					
					
					
					
					
					
				} else {
					final String error = "Message needs to be of the ObjectMessage type";
					LOG.error(error);
				}
				
			} else {
				final String error = "Message needs to be of the ObjectMessage type";
				LOG.error(error);
			}
		} catch (JMSException e) {
			final String error = "JMSException caught: " + e.getMessage();
			LOG.error(error);
		}
		
//		try {
//            LOG.info("Received message: {}", ((TextMessage)message).getText());
//            try {
//                Thread.sleep(15000);
//            } catch(InterruptedException ex) {
//                Thread.currentThread().interrupt();
//            }
//            LOG.info("Delayed message: {}", ((TextMessage)message).getText());
//        } catch (JMSException e) {
//            LOG.error(e.getMessage(), e);
//        }
	}

}
