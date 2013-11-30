package com.dangarfield.lkhelper.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.dangarfield.lkhelper.dao.ServerDAO;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.exceptions.LKServerNotFoundException;

public class ServerDAOImpl implements ServerDAO {
	private static Logger LOG = LogManager.getLogger("ServerDAOImpl");
	
	private MongoCollection serverCollection;

    public ServerDAOImpl(final Jongo jongo) {
    	serverCollection = jongo.getCollection("servers");
    }

    @Override
    public void ensureIndexes() {
    	serverCollection.dropIndexes();
    }
    
	@Override
	public List<ServerData> getAllServerData() {
		Iterable<ServerData> sdIterable = serverCollection.find().as(ServerData.class);
		
		List<ServerData> serverDatasList = new ArrayList<ServerData>();
		CollectionUtils.addAll(serverDatasList, sdIterable.iterator());
		
		return serverDatasList;
	}



	@Override
	public ServerData getServerDataForId(final String id) throws LKServerNotFoundException {
		ServerData serverData = serverCollection.findOne("{_id: '"+id+"'}").as(ServerData.class);
		if (serverData == null) {
			final String error = "Server not in db: " + id;
			LOG.info(error);
			throw new LKServerNotFoundException(error);
		}
		return serverData;
	}



	@Override
	public void saveServerData(ServerData serverData) {
		LOG.debug("Saving serverData: " + serverData.getId());
		serverCollection.save(serverData);
	}
	
	@Override
	public void removeServerData(ServerData serverData) {
		LOG.debug("Removing serverData: " + serverData.getId());
		serverCollection.remove("{_id: '"+serverData.getId()+"'}");
	}

	
}
