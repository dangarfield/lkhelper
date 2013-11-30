package com.dangarfield.lkhelper.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dangarfield.lkhelper.constants.ControllerConstants;
import com.dangarfield.lkhelper.dao.AllianceDAO;
import com.dangarfield.lkhelper.dao.CastleDAO;
import com.dangarfield.lkhelper.dao.PlayerDAO;
import com.dangarfield.lkhelper.data.search.SearchDataAlliance;
import com.dangarfield.lkhelper.data.search.SearchDataCastle;
import com.dangarfield.lkhelper.data.search.SearchDataPlayer;
import com.dangarfield.lkhelper.data.search.TypeaheadAlliance;
import com.dangarfield.lkhelper.data.search.TypeaheadCastle;
import com.dangarfield.lkhelper.data.search.TypeaheadPlayer;

@Controller
public class SearchController extends AbstractLKController {

	public static final int RESULTS_PER_PAGE = 50;
	public static final int RESULTS_FOR_TYPEAHEAD = 20;
	public static final int RESULTS_FOR_COMPARE = 10;
	
	@Autowired
	private CastleDAO castleDAO;
	@Autowired
	private PlayerDAO playerDAO;
	@Autowired
	private AllianceDAO allianceDAO;
	
	

	@RequestMapping(value=ControllerConstants.URL.Search.TypeaheadCastle, method = RequestMethod.GET)
	public @ResponseBody List<TypeaheadCastle> typeaheadCastle(Model model, Principal principal, HttpServletRequest request,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@PathVariable(ControllerConstants.URL.PathVariable.SearchString) String searchString) throws UnsupportedEncodingException {
		String uri = request.getRequestURI();
		String[] uriSplit = uri.split("/");
		String unencodedSearchString = uriSplit[uriSplit.length-1]; 
		String uriD = URLDecoder.decode(unencodedSearchString, "UTF-8");
		List<TypeaheadCastle> results = castleDAO.getTypeaheadCastles(serverId, uriD);
		return results;
	}
	@RequestMapping(value=ControllerConstants.URL.Search.TypeaheadPlayer, method = RequestMethod.GET)
	public @ResponseBody List<TypeaheadPlayer> typeaheadPlayer(Model model, Principal principal, HttpServletRequest request,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@PathVariable(ControllerConstants.URL.PathVariable.SearchString) String searchString) throws UnsupportedEncodingException {
		String uri = request.getRequestURI();
		String[] uriSplit = uri.split("/");
		String unencodedSearchString = uriSplit[uriSplit.length-1]; 
		String uriD = URLDecoder.decode(unencodedSearchString, "UTF-8");
		List<TypeaheadPlayer> results = playerDAO.getTypeaheadPlayers(serverId, uriD);
		return results;
	}
	@RequestMapping(value=ControllerConstants.URL.Search.TypeaheadAlliance, method = RequestMethod.GET)
	public @ResponseBody List<TypeaheadAlliance> typeaheadAlliance(Model model, Principal principal, HttpServletRequest request,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@PathVariable(ControllerConstants.URL.PathVariable.SearchString) String searchString) throws UnsupportedEncodingException {
		String uri = request.getRequestURI();
		String[] uriSplit = uri.split("/");
		String unencodedSearchString = uriSplit[uriSplit.length-1]; 
		String uriD = URLDecoder.decode(unencodedSearchString, "UTF-8");
		List<TypeaheadAlliance> results = allianceDAO.getTypeaheadAlliances(serverId, uriD);
		return results;
	}

	@RequestMapping(value=ControllerConstants.URL.Search.SearchCastleParam, method = RequestMethod.GET)
	public String searchByParamCastle(Model model, Principal principal, HttpServletRequest request,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchString, required = true) String searchString,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchPage, required = false) Integer searchPageBigInt) throws UnsupportedEncodingException {
		
		final String decodedSearchString = getParameterFromQueryString(request.getQueryString(), ControllerConstants.URL.PathVariable.SearchString);
		int page = 1;
		int skip = 0;
		int displayFrom = 1;
		if(searchPageBigInt != null && searchPageBigInt > 0) {
			page = searchPageBigInt.intValue();
			skip = RESULTS_PER_PAGE * (page-1);
			displayFrom = (RESULTS_PER_PAGE * (page-1)) + 1;
		}
		
		final SearchDataCastle searchData = new SearchDataCastle(serverId, decodedSearchString, skip, RESULTS_PER_PAGE, page, displayFrom);
		final SearchDataCastle searchResults = castleDAO.getSearchForCastles(searchData);
		
		if(searchResults.getTotalResults() == 1) {
			// Only one, therefore, redirect to that page
			return "redirect:" + ControllerConstants.URL.Report.OwnCastleReportScorecard + "/" + serverId + "/" + searchResults.getResults().iterator().next().getId();
		}
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Castle Search | " + decodedSearchString);
		model.addAttribute("searchResults", searchResults);
		
		model.addAttribute("searchTitle", "Castle Search for " + decodedSearchString);
		model.addAttribute("searchType", "castle");
		
		return ControllerConstants.Views.Search.SearchResults;
	}
	@RequestMapping(value=ControllerConstants.URL.Search.CompareCastleParam, method = RequestMethod.GET)
	public @ResponseBody SearchDataCastle searchCompareByParamCastle(Model model, Principal principal, HttpServletRequest request,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchString, required = true) String searchString,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchPage, required = false) Integer searchPageBigInt) throws UnsupportedEncodingException {
		
		final String decodedSearchString = getParameterFromQueryString(request.getQueryString(), ControllerConstants.URL.PathVariable.SearchString);
		int page = 1;
		int skip = 0;
		int displayFrom = 1;
		if(searchPageBigInt != null && searchPageBigInt > 0) {
			page = searchPageBigInt.intValue();
			skip = RESULTS_FOR_COMPARE * (page-1);
			displayFrom = (RESULTS_FOR_COMPARE * (page-1)) + 1;
		}
		
		final SearchDataCastle searchData = new SearchDataCastle(serverId, decodedSearchString, skip, RESULTS_FOR_COMPARE, page, displayFrom);
		final SearchDataCastle searchResults = castleDAO.getSearchForCastles(searchData);
		
		return searchResults;
	}
	
	@RequestMapping(value=ControllerConstants.URL.Search.SearchPlayerParam, method = RequestMethod.GET)
	public String searchByParamPlayer(Model model, Principal principal, HttpServletRequest request,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchString, required = true) String searchString,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchPage, required = false) Integer searchPageBigInt) throws UnsupportedEncodingException {
		
		final String decodedSearchString = getParameterFromQueryString(request.getQueryString(), ControllerConstants.URL.PathVariable.SearchString);
		int page = 1;
		int skip = 0;
		int displayFrom = 1;
		if(searchPageBigInt != null && searchPageBigInt > 0) {
			page = searchPageBigInt.intValue();
			skip = RESULTS_PER_PAGE * (page-1);
			displayFrom = (RESULTS_PER_PAGE * (page-1)) + 1;
		}
		
		final SearchDataPlayer searchData = new SearchDataPlayer(serverId, decodedSearchString, skip, RESULTS_PER_PAGE, page, displayFrom);
		final SearchDataPlayer searchResults = playerDAO.getSearchForPlayers(searchData);
		
		if(searchResults.getTotalResults() == 1) {
			// Only one, therefore, redirect to that page
			return "redirect:" + ControllerConstants.URL.Report.OwnPlayerReportScorecard + "/" + serverId + "/" + searchResults.getResults().iterator().next().getId();
		}
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Player Search | " + decodedSearchString);
		model.addAttribute("searchResults", searchResults);
		
		model.addAttribute("searchTitle", "Player Search for " + decodedSearchString);
		model.addAttribute("searchType", "player");
		
		return ControllerConstants.Views.Search.SearchResults;
	}
	@RequestMapping(value=ControllerConstants.URL.Search.ComparePlayerParam, method = RequestMethod.GET)
	public @ResponseBody SearchDataPlayer searchCompareByParamPlayer(Model model, Principal principal, HttpServletRequest request,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchString, required = true) String searchString,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchPage, required = false) Integer searchPageBigInt) throws UnsupportedEncodingException {
		
		final String decodedSearchString = getParameterFromQueryString(request.getQueryString(), ControllerConstants.URL.PathVariable.SearchString);
		int page = 1;
		int skip = 0;
		int displayFrom = 1;
		if(searchPageBigInt != null && searchPageBigInt > 0) {
			page = searchPageBigInt.intValue();
			skip = RESULTS_FOR_COMPARE * (page-1);
			displayFrom = (RESULTS_FOR_COMPARE * (page-1)) + 1;
		}
		
		final SearchDataPlayer searchData = new SearchDataPlayer(serverId, decodedSearchString, skip, RESULTS_FOR_COMPARE, page, displayFrom);
		final SearchDataPlayer searchResults = playerDAO.getSearchForPlayers(searchData);
		
		return searchResults;
	}
	
	
	
	@RequestMapping(value=ControllerConstants.URL.Search.SearchAllianceParam, method = RequestMethod.GET)
	public String searchByParamAlliance(Model model, Principal principal, HttpServletRequest request,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchString, required = true) String searchString,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchPage, required = false) Integer searchPageBigInt) throws UnsupportedEncodingException {
		
		final String decodedSearchString = getParameterFromQueryString(request.getQueryString(), ControllerConstants.URL.PathVariable.SearchString);
		int page = 1;
		int skip = 0;
		int displayFrom = 1;
		if(searchPageBigInt != null && searchPageBigInt > 0) {
			page = searchPageBigInt.intValue();
			skip = RESULTS_PER_PAGE * (page-1);
			displayFrom = (RESULTS_PER_PAGE * (page-1)) + 1;
		}
		
		final SearchDataAlliance searchData = new SearchDataAlliance(serverId, decodedSearchString, skip, RESULTS_PER_PAGE, page, displayFrom);
		final SearchDataAlliance searchResults = allianceDAO.getSearchForAlliances(searchData);
		
		if(searchResults.getTotalResults() == 1) {
			// Only one, therefore, redirect to that page
			return "redirect:" + ControllerConstants.URL.Report.OwnAllianceReportScorecard + "/" + serverId + "/" + searchResults.getResults().iterator().next().getId();
		}
		
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Alliance Search | " + decodedSearchString);
		model.addAttribute("searchResults", searchResults);
		
		model.addAttribute("searchTitle", "Alliance Search for " + decodedSearchString);
		model.addAttribute("searchType", "alliance");
		
		return ControllerConstants.Views.Search.SearchResults;
	}
	
	@RequestMapping(value=ControllerConstants.URL.Search.CompareAllianceParam, method = RequestMethod.GET)
	public @ResponseBody SearchDataAlliance searchCompareByParamAlliance(Model model, Principal principal, HttpServletRequest request,
			@PathVariable(ControllerConstants.URL.PathVariable.ServerID) String serverId,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchString, required = true) String searchString,
			@RequestParam(value = ControllerConstants.URL.PathVariable.SearchPage, required = false) Integer searchPageBigInt) throws UnsupportedEncodingException {
		
		final String decodedSearchString = getParameterFromQueryString(request.getQueryString(), ControllerConstants.URL.PathVariable.SearchString);
		int page = 1;
		int skip = 0;
		int displayFrom = 1;
		if(searchPageBigInt != null && searchPageBigInt > 0) {
			page = searchPageBigInt.intValue();
			skip = RESULTS_FOR_COMPARE * (page-1);
			displayFrom = (RESULTS_FOR_COMPARE * (page-1)) + 1;
		}
		
		final SearchDataAlliance searchData = new SearchDataAlliance(serverId, decodedSearchString, skip, RESULTS_FOR_COMPARE, page, displayFrom);
		final SearchDataAlliance searchResults = allianceDAO.getSearchForAlliances(searchData);
		
		return searchResults;
	}
}
