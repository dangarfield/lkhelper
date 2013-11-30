package com.dangarfield.lkhelper.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.dangarfield.lkhelper.dao.PlayerDAO;
import com.dangarfield.lkhelper.dao.ServerDAO;
import com.dangarfield.lkhelper.dao.UserDAO;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.exceptions.LKServerNotFoundException;

public abstract class AbstractLKController {

	protected static final String PAGE_TITLE_PREPEND = "LK Helper | ";
	@Autowired
	protected PlayerDAO playerDAO;
	@Autowired
	protected UserDAO userDAO;
	@Autowired
	protected ServerDAO serverDAO;

	@ModelAttribute("userData")
	public UserData getUserData(final Principal principal) {
		try {
			if (principal == null) {
				return null;
			} else {
				final UserData userData = userDAO.getUser(principal.getName());
				return userData;
			}
		} catch (UsernameNotFoundException e) {
			return null;
		}
		
		
	}
	
	@ModelAttribute("currentContextPath")
	public String currentContextPath(final HttpServletRequest request) {
		final String path = request.getRequestURI();
		return path;
	}
	
	@ModelAttribute("moduleRootURL")
	public String moduleHomeURL(final HttpServletRequest request) {
		final String path = request.getRequestURI();
		if(path.lastIndexOf("/") > 1) {
			return path.substring(1,path.indexOf("/", 1));
		} else {
			return path;
		}
	}
	
	@ModelAttribute("playerName")
	public String getPlayerName(final Principal principal) {
		final UserData userData = getUserData(principal);
		if(userData != null) {
			final int playerId = userData.getPlayerId();
			final String serverId = userData.getServerId();
			final PlayerData playerData = playerDAO.getPlayerForId(serverId, playerId);
			final String playerName = playerData.getName();
			if (playerName != null && !playerName.isEmpty()) {
				return playerData.getName();	
			}
			return userData.getEmail();
			
		} else {
			return "Unknown User";
		}
	}
	@ModelAttribute("serverData")
	public ServerData getServerId(final Principal principal) {
		final UserData userData = getUserData(principal);
		if(userData != null) {
			final String serverId = userData.getServerId();
			try {
				return serverDAO.getServerDataForId(serverId);
			} catch (LKServerNotFoundException e) {
				return null;
			}
			
		} else {
			return null;
		}
	}
	
	public String getParameterFromQueryString(final String queryString, final String parameterKey) throws UnsupportedEncodingException {
		Map<String, String> params = getParametersFromQueryString(queryString);
		String param = params.get(parameterKey);
		return param;
	}
	public Map<String, String> getParametersFromQueryString(final String queryString) throws UnsupportedEncodingException {
	    Map<String, String> params = new HashMap<String, String>();
        for (String param : queryString.split("&")) {
            String pair[] = param.split("=");
            String key = URLDecoder.decode(pair[0], "UTF-8");
            String value = "";
            if (pair.length > 1) {
                value = URLDecoder.decode(pair[1], "UTF-8");
            }
            params.put(new String(key), new String(value));
        }
	    return params;
	}

}
