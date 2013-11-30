package com.dangarfield.lkhelper.cron;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.dangarfield.lkhelper.dao.ServerDAO;
import com.dangarfield.lkhelper.dao.UserDAO;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.jms.GatherMessageSender;
import com.dangarfield.lkhelper.jms.event.GatherAllCastlesEvent;

public class GatherAllDataCronJob extends QuartzJobBean {
	
	@Autowired
	private ServerDAO serverDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private GatherMessageSender messageProducer;
	@Resource(name="gatherCastleDataDefaultUser")
	private Map<String,String> gatherCastleDataDefaultUser;
	
	private static Logger LOG = LogManager.getLogger("GatherAllDataCronJob");
	
	@Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //Code to be executed on specified interval
		
		LOG.info("GatherAllDataCronJob Started");
		List<ServerData> allServers = serverDAO.getAllServerData();
		for (ServerData serverData : allServers) {
			final String defaultUserIdForServer = gatherCastleDataDefaultUser.get(serverData.getId());
			final UserData defaultUserForServer = userDAO.getUser(defaultUserIdForServer);
			messageProducer.send(new GatherAllCastlesEvent(defaultUserForServer.getEmail(), defaultUserForServer.getPlayerId(), serverData.getId()));
		}
		LOG.info("GatherAllDataCronJob Ended");
    }
}
