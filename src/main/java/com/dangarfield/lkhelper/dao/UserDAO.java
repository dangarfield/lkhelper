package com.dangarfield.lkhelper.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.data.users.UserData.UserRole;

public interface UserDAO {

	void ensureIndexes();
	
	List<UserData> getAllUserData();
	
	UserData getUser(final String email) throws UsernameNotFoundException;

	void saveUser(final UserData user);
	
	UserData createAndSaveNewUser(final String email, final String password, final String serverId);
	
	void removeUserData(final UserData userData);
	
	List<UserRole> getAllUserRoles();
}
