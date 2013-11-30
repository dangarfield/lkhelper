package com.dangarfield.lkhelper.utils;

import java.util.Date;

public interface GeneralUtils {

	public Date newDateForDay();
	public Date newDateForCurrentDayMinusDays(final int minusDayCount);
	
	public String getCollectionNameForServer(final String baseCollectionName, final String serverId);
	
	public String createRandomString();
	
	public String createLinkForCastle(final int serverCode, final int mapX, final int mapY);
	public String createLinkForPlayer(final int serverCode, final int playerId);
	public String createLinkForAlliance(final int serverCode, final int allianceId);
	
	public String normaliseString(final String unicodeString);
}
