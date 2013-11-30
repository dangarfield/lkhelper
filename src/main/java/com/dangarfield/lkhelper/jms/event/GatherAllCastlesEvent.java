package com.dangarfield.lkhelper.jms.event;

import java.io.Serializable;

public class GatherAllCastlesEvent extends AbstractLKEvent implements Serializable {

	private static final long serialVersionUID = -7009669392694703979L;
	
	private String userId;
	private int playerId;
	private String serverId;
	private String correllationId;

	/**
	 * @param serverId
	 */
	public GatherAllCastlesEvent(final String userId, final int playerId, final String serverId) {
		super();
		this.userId = userId;
		this.playerId = playerId;
		this.serverId = serverId;
		this.correllationId = createRandomString();
	}
	

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}

	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}
	
	/**
	 * @return the correllationId
	 */
	public String getCorrellationId() {
		return correllationId;
	}



	@Override
	public String toString() {
		return "ServerId: " + serverId + ". PlayerId: " + playerId;
	}



	
}
