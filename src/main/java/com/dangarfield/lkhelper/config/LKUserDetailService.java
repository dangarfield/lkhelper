package com.dangarfield.lkhelper.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dangarfield.lkhelper.dao.UserDAO;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.data.users.UserData.UserRole;

public class LKUserDetailService implements UserDetailsService {
	
	private static Logger LOG = LogManager.getLogger("LKUserDetailService");
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(final String email)
	        throws UsernameNotFoundException, DataAccessException {
		UserData user = userDAO.getUser(email);
		
		User userDetail = new User(user.getEmail(), user.getPassword(), true, true, true, true,
				getAuthorities(user));
		return userDetail;
	}


	public List<GrantedAuthority> getAuthorities(final UserData user) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		LOG.debug("Getting authorities for user: " + user);
		for (UserRole userRole : user.getUserRoles()) {
			LOG.debug(userRole.toString());
			authList.add(new GrantedAuthorityImpl(userRole.toString()));
		}
		return authList;
	}


}