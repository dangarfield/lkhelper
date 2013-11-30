package com.dangarfield.lkhelper.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dangarfield.lkhelper.constants.ControllerConstants;

@Controller
public class BattleCalculatorController extends AbstractLKController {
	
	@RequestMapping(value=ControllerConstants.URL.Tools.BattleCalculatorBase, method = RequestMethod.GET)
	public String battleCalculatorHome(Model model, Principal principal) {
		
		return ControllerConstants.Views.Tools.BattleCalculator;
	}
	
}
