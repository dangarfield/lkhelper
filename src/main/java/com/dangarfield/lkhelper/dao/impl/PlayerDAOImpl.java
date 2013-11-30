package com.dangarfield.lkhelper.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
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
import com.dangarfield.lkhelper.dao.PlayerDAO;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.historic.HistoricAllianceData;
import com.dangarfield.lkhelper.data.historic.HistoricCastleCountData;
import com.dangarfield.lkhelper.data.historic.HistoricCastleData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;
import com.dangarfield.lkhelper.data.historic.HistoricPointsData;
import com.dangarfield.lkhelper.data.historic.HistoricRankData;
import com.dangarfield.lkhelper.data.report.AllianceLeaderboardData;
import com.dangarfield.lkhelper.data.search.SearchDataPlayer;
import com.dangarfield.lkhelper.data.search.TypeaheadPlayer;
import com.dangarfield.lkhelper.utils.GeneralUtils;
import com.mongodb.DBObject;

public class PlayerDAOImpl implements PlayerDAO {
	
	private static Logger LOG = LogManager.getLogger("PlayerDAOImpl");
	
	private static String BASE_COLLECTION_NAME = "players";
	
	private MongoCollection playersCollection;

	@Autowired
	private Jongo jongo;
	
	@Autowired
	private GeneralUtils generalUtils;
	
    private void setPlayersCollection(final String serverId) {
    	playersCollection = jongo.getCollection(generalUtils.getCollectionNameForServer(BASE_COLLECTION_NAME, serverId));
    }
    
    @Override
    public void ensureIndexes(final String serverId) {
    	setPlayersCollection(serverId);
    	playersCollection.dropIndexes();
    	playersCollection.ensureIndex("{allianceId:1}");
    	playersCollection.ensureIndex("{name:1}");
    	playersCollection.ensureIndex("{normalisedName:1}");
    }

	@Override
	public PlayerData getPlayerForId(final String serverId, final int id) {
		setPlayersCollection(serverId);
		PlayerData player;
		player = playersCollection.findOne("{_id: "+id+"}").as(PlayerData.class);
		
		if (player != null) {
			LOG.debug("Player retrieved from db: " + player.getId());
		} else {
			LOG.debug("Player not in db, creating new Player: " + id);
			player = new PlayerData(id);
		}
		return player;
	}
	@Override
	public void savePlayers(final String serverId, final List<PlayerData> playerDataList) {
		for (PlayerData playerData : playerDataList) {
			savePlayer(serverId, playerData);
		}
	}
	@Override
    public void savePlayer(final String serverId, final PlayerData playerData) {
    	setPlayersCollection(serverId);
    	// Uncomment to make sure array data is set
    	if(playerData.getHistoricPlayerName() == null) {
    		playerData.setHistoricPlayerName(new ArrayList<HistoricNameData>());
    	}
    	if(playerData.getHistoricPoints() == null) {
    		playerData.setHistoricPoints(new ArrayList<HistoricPointsData>());
    	}
    	if(playerData.getHistoricRank() == null) {
    		playerData.setHistoricRank(new ArrayList<HistoricRankData>());
    	}
    	if(playerData.getHistoricAllianceRank() == null) {
    		playerData.setHistoricAllianceRank(new ArrayList<HistoricRankData>());
    	}
    	if(playerData.getHistoricAllianceId() == null) {
    		playerData.setHistoricAllianceId(new ArrayList<HistoricAllianceData>());
    	}
    	if(playerData.getHistoricCastleCount() == null) {
    		playerData.setHistoricCastleCount(new ArrayList<HistoricCastleCountData>());
    	}
    	if(playerData.getHistoricLostCastles() == null) {
    		playerData.setHistoricLostCastles(new ArrayList<HistoricCastleData>());
    	}
    	
    	LOG.debug("Saving playerData: " + playerData.getId());
		playersCollection.save(playerData);
	}

	@Override
	public List<Integer> getAllPlayerIds(final String serverId) {
		setPlayersCollection(serverId);
		List<Integer> allPlayerIds = new ArrayList<Integer>();
		
		Iterable<Integer> playerIdIterable = playersCollection.find().map(
			    new ResultHandler<Integer>() {
			        @Override
			        public Integer map(DBObject result) {
			            return (Integer) result.get("_id");
			        }
			});
		CollectionUtils.addAll(allPlayerIds, playerIdIterable.iterator());
		return allPlayerIds;
	}
	@Override
	public List<Integer> getAllPlayerIdsWithNoAlliance(final String serverId) {
		setPlayersCollection(serverId);
		List<Integer> allPlayerIds = new ArrayList<Integer>();
		
		Iterable<Integer> playerIdIterable = playersCollection.find("{allianceId:0}").map(
			    new ResultHandler<Integer>() {
			        @Override
			        public Integer map(DBObject result) {
			            return (Integer) result.get("_id");
			        }
			});
		CollectionUtils.addAll(allPlayerIds, playerIdIterable.iterator());
		return allPlayerIds;
	}
	@Override
	public List<PlayerData> getAllPlayersForAlliance(final String serverId, final int allianceId) {
		setPlayersCollection(serverId);
		List<PlayerData> allPlayerDatas = new ArrayList<PlayerData>();
		
		Iterable<PlayerData> playerDataIterable = playersCollection.find("{allianceId:"+allianceId+"}").sort("{points:1}").as(PlayerData.class);
		CollectionUtils.addAll(allPlayerDatas, playerDataIterable.iterator());
		return allPlayerDatas;
	}
	
	@Override
	public List<PlayerData> getExPlayersForAlliance(final String serverId, final int allianceId, final Date reportCutoffDate) {
		setPlayersCollection(serverId);
	
		// Step 1: Create empty list of players
		List<PlayerData> exMembers = new ArrayList<PlayerData>();
		
		// Step 2: Find all the ex members
		String findQuery = "";
		findQuery = findQuery + "{";
		findQuery = findQuery + "  allianceId: {$ne:"+allianceId+"},";
		findQuery = findQuery + "  \"historicAllianceId.allianceId\": "+allianceId;
		//TODO: Add reporting cut off date
		findQuery = findQuery + "}";
		Iterable<PlayerData> allExMembersIterable = playersCollection.find(findQuery).as(PlayerData.class);
		
		// Step 3: Iterate through list
		while (allExMembersIterable.iterator().hasNext()) {
			final PlayerData playerData = allExMembersIterable.iterator().next();
			
			// Step 4: Get the historicAllianceData
			final List<HistoricAllianceData> historicAllianceDatas = playerData.getHistoricAllianceId();
			
			// Step 5: Loop through this list in reverse
			Collections.reverse(historicAllianceDatas);
			
			// Step 6: Create a temporary variable to hold the previous alliance history, eg, after we find the alliance the player left, this will be the players new alliance
			
			HistoricAllianceData nextAllianceAlliance = null;
			for (HistoricAllianceData historicAllianceData : historicAllianceDatas) {
				
				
				// Step 7: Get the allianceId and lastUpdated date
				final int historicAllianceDataId = historicAllianceData.getAllianceId();
				
				// Step 8: If the historic allianceID is equal, eg this is when they were last in the alliance
				if (allianceId == historicAllianceDataId) {
					final Date dateOfChange = historicAllianceData.getLastUpdate();
					playerData.setLastUpdate(dateOfChange);
					
					// Step 9: Add the nextAlliance to a fresh array and store as part of the
					final List<HistoricAllianceData> nextAllianceArray = new ArrayList<HistoricAllianceData>();
					nextAllianceArray.add(nextAllianceAlliance);
		        	playerData.setHistoricAllianceId(nextAllianceArray);
		        	playerData.setLastUpdate(dateOfChange);
		        	exMembers.add(playerData);
		        	break;
				}
				
				// Step 10: If the date is earlier than the cut off, break the loop to get to the next castle
				if(reportCutoffDate.compareTo(historicAllianceData.getLastUpdate()) > 0) {
					break;
				}
				nextAllianceAlliance = historicAllianceData;
			}
		}
		return exMembers;
	}
	
	@Override
	public List<TypeaheadPlayer> getTypeaheadPlayers(final String serverId, final String playerSearch) {

		final SearchDataPlayer searchData = new SearchDataPlayer(serverId, playerSearch, 0, SearchController.RESULTS_FOR_TYPEAHEAD, 1, 1);
		final SearchDataPlayer searchResults = getSearchForPlayers(searchData);
		return searchResults.getResults();
	}
	@Override
	public SearchDataPlayer getSearchForPlayers(final SearchDataPlayer searchData) {
		setPlayersCollection(searchData.getServerId());
		
		Object queryString = searchData.getQuery();
		
		String query = "";
		int count = 0;
		int paramCount = 1;
		
		if (((String)queryString).matches("\\d+")) {
			// Search on _id field
			int queryStringInt = Integer.parseInt((String)queryString);
			queryString = queryStringInt;
			query = "{ _id : #}";
			count = (int) playersCollection.count(query,queryString);
			
		} else {
			// Search on name field using regex
			query = query + "{$or:";
			query = query + "  [";
			query = query + "    { name : {$regex : #, $options: 'i'}},";
			query = query + "    { normalisedName : {$regex : #, $options: 'i'}}";
			query = query + "  ]";
			query = query + "}";
			paramCount = 2;
			count = (int) playersCollection.count(query,queryString,queryString);
		}
		
		if (count == 0) {
			query = "{ name : # }";
			paramCount = 1;
			count = (int) playersCollection.count(query,queryString);
		}
		
		Iterable<TypeaheadPlayer> resultsIterable = null;
		if(paramCount == 1) {
			resultsIterable = playersCollection.find(query,queryString).skip(searchData.getSkip()).limit(searchData.getLimit()).map(
					new ResultHandler<TypeaheadPlayer>() {
			        @Override
			        public TypeaheadPlayer map(DBObject result) {
			        	final String name = (String) result.get("name");
			        	final int id = ((Integer) result.get("_id")).intValue();
			        	final String allianceName = (String) result.get("allianceName");
			        	final TypeaheadPlayer typeaheadPlayer = new TypeaheadPlayer(name, id, allianceName);
			        	return typeaheadPlayer;
			        }});
		} else if(paramCount == 2) {
			resultsIterable = playersCollection.find(query,queryString,queryString).skip(searchData.getSkip()).limit(searchData.getLimit()).map(
					new ResultHandler<TypeaheadPlayer>() {
			        @Override
			        public TypeaheadPlayer map(DBObject result) {
			        	final String name = (String) result.get("name");
			        	final int id = ((Integer) result.get("_id")).intValue();
			        	final String allianceName = (String) result.get("allianceName");
			        	final TypeaheadPlayer typeaheadPlayer = new TypeaheadPlayer(name, id, allianceName);
			        	return typeaheadPlayer;
			        }});
		}
		
		final List<TypeaheadPlayer> results = new ArrayList<TypeaheadPlayer>();
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
	public List<AllianceLeaderboardData> getAllianceLeaderboardData(final String serverId, final int allianceId, final Date startDate, final Date endDate) {
		setPlayersCollection(serverId);
		
//		db.uk3_players.aggregate([
//          	{$match:
//          		{"allianceId":15029}
//          	},
//          	{ $project :
//          		{
//          			_id : 1,
//          			name : 1,
//          			pointsStart : "$historicPoints",
//          			pointsEnd : "$historicPoints",
//          			castleCountStart : "$historicCastleCount",
//          			castleCountEnd : "$historicCastleCount",
//          			rankStart : "$historicRank",
//          			rankEnd : "$historicRank",
//          			allianceRankStart : "$historicAllianceRank",
//          			allianceRankEnd : "$historicAllianceRank"
//          		}
//          	},
//          	{$unwind : "$pointsStart"},
//          	{$match:
//          		{"pointsStart.lastUpdate":ISODate("2013-11-16T00:00:00Z")}
//          	},
//          	{$unwind : "$pointsEnd"},
//          	{$match:
//          		{"pointsEnd.lastUpdate":ISODate("2013-11-17T00:00:00Z")}
//          	},
//          	{$unwind : "$castleCountStart"},
//          	{$match:
//          		{"castleCountStart.lastUpdate":ISODate("2013-11-16T00:00:00Z")}
//          	},
//          	{$unwind : "$castleCountEnd"},
//          	{$match:
//          		{"castleCountEnd.lastUpdate":ISODate("2013-11-17T00:00:00Z")}
//          	},
//          	{$unwind : "$rankStart"},
//          	{$match:
//          		{"rankStart.lastUpdate":ISODate("2013-11-16T00:00:00Z")}
//          	},
//          	{$unwind : "$rankEnd"},
//          	{$match:
//          		{"rankEnd.lastUpdate":ISODate("2013-11-17T00:00:00Z")}
//          	},
//          	{$unwind : "$allianceRankStart"},
//          	{$match:
//          		{"allianceRankStart.lastUpdate":ISODate("2013-11-16T00:00:00Z")}
//          	},
//          	{$unwind : "$allianceRankEnd"},
//          	{$match:
//          		{"allianceRankEnd.lastUpdate":ISODate("2013-11-17T00:00:00Z")}
//          	},
//          	{ $project :
//          		{
//          			_id : 1,
//          			name : 1,
//          			
//          			pointsStart : "$pointsStart.points",
//          			pointsEnd : "$pointsEnd.points",
//          			pointsGrowth : { $subtract:["$pointsEnd.points","$pointsStart.points"] },
//          			
//          			castleCountStart : "$castleCountStart.castleCount",
//          			castleCountEnd : "$castleCountEnd.castleCount",
//          			castleCountGrowth : { $subtract:["$castleCountEnd.castleCount","$castleCountStart.castleCount"] },
//          			
//          			rankStart : "$rankStart.rank",
//          			rankEnd : "$rankEnd.rank",
//          			rankGrowth : { $subtract:["$rankStart.rank","$rankEnd.rank"] },
//          			
//          			allianceRankStart : "$allianceRankStart.rank",
//          			allianceRankEnd : "$allianceRankEnd.rank",
//          			allianceRankGrowth : { $subtract:["$allianceRankStart.rank","$allianceRankEnd.rank"] }
//          		}
//          	}
//          ]);
		
		String matchInitial = "";
		matchInitial = matchInitial + "{$match:";
		matchInitial = matchInitial + "  {\"allianceId\":"+allianceId+"}";
		matchInitial = matchInitial + "},";
		
		String projectInitial = "";
		projectInitial = projectInitial + "{$project:";
		projectInitial = projectInitial + "  {";
		projectInitial = projectInitial + "    _id : 1,";
		projectInitial = projectInitial + "    name : 1,";
		projectInitial = projectInitial + "    pointsStart : \"$historicPoints\",";
		projectInitial = projectInitial + "    pointsEnd : \"$historicPoints\",";
		projectInitial = projectInitial + "    castleCountStart : \"$historicCastleCount\",";
		projectInitial = projectInitial + "    castleCountEnd : \"$historicCastleCount\",";
		projectInitial = projectInitial + "    rankStart : \"$historicRank\",";
		projectInitial = projectInitial + "    rankEnd : \"$historicRank\",";
		projectInitial = projectInitial + "    allianceRankStart : \"$historicAllianceRank\",";
		projectInitial = projectInitial + "    allianceRankEnd : \"$historicAllianceRank\"";
		projectInitial = projectInitial + "  }";
		projectInitial = projectInitial + "}";

		String unwindPointsStart = "{$unwind : \"$pointsStart\"}";
		String matchPointsStart = "";
		matchPointsStart = matchPointsStart + "{$match:";
		matchPointsStart = matchPointsStart + "  {\"pointsStart.lastUpdate\":#}";
		matchPointsStart = matchPointsStart + "}";

		String unwindPointsEnd = "{$unwind : \"$pointsEnd\"}";
		String matchPointsEnd = "";
		matchPointsEnd = matchPointsEnd + "{$match:";
		matchPointsEnd = matchPointsEnd + "  {\"pointsEnd.lastUpdate\":#}";
		matchPointsEnd = matchPointsEnd + "}";
		
		String unwindCastleCountStart = "{$unwind : \"$castleCountStart\"}";
		String matchCastleCountStart = "";
		matchCastleCountStart = matchCastleCountStart + "{$match:";
		matchCastleCountStart = matchCastleCountStart + "  {\"castleCountStart.lastUpdate\":#}";
		matchCastleCountStart = matchCastleCountStart + "}";

		String unwindCastleCountEnd = "{$unwind : \"$castleCountEnd\"}";
		String matchCastleCountEnd = "";
		matchCastleCountEnd = matchCastleCountEnd + "{$match:";
		matchCastleCountEnd = matchCastleCountEnd + "  {\"castleCountEnd.lastUpdate\":#}";
		matchCastleCountEnd = matchCastleCountEnd + "}";

		String unwindRankStart = "{$unwind : \"$rankStart\"}";
		String matchRankStart = "";
		matchRankStart = matchRankStart + "{$match:";
		matchRankStart = matchRankStart + "  {\"rankStart.lastUpdate\":#}";
		matchRankStart = matchRankStart + "}";
		
		String unwindRankEnd = "{$unwind : \"$rankEnd\"}";
		String matchRankEnd = "";
		matchRankEnd = matchRankEnd + "{$match:";
		matchRankEnd = matchRankEnd + "  {\"rankEnd.lastUpdate\":#}";
		matchRankEnd = matchRankEnd + "}";

		String unwindAllianceRankStart = "{$unwind : \"$allianceRankStart\"}";
		String matchAllianceRankStart = "";
		matchAllianceRankStart = matchAllianceRankStart + "{$match:";
		matchAllianceRankStart = matchAllianceRankStart + "  {\"allianceRankStart.lastUpdate\":#}";
		matchAllianceRankStart = matchAllianceRankStart + "}";
		
		String unwindAllianceRankEnd = "{$unwind : \"$allianceRankEnd\"}";
		String matchAllianceRankEnd = "";
		matchAllianceRankEnd = matchAllianceRankEnd + "{$match:";
		matchAllianceRankEnd = matchAllianceRankEnd + "  {\"allianceRankEnd.lastUpdate\":#}";
		matchAllianceRankEnd = matchAllianceRankEnd + "}";
		
		String projectFinal = "";
		projectFinal = projectFinal + "{$project:";
		projectFinal = projectFinal + "  {";
		projectFinal = projectFinal + "    _id : 1,";
		projectFinal = projectFinal + "    name : 1,";
		projectFinal = projectFinal + "    pointsStart : \"$pointsStart.points\",";
		projectFinal = projectFinal + "    pointsEnd : \"$pointsEnd.points\",";
		projectFinal = projectFinal + "    pointsGrowth : { $subtract:[\"$pointsEnd.points\",\"$pointsStart.points\"] },";
		projectFinal = projectFinal + "    castleCountStart : \"$castleCountStart.castleCount\",";
		projectFinal = projectFinal + "    castleCountEnd : \"$castleCountEnd.castleCount\",";
		projectFinal = projectFinal + "    castleCountGrowth : { $subtract:[\"$castleCountEnd.castleCount\",\"$castleCountStart.castleCount\"] },";
		projectFinal = projectFinal + "    rankStart : \"$rankStart.rank\",";
		projectFinal = projectFinal + "    rankEnd : \"$rankEnd.rank\",";
		projectFinal = projectFinal + "    rankGrowth : { $subtract:[\"$rankStart.rank\",\"$rankEnd.rank\"] },";
		projectFinal = projectFinal + "    allianceRankStart : \"$allianceRankStart.rank\",";
		projectFinal = projectFinal + "    allianceRankEnd : \"$allianceRankEnd.rank\",";
		projectFinal = projectFinal + "   allianceRankGrowth : { $subtract:[\"$allianceRankStart.rank\",\"$allianceRankEnd.rank\"] }";
		projectFinal = projectFinal + "  }";
		projectFinal = projectFinal + "}";
		
		
		List<AllianceLeaderboardData> allianceLeaderboardData = playersCollection.aggregate(matchInitial)
		//List<AllianceLeaderboardData> allianceLeaderboardData = playersCollection.aggregate(matchInitial)
			.and(projectInitial)
			
			.and(unwindPointsStart)
			.and(matchPointsStart,startDate)
			.and(unwindPointsEnd)
			.and(matchPointsEnd,endDate)
		
			.and(unwindCastleCountStart)
			.and(matchCastleCountStart,startDate)
			.and(unwindCastleCountEnd)
			.and(matchCastleCountEnd,endDate)
			
			.and(unwindRankStart)
			.and(matchRankStart,startDate)
			.and(unwindRankEnd)
			.and(matchRankEnd,endDate)
			
			.and(unwindAllianceRankStart)
			.and(matchAllianceRankStart,startDate)
			.and(unwindAllianceRankEnd)
			.and(matchAllianceRankEnd,endDate)
//			
			.and(projectFinal)
			.as(AllianceLeaderboardData.class);
//			.map(
//				new ResultHandler<AllianceLeaderboardData>() {
//		        @Override
//		        public AllianceLeaderboardData map(DBObject result) {
//		        	final String name = (String) result.get("name");
//		        	final int id = ((Integer) result.get("_id")).intValue();
//		        	final String allianceName = (String) result.get("allianceName");
//		        	final AllianceLeaderboardData typeaheadPlayer = new AllianceLeaderboardData();
//		        	return typeaheadPlayer;
//		        }});
//		List<AllianceLeaderboardData> allianceLeaderboardDatas = new ArrayList<AllianceLeaderboardData>();
//		CollectionUtils.addAll(allianceLeaderboardDatas, allianceLeaderboardData.iterator());
		return allianceLeaderboardData;
	}

	@Override
	public boolean isPlayerInAlliance(final String serverId, final int playerId, final int allianceId) {
		setPlayersCollection(serverId);
		final PlayerData playerData = playersCollection.findOne("{_id:"+playerId+"}").as(PlayerData.class);
		if (playerData.getAllianceId() == allianceId) {
			return true;
		} else {
			return false;	
		}
		
	}
	
	@Override
	public List<PlayerData> getRivalPlayersInAlliance(String serverId, PlayerData playerData) {
		setPlayersCollection(serverId);
		
		List<PlayerData> closePlayerDatas = new ArrayList<PlayerData>();
		
		final int allianceId = playerData.getAllianceId();
		final int allianceRank = playerData.getAllianceRank();
		int compareAllianceRank = allianceRank - 2;
		
		Iterable<PlayerData> playerDataIterable = playersCollection.find("{allianceId:"+allianceId+",allianceRank:{$gte:"+compareAllianceRank+"}}").sort("{points:-1}").limit(5).as(PlayerData.class);
		CollectionUtils.addAll(closePlayerDatas, playerDataIterable.iterator());
		return closePlayerDatas;
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
