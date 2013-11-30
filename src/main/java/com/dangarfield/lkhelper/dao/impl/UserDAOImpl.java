package com.dangarfield.lkhelper.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jongo.FindOne;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dangarfield.lkhelper.dao.UserDAO;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.data.users.UserData.UserRole;

public class UserDAOImpl implements UserDAO {
	private static Logger LOG = LogManager.getLogger("UserDAOImpl");
	
	private MongoCollection userCollection;

    public UserDAOImpl(final Jongo jongo) {
    	userCollection = jongo.getCollection("users");
    }

	@Override
    public void ensureIndexes() {
    	userCollection.dropIndexes();
    }
   
    @Override
	public UserData getUser(final String email) throws UsernameNotFoundException {
		UserData user;
		FindOne findOne = userCollection.findOne("{_id: '"+email+"'}");
		user = findOne.as(UserData.class);
		if (user == null) {
			final String error = "User not in db: " + email;
			LOG.info(error);
			throw new UsernameNotFoundException(error);
		}
		return user;
	}
	

	@Override
    public void saveUser(final UserData user) {
    	LOG.debug("Saving castleData: " + user.getEmail());
		userCollection.save(user);
	}

	@Override
	public UserData createAndSaveNewUser(final String email, final String password, final String serverId) {
		final UserData newUser = new UserData(email, password, serverId);
		saveUser(newUser);
		return newUser; 
	}



	@Override
	public List<UserData> getAllUserData() {
		final Iterable<UserData> usersIterable = userCollection.find().sort("{_id:1}").as(UserData.class);
		
		final List<UserData> userDatasList = new ArrayList<UserData>();
		CollectionUtils.addAll(userDatasList, usersIterable.iterator());
		
		return userDatasList;
	}



	@Override
	public void removeUserData(final UserData userData) {
		LOG.debug("Removing userData: " + userData.getEmail());
		userCollection.remove("{_id: '"+userData.getEmail()+"'}");
	}

	
	@Override
	public List<UserRole> getAllUserRoles() {
		List<UserRole> allUserRoles = new ArrayList<UserRole>();
		allUserRoles.add(UserRole.ROLE_UNAUTHENTICATED_USER);
		allUserRoles.add(UserRole.ROLE_USER);
		allUserRoles.add(UserRole.ROLE_CRUSADER);
		allUserRoles.add(UserRole.ROLE_REPORTER);
		allUserRoles.add(UserRole.ROLE_ADMIN);
		return allUserRoles;
	}

}
