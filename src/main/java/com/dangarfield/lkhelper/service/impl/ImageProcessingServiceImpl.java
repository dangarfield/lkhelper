package com.dangarfield.lkhelper.service.impl;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.AttributedString;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangarfield.lkhelper.constants.ControllerConstants;
import com.dangarfield.lkhelper.dao.AllianceDAO;
import com.dangarfield.lkhelper.dao.CastleDAO;
import com.dangarfield.lkhelper.dao.PlayerDAO;
import com.dangarfield.lkhelper.dao.ServerDAO;
import com.dangarfield.lkhelper.data.AllianceData;
import com.dangarfield.lkhelper.data.CastleData;
import com.dangarfield.lkhelper.data.PlayerData;
import com.dangarfield.lkhelper.data.report.MapCompareRequest;
import com.dangarfield.lkhelper.data.report.MapCoordinates;
import com.dangarfield.lkhelper.data.users.ServerData;
import com.dangarfield.lkhelper.service.ImageProcessingService;

public class ImageProcessingServiceImpl implements ImageProcessingService {

	@Autowired
	private ServerDAO serverDAO;
	@Autowired
	private CastleDAO castleDAO;
	@Autowired
	private AllianceDAO allianceDAO;
	@Autowired
	private PlayerDAO playerDAO;

	private static Logger LOG = LogManager.getLogger("ImageProcessingServiceImpl");
	
	private static long IMAGE_CACHE_TIME = (2 * 60 * 60 * 1000);

	public final static int DEFAULT_THICKNESS_BASE = 1;
	public final static int DEFAULT_THICKNESS_ALLIANCE = 2;
	public final static int DEFAULT_THICKNESS_PLAYER = 3;
	public final static int DEFAULT_THICKNESS_CASTLE = 5;
	
	public final static int MAX_COMPARE_COLOURS = 12;

	public final static String DEFAULT_COLOUR_TEXT = "222222"; //dark grey
	public final static String DEFAULT_COLOUR_BASE = "000000"; //black
	public final static String DEFAULT_COLOUR_ALLIANCE = "ffa500"; // yellow //f6f144
	public final static String DEFAULT_COLOUR_PLAYER = "580ead"; // blue //3569a0
	public final static String DEFAULT_COLOUR_CASTLE = "00bd39"; // red //d73b73
	
	private final static int TEXT_X_POS = 20;
	private static int TEXT_Y_POS = 20;
	private final static int TEXT_LINE_SPACING = 30;
	private final static String TEXT_DEFAULT_FONT = "Segoe UI";
	private final static int TEXT_DEFAULT_FONT_SIZE = 20;
	private final static int TEXT_DEFAULT_FONT_SIZE_SERVER_DATE = 15;
	
	private enum PathType {
		IMAGE,
		CSV
	}
	
	@Override
	public void generateAllRawCSVFiles() {

		
		List<ServerData> allServers = serverDAO.getAllServerData();
		for (ServerData serverData : allServers) {
			try {
				final String serverId = serverData.getId();
				final List<MapCoordinates> boundaries = castleDAO.getBoundaryCoordinatesForServer(serverId);
				
				LOG.info("Creating CSVs for base server " + serverId + ": START");
				generateRawCSVFileForServer(serverId, boundaries);
				LOG.info("Creating CSVs for base server " + serverId + ": COMPLETE");
				
				final List<Integer> allianceIds = allianceDAO.getAllAllianceIds(serverId);
				LOG.info("Creating CSVs for alliances on " + serverId + ": START");
				for (Integer allianceId : allianceIds) {
					generateRawCSVFileForAlliance(serverId, allianceId.intValue(), boundaries);	
				}
				LOG.info("Creating CSVs for alliances on " + serverId + ": END");
				
				
				final List<Integer> playerIds = playerDAO.getAllPlayerIds(serverId);
				LOG.info("Creating CSVs for players on " + serverId + ": START");
				for (Integer playerId : playerIds) {
					generateRawCSVFileForPlayer(serverId, playerId.intValue(), boundaries);	
				}
				LOG.info("Creating CSVs for players on " + serverId + ": END");
				
			} catch (IOException e) {
				final String error = e.getMessage();
				LOG.error(error);
			}
			
		}
	}

	
	private void generateRawCSVFileForServer(final String serverId, final List<MapCoordinates> boundaries) throws IOException {

		List<MapCoordinates> allCoords = castleDAO.getAllCastlesCoordinatesForServer(serverId);
		
		final String workingDir = System.getProperty("user.dir");
        
		File csvDir = new File(workingDir + "/lkhelper-data/map-csv/"+serverId);
		File csvFile = new File(csvDir, "all.csv");
		csvDir.mkdirs();
		writeCSVFile(csvFile, allCoords);
	}

	private void generateRawCSVFileForAlliance(final String serverId,final int allianceId, final List<MapCoordinates> boundaries) throws IOException {

		List<MapCoordinates> allCoords = castleDAO.getAllCastlesCoordinatesForAlliance(serverId,allianceId);
		
		final String workingDir = System.getProperty("user.dir");
        
		File csvDir = new File(workingDir + "/lkhelper-data/map-csv/"+serverId+"/alliances");
		File csvFile = new File(csvDir, allianceId+".csv");
		csvDir.mkdirs();
		writeCSVFile(csvFile, allCoords);
	}
	private void generateRawCSVFileForPlayer(final String serverId,final int playerId, final List<MapCoordinates> boundaries) throws IOException {
		
		List<MapCoordinates> allCoords = castleDAO.getAllCastlesCoordinatesForPlayer(serverId,playerId);
		
        final String workingDir = System.getProperty("user.dir");
        
        File csvDir = new File(workingDir + "/lkhelper-data/map-csv/"+serverId+"/players");
        File csvFile = new File(csvDir, playerId+".csv");
		csvDir.mkdirs();
		writeCSVFile(csvFile, allCoords);
	}
	private void writeCSVFile(final File file, final List<MapCoordinates> allCoords) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		for (MapCoordinates mapCoordinates : allCoords) {
			bw.write(String.valueOf(mapCoordinates.getX()));
			bw.write(" ");
			bw.write(String.valueOf(mapCoordinates.getY()));
			bw.write("\n");
		}
		bw.close();
	}
	
	private File writeImageFile(final String serverId, final File file, final List<MapCoordinates> allCoords, final Color colour, final int thickness, final boolean renderBase, final String heading, final String textValue) throws IOException {
		
		final List<MapCoordinates> boundaries = castleDAO.getBoundaryCoordinatesForServer(serverId);
		
		final int leftMostPos = boundaries.get(0).getX();
		final int rightMostPos = boundaries.get(1).getX();
		final int topMostPos = boundaries.get(2).getY();
		final int bottomMostPos = boundaries.get(3).getY();
		
		final int width = rightMostPos - leftMostPos;
		final int height = bottomMostPos - topMostPos;
		
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = newImage.createGraphics();
		
		if(renderBase) {
			BufferedImage baseImage = ImageIO.read(generateBaseMap(serverId, false, 1, new Color(Integer.parseInt(DEFAULT_COLOUR_BASE,16))));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
			g.drawImage(baseImage, 0, 0, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		
		int textYPos = TEXT_Y_POS;
		if (heading != null && textValue != null) {
			
			g.setColor(new Color(Integer.parseInt(DEFAULT_COLOUR_TEXT,16)));
			
			//Draw server and time
			Format serverTimeFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			final String serverAndTime = serverId + " - " + serverTimeFormatter.format(new Date());
			g.drawString(getStringAndFont(serverAndTime, TEXT_DEFAULT_FONT_SIZE_SERVER_DATE).getIterator(), TEXT_X_POS, (height-textYPos));
			
			//Draw heading
			g.drawString(getStringAndFont(heading, TEXT_DEFAULT_FONT_SIZE).getIterator(), TEXT_X_POS, textYPos);
			textYPos = textYPos + TEXT_LINE_SPACING;
			
			//Draw item heading
			g.setColor(colour);
			g.drawString(getStringAndFont(textValue, TEXT_DEFAULT_FONT_SIZE).getIterator(), TEXT_X_POS, textYPos);
		}
		
		g.setColor(colour);
		g.setStroke(new BasicStroke(thickness));
		
		for (MapCoordinates mapCoordinates : allCoords) {
			int locX = mapCoordinates.getX() - leftMostPos;
			int locY = height - (bottomMostPos - mapCoordinates.getY());
			
			g.drawLine(locX, locY, locX, locY);
		}
		
		ImageIO.write(newImage, "png", file);
		
		return file;
	}
	private AttributedString getStringAndFont(final String text, final int fontSize) {
        AttributedString result = new AttributedString(text);

        Font mainFont = new Font(TEXT_DEFAULT_FONT, Font.PLAIN, fontSize);
        Font fallbackFont = new Font("Serif", Font.PLAIN, fontSize);
        
        int textLength = text.length(); 
        result.addAttribute(TextAttribute.FONT, mainFont, 0, textLength);

        boolean fallback = false;
        int fallbackBegin = 0;
        for (int i = 0; i < text.length(); i++) {
            boolean curFallback = !mainFont.canDisplay(text.charAt(i));
            if (curFallback != fallback) {
                fallback = curFallback;
                if (fallback) {
                    fallbackBegin = i;
                } else {
                    result.addAttribute(TextAttribute.FONT, fallbackFont, fallbackBegin, i);
                }
            }
        }
        return result;
    } 

	@Override
	public File generateBaseMap(final String serverId, final boolean clear, final int thickness, final Color colour) throws IOException {
		
		File imgDir = new File(getDirForBaseServer(PathType.IMAGE, serverId));
		File imgFile = new File(imgDir, ControllerConstants.URL.Maps.BaseMapName+"_"+thickness+"."+ControllerConstants.URL.Maps.ImageFormat);
		imgDir.mkdirs();
		
		if (imgFile.exists() && !clear) {
			final long lastModified = imgFile.lastModified();
			long now = new Date().getTime();
			now = now - IMAGE_CACHE_TIME;
			if (lastModified > now) {
				return imgFile;
			}
		}
		
		final List<MapCoordinates> allCoords = castleDAO.getAllCastlesCoordinatesForServer(serverId);
		
		return writeImageFile(serverId, imgFile, allCoords, colour, thickness, false, null, null);
	}
	
	@Override
	public File generateAllianceMap(final String serverId, final int allianceId, final boolean clear, int thickness, final boolean renderBase, final Color colour) throws IOException {
		
		if(thickness==0) {
			thickness = DEFAULT_THICKNESS_ALLIANCE;
		}
		
		File imgDir = new File(getDirForAlliance(PathType.IMAGE, serverId, allianceId));
		File imgFile = new File(imgDir, allianceId+"_"+thickness+(renderBase?"_base":"")+"."+ControllerConstants.URL.Maps.ImageFormat);
		imgDir.mkdirs();
		
		if (imgFile.exists() && !clear) {
			final long lastModified = imgFile.lastModified();
			long now = new Date().getTime();
			now = now - IMAGE_CACHE_TIME;
			if (lastModified > now) {
				return imgFile;
			}
		}
		
		final List<MapCoordinates> allCoords = castleDAO.getAllCastlesCoordinatesForAlliance(serverId, allianceId);
		
		final AllianceData allianceData = allianceDAO.getAllianceForId(serverId, allianceId);
		final String textValue = allianceData.getName() + " (" + String.valueOf(allianceId) + ")";
		
		return writeImageFile(serverId, imgFile, allCoords, colour, thickness, renderBase, "Alliance", textValue);
	}
	
	@Override
	public File generatePlayerMap(final String serverId, final int playerId, final boolean clear, int thickness, final boolean renderBase, final Color colour) throws IOException {
		
		File imgDir = new File(getDirForPlayer(PathType.IMAGE, serverId, playerId));
		File imgFile = new File(imgDir, playerId+"_"+thickness+(renderBase?"_base":"")+"."+ControllerConstants.URL.Maps.ImageFormat);
		imgDir.mkdirs();
		
		if (imgFile.exists() && !clear) {
			final long lastModified = imgFile.lastModified();
			long now = new Date().getTime();
			now = now - IMAGE_CACHE_TIME;
			if (lastModified > now) {
				return imgFile;
			}
		}
		
		final List<MapCoordinates> allCoords = castleDAO.getAllCastlesCoordinatesForPlayer(serverId, playerId);
		
		final PlayerData playerData = playerDAO.getPlayerForId(serverId, playerId);
		final String textValue = playerData.getName() + " (" + String.valueOf(playerId) + ")";
		
		return writeImageFile(serverId, imgFile, allCoords, colour, thickness, renderBase, "Player", textValue);
	}
	@Override
	public File generateCastleMap(final String serverId, final int castleId, final boolean clear, int thickness, final boolean renderBase, final Color colour) throws IOException {
		
		File imgDir = new File(getDirForCastle(PathType.IMAGE, serverId, castleId));
		File imgFile = new File(imgDir, castleId+"_"+thickness+(renderBase?"_base":"")+"."+ControllerConstants.URL.Maps.ImageFormat);
		imgDir.mkdirs();
		
		if (imgFile.exists() && !clear) {
			final long lastModified = imgFile.lastModified();
			long now = new Date().getTime();
			now = now - IMAGE_CACHE_TIME;
			if (lastModified > now) {
				return imgFile;
			}
		}
		
		final List<MapCoordinates> allCoords = castleDAO.getAllCastlesCoordinatesForCastle(serverId, castleId);
		
		final CastleData castleData = castleDAO.getCastleForId(serverId, castleId);
		final String textValue = castleData.getName() + " (" + String.valueOf(castleId) + ")";
		
		return writeImageFile(serverId, imgFile, allCoords, colour, thickness, renderBase, "Castle", textValue);
	}
	
	private String getDirForBaseServer(final PathType pathType, final String serverId) {
		return getPath(pathType, "/"+serverId);
	}
	private String getDirForAlliance(final PathType pathType, final String serverId, final int allianceId) {
		final String splitDir = String.valueOf(allianceId - allianceId % 1000);
		return getPath(pathType, "/"+serverId+"/alliances/"+splitDir);
	}
	private String getDirForPlayer(final PathType pathType, final String serverId, final int playerId) {
		final String splitDir = String.valueOf(playerId - playerId % 1000);
		return getPath(pathType, "/"+serverId+"/players/"+splitDir);
	}
	private String getDirForCastle(final PathType pathType, final String serverId, final int castleId) {
		final String splitDir = String.valueOf(castleId - castleId % 1000);
		return getPath(pathType, "/"+serverId+"/castles/"+splitDir);
	}
	private String getDirForCompare(final PathType pathType, final String serverId) {
		return getPath(pathType, "/"+serverId+"/compare");
	}
	private String getPath(final PathType pathtype, final String path) {
		final String workingDir = System.getProperty("user.dir");
		String type = "";
		if(pathtype.equals(PathType.IMAGE)) {
			type = "/map-img";
		} else if(pathtype.equals(PathType.CSV)) {
			type = "/map-csv";
		}
		return workingDir + "/lkhelper-data" + type + path;
	}
	

	@Override
	public File generateCompareMap(final String serverId, final List<MapCompareRequest> mapCompareRequests, final String mapCompareRequestString, final boolean clear) throws IOException {
		
		File imgDir = new File(getDirForCompare(PathType.IMAGE, serverId));
		File imgFile = new File(imgDir, mapCompareRequestString+"."+ControllerConstants.URL.Maps.ImageFormat);
		imgDir.mkdirs();
		
		if (imgFile.exists() && !clear) {
			final long lastModified = imgFile.lastModified();
			long now = new Date().getTime();
			now = now - IMAGE_CACHE_TIME;
			if (lastModified > now) {
				return imgFile;
			}
		}
		
		final List<MapCompareRequest> allianceMapRequests = new ArrayList<MapCompareRequest>();
		final List<MapCompareRequest> playerMapRequests = new ArrayList<MapCompareRequest>();
		final List<MapCompareRequest> castleMapRequests = new ArrayList<MapCompareRequest>();
		
		for (MapCompareRequest mapCompareRequest : mapCompareRequests) {
			final String type = mapCompareRequest.getType();
			if(type.equals(ControllerConstants.URL.Maps.CompareTypeAlliance)) {
				final int id = mapCompareRequest.getId();
				final List<MapCoordinates> allCoords = castleDAO.getAllCastlesCoordinatesForAlliance(serverId, id);
				mapCompareRequest.setAllCoords(allCoords);
				final AllianceData allianceData = allianceDAO.getAllianceForId(serverId, id);
				mapCompareRequest.setTextValue(allianceData.getName() + " (" + String.valueOf(id) + ")");
				allianceMapRequests.add(mapCompareRequest);
			} else if(type.equals(ControllerConstants.URL.Maps.CompareTypePlayer)) {
				final int id = mapCompareRequest.getId();
				final List<MapCoordinates> allCoords = castleDAO.getAllCastlesCoordinatesForPlayer(serverId, id);
				mapCompareRequest.setAllCoords(allCoords);
				final PlayerData playerData = playerDAO.getPlayerForId(serverId, id);
				mapCompareRequest.setTextValue(playerData.getName() + " (" + String.valueOf(id) + ")");
				playerMapRequests.add(mapCompareRequest);
			} else if(type.equals(ControllerConstants.URL.Maps.CompareTypeCastle)) {
				final int id = mapCompareRequest.getId();
				final List<MapCoordinates> allCoords = castleDAO.getAllCastlesCoordinatesForCastle(serverId, id);
				mapCompareRequest.setAllCoords(allCoords);
				final CastleData castleData = castleDAO.getCastleForId(serverId, id);
				mapCompareRequest.setTextValue(castleData.getName() + " (" + String.valueOf(id) + ")");
				castleMapRequests.add(mapCompareRequest);
			} else {
				LOG.info("Unknown map compare type: " + type + " for request: " + mapCompareRequestString + ". Ignoring request");
			}
		}
		
		writeCompareImageFile(serverId, imgFile, allianceMapRequests, playerMapRequests, castleMapRequests);
		return imgFile;
	}
	
	private File writeCompareImageFile(final String serverId, final File file, final List<MapCompareRequest> allianceMapRequests, final List<MapCompareRequest> playerMapRequests, final List<MapCompareRequest> castleMapRequests) throws IOException {
		
		final List<MapCoordinates> boundaries = castleDAO.getBoundaryCoordinatesForServer(serverId);
		
		final int leftMostPos = boundaries.get(0).getX();
		final int rightMostPos = boundaries.get(1).getX();
		final int topMostPos = boundaries.get(2).getY();
		final int bottomMostPos = boundaries.get(3).getY();
		
		final int width = rightMostPos - leftMostPos;
		final int height = bottomMostPos - topMostPos;
		
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = newImage.createGraphics();
		
		//Render base
		BufferedImage baseImage = ImageIO.read(generateBaseMap(serverId, false, 1, new Color(Integer.parseInt(DEFAULT_COLOUR_BASE,16))));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		g.drawImage(baseImage, 0, 0, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		int textYPos = TEXT_Y_POS;
		//Draw server and time
		final Color defaultTextColour = new Color(Integer.parseInt(DEFAULT_COLOUR_TEXT,16)); 
		Format serverTimeFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		final String serverAndTime = serverId + " - " + serverTimeFormatter.format(new Date());
		g.setColor(defaultTextColour);
		g.drawString(getStringAndFont(serverAndTime, TEXT_DEFAULT_FONT_SIZE_SERVER_DATE).getIterator(), TEXT_X_POS, (height-textYPos));
		
		//Render alliances
		if(!allianceMapRequests.isEmpty()) {
			g.setColor(defaultTextColour);
			g.drawString(getStringAndFont("Alliance", TEXT_DEFAULT_FONT_SIZE).getIterator(), TEXT_X_POS, textYPos);
			textYPos = textYPos + TEXT_LINE_SPACING;
			for (MapCompareRequest mapRequest : allianceMapRequests) {
				g.setStroke(new BasicStroke(mapRequest.getThickness()));
				g.setColor(new Color(Integer.parseInt(mapRequest.getColor(),16)));
				g.drawString(getStringAndFont(String.valueOf(mapRequest.getTextValue()), TEXT_DEFAULT_FONT_SIZE).getIterator(), TEXT_X_POS, textYPos);
				textYPos = textYPos + TEXT_LINE_SPACING;
				for (MapCoordinates mapCoordinates : mapRequest.getAllCoords()) {
					int locX = mapCoordinates.getX() - leftMostPos;
					int locY = height - (bottomMostPos - mapCoordinates.getY());
					
					g.drawLine(locX, locY, locX, locY);
				}
			}
			
		}

		//Render players
		if(!playerMapRequests.isEmpty()) {
			g.setColor(defaultTextColour);
			g.drawString(getStringAndFont("Players", TEXT_DEFAULT_FONT_SIZE).getIterator(), TEXT_X_POS, textYPos);
			textYPos = textYPos + TEXT_LINE_SPACING;
			for (MapCompareRequest mapRequest : playerMapRequests) {
				g.setStroke(new BasicStroke(mapRequest.getThickness()));
				g.setColor(new Color(Integer.parseInt(mapRequest.getColor(),16)));
				g.drawString(getStringAndFont(String.valueOf(mapRequest.getTextValue()), TEXT_DEFAULT_FONT_SIZE).getIterator(), TEXT_X_POS, textYPos);
				textYPos = textYPos + TEXT_LINE_SPACING;
				for (MapCoordinates mapCoordinates : mapRequest.getAllCoords()) {
					int locX = mapCoordinates.getX() - leftMostPos;
					int locY = height - (bottomMostPos - mapCoordinates.getY());
					
					g.drawLine(locX, locY, locX, locY);
				}
			}
			
		}
		
		//Render castles
		if(!castleMapRequests.isEmpty()) {
			g.setColor(defaultTextColour);
			g.drawString(getStringAndFont("Castles", TEXT_DEFAULT_FONT_SIZE).getIterator(), TEXT_X_POS, textYPos);
			textYPos = textYPos + TEXT_LINE_SPACING;
			for (MapCompareRequest mapRequest : castleMapRequests) {
				g.setStroke(new BasicStroke(mapRequest.getThickness()));
				g.setColor(new Color(Integer.parseInt(mapRequest.getColor(),16)));
				g.drawString(getStringAndFont(String.valueOf(mapRequest.getTextValue()), TEXT_DEFAULT_FONT_SIZE).getIterator(), TEXT_X_POS, textYPos);
				textYPos = textYPos + TEXT_LINE_SPACING;
				for (MapCoordinates mapCoordinates : mapRequest.getAllCoords()) {
					int locX = mapCoordinates.getX() - leftMostPos;
					int locY = height - (bottomMostPos - mapCoordinates.getY());
					
					g.drawLine(locX, locY, locX, locY);
				}
			}
			
		}
		
		
		ImageIO.write(newImage, "png", file);
		
		return file;
	}

	@Override
	public List<MapCompareRequest> decodeMapCompareRequests(final String mapCompareRequestString) {
		
		final List<MapCompareRequest> mapCompareRequests = new ArrayList<MapCompareRequest>();
		
		String[] eachCompareStringList = mapCompareRequestString.split(ControllerConstants.URL.Maps.CompareDividerItem);
		for (String eachCompareString : eachCompareStringList) {
			List<String> attributeList = Arrays.asList(eachCompareString.split(ControllerConstants.URL.Maps.CompareDividerAttribute));
			final String type = attributeList.get(0);
			final int id = Integer.valueOf(attributeList.get(1)).intValue();
			final int thickness = Integer.valueOf(attributeList.get(2)).intValue();
			final String color = attributeList.get(3);
			mapCompareRequests.add(new MapCompareRequest(type, id, thickness, color));
		}
		return mapCompareRequests;
	}
	@Override
	public String encodeMapCompareRequests(final List<MapCompareRequest> mapCompareRequests) {
		
		final List<StringBuilder> mapCompareRequestStrings = new ArrayList<StringBuilder>();
		
		for (MapCompareRequest mapCompareRequest : mapCompareRequests) {
			final StringBuilder mapCompareRequestString = new StringBuilder();
			mapCompareRequestString.append(mapCompareRequest.getType()).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
			mapCompareRequestString.append(mapCompareRequest.getId()).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
			mapCompareRequestString.append(mapCompareRequest.getThickness()).append(ControllerConstants.URL.Maps.CompareDividerAttribute);
			mapCompareRequestString.append(mapCompareRequest.getColor());
			mapCompareRequestStrings.add(mapCompareRequestString);
		}
		
		String mapCompareRequestString = StringUtils.join(mapCompareRequestStrings, ControllerConstants.URL.Maps.CompareDividerItem);
		return mapCompareRequestString;
	}
	@Override
	public String[] getCompareMapColoursCastle() {
		String[] colours = new String[] { // dark
				  "a60000",  //ff7373 ff0000 a60000 red
				  "1d8c00",  //85eb6a 2dd700 1d8c00 green
				  "a68b00",  //ffe873 ffd600 a68b00 yellow
				  "080b74",  //7375d8 1a1eb2 080b74 blue
				  "a66a00",  //ffcc73 ffa200 a66a00 peach
				  "033e6b",  //66a3d2 0b61a4 033e6b light blue
				  "a64e00",  //ffB573 ff7800 a64e00 orange
				  "4e026e", //b365d4 7908aa 4e026e purple
				  "8fa200",  //ecfc71 dcf900 8fa200 lime
				  "370470", //9c6ad6 580ead 370470 mauve
				  "00675c",  //5dcfc3 009e8e 00675c turquoise
				  "800053"  //e266b7 c50080 800053 pink
				};
		return colours;
	}
	@Override
	public String[] getCompareMapColoursPlayer() {
		String[] colours = new String[] { // main
				  "ff0000",  //ff7373 ff0000 a60000 red
				  "2dd700",  //85eb6a 2dd700 1d8c00 green
				  "ffd600",  //ffe873 ffd600 a68b00 yellow
				  "1a1eb2",  //7375d8 1a1eb2 080b74 blue
				  "ffa200",  //ffcc73 ffa200 a66a00 peach
				  "0b61a4",  //66a3d2 0b61a4 033e6b light blue
				  "ff7800",  //ffB573 ff7800 a64e00 orange
				  "7908aa", //b365d4 7908aa 4e026e purple
				  "dcf900",  //ecfc71 dcf900 8fa200 lime
				  "580ead", //9c6ad6 580ead 370470 mauve
				  "009e8e",  //5dcfc3 009e8e 00675c turquoise
				  "c50080"  //e266b7 c50080 800053 pink
				};
		
		return colours;
	}
	@Override
	public String[] getCompareMapColoursAlliance() {
		String[] colours = new String[] { // light
				  "ff7373",  //ff7373 ff0000 a60000 red
				  "85eb6a",  //85eb6a 2dd700 1d8c00 green
				  "ffe873",  //ffe873 ffd600 a68b00 yellow
				  "7375d8",  //7375d8 1a1eb2 080b74 blue
				  "ffcc73",  //ffcc73 ffa200 a66a00 peach
				  "66a3d2",  //66a3d2 0b61a4 033e6b light blue
				  "ffB573",  //ffB573 ff7800 a64e00 orange
				  "b365d4", //b365d4 7908aa 4e026e purple
				  "ecfc71",  //ecfc71 dcf900 8fa200 lime
				  "9c6ad6", //9c6ad6 580ead 370470 mauve
				  "5dcfc3",  //5dcfc3 009e8e 00675c turquoise
				  "e266b7"  //e266b7 c50080 800053 pink
				};
		return colours;
	}
	@Override
	public String[] getCompareMapColoursAll() {
		String[] colours = new String[] { // light - dark - lgith
				  "ff0000",  //ff7373 ff0000 a60000 red
				  "2dd700",  //85eb6a 2dd700 1d8c00 green
				  "ffd600",  //ffe873 ffd600 a68b00 yellow
				  "1a1eb2",  //7375d8 1a1eb2 080b74 blue
				  "ffa200",  //ffcc73 ffa200 a66a00 peach
				  "0b61a4",  //66a3d2 0b61a4 033e6b light blue
				  "ff7800",  //ffB573 ff7800 a64e00 orange
				  "7908aa", //b365d4 7908aa 4e026e purple
				  "dcf900",  //ecfc71 dcf900 8fa200 lime
				  "580ead", //9c6ad6 580ead 370470 mauve
				  "009e8e",  //5dcfc3 009e8e 00675c turquoise
				  "c50080", //e266b7 c50080 800053 pink
				  
				  "a60000",  //ff7373 ff0000 a60000 red
				  "1d8c00",  //85eb6a 2dd700 1d8c00 green
				  "a68b00",  //ffe873 ffd600 a68b00 yellow
				  "080b74",  //7375d8 1a1eb2 080b74 blue
				  "a66a00",  //ffcc73 ffa200 a66a00 peach
				  "033e6b",  //66a3d2 0b61a4 033e6b light blue
				  "a64e00",  //ffB573 ff7800 a64e00 orange
				  "4e026e", //b365d4 7908aa 4e026e purple
				  "8fa200",  //ecfc71 dcf900 8fa200 lime
				  "370470", //9c6ad6 580ead 370470 mauve
				  "00675c",  //5dcfc3 009e8e 00675c turquoise
				  "800053", //e266b7 c50080 800053 pink
				  
				  "ff7373",  //ff7373 ff0000 a60000 red
				  "85eb6a",  //85eb6a 2dd700 1d8c00 green
				  "ffe873",  //ffe873 ffd600 a68b00 yellow
				  "7375d8",  //7375d8 1a1eb2 080b74 blue
				  "ffcc73",  //ffcc73 ffa200 a66a00 peach
				  "66a3d2",  //66a3d2 0b61a4 033e6b light blue
				  "ffB573",  //ffB573 ff7800 a64e00 orange
				  "b365d4", //b365d4 7908aa 4e026e purple
				  "ecfc71",  //ecfc71 dcf900 8fa200 lime
				  "9c6ad6", //9c6ad6 580ead 370470 mauve
				  "5dcfc3",  //5dcfc3 009e8e 00675c turquoise
				  "e266b7"  //e266b7 c50080 800053 pink
				};
		return colours;
	}
}
