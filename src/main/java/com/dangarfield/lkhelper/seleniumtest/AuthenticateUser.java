package com.dangarfield.lkhelper.seleniumtest;

import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.dangarfield.lkhelper.data.users.JMSJobData;

public interface AuthenticateUser extends AbstractGatherBase {
	
	public void authenticateUser(final UserData user, final JMSJobData jmsJob) throws GatherDataException;
}
