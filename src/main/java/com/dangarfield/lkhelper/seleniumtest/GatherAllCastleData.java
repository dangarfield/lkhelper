package com.dangarfield.lkhelper.seleniumtest;

import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.dangarfield.lkhelper.data.users.JMSJobData;

public interface GatherAllCastleData extends AbstractGatherBase {
	
	public int getAllCastleData(final ServerData serverData, final JMSJobData jmsJob) throws GatherDataException;
	
}
