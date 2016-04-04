package com.tinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/AppSettings")
public class AppSettings {

	@RequestMapping(method = RequestMethod.GET)
	public String appSettings() {
		return "app-settings";
	}

}
