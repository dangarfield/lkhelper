package com.dangarfield.lkhelper.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dangarfield.lkhelper.constants.ControllerConstants;

@Controller
public class SuggestionsController extends AbstractLKController {
	
	@RequestMapping(value=ControllerConstants.URL.Suggestions.Suggestions, method = RequestMethod.GET)
	public String getSuggestionsPage(Model model, Principal principal) {
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Add Suggestions");
		return ControllerConstants.Views.Suggestions.Suggestions;
	}
	
	@RequestMapping(value=ControllerConstants.URL.Suggestions.Suggestions, method = RequestMethod.POST)
	public String postSuggestion(Model model, Principal principal) {
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Add Suggestions");
		return ControllerConstants.Views.Suggestions.Suggestions;
	}
	
}
