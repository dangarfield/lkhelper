package com.dangarfield.lkhelper.dao;

import java.util.Date;
import java.util.List;

import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerCastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricCastleData;
import com.dangarfield.lkhelper.data.report.MapCoordinates;
import com.dangarfield.lkhelper.data.report.ReportCastleChangeData;
import com.dangarfield.lkhelper.data.report.ReportPlayerIdPoints;
import com.dangarfield.lkhelper.data.search.SearchDataCastle;
import com.dangarfield.lkhelper.data.search.TypeaheadCastle;

public interface CastleDAO extends AbstractDAO {
	
	PlayerCastleData getPlayerCastleForId(final String serverName, final int playerId);
	
	void savePlayerCastles(final String serverId, final List<PlayerCastleData> playerCastleDataList);
    
	void savePlayerCastle(final String serverId, final PlayerCastleData playerCastleData);
	
	CastleData getCastleForId(final String serverId, final int castleId);
	
	void saveCastles(final String serverId, final List<CastleData> castleDataList);
	
    void saveCastle(final String serverId, final CastleData castleData);

    int getAllianceIdForPlayerId(final String serverId, final int playerId);

    int getCastleCountForAlliance(final String serverId, final int allianceId);

    List<CastleData> getCastlesForAlliance(final String serverId, final int allianceId);
    
    int getCastleCountForPlayer(final String serverId, final int playerId);
    
    List<CastleData> getCastlesForPlayer(final String serverId, final int playerId);

	List<ReportPlayerIdPoints> getMemberPointsForAlliance(String serverId, Integer allianceId);

	List<HistoricCastleData> getLostCastlesForPlayer(final String serverId, final int playerId);
	
	List<HistoricCastleData> getLostCastlesForAlliance(final String serverId, final int allianceId);
    
	List<MapCoordinates> getBoundaryCoordinatesForServer(final String serverId);
	List<MapCoordinates> getAllCastlesCoordinatesForServer(final String serverId);
	List<MapCoordinates> getAllCastlesCoordinatesForAlliance(final String serverId, final int allianceId);
	List<MapCoordinates> getAllCastlesCoordinatesForPlayer(final String serverId, final int playerId);
	List<MapCoordinates> getAllCastlesCoordinatesForCastle(final String serverId, final int castleId);

	List<ReportCastleChangeData> getNewCastlesForPlayer(final String serverId, final PlayerData playerData, final Date reportCutoffDate);
	List<ReportCastleChangeData> getNewCastlesForAlliance(final String serverId, final AllianceData allianceData, final Date reportCutoffDate);
	List<ReportCastleChangeData> getLostCastlesForPlayer(final String serverId, final PlayerData playerData, final Date reportCutoffDate);
	List<ReportCastleChangeData> getLostCastlesForAlliance(final String serverId, final AllianceData allianceData, final Date reportCutoffDate);

	List<TypeaheadCastle> getTypeaheadCastles(final String serverId, final String castleSearch);
	SearchDataCastle getSearchForCastles(final SearchDataCastle searchData);

	List<CastleData> getRivalCastles(String serverId, CastleData castleData);
	
}
