package com.dangarfield.lkhelper.utils;

import com.dangarfield.lkhelper.data.users.UserData;

public interface UserUtils {

	public void authenticateUserAndUpdateUserRights(final UserData userData);
	
	public void updateUserRights(final UserData userData, final boolean authenticate);
	
}
