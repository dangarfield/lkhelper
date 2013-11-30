package com.dangarfield.lkhelper.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;


import com.dangarfield.lkhelper.controller.SearchController;
import com.dangarfield.lkhelper.dao.CastleDAO;
import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerCastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricAllianceData;
import com.dangarfield.lkhelper.data.historic.HistoricCastleData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;
import com.dangarfield.lkhelper.data.historic.HistoricPlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricPointsData;
import com.dangarfield.lkhelper.data.report.MapCoordinates;
import com.dangarfield.lkhelper.data.report.ReportCastleChangeData;
import com.dangarfield.lkhelper.data.report.ReportPlayerIdPoints;
import com.dangarfield.lkhelper.data.search.SearchDataCastle;
import com.dangarfield.lkhelper.data.search.TypeaheadCastle;
import com.dangarfield.lkhelper.utils.GeneralUtils;
import com.mongodb.DBObject;

public class CastleDAOImpl implements CastleDAO {
	
	private static Logger LOG = LogManager.getLogger("CastleDAOImpl");
	
	private static String BASE_COLLECTION_NAME = "castles";
	
	private MongoCollection castlesCollection;

	@Autowired
	private Jongo jongo;
	
	@Autowired
	private GeneralUtils generalUtils;
	
    private void setCastlesCollection(final String serverId) {
    	castlesCollection = jongo.getCollection(generalUtils.getCollectionNameForServer(BASE_COLLECTION_NAME, serverId));
    }
    
    @Override
    public void ensureIndexes(final String serverId) {
    	setCastlesCollection(serverId);
    	castlesCollection.dropIndexes();
    	castlesCollection.ensureIndex("{playerId:1}");
    	castlesCollection.ensureIndex("{name:1}");
    	castlesCollection.ensureIndex("{normalisedName:1}");
    	castlesCollection.ensureIndex("{allianceId:1}");
    	castlesCollection.ensureIndex("{allianceId:1,playerId:1,points:1}");
    	castlesCollection.ensureIndex("{\"historicPlayerName.playerId\":1}");
    	castlesCollection.ensureIndex("{\"historicAllianceName.allianceId\":1}");
    	castlesCollection.ensureIndex("{historicPlayerName:1}");
    	castlesCollection.ensureIndex("{historicAllianceName:1}");
    	castlesCollection.ensureIndex("{mapX:1}");
    	castlesCollection.ensureIndex("{mapY:1}");
    	
    }
   
    @Override
	public PlayerCastleData getPlayerCastleForId(final String serverId, final int id) {
		setCastlesCollection(serverId);
		PlayerCastleData castle;
		castle = castlesCollection.findOne("{_id: "+id+"}").as(PlayerCastleData.class);
		
		if (castle != null) {
			LOG.debug("PlayerCastle retrieved from db: " + castle.getId());
		} else {
			LOG.debug("PlayerCastle not in db, creating new PlayerCastle: " + id);
			castle = new PlayerCastleData(id);
		}
		return castle;
	}
    @Override
	public void savePlayerCastles(final String serverId, final List<PlayerCastleData> playerCastleDataList) {
		for (PlayerCastleData playerCastleData : playerCastleDataList) {
			savePlayerCastle(serverId, playerCastleData);
		}
	}
	@Override
    public void savePlayerCastle(final String serverId, final PlayerCastleData playerCastleData) {
    	setCastlesCollection(serverId);
    	LOG.debug("Saving playerCastleData: " + playerCastleData.getId());
		castlesCollection.save(playerCastleData);
	}

	@Override
	public CastleData getCastleForId(final String serverId, final int id) {
		setCastlesCollection(serverId);
		CastleData castle;
		castle = castlesCollection.findOne("{_id: "+id+"}").as(CastleData.class);
		
		if (castle != null) {
			LOG.debug("Castle retrieved from db: " + castle.getId());
		} else {
			LOG.debug("Castle not in db, creating new Castle: " + id);
			castle = new CastleData(id);
		}
		return castle;
	}
	@Override
	public void saveCastles(final String serverId, final List<CastleData> castleDataList) {
		for (CastleData castleData : castleDataList) {
			saveCastle(serverId, castleData);
		}
	}
	@Override
    public void saveCastle(final String serverId, final CastleData castleData) {
    	setCastlesCollection(serverId);
    	// Uncomment to make sure array data is set
    	if(castleData.getHistoricPoints() == null) {
    		castleData.setHistoricPoints(new ArrayList<HistoricPointsData>());
    	}
    	if(castleData.getHistoricCastleName() == null) {
    		castleData.setHistoricCastleName(new ArrayList<HistoricNameData>());
    	}
    	if(castleData.getHistoricPlayerName() == null) {
    		castleData.setHistoricPlayerName(new ArrayList<HistoricPlayerData>());
    	}
    	if(castleData.getHistoricAllianceName() == null) {
    		castleData.setHistoricAllianceName(new ArrayList<HistoricAllianceData>());
    	}
    	LOG.debug("Saving castleData: " + castleData.getId());
		castlesCollection.save(castleData);
	}

	@Override
	public int getAllianceIdForPlayerId(final String serverId, final int playerId) {
		setCastlesCollection(serverId);
		//TODO index - db.uk3_castles.ensureIndex({playerId:1});
		
		final CastleData castle = castlesCollection.findOne("{playerId: "+playerId+"}").as(CastleData.class);
		final int allianceId = castle.getAllianceId();
		return allianceId;
	}

	@Override
	public int getCastleCountForAlliance(String serverId, int allianceId) {
		setCastlesCollection(serverId);
		//TODO index - db.uk3_castles.ensureIndex({allianceId:1});
		
		long castleCountLong = castlesCollection.count("{allianceId:"+allianceId+"}");
		int castleCount = new BigDecimal(castleCountLong).intValueExact(); 
		return castleCount;
	}
	@Override
	public List<CastleData> getCastlesForAlliance(String serverId, int allianceId) {
		setCastlesCollection(serverId);
		final List<CastleData> allCastles = new ArrayList<CastleData>();
		
		//TODO index - db.uk3_castles.ensureIndex({allianceId:1});
		
		
		final Iterable<CastleData> castlesIterable = castlesCollection.find("{allianceId:"+allianceId+"}").as(CastleData.class);
		
		CollectionUtils.addAll(allCastles, castlesIterable.iterator());
		return null;
	}
	
	@Override
	public int getCastleCountForPlayer(String serverId, int playerId) {
		setCastlesCollection(serverId);
		
		//TODO index - db.uk3_castles.ensureIndex({playerId:1});
		
		long castleCountLong = castlesCollection.count("{playerId:"+playerId+"}");
		int castleCount = new BigDecimal(castleCountLong).intValueExact(); 
		return castleCount;
	}

	@Override
	public List<CastleData> getCastlesForPlayer(String serverId, int playerId) {
		setCastlesCollection(serverId);
		final List<CastleData> allCastles = new ArrayList<CastleData>();
		
		//TODO index - db.uk3_castles.ensureIndex({playerId:1});
		
		final Iterable<CastleData> castlesIterable = castlesCollection.find("{playerId:"+playerId+"}").sort("{name:1}").as(CastleData.class);
		CollectionUtils.addAll(allCastles, castlesIterable.iterator());
		
		return allCastles;
	}

	@Override
	public List<ReportPlayerIdPoints> getMemberPointsForAlliance(final String serverId, final Integer allianceId) {
		setCastlesCollection(serverId);
//		final List<ReportPlayerIdPoints> report = new ArrayList<ReportPlayerIdPoints>();
		
		//TODO index - db.uk3_castles.ensureIndex({allianceId:1,playerId:1,points:1});
		
//	db.uk3_castles.aggregate([
//		{$match:
//			{"allianceId":15029}
//		},
//		{$group:
//			{
//				_id:"$playerId",
//				castlePointsCount:{$sum:"$points"}
//			}
//		},
//		{$sort:
//			{"castlePointsCount":-1}
//		}
//	]);
		
		List<ReportPlayerIdPoints> report = castlesCollection.aggregate("{$match:{\"allianceId\":"+allianceId+"}}")
						.and("{$group:{_id:\"$playerId\",castlePointsCount:{$sum:\"$points\"},castleCount:{$sum:1}}},")
						.and("{$sort:{\"castlePointsCount\":-1}}").as(ReportPlayerIdPoints.class);
//		CollectionUtils.addAll(report, reportIterbale.iterator());
		return report;
	}

	@Override
	public List<HistoricCastleData> getLostCastlesForPlayer(final String serverId, final int playerId) {
		setCastlesCollection(serverId);
		final List<HistoricCastleData> allCastles = new ArrayList<HistoricCastleData>();
		//TODO index - db.uk3_castles.ensureIndex("{\"historicPlayerName.playerId\":1");
		String match1 = "";
      	match1 = match1 + "{ $match:";
      	match1 = match1 + "  {\"historicPlayerName.playerId\":"+playerId+"}";
      	match1 = match1 + "}";
      	
      	String project1 = "";
      	project1 = project1 + "{ $project :";
	    project1 = project1 + "  {";
	    project1 = project1 + "    _id: 1,";
	    project1 = project1 + "    castleId : \"$_id\",";
	    project1 = project1 + "    castleName : \"$name\",";
	    project1 = project1 + "    allOwners : \"$historicPlayerName.playerId\",";
	    project1 = project1 + "    lastUpdate : \"$lastUpdate\"";
	    project1 = project1 + "  }";
	    project1 = project1 + "}";
	    
	    final String unwind = "{ $unwind : \"$allOwners\"},";
	    
	    String match2 = "";
      	match2 = match2 + "{ $match:";
      	match2 = match2 + "  {allOwners:";
		match2 = match2 + "    {";
		match2 = match2 + "      $ne: "+playerId+"";
		match2 = match2 + "    }";
		match2 = match2 + "  }";
      	match2 = match2 + "}";
      	
      	String project2 = "";
      	project2 = project2 + "{ $project :";
      	project2 = project2 + "  {";
		project2 = project2 + "    _id : 1,";
		project2 = project2 + "    castleId : 1,";
		project2 = project2 + "    castleName : 1,";
		project2 = project2 + "    lastUpdate: 1";
		project2 = project2 + "  }";
		project2 = project2 + "}";
		List<HistoricCastleData> castlesIterable = castlesCollection.aggregate(match1).and(project1).and(unwind).and(match2).and(project2).as(HistoricCastleData.class);
		
		CollectionUtils.addAll(allCastles, castlesIterable.iterator());
		return allCastles;
	}
	
	@Override
	public List<HistoricCastleData> getLostCastlesForAlliance(final String serverId, final int allianceId) {
		setCastlesCollection(serverId);
		final List<HistoricCastleData> allCastles = new ArrayList<HistoricCastleData>();
		//TODO index - db.uk3_castles.ensureIndex("{\"historicAllianceName.playerId\":1");
		
//		db.uk3_castles.aggregate([
//	      	{ $match:
//	      		{"historicPlayerName.playerId":108147}
//	      	},
//	      	{ $project :
//	      		{
//	      			_id: 0,
//	      			castleId : "$_id",
//	      			castleName : "$name",
//	      			allOwners : "$historicPlayerName.playerId",
//	      			playerId: "$playerId",
//	      			lastUpdate : "$lastUpdate"
//	      		}
//	      	},
//	      	{ $unwind : "$allOwners"},
//	      	{$match:
//	      		{allOwners:
//	      			{
//	      				$ne: 108147
//	      			}
//	      		}
//	      	},
//	      	{ $project :
//	      		{
//	      			castleId : 1,
//	      			castleName : 1,
//	      			lastUpdate: 1
//	      		}
//	      	},
//	      	{ $limit: 3}
//	      ]);
		
		String match1 = "";
      	match1 = match1 + "{ $match:";
      	match1 = match1 + "  {\"historicPlayerName.playerId\":"+allianceId+"}";
      	match1 = match1 + "}";
      	
      	String project1 = "";
      	project1 = project1 + "{ $project :";
	    project1 = project1 + "  {";
	    project1 = project1 + "    _id: 1,";
	    project1 = project1 + "    castleId : \"$_id\",";
	    project1 = project1 + "    castleName : \"$name\",";
	    project1 = project1 + "    allOwners : \"$historicAllianceName.allianceId\",";
	    project1 = project1 + "    lastUpdate : \"$lastUpdate\"";
	    project1 = project1 + "  }";
	    project1 = project1 + "}";
	    
	    final String unwind = "{ $unwind : \"$allOwners\"},";
	    
	    String match2 = "";
      	match2 = match2 + "{ $match:";
      	match2 = match2 + "  {allOwners:";
		match2 = match2 + "    {";
		match2 = match2 + "      $ne: "+allianceId+"";
		match2 = match2 + "    }";
		match2 = match2 + "  }";
      	match2 = match2 + "}";
      	
      	String project2 = "";
      	project2 = project2 + "{ $project :";
      	project2 = project2 + "  {";
		project2 = project2 + "    _id : 1,";
		project2 = project2 + "    castleId : 1,";
		project2 = project2 + "    castleName : 1,";
		project2 = project2 + "    lastUpdate: 1";
		project2 = project2 + "  }";
		project2 = project2 + "}";
		List<HistoricCastleData> castlesIterable = castlesCollection.aggregate(match1).and(project1).and(unwind).and(match2).and(project2).as(HistoricCastleData.class);
		
		CollectionUtils.addAll(allCastles, castlesIterable.iterator());
		return allCastles;
	}

	@Override
	public List<MapCoordinates> getBoundaryCoordinatesForServer(final String serverId) {
		setCastlesCollection(serverId);

		final List<MapCoordinates> boundaries = new ArrayList<MapCoordinates>();

		Iterable<MapCoordinates> leftmost = castlesCollection.find().sort("{mapX:1}").limit(1).map(
			    new ResultHandler<MapCoordinates>() {
			        @Override
			        public MapCoordinates map(DBObject result) {
			        	final MapCoordinates coord = new MapCoordinates((Integer) result.get("mapX"), (Integer) result.get("mapY"));
			        	return coord; 
			        }
			});
		boundaries.add(leftmost.iterator().next());
		Iterable<MapCoordinates> rightmost = castlesCollection.find().sort("{mapX:-1}").limit(1).map(
			    new ResultHandler<MapCoordinates>() {
			        @Override
			        public MapCoordinates map(DBObject result) {
			        	final MapCoordinates coord = new MapCoordinates((Integer) result.get("mapX"), (Integer) result.get("mapY"));
			        	return coord; 
			        }
			});
		boundaries.add(rightmost.iterator().next());
		Iterable<MapCoordinates> topmost = castlesCollection.find().sort("{mapY:1}").limit(1).map(
			    new ResultHandler<MapCoordinates>() {
			        @Override
			        public MapCoordinates map(DBObject result) {
			        	final MapCoordinates coord = new MapCoordinates((Integer) result.get("mapX"), (Integer) result.get("mapY"));
			        	return coord; 
			        }
			});
		boundaries.add(topmost.iterator().next());
		Iterable<MapCoordinates> bottommost = castlesCollection.find().sort("{mapY:-1}").limit(1).map(
			    new ResultHandler<MapCoordinates>() {
			        @Override
			        public MapCoordinates map(DBObject result) {
			        	final MapCoordinates coord = new MapCoordinates((Integer) result.get("mapX"), (Integer) result.get("mapY"));
			        	return coord; 
			        }
			});
		boundaries.add(bottommost.iterator().next());
		
		return boundaries;
	}
	@Override
	public List<MapCoordinates> getAllCastlesCoordinatesForServer(final String serverId) {
		setCastlesCollection(serverId);
		final String query = "{}";
		return getAllCastlesCoordinatesForQuery(query);
	}
	@Override
	public List<MapCoordinates> getAllCastlesCoordinatesForAlliance(final String serverId, final int allianceId) {
		setCastlesCollection(serverId);
		final String query = "{allianceId:"+allianceId+"}";
		return getAllCastlesCoordinatesForQuery(query);
	}
	@Override
	public List<MapCoordinates> getAllCastlesCoordinatesForPlayer(final String serverId, final int playerId) {
		setCastlesCollection(serverId);
		final String query = "{playerId:"+playerId+"}";
		return getAllCastlesCoordinatesForQuery(query);
	}
	@Override
	public List<MapCoordinates> getAllCastlesCoordinatesForCastle(final String serverId, final int castleId) {
		setCastlesCollection(serverId);
		final String query = "{_id:"+castleId+"}";
		return getAllCastlesCoordinatesForQuery(query);
	}
	private List<MapCoordinates> getAllCastlesCoordinatesForQuery(final String query) {
		
		List<MapCoordinates> allMapItems = new ArrayList<MapCoordinates>();
		
		Iterable<MapCoordinates> allMapItemsIterable = castlesCollection.find(query).map(
			    new ResultHandler<MapCoordinates>() {
			        @Override
			        public MapCoordinates map(DBObject result) {
			        	final MapCoordinates coord = new MapCoordinates((Integer) result.get("mapX"), (Integer) result.get("mapY"));
			        	return coord; 
			        }
			});
		CollectionUtils.addAll(allMapItems, allMapItemsIterable.iterator());
		return allMapItems;
	}
	

	@Override
	public List<ReportCastleChangeData> getNewCastlesForPlayer(final String serverId, final PlayerData playerData, final Date reportCutoffDate) {
		setCastlesCollection(serverId);
		final int playerId = playerData.getId();

		String matchInitial = "";
		matchInitial = matchInitial + "{$match:";
		matchInitial = matchInitial + "  {\"playerId\":"+playerId+"}";
		matchInitial = matchInitial + "}";

		String projectInitial = "";
		projectInitial = projectInitial + "{$project:";
		projectInitial = projectInitial + "  {";
		projectInitial = projectInitial + "    _id: 1,";
		projectInitial = projectInitial + "    name: 1,";
		projectInitial = projectInitial + "    allianceId: 1,";
		projectInitial = projectInitial + "    allianceName: 1,";
		projectInitial = projectInitial + "    playerId: 1,";
		projectInitial = projectInitial + "    playerName: 1,";
		projectInitial = projectInitial + "    historicAllianceName: 1,";
		projectInitial = projectInitial + "    historicPlayerName: 1,";
		projectInitial = projectInitial + "    historicCastleName: 1,";
		projectInitial = projectInitial + "    points: 1";
		projectInitial = projectInitial + "  }";
		projectInitial = projectInitial + "}";

		String unwindPlayer = "{$unwind : \"$historicPlayerName\"}";

		String matchPlayer = "";
		matchPlayer = matchPlayer + "{$match:";
		matchPlayer = matchPlayer + "  {$and:";
		matchPlayer = matchPlayer + "      [";
		matchPlayer = matchPlayer + "        {\"historicPlayerName.playerId\": {$ne:"+playerId+"}},";
		matchPlayer = matchPlayer + "        {\"historicPlayerName.lastUpdate\": {$gte:#}}";
		matchPlayer = matchPlayer + "      ]";
		matchPlayer = matchPlayer + "  }";
		matchPlayer = matchPlayer + "}";

		String unwindAlliance = "{$unwind : \"$historicAllianceName\"}";

		String sortAlliance = "";
		sortAlliance = sortAlliance + "{$sort:";
		sortAlliance = sortAlliance + "  {\"historicAllianceName.lastUpdate\" : 1}";
		sortAlliance = sortAlliance + "}";

		String groupAlliance = "";
		groupAlliance = groupAlliance + "{$group:";
		groupAlliance = groupAlliance + "  {";
		groupAlliance = groupAlliance + "    _id : \"$_id\",";
		groupAlliance = groupAlliance + "    \"historicPlayerName\": {$first: \"$historicPlayerName\"},";
		groupAlliance = groupAlliance + "    \"name\": {$first: \"$name\"},";
		groupAlliance = groupAlliance + "    \"allianceId\": {$first: \"$allianceId\"},";
		groupAlliance = groupAlliance + "    \"allianceName\": {$first: \"$allianceName\"},";
		groupAlliance = groupAlliance + "    \"playerId\": {$first: \"$playerId\"},";
		groupAlliance = groupAlliance + "    \"playerName\": {$first: \"$playerName\"},";
		groupAlliance = groupAlliance + "    \"historicAllianceName\": {$first: \"$historicAllianceName\"},";
		groupAlliance = groupAlliance + "    \"historicCastleName\": {$first: \"$historicCastleName\"},";
		groupAlliance = groupAlliance + "    \"points\": {$first: \"$points\"}";
		groupAlliance = groupAlliance + "  }";
		groupAlliance = groupAlliance + "}";

		String unwindCastle = "{$unwind : \"$historicCastleName\"}";

		String sortCastle = "";
		sortCastle = sortCastle + "{$sort:";
		sortCastle = sortCastle + "  {\"historicCastleName.lastUpdate\" : 1}";
		sortCastle = sortCastle + "}";

		String groupCastle = "";
		groupCastle = groupCastle + "{$group:";
		groupCastle = groupCastle + "  {";
		groupCastle = groupCastle + "    _id : \"$_id\",";
		groupCastle = groupCastle + "    \"historicCastleName\": {$first: \"$historicCastleName\"},";
		groupCastle = groupCastle + "    \"name\": {$first: \"$name\"},";
		groupCastle = groupCastle + "    \"allianceId\": {$first: \"$allianceId\"},";
		groupCastle = groupCastle + "    \"allianceName\": {$first: \"$allianceName\"},";
		groupCastle = groupCastle + "    \"playerId\": {$first: \"$playerId\"},";
		groupCastle = groupCastle + "    \"playerName\": {$first: \"$playerName\"},";
		groupCastle = groupCastle + "    \"historicAllianceName\": {$first: \"$historicAllianceName\"},";
		groupCastle = groupCastle + "    \"historicPlayerName\": {$first: \"$historicPlayerName\"},";
		groupCastle = groupCastle + "    \"points\": {$first: \"$points\"}";
		groupCastle = groupCastle + "  }";
		groupCastle = groupCastle + "}";

		String projectEnd = "";
		projectEnd = projectEnd + "{$project:";
		projectEnd = projectEnd + "  {";
		projectEnd = projectEnd + "    _id : \"$_id\",";
		projectEnd = projectEnd + "    lastUpdate : {$add:[\"$historicAllianceName.lastUpdate\",86400000]},";
		projectEnd = projectEnd + "    points : \"$points\",";
		projectEnd = projectEnd + "    allianceNameStart : \"$historicAllianceName.allianceName\",";
		projectEnd = projectEnd + "    allianceIdStart : \"$historicAllianceName.allianceId\",";
		projectEnd = projectEnd + "    playerNameStart : \"$historicPlayerName.playerName\",";
		projectEnd = projectEnd + "    playerIdStart : \"$historicPlayerName.playerId\",";
		projectEnd = projectEnd + "    castleNameStart : \"$historicCastleName.name\",";
		projectEnd = projectEnd + "    allianceNameEnd : \"$allianceName\",";
		projectEnd = projectEnd + "    allianceIdEnd : \"$allianceId\",";
		projectEnd = projectEnd + "    playerNameEnd : \"$playerName\",";
		projectEnd = projectEnd + "    playerIdEnd : \"$playerId\",";
		projectEnd = projectEnd + "    castleNameEnd : \"$name\"";
		projectEnd = projectEnd + "  }";
		projectEnd = projectEnd + "}";

		String sortEnd = "";
		sortEnd = sortEnd + "{$sort:";
		sortEnd = sortEnd + "  {\"lastUpdate\" : -1}";
		sortEnd = sortEnd + "}";
		
//		db.uk3_castles.aggregate([
//	      	{$match:
//	      		{"playerId":108147}
//	      	},
//	      	{ $project :
//	      		{
//	      			_id : 1,
//	      			name : 1,
//	      			allianceId : 1,
//	      			allianceName : 1,
//	      			playerId : 1,
//	      			playerName : 1,
//	      			historicAllianceName : 1,
//	      			historicPlayerName : 1,
//	      			historicCastleName : 1,
//	      			points : 1
//	      		}
//	      	},
//	      	{$unwind : "$historicPlayerName"},
//	      	{$match :
//	      		{$and:
//	      			[
//	      				{"historicPlayerName.allianceId": {$ne:108147}},
//	      				{"historicPlayerName.lastUpdate": {$gte:ISODate("2013-11-14T00:00:00Z")}}
//	      			]
//	      		}
//	      	},
//	      	{$unwind : "$historicNameName"},
//	      	{$sort :
//	      		{"historicNameName.lastUpdate" : 1}
//	      	},
//	      	{$group :
//	      		{
//	      			_id : "$_id",
//	      			"historicPlayerName": {$first: "$historicPlayerName"},
//	      			"name": {$first: "$name"},
//	      			"allianceId": {$first: "$allianceId"},
//	      			"allianceName": {$first: "$allianceName"},
//	      			"playerId": {$first: "$playerId"},
//	      			"playerName": {$first: "$playerName"},
//	      			"historicAllianceName": {$first: "$historicAllianceName"},
//	      			"historicCastleName": {$first: "$historicCastleName"},
//	      			"points": {$first: "$points"}
//	      		}
//	      	},
//	      	{$unwind : "$historicCastleName"},
//	      	{$sort :
//	      		{"historicCastleName.lastUpdate" : 1}
//	      	},
//	      	{$group :
//	      		{
//	      			_id : "$_id",
//	      			"historicCastleName": {$first: "$historicCastleName"},
//	      			"name": {$first: "$name"},
//	      			"allianceId": {$first: "$allianceId"},
//	      			"allianceName": {$first: "$allianceName"},
//	      			"playerId": {$first: "$playerId"},
//	      			"playerName": {$first: "$playerName"},
//	      			"historicAllianceName": {$first: "$historicAllianceName"},
//	      			"historicPlayerName": {$first: "$historicPlayerName"},
//	      			"points": {$first: "$points"}
//	      		}
//	      	},
//	      	{$project :
//	      		{
//	      			_id : "$_id",
//	      			lastUpdate : {$add:["$historicAllianceName.lastUpdate",86400000]},
//	      			points : "$points",
//	      			allianceNameStart : "$historicAllianceName.allianceName",
//	      			allianceIdStart : "$historicAllianceName.allianceId",
//	      			playerNameStart : "$historicPlayerName.playerName",
//	      			playerIdStart : "$historicPlayerName.playerId",
//	      			castleNameStart : "$historicCastleName.name",
//	      			
//	      			allianceNameEnd : "$allianceName",
//	      			allianceIdEnd : "$allianceId",
//	      			playerNameEnd : "$playerName",
//	      			playerIdEnd : "$playerId",
//	      			castleNameEnd : "$name"
//	      		}
//	      	},
//	      	{$sort :
//	      		{"lastUpdate" : 1}
//	      	},
//	      	{$limit:10}
//	      ]);
		
		List<ReportCastleChangeData> newCastles = castlesCollection
			.aggregate(matchInitial)
			.and(projectInitial)
			.and(unwindPlayer)
			.and(matchPlayer, reportCutoffDate)
			.and(unwindAlliance)
			.and(sortAlliance)
			.and(groupAlliance)
			.and(unwindCastle)
			.and(groupCastle)
			.and(projectEnd)
			.and(sortEnd)
			.as(ReportCastleChangeData.class);
		
		return newCastles;
	}

	@Override
	public List<ReportCastleChangeData> getNewCastlesForAlliance(final String serverId, final AllianceData allianceData, final Date reportCutoffDate) {
		setCastlesCollection(serverId);
		final int allianceId = allianceData.getId();

		String matchInitial = "";
		matchInitial = matchInitial + "{$match:";
		matchInitial = matchInitial + "  {\"allianceId\":"+allianceId+"}";
		matchInitial = matchInitial + "}";

		String projectInitial = "";
		projectInitial = projectInitial + "{$project:";
		projectInitial = projectInitial + "  {";
		projectInitial = projectInitial + "    _id: 1,";
		projectInitial = projectInitial + "    name: 1,";
		projectInitial = projectInitial + "    allianceId: 1,";
		projectInitial = projectInitial + "    allianceName: 1,";
		projectInitial = projectInitial + "    playerId: 1,";
		projectInitial = projectInitial + "    playerName: 1,";
		projectInitial = projectInitial + "    historicAllianceName: 1,";
		projectInitial = projectInitial + "    historicPlayerName: 1,";
		projectInitial = projectInitial + "    historicCastleName: 1,";
		projectInitial = projectInitial + "    points: 1";
		projectInitial = projectInitial + "  }";
		projectInitial = projectInitial + "}";

		String unwindAlliance = "{$unwind : \"$historicAllianceName\"}";

		String matchAlliance = "";
		matchAlliance = matchAlliance + "{$match:";
		matchAlliance = matchAlliance + "  {$and:";
		matchAlliance = matchAlliance + "      [";
		matchAlliance = matchAlliance + "        {\"historicAllianceName.allianceId\": {$ne:"+allianceId+"}},";
		matchAlliance = matchAlliance + "        {\"historicAllianceName.lastUpdate\": {$gte:#}}";
		matchAlliance = matchAlliance + "      ]";
		matchAlliance = matchAlliance + "  }";
		matchAlliance = matchAlliance + "}";

		String unwindPlayer = "{$unwind : \"$historicPlayerName\"}";

		String sortPlayer = "";
		sortPlayer = sortPlayer + "{$sort:";
		sortPlayer = sortPlayer + "  {\"historicPlayerName.lastUpdate\" : 1}";
		sortPlayer = sortPlayer + "}";

		String groupPlayer = "";
		groupPlayer = groupPlayer + "{$group:";
		groupPlayer = groupPlayer + "  {";
		groupPlayer = groupPlayer + "    _id : \"$_id\",";
		groupPlayer = groupPlayer + "    \"historicPlayerName\": {$first: \"$historicPlayerName\"},";
		groupPlayer = groupPlayer + "    \"name\": {$first: \"$name\"},";
		groupPlayer = groupPlayer + "    \"allianceId\": {$first: \"$allianceId\"},";
		groupPlayer = groupPlayer + "    \"allianceName\": {$first: \"$allianceName\"},";
		groupPlayer = groupPlayer + "    \"playerId\": {$first: \"$playerId\"},";
		groupPlayer = groupPlayer + "    \"playerName\": {$first: \"$playerName\"},";
		groupPlayer = groupPlayer + "    \"historicAllianceName\": {$first: \"$historicAllianceName\"},";
		groupPlayer = groupPlayer + "    \"historicCastleName\": {$first: \"$historicCastleName\"},";
		groupPlayer = groupPlayer + "    \"points\": {$first: \"$points\"}";
		groupPlayer = groupPlayer + "  }";
		groupPlayer = groupPlayer + "}";

		String unwindCastle = "{$unwind : \"$historicCastleName\"}";

		String sortCastle = "";
		sortCastle = sortCastle + "{$sort:";
		sortCastle = sortCastle + "  {\"historicCastleName.lastUpdate\" : 1}";
		sortCastle = sortCastle + "}";

		String groupCastle = "";
		groupCastle = groupCastle + "{$group:";
		groupCastle = groupCastle + "  {";
		groupCastle = groupCastle + "    _id : \"$_id\",";
		groupCastle = groupCastle + "    \"historicCastleName\": {$first: \"$historicCastleName\"},";
		groupCastle = groupCastle + "    \"name\": {$first: \"$name\"},";
		groupCastle = groupCastle + "    \"allianceId\": {$first: \"$allianceId\"},";
		groupCastle = groupCastle + "    \"allianceName\": {$first: \"$allianceName\"},";
		groupCastle = groupCastle + "    \"playerId\": {$first: \"$playerId\"},";
		groupCastle = groupCastle + "    \"playerName\": {$first: \"$playerName\"},";
		groupCastle = groupCastle + "    \"historicAllianceName\": {$first: \"$historicAllianceName\"},";
		groupCastle = groupCastle + "    \"historicPlayerName\": {$first: \"$historicPlayerName\"},";
		groupCastle = groupCastle + "    \"points\": {$first: \"$points\"}";
		groupCastle = groupCastle + "  }";
		groupCastle = groupCastle + "}";

		String projectEnd = "";
		projectEnd = projectEnd + "{$project:";
		projectEnd = projectEnd + "  {";
		projectEnd = projectEnd + "    _id : \"$_id\",";
		projectEnd = projectEnd + "    lastUpdate : {$add:[\"$historicAllianceName.lastUpdate\",86400000]},";
		projectEnd = projectEnd + "    points : \"$points\",";
		projectEnd = projectEnd + "    allianceNameStart : \"$historicAllianceName.allianceName\",";
		projectEnd = projectEnd + "    allianceIdStart : \"$historicAllianceName.allianceId\",";
		projectEnd = projectEnd + "    playerNameStart : \"$historicPlayerName.playerName\",";
		projectEnd = projectEnd + "    playerIdStart : \"$historicPlayerName.playerId\",";
		projectEnd = projectEnd + "    castleNameStart : \"$historicCastleName.name\",";
		projectEnd = projectEnd + "    allianceNameEnd : \"$allianceName\",";
		projectEnd = projectEnd + "    allianceIdEnd : \"$allianceId\",";
		projectEnd = projectEnd + "    playerNameEnd : \"$playerName\",";
		projectEnd = projectEnd + "    playerIdEnd : \"$playerId\",";
		projectEnd = projectEnd + "    castleNameEnd : \"$name\"";
		projectEnd = projectEnd + "  }";
		projectEnd = projectEnd + "}";

		String sortEnd = "";
		sortEnd = sortEnd + "{$sort:";
		sortEnd = sortEnd + "  {\"lastUpdate\" : -1}";
		sortEnd = sortEnd + "}";
		
//		db.uk3_castles.aggregate([
//	      	{$match:
//	      		{"allianceId":15029}
//	      	},
//	      	{ $project :
//	      		{
//	      			_id : 1,
//	      			name : 1,
//	      			allianceId : 1,
//	      			allianceName : 1,
//	      			playerId : 1,
//	      			playerName : 1,
//	      			historicAllianceName : 1,
//	      			historicPlayerName : 1,
//	      			historicCastleName : 1,
//	      			points : 1
//	      		}
//	      	},
//	      	{$unwind : "$historicAllianceName"},
//	      	{$match :
//	      		{$and:
//	      			[
//	      				{"historicAllianceName.allianceId": {$ne:15029}},
//	      				{"historicAllianceName.lastUpdate": {$gte:ISODate("2013-11-19T00:00:00Z")}}
//	      			]
//	      		}
//	      	},
//	      	{$unwind : "$historicPlayerName"},
//	      	{$sort :
//	      		{"historicPlayerName.lastUpdate" : 1}
//	      	},
//	      	{$group :
//	      		{
//	      			_id : "$_id",
//	      			"historicPlayerName": {$first: "$historicPlayerName"},
//	      			"name": {$first: "$name"},
//	      			"allianceId": {$first: "$allianceId"},
//	      			"allianceName": {$first: "$allianceName"},
//	      			"playerId": {$first: "$playerId"},
//	      			"playerName": {$first: "$playerName"},
//	      			"historicAllianceName": {$first: "$historicAllianceName"},
//	      			"historicCastleName": {$first: "$historicCastleName"},
//	      			"points": {$first: "$points"}
//	      		}
//	      	},
//	      	{$unwind : "$historicCastleName"},
//	      	{$sort :
//	      		{"historicCastleName.lastUpdate" : 1}
//	      	},
//	      	{$group :
//	      		{
//	      			_id : "$_id",
//	      			"historicCastleName": {$first: "$historicCastleName"},
//	      			"name": {$first: "$name"},
//	      			"allianceId": {$first: "$allianceId"},
//	      			"allianceName": {$first: "$allianceName"},
//	      			"playerId": {$first: "$playerId"},
//	      			"playerName": {$first: "$playerName"},
//	      			"historicAllianceName": {$first: "$historicAllianceName"},
//	      			"historicPlayerName": {$first: "$historicPlayerName"},
//	      			"points": {$first: "$points"}
//	      		}
//	      	},
//	      	{$project :
//	      		{
//	      			_id : "$_id",
//	      			lastUpdate : {$add:["$historicAllianceName.lastUpdate",86400000]},
//	      			points : "$points",
//	      			allianceNameStart : "$historicAllianceName.allianceName",
//	      			allianceIdStart : "$historicAllianceName.allianceId",
//	      			playerNameStart : "$historicPlayerName.playerName",
//	      			playerIdStart : "$historicPlayerName.playerId",
//	      			castleNameStart : "$historicCastleName.name",
//	      			
//	      			allianceNameEnd : "$allianceName",
//	      			allianceIdEnd : "$allianceId",
//	      			playerNameEnd : "$playerName",
//	      			playerIdEnd : "$playerId",
//	      			castleNameEnd : "$name"
//	      		}
//	      	},
//	      	{$sort :
//	      		{"lastUpdate" : 1}
//	      	},
//	      	{$limit:10}
//	      ]);
		
		List<ReportCastleChangeData> newCastles = castlesCollection
			.aggregate(matchInitial)
			.and(projectInitial)
			.and(unwindAlliance)
			.and(matchAlliance, reportCutoffDate)
			.and(unwindPlayer)
			.and(sortPlayer)
			.and(groupPlayer)
			.and(unwindCastle)
			.and(groupCastle)
			.and(projectEnd)
			.and(sortEnd)
			.as(ReportCastleChangeData.class);
		
		return newCastles;
	}
	@Override
	public List<ReportCastleChangeData> getLostCastlesForPlayer(final String serverId, final PlayerData playerData, final Date reportCutoffDate) {
		setCastlesCollection(serverId);
		final int playerId = playerData.getId();
      	
//		db.uk3_castles.aggregate([
//	      	{$match:
//	      		{$and:
//	      			[
//	      				{"playerId":{$ne:110522}},
//	      				{"historicPlayerName.playerId":110522},
//	      				{_id:228143}
//	      			]
//	      		}
//	      	},
//	      	{ $project :
//	      		{
//	      			_id : 1,
//	      			name : 1,
//	      			allianceId : 1,
//	      			allianceName : 1,
//	      			playerId : 1,
//	      			playerName : 1,
//	      			historicAllianceName : 1,
//	      			historicPlayerName : 1,
//	      			historicCastleName : 1,
//	      			points : 1
//	      		}
//	      	},
//	      	{$unwind : "$historicPlayerName"},
//	      	{$match :
//	      		{$and:
//	      			[
//	      				{"historicPlayerName.playerId": 110522},
//	      				{"historicPlayerName.lastUpdate": {$gte:ISODate("2013-11-15T00:00:00Z")}}
//	      			]
//	      		}
//	      	},
//	      	{$unwind : "$historicAllianceName"},
//	      	{$match :
//	      		{"historicAllianceName.lastUpdate": {$gte:ISODate("2013-11-15T00:00:00Z")}}
//	      	},
//	      	{$group :
//	      		{
//	      			_id : "$_id",
//	      			"historicPlayerName": {$first: "$historicPlayerName"},
//	      			"name": {$first: "$name"},
//	      			"allianceId": {$first: "$allianceId"},
//	      			"allianceName": {$first: "$allianceName"},
//	      			"playerId": {$first: "$playerId"},
//	      			"playerName": {$first: "$playerName"},
//	      			"historicAllianceName": {$first: "$historicAllianceName"},
//	      			"historicCastleName": {$first: "$historicCastleName"},
//	      			"points": {$first: "$points"}
//	      		}
//	      	},
//	      	{$unwind : "$historicCastleName"},
//	      	{$match :
//	      		{"historicCastleName.lastUpdate": {$gte:ISODate("2013-11-15T00:00:00Z")}}
//	      	},
//	      	{$group :
//	      		{
//	      			_id : "$_id",
//	      			"historicPlayerName": {$first: "$historicPlayerName"},
//	      			"name": {$first: "$name"},
//	      			"allianceId": {$first: "$allianceId"},
//	      			"allianceName": {$first: "$allianceName"},
//	      			"playerId": {$first: "$playerId"},
//	      			"playerName": {$first: "$playerName"},
//	      			"historicAllianceName": {$first: "$historicAllianceName"},
//	      			"historicCastleName": {$first: "$historicCastleName"},
//	      			"points": {$first: "$points"}
//	      		}
//	      	},
//	      	{$project :
//	      		{
//	      			_id : "$_id",
//	      			lastUpdate : {$add:["$historicAllianceName.lastUpdate",86400000]},
//	      			points : "$points",
//	      			allianceNameStart : "$historicAllianceName.allianceName",
//	      			allianceIdStart : "$historicAllianceName.allianceId",
//	      			playerNameStart : "$historicPlayerName.playerName",
//	      			playerIdStart : "$historicPlayerName.playerId",
//	      			castleNameStart : "$historicCastleName.name",
//	      			
//	      			allianceNameEnd : "$allianceName",
//	      			allianceIdEnd : "$allianceId",
//	      			playerNameEnd : "$playerName",
//	      			playerIdEnd : "$playerId",
//	      			castleNameEnd : "$name"
//	      		}
//	      	},
//	      	{$sort :
//	      		{"lastUpdate" : 1}
//	      	},
//	      	{$limit:10}
//	      ]);
		
		String matchInitial = "";
		matchInitial = matchInitial + "{$match:";
		matchInitial = matchInitial + "  {$and:";
		matchInitial = matchInitial + "    [";
		matchInitial = matchInitial + "      {\"playerId\":{$ne:"+playerId+"}},";
		matchInitial = matchInitial + "      {\"historicPlayerName.playerId\":"+playerId+"}";
		matchInitial = matchInitial + "    ]";
		matchInitial = matchInitial + "  }";
		matchInitial = matchInitial + "}";

		String projectInitial = "";
		projectInitial = projectInitial + "{ $project :";
		projectInitial = projectInitial + "  {";
		projectInitial = projectInitial + "    _id : 1,";
		projectInitial = projectInitial + "    name : 1,";
		projectInitial = projectInitial + "    allianceId : 1,";
		projectInitial = projectInitial + "    allianceName : 1,";
		projectInitial = projectInitial + "    playerId : 1,";
		projectInitial = projectInitial + "    playerName : 1,";
		projectInitial = projectInitial + "    historicAllianceName : 1,";
		projectInitial = projectInitial + "    historicPlayerName : 1,";
		projectInitial = projectInitial + "    historicCastleName : 1,";
		projectInitial = projectInitial + "    points : 1";
		projectInitial = projectInitial + "  }";
		projectInitial = projectInitial + "}";

		String unwindPlayer = "{$unwind : \"$historicPlayerName\"}";

		String matchPlayer = "";
		matchPlayer = matchPlayer + "{$match :";
		matchPlayer = matchPlayer + "  {$and:";
		matchPlayer = matchPlayer + "    [";
		matchPlayer = matchPlayer + "      {\"historicPlayerName.playerId\": "+playerId+"},";
		matchPlayer = matchPlayer + "      {\"historicPlayerName.lastUpdate\": {$gte:#}}";
		matchPlayer = matchPlayer + "    ]";
		matchPlayer = matchPlayer + "  }";
		matchPlayer = matchPlayer + "}";

		String unwindAlliance = "{$unwind : \"$historicAllianceName\"}";

		String matchAlliance = "";
		matchAlliance = matchAlliance + "{$match :";
		matchAlliance = matchAlliance + "  {\"historicAllianceName.lastUpdate\": {$gte:#}}";
		matchAlliance = matchAlliance + "}";

		String groupAlliance = "";
		groupAlliance = groupAlliance + "{$group :";
		groupAlliance = groupAlliance + "  {";
		groupAlliance = groupAlliance + "    _id : \"$_id\",";
		groupAlliance = groupAlliance + "    \"historicPlayerName\": {$first: \"$historicPlayerName\"},";
		groupAlliance = groupAlliance + "    \"name\": {$first: \"$name\"},";
		groupAlliance = groupAlliance + "    \"allianceId\": {$first: \"$allianceId\"},";
		groupAlliance = groupAlliance + "    \"allianceName\": {$first: \"$allianceName\"},";
		groupAlliance = groupAlliance + "    \"playerId\": {$first: \"$playerId\"},";
		groupAlliance = groupAlliance + "    \"playerName\": {$first: \"$playerName\"},";
		groupAlliance = groupAlliance + "    \"historicAllianceName\": {$first: \"$historicAllianceName\"},";
		groupAlliance = groupAlliance + "    \"historicCastleName\": {$first: \"$historicCastleName\"},";
		groupAlliance = groupAlliance + "    \"points\": {$first: \"$points\"}";
		groupAlliance = groupAlliance + "  }";
		groupAlliance = groupAlliance + "}";
			
		String unwindCastle = "{$unwind : \"$historicCastleName\"}";
		
		String matchCastle = "";
		matchCastle = matchCastle + "{$match :";
		matchCastle = matchCastle + "  {\"historicCastleName.lastUpdate\": {$gte:#}}";
		matchCastle = matchCastle + "}";
		
		String groupCastle = "";
		groupCastle = groupCastle + "{$group :";
		groupCastle = groupCastle + "  {";
		groupCastle = groupCastle + "    _id : \"$_id\",";
		groupCastle = groupCastle + "    \"historicPlayerName\": {$first: \"$historicPlayerName\"},";
		groupCastle = groupCastle + "    \"name\": {$first: \"$name\"},";
		groupCastle = groupCastle + "    \"allianceId\": {$first: \"$allianceId\"},";
		groupCastle = groupCastle + "    \"allianceName\": {$first: \"$allianceName\"},";
		groupCastle = groupCastle + "    \"playerId\": {$first: \"$playerId\"},";
		groupCastle = groupCastle + "    \"playerName\": {$first: \"$playerName\"},";
		groupCastle = groupCastle + "    \"historicAllianceName\": {$first: \"$historicAllianceName\"},";
		groupCastle = groupCastle + "    \"historicCastleName\": {$first: \"$historicCastleName\"},";
		groupCastle = groupCastle + "    \"points\": {$first: \"$points\"}";
		groupCastle = groupCastle + "  }";
		groupCastle = groupCastle + "}";
		
		String projectEnd = "";
		projectEnd = projectEnd + "{$project :";
		projectEnd = projectEnd + "  {";
		projectEnd = projectEnd + "    _id : \"$_id\",";
		projectEnd = projectEnd + "    lastUpdate : {$add:[\"$historicAllianceName.lastUpdate\",86400000]},";
		projectEnd = projectEnd + "    points : \"$points\",";
		projectEnd = projectEnd + "    allianceNameStart : \"$historicAllianceName.allianceName\",";
		projectEnd = projectEnd + "    allianceIdStart : \"$historicAllianceName.allianceId\",";
		projectEnd = projectEnd + "    playerNameStart : \"$historicPlayerName.playerName\",";
		projectEnd = projectEnd + "    playerIdStart : \"$historicPlayerName.playerId\",";
		projectEnd = projectEnd + "    castleNameStart : \"$historicCastleName.name\",";
					
		projectEnd = projectEnd + "    allianceNameEnd : \"$allianceName\",";
		projectEnd = projectEnd + "    allianceIdEnd : \"$allianceId\",";
		projectEnd = projectEnd + "    playerNameEnd : \"$playerName\",";
		projectEnd = projectEnd + "    playerIdEnd : \"$playerId\",";
		projectEnd = projectEnd + "    castleNameEnd : \"$name\"";
		projectEnd = projectEnd + "  }";
		projectEnd = projectEnd + "},";
		
		String sortEnd = "";
		sortEnd = sortEnd + "{$sort :";
		sortEnd = sortEnd + "  {\"lastUpdate\" : 1}";
		sortEnd = sortEnd + "},";
		
		List<ReportCastleChangeData> lostCastles = castlesCollection
				.aggregate(matchInitial)
				.and(projectInitial)
				.and(unwindPlayer)
				.and(matchPlayer, reportCutoffDate)
				.and(unwindAlliance)
				.and(matchAlliance, reportCutoffDate)
				.and(groupAlliance)
				.and(unwindCastle)
				.and(matchCastle, reportCutoffDate)
				.and(groupCastle)
				//.and(unwindCastle)
				.and(projectEnd)
				.and(sortEnd)
				.as(ReportCastleChangeData.class);

		return lostCastles;
		
	}@Override
	public List<ReportCastleChangeData> getLostCastlesForAlliance(final String serverId, final AllianceData allianceData, final Date reportCutoffDate) {
		setCastlesCollection(serverId);
		final int allianceId = allianceData.getId();
      	
//		db.uk3_castles.aggregate([
//          	{$match:
//          		{$and:
//          			[
//          				{"allianceId":{$ne:7371}},
//          				{"historicAllianceName.allianceId":7371},
//          				//{"historicAllianceName.allianceId":15029},
//          				//{_id:229799}
//          			]
//          		}
//          	},
//          	{ $project :
//          		{
//          			_id : 1,
//          			name : 1,
//          			allianceId : 1,
//          			allianceName : 1,
//          			playerId : 1,
//          			playerName : 1,
//          			historicAllianceName : 1,
//          			historicPlayerName : 1,
//          			historicCastleName : 1,
//          			points : 1
//          		}
//          	},
//          	{$unwind : "$historicAllianceName"},
//          	{$match :
//          		{$and:
//          			[
//          				{"historicAllianceName.allianceId": 7371},
//          				{"historicAllianceName.lastUpdate": {$gte:ISODate("2013-11-19T00:00:00Z")}}
//          			]
//          		}
//          	},
//          	{$unwind : "$historicPlayerName"},
//				{$match :
//					{"historicPlayerName.lastUpdate": {$gte:ISODate("2013-11-19T00:00:00Z")}}
//				},
//          	{$sort:{"historicPlayerName.lastUpdate":1}},
//          	{$group :
//          		{
//          			_id : "$_id",
//          			"playerIdStart": {$first: "$historicPlayerName.playerId"},
//          			"playerNameStart": {$first: "$historicPlayerName.playerName"},
//          			
//          			"playerIdEnd": {$last: "$historicPlayerName.playerId"},
//          			"playerNameEnd": {$last: "$historicPlayerName.playerName"},
//          			
//          			"allianceIdEnd": {$first: "$allianceId"},
//          			"allianceNameEnd": {$first: "$allianceName"},
//          			
//          			"allianceIdStart": {$first: "$historicAllianceName.allianceId"},
//          			"allianceNameStart": {$first: "$historicAllianceName.allianceName"},
//          			
//          			"castleNameEnd": {$first: "$name"},
//          			"historicCastleName": {$first: "$historicCastleName"},
//          			"points": {$first: "$points"}
//          		}
//          	},
//          	{$unwind : "$historicCastleName"},
//          	{$sort:{"historicCastleName.lastUpdate":1}},
//          	{$group :
//          		{
//          			_id : "$_id",
//          			"historicPlayerIdStart": {$first: "$historicPlayerIdStart"},
//          			"historicPlayerNameStart": {$first: "$historicPlayerNameStart"},
//          			
//          			"historicPlayerIdEnd": {$last: "$historicPlayerIdEnd"},
//          			"historicPlayerNameEnd": {$last: "$historicPlayerNameEnd"},
//          			
//          			"allianceIdEnd": {$first: "$allianceIdEnd"},
//          			"allianceNameEnd": {$first: "$allianceNameEnd"},
//          			
//          			"allianceIdStart": {$first: "$allianceIdStart"},
//          			"allianceNameStart": {$first: "$allianceNameStart"},
//          			
//          			"castleNameEnd": {$first: "$castleNameEnd"},
//          			"castleNameStart": {$first: "$historicCastleName.name"},
//          			"points": {$first: "$points"}
//          		}
//          	},
//          	{$limit:10}
//          ]);
		
		
		
		
		
		
		
		String matchInitial = "";
		matchInitial = matchInitial + "{$match:";
		matchInitial = matchInitial + "  {$and:";
		matchInitial = matchInitial + "    [";
		matchInitial = matchInitial + "      {\"allianceId\": {$ne:"+allianceId+"}},";
		matchInitial = matchInitial + "      {\"historicAllianceName.allianceId\": "+allianceId+"}";
		matchInitial = matchInitial + "    ]";
		matchInitial = matchInitial + "  }";
		matchInitial = matchInitial + "}";
		
		String projectInitial = "";
		projectInitial = projectInitial + "{$project:";
		projectInitial = projectInitial + "  {";
		projectInitial = projectInitial + "    _id: 1,";
		projectInitial = projectInitial + "    name: 1,";
		projectInitial = projectInitial + "    allianceId: 1,";
		projectInitial = projectInitial + "    allianceName: 1,";
		projectInitial = projectInitial + "    playerId: 1,";
		projectInitial = projectInitial + "    playerName: 1,";
		projectInitial = projectInitial + "    historicAllianceName: 1,";
		projectInitial = projectInitial + "    historicPlayerName: 1,";
		projectInitial = projectInitial + "    historicCastleName: 1,";
		projectInitial = projectInitial + "    points: 1";
		projectInitial = projectInitial + "  }";
		projectInitial = projectInitial + "}";
		
		String unwindAlliance = "{$unwind : \"$historicAllianceName\"}";
		
		String matchAlliance = "";
		matchAlliance = matchAlliance + "{$match:";
		matchAlliance = matchAlliance + "  {$and:";
		matchAlliance = matchAlliance + "      [";
		matchAlliance = matchAlliance + "        {\"historicAllianceName.allianceId\": "+allianceId+"},";
		matchAlliance = matchAlliance + "        {\"historicAllianceName.lastUpdate\": {$gte:#}}";
		matchAlliance = matchAlliance + "      ]";
		matchAlliance = matchAlliance + "  }";
		matchAlliance = matchAlliance + "}";
		
		String unwindPlayer = "{$unwind : \"$historicPlayerName\"}";
		
		String matchPlayer = "";
		matchPlayer = matchPlayer + "{$match:";
		matchPlayer = matchPlayer + "  {\"historicPlayerName.lastUpdate\": {$gte:#}}";
		matchPlayer = matchPlayer + "}";
		
//		String sortPlayer = "{$sort : {\"$historicPlayerName.lastUpdate\":1}}";
		
		String groupPlayer = "";
		groupPlayer = groupPlayer + "{$group:";
		groupPlayer = groupPlayer + "  {";
		groupPlayer = groupPlayer + "    _id : \"$_id\",";
		groupPlayer = groupPlayer + "    \"playerIdStart\": {$first: \"$historicPlayerName.playerId\"},";
		groupPlayer = groupPlayer + "    \"playerNameStart\": {$first: \"$historicPlayerName.playerName\"},";

		groupPlayer = groupPlayer + "    \"playerIdEnd\": {$last: \"$historicPlayerName.playerId\"},";
		groupPlayer = groupPlayer + "    \"playerNameEnd\": {$last: \"$historicPlayerName.playerName\"},";
		
		groupPlayer = groupPlayer + "    \"allianceIdEnd\": {$first: \"$allianceId\"},";
		groupPlayer = groupPlayer + "    \"allianceNameEnd\": {$first: \"$allianceName\"},";
		
		groupPlayer = groupPlayer + "    \"allianceIdStart\": {$first: \"$historicAllianceName.allianceId\"},";
		groupPlayer = groupPlayer + "    \"allianceNameStart\": {$first: \"$historicAllianceName.allianceName\"},";
		
		groupPlayer = groupPlayer + "    \"castleNameEnd\": {$first: \"$name\"},";
		groupPlayer = groupPlayer + "    \"historicCastleName\": {$first: \"$historicCastleName\"},";
		groupPlayer = groupPlayer + "    \"points\": {$first: \"$points\"},";
		groupPlayer = groupPlayer + "    \"lastUpdate\": {$first: \"$historicPlayerName.lastUpdate\"}";
		groupPlayer = groupPlayer + "  }";
		groupPlayer = groupPlayer + "}";
		
		String unwindCastle = "{$unwind : \"$historicCastleName\"}";
		
//		String sortCastle = "{$sort : {\"$historicCastleName.lastUpdate\":1}}";
		
		String groupCastle = "";
		groupCastle = groupCastle + "{$group:";
		groupCastle = groupCastle + "  {";
		groupCastle = groupCastle + "    _id : \"$_id\",";
		groupCastle = groupCastle + "    \"playerIdStart\": {$first: \"$playerIdStart\"},";
		groupCastle = groupCastle + "    \"playerNameStart\": {$first: \"$playerNameStart\"},";

		groupCastle = groupCastle + "    \"playerIdEnd\": {$last: \"$playerIdEnd\"},";
		groupCastle = groupCastle + "    \"playerNameEnd\": {$last: \"$playerNameEnd\"},";
		
		groupCastle = groupCastle + "    \"allianceIdEnd\": {$first: \"$allianceIdEnd\"},";
		groupCastle = groupCastle + "    \"allianceNameEnd\": {$first: \"$allianceNameEnd\"},";
		
		groupCastle = groupCastle + "    \"allianceIdStart\": {$first: \"$allianceIdStart\"},";
		groupCastle = groupCastle + "    \"allianceNameStart\": {$first: \"$allianceNameStart\"},";
		
		groupCastle = groupCastle + "    \"castleNameEnd\": {$first: \"$castleNameEnd\"},";
		groupCastle = groupCastle + "    \"castleNameStart\": {$first: \"$historicCastleName.name\"},";
		groupCastle = groupCastle + "    \"points\": {$first: \"$points\"},";
		groupCastle = groupCastle + "    \"lastUpdate\": {$first: \"$lastUpdate\"}";
		groupCastle = groupCastle + "  }";
		groupCastle = groupCastle + "}";
		
		String sortEnd = "";
		sortEnd = sortEnd + "{$sort:";
		sortEnd = sortEnd + "  {\"lastUpdate\" : -1}";
		sortEnd = sortEnd + "}";
		
		String projectEnd = "";
		projectEnd = projectEnd + "{$project:";
		projectEnd = projectEnd + "  {";
		projectEnd = projectEnd + "    _id: \"$_id\",";
		projectEnd = projectEnd + "    playerIdStart: \"$playerIdStart\",";
		projectEnd = projectEnd + "    playerNameStart: \"$playerNameStart\",";
		
		projectEnd = projectEnd + "    playerIdEnd: \"$playerIdEnd\",";
		projectEnd = projectEnd + "    playerNameEnd: \"$playerNameEnd\",";

		projectEnd = projectEnd + "    allianceIdEnd: \"$allianceIdEnd\",";
		projectEnd = projectEnd + "    allianceNameEnd: \"$allianceNameEnd\",";

		projectEnd = projectEnd + "    allianceIdStart: \"$allianceIdStart\",";
		projectEnd = projectEnd + "    allianceNameStart: \"$allianceNameStart\",";
		
		projectEnd = projectEnd + "    castleNameEnd: \"$castleNameEnd\",";
		projectEnd = projectEnd + "    castleNameStart: \"$castleNameStart\",";
		projectEnd = projectEnd + "    points: \"$points\",";
		projectEnd = projectEnd + "    lastUpdate: \"$lastUpdate\",";
		projectEnd = projectEnd + "  }";
		projectEnd = projectEnd + "}";
		
		List<ReportCastleChangeData> lostCastles = castlesCollection
				.aggregate(matchInitial)
				.and(projectInitial)
				.and(unwindAlliance)
				.and(matchAlliance, reportCutoffDate)
				.and(unwindPlayer)
				.and(matchPlayer, reportCutoffDate)
				//.and(sortPlayer)
				.and(groupPlayer)
				.and(unwindCastle)
				//.and(sortCastle)
				.and(groupCastle)
				.and(projectEnd)
				//.and(sortEnd)
				.as(ReportCastleChangeData.class);
		
		
		return lostCastles;
	}

	enum DateCompare {
		EQUALS,
		LATER_THAN,
		EARLIER_THAN
	}
	

	@Override
	public List<TypeaheadCastle> getTypeaheadCastles(final String serverId, final String castleSearch) {

		final SearchDataCastle searchData = new SearchDataCastle(serverId, castleSearch, 0, SearchController.RESULTS_FOR_TYPEAHEAD, 1, 1);
		final SearchDataCastle searchResults = getSearchForCastles(searchData);
		return searchResults.getResults();
	}
	@Override
	public SearchDataCastle getSearchForCastles(final SearchDataCastle searchData) {
		setCastlesCollection(searchData.getServerId());
		
		Object queryString = searchData.getQuery();
		
		String query = "";
		int count = 0;
		int paramCount = 1;
		
		if (((String)queryString).matches("\\d+")) {
			// Search on _id field
			int queryStringInt = Integer.parseInt((String)queryString);
			queryString = queryStringInt;
			query = "{ _id : #}";
			count = (int) castlesCollection.count(query,queryString);
		} else {
			// Search on name field using regex
			query = query + "{$or:";
			query = query + "  [";
			query = query + "    { name : {$regex : #, $options: 'i'}},";
			query = query + "    { normalisedName : {$regex : #, $options: 'i'}}";
			query = query + "  ]";
			query = query + "}";
			paramCount = 2;
			count = (int) castlesCollection.count(query,queryString,queryString);
		}
		
		if (count == 0) {
			query = "{ name : # }";
			paramCount = 1;
			count = (int) castlesCollection.count(query,queryString);
		}
		Iterable<TypeaheadCastle> resultsIterable = null;
		if(paramCount == 1) {
			resultsIterable = castlesCollection.find(query,queryString).skip(searchData.getSkip()).limit(searchData.getLimit()).map(
					new ResultHandler<TypeaheadCastle>() {
			        @Override
			        public TypeaheadCastle map(DBObject result) {
			        	final String name = (String) result.get("name");
			        	final int id = ((Integer) result.get("_id")).intValue();
			        	final String playerName = (String) result.get("playerName");
			        	final String allianceName = (String) result.get("allianceName");
			        	final TypeaheadCastle typeaheadCastle = new TypeaheadCastle(name, id, playerName, allianceName);
			        	return typeaheadCastle;
			        }});
		} else if(paramCount == 2) {
			resultsIterable = castlesCollection.find(query,queryString,queryString).skip(searchData.getSkip()).limit(searchData.getLimit()).map(
					new ResultHandler<TypeaheadCastle>() {
			        @Override
			        public TypeaheadCastle map(DBObject result) {
			        	final String name = (String) result.get("name");
			        	final int id = ((Integer) result.get("_id")).intValue();
			        	final String playerName = (String) result.get("playerName");
			        	final String allianceName = (String) result.get("allianceName");
			        	final TypeaheadCastle typeaheadCastle = new TypeaheadCastle(name, id, playerName, allianceName);
			        	return typeaheadCastle;
			        }});
		}

		final List<TypeaheadCastle> results = new ArrayList<TypeaheadCastle>();
		CollectionUtils.addAll(results, resultsIterable.iterator());
		
		searchData.setResults(results);
		searchData.setTotalResults(count);
		
		final int displayTo = (searchData.getDisplayFrom()-1) + results.size();
		searchData.setDisplayTo(displayTo);
		
		final int lastPageRemainder = count % searchData.getLimit();
		int pageTotal = (count - lastPageRemainder) / searchData.getLimit();
		if (lastPageRemainder > 0) {
			pageTotal = pageTotal + 1;
		}
		searchData.setTotalPages(pageTotal);
		
		return searchData;
	}
	
	@Override
	public List<CastleData> getRivalCastles(String serverId, CastleData castleData) {
		setCastlesCollection(serverId);

		List<CastleData> rivalCastlesDatas = new ArrayList<CastleData>();
		
		final int id = castleData.getId();
		int compareId = id - 2;
		
		Iterable<CastleData> castleDataIterable = castlesCollection.find("{_id:{$gte:"+compareId+"}}").sort("{_id:1}").limit(5).as(CastleData.class);
		CollectionUtils.addAll(rivalCastlesDatas, castleDataIterable.iterator());
		return rivalCastlesDatas;
	}
    /**
	 * @return the jongo
	 */
	public Jongo getJongo() {
		return jongo;
	}

	/**
	 * @param jongo the jongo to set
	 */
	public void setJongo(Jongo jongo) {
		this.jongo = jongo;
	}

	/**
	 * @return the generalUtils
	 */
	public GeneralUtils getGeneralUtils() {
		return generalUtils;
	}

	/**
	 * @param generalUtils the generalUtils to set
	 */
	public void setGeneralUtils(GeneralUtils generalUtils) {
		this.generalUtils = generalUtils;
	}




}
