package com.bucharest.ag.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class Home {
	@RequestMapping(method = RequestMethod.GET)
	public String goToHomePage(Model model) {
		return "index";
	}
}
