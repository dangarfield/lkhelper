package com.dangarfield.lkhelper.jms.event;

import java.io.Serializable;

import com.dangarfield.lkhelper.data.users.UserData;

public class GatherPlayerCastlesEvent extends AbstractLKEvent implements Serializable {

	private static final long serialVersionUID = -5585624088045350855L;
	
	private String userId;
	private int playerId;
	private String serverId;
	private String correllationId;

	/**
	 * @param serverId
	 */
	public GatherPlayerCastlesEvent(final String userId, final UserData player, final String serverId) {
		super();
		this.userId = userId;
		this.playerId = player.getPlayerId();
		this.serverId = serverId;
		this.correllationId = createRandomString();
	}
	
	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * @return the correllationId
	 */
	public String getCorrellationId() {
		return correllationId;
	}

	@Override
	public String toString() {
		return "PlayerID: " + playerId + ". ServerID: " + serverId;
	}


}
