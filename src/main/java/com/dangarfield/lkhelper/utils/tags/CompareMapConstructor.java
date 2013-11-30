package com.dangarfield.lkhelper.utils.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;

import com.dangarfield.lkhelper.constants.ControllerConstants;
import com.dangarfield.lkhelper.service.ImageProcessingService;
import com.dangarfield.lkhelper.service.impl.ImageProcessingServiceImpl;

public class CompareMapConstructor extends SpringSupportedTag {

	private String castles;
	private String players;
	private String alliances;

	private ImageProcessingService imageProcessingService;
	
	@Override
	protected void initCustomBeans() {
		imageProcessingService = (ImageProcessingService) getSpringContext().getBean("imageProcessingService");
	}


	@Override
	public void doStartTagWithSpring() throws JspException {
		try {
			JspWriter out = getJspContext().getOut();
			
			List<String> castlesList = new ArrayList<String>();
			List<String> playersList = new ArrayList<String>();
			List<String> alliancesList = new ArrayList<String>();
			
			if(!castles.isEmpty()) {
				List<String> provisionalCastlesList = Arrays.asList(castles.split(ControllerConstants.URL.Report.CompareDivider));
				for (String provisionalCastle : provisionalCastlesList) {
					if(!castlesList.contains(provisionalCastle)) {
						castlesList.add(provisionalCastle);
					}
				}
			}
			
			if(!players.isEmpty()) {
				List<String> provisionalPlayersList = Arrays.asList(players.split(ControllerConstants.URL.Report.CompareDivider));
				for (String provisionalPlayer : provisionalPlayersList) {
					if(!playersList.contains(provisionalPlayer)) {
						playersList.add(provisionalPlayer);
					}
				}
			}
			
			if(!alliances.isEmpty()) {
				List<String> provisionalAlliancesList = Arrays.asList(alliances.split(ControllerConstants.URL.Report.CompareDivider));
				for (String provisionalAlliance : provisionalAlliancesList) {
					if(!alliancesList.contains(provisionalAlliance)) {
						alliancesList.add(provisionalAlliance);
					}
				}
			}
			
			final List<String> mapParams = new ArrayList<String>();

			String[] allColours = imageProcessingService.getCompareMapColoursAll();
			int allCount = 0;
			
//			String[] castleColours = imageProcessingService.getCompareMapColoursCastle();
//			int castleCount = 0;
			for (String castle : castlesList) {
				StringBuilder sb = new StringBuilder();
				sb.append(ControllerConstants.URL.Maps.CompareTypeCastle).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
				sb.append(castle).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
				sb.append(ImageProcessingServiceImpl.DEFAULT_THICKNESS_CASTLE).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
				//sb.append(castleColours[castleCount]);
				sb.append(allColours[allCount]);
//				castleCount++;
				allCount++;
				mapParams.add(sb.toString());
			}

//			String[] playerColours = imageProcessingService.getCompareMapColoursPlayer();
//			int playerCount = 0;
			for (String player : playersList) {
				StringBuilder sb = new StringBuilder();
				sb.append(ControllerConstants.URL.Maps.CompareTypePlayer).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
				sb.append(player).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
				sb.append(ImageProcessingServiceImpl.DEFAULT_THICKNESS_PLAYER).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
				//sb.append(playerColours[playerCount]);
				sb.append(allColours[allCount]);
//				playerCount++;
				allCount++;
				mapParams.add(sb.toString());
			}

//			String[] allianceColours = imageProcessingService.getCompareMapColoursAlliance();
//			int allianceCount = 0;
			for (String alliance : alliancesList) {
				StringBuilder sb = new StringBuilder();
				sb.append(ControllerConstants.URL.Maps.CompareTypeAlliance).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
				sb.append(alliance).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
				sb.append(ImageProcessingServiceImpl.DEFAULT_THICKNESS_ALLIANCE).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
				//sb.append(allianceColours[allianceCount]);
				sb.append(allColours[allCount]);
//				allianceCount++;
				allCount++;
				mapParams.add(sb.toString());
			}
			
			
			final String mapParmsString = StringUtils.join(mapParams.toArray(),ControllerConstants.URL.Maps.CompareDividerItem);
			
			out.print(mapParmsString);

		} catch (IOException ioe) {
			throw new JspException("Error: " + ioe.getMessage());
		}
	}


	/**
	 * @return the imageProcessingService
	 */
	public ImageProcessingService getImageProcessingService() {
		return imageProcessingService;
	}

	/**
	 * @param imageProcessingService the imageProcessingService to set
	 */
	public void setImageProcessingService(
			ImageProcessingService imageProcessingService) {
		this.imageProcessingService = imageProcessingService;
	}
	
	/**
	 * @return the castles
	 */
	public String getCastles() {
		return castles;
	}

	/**
	 * @param castles the castles to set
	 */
	public void setCastles(String castles) {
		this.castles = castles;
	}

	/**
	 * @return the players
	 */
	public String getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(String players) {
		this.players = players;
	}

	/**
	 * @return the alliances
	 */
	public String getAlliances() {
		return alliances;
	}

	/**
	 * @param alliances the alliances to set
	 */
	public void setAlliances(String alliances) {
		this.alliances = alliances;
	}




}
