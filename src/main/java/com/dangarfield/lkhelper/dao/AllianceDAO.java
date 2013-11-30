package com.dangarfield.lkhelper.dao;

import java.util.List;

import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.PlayerAllianceData;
import com.dangarfield.lkhelper.data.search.SearchDataAlliance;
import com.dangarfield.lkhelper.data.search.TypeaheadAlliance;

public interface AllianceDAO extends AbstractDAO {

	PlayerAllianceData getPlayerAllianceForId(final String serverName, final int allianceId);
	
	void savePlayerAlliances(final String serverId, final List<PlayerAllianceData> playerAllianceDataList);
    
	void savePlayerAlliance(final String serverId, final PlayerAllianceData playerAllianceData);
	
	AllianceData getAllianceForId(final String serverId, final int allianceId);
	
	void saveAlliances(final String serverId, final List<AllianceData> allianceDataList);
	
    void saveAlliance(final String serverId, final AllianceData allianceData);

    List<Integer> getAllAllianceIds(final String serverId);
    
    List<TypeaheadAlliance> getTypeaheadAlliances(final String serverId, final String allianceSearch);
	SearchDataAlliance getSearchForAlliances(final SearchDataAlliance searchData);

	List<AllianceData> getRivalAlliances(final String serverId, final AllianceData allianceData);
}
