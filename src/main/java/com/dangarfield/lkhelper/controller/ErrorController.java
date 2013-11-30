package com.dangarfield.lkhelper.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dangarfield.lkhelper.constants.ControllerConstants;
import com.dangarfield.lkhelper.exceptions.LKServerNotFoundException;

@Controller
public class ErrorController extends AbstractLKController{

	@RequestMapping(value=ControllerConstants.URL.Error.AccessDenied403, method = RequestMethod.GET)
	public String accessDeniued403(Model model, Principal principal) throws LKServerNotFoundException {
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Access Denied");
		return ControllerConstants.Views.Error.AccessDenied403;
	}
	@RequestMapping(value=ControllerConstants.URL.Error.PageNotFound404, method = RequestMethod.GET)
	public String pageNotFound04(Model model, Principal principal) throws LKServerNotFoundException {
		model.addAttribute("pageTitle", PAGE_TITLE_PREPEND + "Page Not Found");
		return ControllerConstants.Views.Error.PageNotFound404;
	}
}
