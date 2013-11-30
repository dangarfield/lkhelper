package com.dangarfield.lkhelper.jms;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.dangarfield.lkhelper.dao.JMSJobDAO;
import com.dangarfield.lkhelper.jms.event.AuthenticateUserEvent;
import com.dangarfield.lkhelper.jms.event.GatherAllCastlesEvent;
import com.dangarfield.lkhelper.jms.event.GatherPlayerCastlesEvent;
import com.dangarfield.lkhelper.data.users.JMSJobData;
import com.dangarfield.lkhelper.data.users.JMSJobData.JMSJobType;

public class GatherMessageSender {
	
	private static final Logger LOG = LogManager.getLogger("GatherMessageSenderImpl");
	
	private static final int PRIORITY_AUTHENTICATE_USER = 1;
	private static final int PRIORITY_GATHER_ALL_CASTLES = 2;
	private static final int PRIORITY_GATHER_PLAYER_CASTLES = 3;
	
	

	@Autowired
	protected JmsTemplate jmsTemplate;
	
	@Autowired
	protected JMSJobDAO jmsJobDAO;
	
	
	public String send(final GatherAllCastlesEvent gatherAllCastlesEvent) {
		
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage(gatherAllCastlesEvent);
				message.setJMSPriority(PRIORITY_GATHER_ALL_CASTLES);
				message.setJMSCorrelationID(gatherAllCastlesEvent.getCorrellationId());
				//TODO log to database - status: Requested
				jmsJobDAO.saveJob(new JMSJobData(message.getJMSCorrelationID(), gatherAllCastlesEvent.getUserId(), gatherAllCastlesEvent.getCorrellationId(), JMSJobType.GATHER_ALL, gatherAllCastlesEvent.getPlayerId(),gatherAllCastlesEvent.getServerId(), new Date(), "Sending to queue"));
				LOG.info("Sending message to JMS Queue for Gathering All Castle Data: " + gatherAllCastlesEvent);
				return message;
			}
		});
		
		return gatherAllCastlesEvent.getCorrellationId();
	}
	
	public String send(final GatherPlayerCastlesEvent gatherPlayerCastlesEvent) {
		
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage(gatherPlayerCastlesEvent);
				message.setJMSPriority(PRIORITY_GATHER_PLAYER_CASTLES);
				message.setJMSCorrelationID(gatherPlayerCastlesEvent.getCorrellationId());
				//TODO log to database - status: Requested
				jmsJobDAO.saveJob(new JMSJobData(message.getJMSCorrelationID(), gatherPlayerCastlesEvent.getUserId(), gatherPlayerCastlesEvent.getCorrellationId(), JMSJobType.GATHER_PLAYER, gatherPlayerCastlesEvent.getPlayerId(), gatherPlayerCastlesEvent.getServerId(), new Date(), "Sending to queue"));
				LOG.info("Sending message to JMS Queue for Gathering Player Castle Data: " + gatherPlayerCastlesEvent);
				return message;
			}
		});
		
		return gatherPlayerCastlesEvent.getCorrellationId();
	}
	
	public String send(final AuthenticateUserEvent authenticateUserEvent) {
		
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage(authenticateUserEvent);
				message.setJMSPriority(PRIORITY_AUTHENTICATE_USER);
				message.setJMSCorrelationID(authenticateUserEvent.getCorrellationId());
				//TODO log to database - status: Requested
				jmsJobDAO.saveJob(new JMSJobData(message.getJMSCorrelationID(), authenticateUserEvent.getUserId(), authenticateUserEvent.getCorrellationId(), JMSJobType.AUTHENTICATE_USER, -1, authenticateUserEvent.getServerId(), new Date(), "Sending to queue"));
				LOG.info("Sending message to JMS Queue for Authenticate user: " + authenticateUserEvent);
				return message;
			}
		});
		
		return authenticateUserEvent.getCorrellationId();
	}
    
	
 
    
    public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
}
