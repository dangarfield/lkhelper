package com.dangarfield.lkhelper.dao;

import java.util.List;

import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.exceptions.LKServerNotFoundException;

public interface ServerDAO {

	void ensureIndexes();
	
	List<ServerData> getAllServerData();
	
	ServerData getServerDataForId(final String id) throws LKServerNotFoundException;
	
	void saveServerData(final ServerData serverData);
	
	void removeServerData(final ServerData serverData);
}
