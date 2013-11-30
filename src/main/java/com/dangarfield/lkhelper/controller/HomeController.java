package com.dangarfield.lkhelper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dangarfield.lkhelper.constants.ControllerConstants;


@Controller
@RequestMapping(ControllerConstants.URL.Home.Home)
public class HomeController extends AbstractLKController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String homePage(Model model) {

		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Home");
		return ControllerConstants.Views.Home.Home;

	}
	
}
