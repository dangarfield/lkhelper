package com.dangarfield.lkhelper.dao;

import java.util.Date;
import java.util.List;

import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.report.AllianceLeaderboardData;
import com.dangarfield.lkhelper.data.search.SearchDataPlayer;
import com.dangarfield.lkhelper.data.search.TypeaheadPlayer;

public interface PlayerDAO extends AbstractDAO {

	PlayerData getPlayerForId(final String serverId, final int playerId);
	
	void savePlayers(final String serverId, final List<PlayerData> playerDataList);
	
    void savePlayer(final String serverId, final PlayerData playerData);
    
    List<Integer> getAllPlayerIds(final String serverId);

    List<Integer> getAllPlayerIdsWithNoAlliance(final String serverId);

    List<PlayerData> getAllPlayersForAlliance(final String serverId, final int allianceId);
    
    List<PlayerData> getExPlayersForAlliance(final String serverId, final int allianceId, final Date reportCutoffDate);
    
    List<TypeaheadPlayer> getTypeaheadPlayers(final String serverId, final String playersSearch);
	SearchDataPlayer getSearchForPlayers(final SearchDataPlayer searchData);
	
	List<AllianceLeaderboardData> getAllianceLeaderboardData(final String serverId, final int allianceId, final Date startDate, final Date endDate);

	boolean isPlayerInAlliance(final String serverId, final int playerId, final int allianceId);

	List<PlayerData> getRivalPlayersInAlliance(String serverId, PlayerData playerData);
}
