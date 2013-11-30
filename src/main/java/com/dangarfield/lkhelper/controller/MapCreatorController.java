package com.dangarfield.lkhelper.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dangarfield.lkhelper.constants.ControllerConstants;
import com.dangarfield.lkhelper.dao.AllianceDAO;
import com.dangarfield.lkhelper.dao.CastleDAO;
import com.dangarfield.lkhelper.dao.PlayerDAO;
import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.report.MapCompareRequest;
import com.dangarfield.lkhelper.data.users.UserData;
import com.dangarfield.lkhelper.service.ImageProcessingService;
import com.dangarfield.lkhelper.service.impl.ImageProcessingServiceImpl;

@Controller
public class MapCreatorController extends AbstractLKController {
	
	@Autowired
	private ImageProcessingService imageProcessingService;
	
	@Autowired
	private CastleDAO castleDAO;
	@Autowired
	private PlayerDAO playerDAO;
	@Autowired
	private AllianceDAO allianceDAO;
	
	@RequestMapping(value=ControllerConstants.URL.Tools.MapCreatorBase, method = RequestMethod.GET)
	public String mapCreatorHome(Model model, Principal principal) {
		
		final UserData userData = getUserData(principal);
		final int playerId = userData.getPlayerId();
		final String serverId = userData.getServerId();
		final PlayerData playerData = playerDAO.getPlayerForId(serverId, playerId);
		
		final int allianceId = playerData.getAllianceId();
		
		final String[] allColoursList = imageProcessingService.getCompareMapColoursAll();
		
		final StringBuilder sb = new StringBuilder();
		sb.append("a").append(ControllerConstants.URL.Maps.CompareDividerAttribute);
		sb.append(allianceId).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
		sb.append(ImageProcessingServiceImpl.DEFAULT_THICKNESS_ALLIANCE).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
		sb.append(allColoursList[0]).append(ControllerConstants.URL.Maps.CompareDividerItem);

		sb.append("p").append(ControllerConstants.URL.Maps.CompareDividerAttribute);
		sb.append(playerId).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
		sb.append(ImageProcessingServiceImpl.DEFAULT_THICKNESS_PLAYER).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
		sb.append(allColoursList[1]);
		
		final String mapCompareRequestString = sb.toString();
		
		return mapCreatorResult(model, principal, serverId, mapCompareRequestString);
	}
	
	@RequestMapping(value=ControllerConstants.URL.Tools.MapCreatorServerOnly, method = RequestMethod.GET)
	public String mapCreatorServerOnly(Model model, Principal principal) {
		return "redirect:" + ControllerConstants.URL.Tools.MapCreatorBase;
	}
	
	@RequestMapping(value=ControllerConstants.URL.Tools.MapCreatorMain, method = RequestMethod.GET)
	public String mapCreatorResult(Model model, Principal principal,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@PathVariable(ControllerConstants.URL.PathVariable.CompareMapString) String mapCompareRequestString) {
		
		final String[] allColoursList = imageProcessingService.getCompareMapColoursAll();
		final String allColours = StringUtils.join(allColoursList,ControllerConstants.URL.Maps.CompareDividerAttribute);
		model.addAttribute("allColours", allColours);
		
		int defaultThicknessBase = ImageProcessingServiceImpl.DEFAULT_THICKNESS_BASE;
		int defaultThicknessAlliance = ImageProcessingServiceImpl.DEFAULT_THICKNESS_ALLIANCE;
		int defaultThicknessPlayer = ImageProcessingServiceImpl.DEFAULT_THICKNESS_PLAYER;
		int defaultThicknessCastle = ImageProcessingServiceImpl.DEFAULT_THICKNESS_CASTLE;
		int maxCompareColours = ImageProcessingServiceImpl.MAX_COMPARE_COLOURS;
		model.addAttribute("defaultThicknessBase", defaultThicknessBase);
		model.addAttribute("defaultThicknessAlliance", defaultThicknessAlliance);
		model.addAttribute("defaultThicknessPlayer", defaultThicknessPlayer);
		model.addAttribute("defaultThicknessCastle", defaultThicknessCastle);
		model.addAttribute("maxCompareColours", maxCompareColours);
		model.addAttribute("serverId", serverId);
		
		final List<MapCompareRequest> mapCompareRequests = imageProcessingService.decodeMapCompareRequests(mapCompareRequestString);
		
		final List<String> pathList = new ArrayList<String>();
		
		for (MapCompareRequest mapCompareRequest : mapCompareRequests) {
			if(mapCompareRequest.getType().equals(ControllerConstants.URL.Maps.CompareTypeCastle)) {
				final CastleData castleData = castleDAO.getCastleForId(serverId, mapCompareRequest.getId());
				final String textValue = castleData.getName();
				mapCompareRequest.setTextValue(textValue);
			} else if(mapCompareRequest.getType().equals(ControllerConstants.URL.Maps.CompareTypePlayer)) {
				final PlayerData playerData = playerDAO.getPlayerForId(serverId, mapCompareRequest.getId());
				final String textValue = playerData.getName();
				mapCompareRequest.setTextValue(textValue);
			} else if(mapCompareRequest.getType().equals(ControllerConstants.URL.Maps.CompareTypeAlliance)) {
				final AllianceData allianceData = allianceDAO.getAllianceForId(serverId, mapCompareRequest.getId());
				final String textValue = allianceData.getName();
				mapCompareRequest.setTextValue(textValue);
			}
			final StringBuilder sbPath = new StringBuilder();
			sbPath.append(mapCompareRequest.getType()).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
			sbPath.append(mapCompareRequest.getId()).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
			sbPath.append(mapCompareRequest.getThickness()).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
			sbPath.append(mapCompareRequest.getColor());
			final String path =  sbPath.toString();
			mapCompareRequest.setPath(path);
			pathList.add(path);
		}
		for (MapCompareRequest mapCompareRequest : mapCompareRequests) {
			final List<String> removePathList = new ArrayList<String>();
			for (String path : pathList) {
				if(!path.equals(mapCompareRequest.getPath())) {
					removePathList.add(path);
				}
				final String removePathUrl = StringUtils.join(removePathList,ControllerConstants.URL.Maps.CompareDividerItem);
				final String removeUrl = ControllerConstants.URL.Tools.MapCreatorBase + "/" + serverId + "/" + removePathUrl;
				mapCompareRequest.setRemoveURL(removeUrl);
			}
		}
		model.addAttribute("mapCompareRequests",mapCompareRequests);
		
		final String mapUrl = ControllerConstants.URL.Maps.CompareMapRoot + serverId + "/" + mapCompareRequestString + "." + ControllerConstants.URL.Maps.ImageFormat;
		model.addAttribute("mapUrl",mapUrl);
		
		
		return ControllerConstants.Views.Tools.MapCreator;
	}
}
