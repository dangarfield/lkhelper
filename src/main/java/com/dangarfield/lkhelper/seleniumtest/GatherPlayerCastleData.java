package com.dangarfield.lkhelper.seleniumtest;

import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.data.users.JMSJobData;

public interface GatherPlayerCastleData extends AbstractGatherBase {
	
	public void getHabitatData(final ServerData serverData, final UserData userData, final JMSJobData jmsJob) throws Exception;
}
