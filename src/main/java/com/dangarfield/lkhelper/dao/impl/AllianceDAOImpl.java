package com.dangarfield.lkhelper.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jongo.FindOne;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangarfield.lkhelper.controller.SearchController;
import com.dangarfield.lkhelper.dao.AllianceDAO;
import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.PlayerAllianceData;
import com.dangarfield.lkhelper.data.historic.HistoricCastleCountData;
import com.dangarfield.lkhelper.data.historic.HistoricCastleData;
import com.dangarfield.lkhelper.data.historic.HistoricMemberCountData;
import com.dangarfield.lkhelper.data.historic.HistoricNameData;
import com.dangarfield.lkhelper.data.historic.HistoricPointsData;
import com.dangarfield.lkhelper.data.historic.HistoricRankData;
import com.dangarfield.lkhelper.data.report.ReportPlayerData;
import com.dangarfield.lkhelper.data.search.SearchDataAlliance;
import com.dangarfield.lkhelper.data.search.TypeaheadAlliance;
import com.dangarfield.lkhelper.utils.GeneralUtils;
import com.mongodb.DBObject;

public class AllianceDAOImpl implements AllianceDAO {
	
	private static Logger LOG = LogManager.getLogger("AllianceDAOImpl");
	
	private static String BASE_COLLECTION_NAME = "alliances";
	
	private MongoCollection allianceCollection;

	@Autowired
	private Jongo jongo;
	
	@Autowired
	private GeneralUtils generalUtils;
	
    private void setAlliancesCollection(final String serverId) {
    	allianceCollection = jongo.getCollection(generalUtils.getCollectionNameForServer(BASE_COLLECTION_NAME, serverId));
    }
    
    @Override
    public void ensureIndexes(final String serverId) {
    	setAlliancesCollection(serverId);
    	allianceCollection.dropIndexes();
    	allianceCollection.ensureIndex("{name:1}");
    	allianceCollection.ensureIndex("{normalisedName:1}");
    }
    
    @Override
	public PlayerAllianceData getPlayerAllianceForId(final String serveId, final int id) {
		setAlliancesCollection(serveId);
		PlayerAllianceData alliance;
		alliance = allianceCollection.findOne("{_id: "+id+"}").as(PlayerAllianceData.class);
		
		if (alliance != null) {
			LOG.debug("PlayerAlliance retrieved from db: " + alliance.getId());
		} else {
			LOG.debug("PlayerAlliance not in db, creating new PlayerAlliance: " + id);
			alliance = new PlayerAllianceData(id);
		}
		return alliance;
	}
    @Override
	public void savePlayerAlliances(final String serverId, final List<PlayerAllianceData> playerAllianceDataList) {
		for (PlayerAllianceData playerAllianceData : playerAllianceDataList) {
			savePlayerAlliance(serverId, playerAllianceData);
		}
	}
    @Override
    public void savePlayerAlliance(final String serverId, final PlayerAllianceData playerAllianceData) {
    	setAlliancesCollection(serverId);
    	LOG.debug("Saving playerAllianceData: " + playerAllianceData.getId());
		allianceCollection.save(playerAllianceData);
	}

    @Override
	public AllianceData getAllianceForId(final String serverId, final int id) {
		setAlliancesCollection(serverId);
		AllianceData alliance;
		FindOne find = allianceCollection.findOne("{_id: "+id+"}");
		alliance = find.as(AllianceData.class);
		
		if (alliance != null) {
			LOG.debug("Alliance retrieved from db: " + alliance.getId());
		} else {
			LOG.debug("Alliance not in db, creating new Alliance: " + id);
			alliance = new AllianceData(id);
		}
		return alliance;
	}
    @Override
	public void saveAlliances(final String serverId, final List<AllianceData> allianceDataList) {
		for (AllianceData allianceData : allianceDataList) {
			saveAlliance(serverId, allianceData);
		}
	}
    @Override
    public void saveAlliance(final String serverId, final AllianceData allianceData) {
    	setAlliancesCollection(serverId);
    	LOG.debug("Saving allianceData: " + allianceData.getId());
    	// Uncomment to make sure array data is set
    	if(allianceData.getHistoricPoints() == null) {
    		allianceData.setHistoricPoints(new ArrayList<HistoricPointsData>());
    	}
    	if(allianceData.getHistoricRank() == null) {
    		allianceData.setHistoricRank(new ArrayList<HistoricRankData>());
    	}
    	if(allianceData.getHistoricMemberCount() == null) {
    		allianceData.setHistoricMemberCount(new ArrayList<HistoricMemberCountData>());
    	}
    	if(allianceData.getHistoricCastleCount() == null) {
    		allianceData.setHistoricCastleCount(new ArrayList<HistoricCastleCountData>());
    	}
    	if(allianceData.getHistoricAllianceName() == null) {
    		allianceData.setHistoricAllianceName(new ArrayList<HistoricNameData>());
    	}
    	if(allianceData.getHistoricLostCastles() == null) {
    		allianceData.setHistoricLostCastles(new ArrayList<HistoricCastleData>());
    	}
    	if(allianceData.getMemberList() == null) {
    		allianceData.setMemberList(new ArrayList<ReportPlayerData>());
    	}
		allianceCollection.save(allianceData);
	}
    @Override
	public List<Integer> getAllAllianceIds(final String serverId) {
    	setAlliancesCollection(serverId);
		List<Integer> allAllianceIds = new ArrayList<Integer>();
		
		Iterable<Integer> allianceIdIterable = allianceCollection.find("{_id:{$gt:0}}").map(
			    new ResultHandler<Integer>() {
			        @Override
			        public Integer map(DBObject result) {
			            return (Integer) result.get("_id");
			        }
			});
		CollectionUtils.addAll(allAllianceIds, allianceIdIterable.iterator());
		return allAllianceIds;
	}
    @Override
	public List<TypeaheadAlliance> getTypeaheadAlliances(final String serverId, final String allianceSearch) {

		final SearchDataAlliance searchData = new SearchDataAlliance(serverId, allianceSearch, 0, SearchController.RESULTS_FOR_TYPEAHEAD, 1, 1);
		final SearchDataAlliance searchResults = getSearchForAlliances(searchData);
		return searchResults.getResults();
	}
	@Override
	public SearchDataAlliance getSearchForAlliances(final SearchDataAlliance searchData) {
		setAlliancesCollection(searchData.getServerId());
		
		Object queryString = searchData.getQuery();
		
		String query = "";
		int count = 0;
		int paramCount = 1;
		
		if (((String)queryString).matches("\\d+")) {
			// Search on _id field
			int queryStringInt = Integer.parseInt((String)queryString);
			queryString = queryStringInt;
			query = "{ _id : #}";
			count = (int) allianceCollection.count(query,queryString);
			
		} else {
			// Search on name field using regex
			
			query = query + "{$or:";
			query = query + "  [";
			query = query + "    { name : {$regex : #, $options: 'i'}},";
			query = query + "    { normalisedName : {$regex : #, $options: 'i'}}";
			query = query + "  ]";
			query = query + "}";
			paramCount = 2;
			count = (int) allianceCollection.count(query,queryString,queryString);
		}
		
		
		if (count == 0) {
			query = "{ name : # }";
			paramCount = 1;
			count = (int) allianceCollection.count(query,queryString);
		}
		
		Iterable<TypeaheadAlliance> resultsIterable = null;
		if(paramCount == 1) {
			resultsIterable = allianceCollection.find(query,queryString).skip(searchData.getSkip()).limit(searchData.getLimit()).map(
					new ResultHandler<TypeaheadAlliance>() {
			        @Override
			        public TypeaheadAlliance map(DBObject result) {
			        	final String name = (String) result.get("name");
			        	final int id = ((Integer) result.get("_id")).intValue();
			        	final TypeaheadAlliance typeaheadAlliance = new TypeaheadAlliance(name, id);
			        	return typeaheadAlliance;
			        }});

		} else if(paramCount == 2){
			resultsIterable = allianceCollection.find(query,queryString,queryString).skip(searchData.getSkip()).limit(searchData.getLimit()).map(
					new ResultHandler<TypeaheadAlliance>() {
			        @Override
			        public TypeaheadAlliance map(DBObject result) {
			        	final String name = (String) result.get("name");
			        	final int id = ((Integer) result.get("_id")).intValue();
			        	final TypeaheadAlliance typeaheadAlliance = new TypeaheadAlliance(name, id);
			        	return typeaheadAlliance;
			        }});

		}
		 		
		final List<TypeaheadAlliance> results = new ArrayList<TypeaheadAlliance>();
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
	public List<AllianceData> getRivalAlliances(final String serverId, final AllianceData allianceData) {
		setAlliancesCollection(serverId);

		List<AllianceData> rivalAlliancesDatas = new ArrayList<AllianceData>();
		
		final int rank = allianceData.getRank();
		int compareRank = rank - 2;
		
		Iterable<AllianceData> allianceDataIterable = allianceCollection.find("{rank:{$gte:"+compareRank+"}}").sort("{rank:1}").limit(5).as(AllianceData.class);
		CollectionUtils.addAll(rivalAlliancesDatas, allianceDataIterable.iterator());
		return rivalAlliancesDatas;
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
