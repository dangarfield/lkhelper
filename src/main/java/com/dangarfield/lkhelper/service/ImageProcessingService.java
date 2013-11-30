package com.dangarfield.lkhelper.service;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.dangarfield.lkhelper.data.report.MapCompareRequest;

public interface ImageProcessingService {

	void generateAllRawCSVFiles();

	File generateBaseMap(final String serverId, final boolean clear, final int thickness, final Color colour) throws IOException;
	File generateAllianceMap(final String serverId, final int allianceId, final boolean clear, final int thickness, final boolean renderBase, final Color colour) throws IOException;
	File generatePlayerMap(final String serverId, final int playerId, final boolean clear, final int thickness, final boolean renderBase, final Color colour) throws IOException;
	File generateCastleMap(final String serverId, final int castleId, final boolean clear, final int thickness, final boolean renderBase, final Color colour) throws IOException;

	File generateCompareMap(final String serverId, final List<MapCompareRequest> mapCompareRequests, final String mapCompareRequestString, final boolean clear) throws IOException;
	
	List<MapCompareRequest> decodeMapCompareRequests(final String mapCompareRequestString);
	String encodeMapCompareRequests(final List<MapCompareRequest> mapCompareRequests);
	
	String[] getCompareMapColoursCastle();
	String[] getCompareMapColoursPlayer();
	String[] getCompareMapColoursAlliance();
	String[] getCompareMapColoursAll();
}