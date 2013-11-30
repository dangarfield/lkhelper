package com.dangarfield.lkhelper.converter;

import java.util.List;
import java.util.Map;

import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.exceptions.GatherDataException;
import com.fasterxml.jackson.databind.JsonNode;

public interface CastleDataJsonConverter {
	
	public final static String HASHMAP_CASTLE_DATA_KEY = "CASTLE_DATA_KEY";
	public final static String HASHMAP_ALLIANCE_DATA_KEY = "CASTLE_ALLIANCE_KEY";
	public final static String HASHMAP_PLAYER_DATA_KEY = "CASTLE_PLAYER_KEY";
	
	List<JsonNode> convertJsonMapToJsonHabitat(final ServerData serverData, final JsonNode rootJson);

	Map<String, List<? extends Object>> convertJsonHabitatsToCastleAndAllianceDatas(final ServerData serverData, final List<JsonNode> jsonHabitats) throws GatherDataException;

	Map<String, ? extends Object> convertJsonHabitatToCastleData(final ServerData serverData, final JsonNode habitatDictionaryNode) throws GatherDataException;
	
	@SuppressWarnings({ "rawtypes" })
	public <T> List<T> convertHashMapList(List list, T t);
}
