package com.dangarfield.lkhelper.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dangarfield.lkhelper.constants.ControllerConstants;
import com.dangarfield.lkhelper.data.report.MapCompareRequest;
import com.dangarfield.lkhelper.service.ImageProcessingService;
import com.dangarfield.lkhelper.service.impl.ImageProcessingServiceImpl;

@Controller
public class MapsController {
	
	@Autowired
	private ImageProcessingService imageProcessingService;

	@RequestMapping(value=ControllerConstants.URL.Maps.BaseMap, method = RequestMethod.GET)
	public @ResponseBody byte[] baseServerMapImage(Model model, Principal principal,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@RequestParam(value = "clear", required = false) boolean clear,
			@RequestParam(value = "thickness", required = false) Integer thickness,
			@RequestParam(value = "colour", required = false) String colour) throws IOException {
		
		final int thicknessInt = ensureCorrectThickness(thickness, ImageProcessingServiceImpl.DEFAULT_THICKNESS_BASE);
		final Color colorObj = ensureCorrectColour(colour, ImageProcessingServiceImpl.DEFAULT_COLOUR_BASE);
		
		final File image = imageProcessingService.generateBaseMap(serverId, clear, thicknessInt, colorObj);
		InputStream inputStream = new FileInputStream(image);
	    return IOUtils.toByteArray(inputStream);
	}
	@RequestMapping(value=ControllerConstants.URL.Maps.AllianceMap, method = RequestMethod.GET)
	public @ResponseBody byte[] allianceMapImage(Model model, Principal principal,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@PathVariable(ControllerConstants.URL.PathVariable.AllianceID) int allianceId,
			@RequestParam(value = "clear", required = false) boolean clear,
			@RequestParam(value = "thickness", required = false) Integer thickness,
			@RequestParam(value = "base", required = false) boolean base,
			@RequestParam(value = "colour", required = false) String colour) throws IOException {
		
		final int thicknessInt = ensureCorrectThickness(thickness, ImageProcessingServiceImpl.DEFAULT_THICKNESS_ALLIANCE);
		final Color colorObj = ensureCorrectColour(colour, ImageProcessingServiceImpl.DEFAULT_COLOUR_ALLIANCE);
		
		final File image = imageProcessingService.generateAllianceMap(serverId, allianceId, clear, thicknessInt, base, colorObj);
		InputStream inputStream = new FileInputStream(image);
	    return IOUtils.toByteArray(inputStream);
	}
	@RequestMapping(value=ControllerConstants.URL.Maps.PlayerMap, method = RequestMethod.GET)
	public @ResponseBody byte[] playerMapImage(Model model, Principal principal,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@PathVariable(ControllerConstants.URL.PathVariable.PlayerID) int playerId,
			@RequestParam(value = "clear", required = false) boolean clear,
			@RequestParam(value = "thickness", required = false) Integer thickness,
			@RequestParam(value = "base", required = false) boolean base,
			@RequestParam(value = "colour", required = false) String colour) throws IOException {
		
		final int thicknessInt = ensureCorrectThickness(thickness, ImageProcessingServiceImpl.DEFAULT_THICKNESS_PLAYER);
		final Color colorObj = ensureCorrectColour(colour, ImageProcessingServiceImpl.DEFAULT_COLOUR_PLAYER);
		
		final File image = imageProcessingService.generatePlayerMap(serverId, playerId, clear, thicknessInt, base, colorObj);
		InputStream inputStream = new FileInputStream(image);
	    return IOUtils.toByteArray(inputStream);
	}
	@RequestMapping(value=ControllerConstants.URL.Maps.CastleMap, method = RequestMethod.GET)
	public @ResponseBody byte[] castleMapImage(Model model, Principal principal,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@PathVariable(ControllerConstants.URL.PathVariable.CastleID) int castleId,
			@RequestParam(value = "clear", required = false) boolean clear,
			@RequestParam(value = "thickness", required = false) Integer thickness,
			@RequestParam(value = "base", required = false) boolean base,
			@RequestParam(value = "colour", required = false) String colour) throws IOException {
		
		final int thicknessInt = ensureCorrectThickness(thickness, ImageProcessingServiceImpl.DEFAULT_THICKNESS_CASTLE);
		final Color colorObj = ensureCorrectColour(colour, ImageProcessingServiceImpl.DEFAULT_COLOUR_CASTLE);
		
		final File image = imageProcessingService.generateCastleMap(serverId, castleId, clear, thicknessInt, base, colorObj);
		InputStream inputStream = new FileInputStream(image);
	    return IOUtils.toByteArray(inputStream);
	}
	@RequestMapping(value=ControllerConstants.URL.Maps.CompareMap, method = RequestMethod.GET)
	public @ResponseBody byte[] compareMapImage(Model model, Principal principal,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@PathVariable(ControllerConstants.URL.PathVariable.CompareMapString) String mapCompareRequestString,
			@RequestParam(value = "clear", required = false) boolean clear) throws IOException {
		
		final List<MapCompareRequest> mapCompareRequests = imageProcessingService.decodeMapCompareRequests(mapCompareRequestString);
		
		final File image = imageProcessingService.generateCompareMap(serverId, mapCompareRequests, mapCompareRequestString, clear);
		InputStream inputStream = new FileInputStream(image);
	    return IOUtils.toByteArray(inputStream);
	}
	
	private int ensureCorrectThickness(final Integer thickness, final int defaultThickness) {
		return thickness!=null?thickness.intValue():defaultThickness;
	}
	
	private Color ensureCorrectColour(final String colour, final String defaultColour) {
		final String correctColour = colour!=null?colour.toLowerCase():defaultColour;
		return new Color(Integer.parseInt(correctColour,16));
	}
}
