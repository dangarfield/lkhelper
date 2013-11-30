package com.dangarfield.lkhelper.utils;

import java.util.Map;

import com.dangarfield.lkhelper.data.PlayerCastleData;

public interface LKUtils {
	
	public int getDistanceToHabitat(final int homeX, final int homeY, final int targetX, final int targetY);
	
	public void addResourceToPlayer(final PlayerCastleData playerCastle, final int resourceId, final int amount) throws Exception;

	public void addTroopsToPlayer(final PlayerCastleData playerCastle, final Map<String, Long> troopMap) throws Exception;
	
	public void resetResourceCount(PlayerCastleData playerCastle);

	public void resetTroopCount(PlayerCastleData playerCastle);
}
