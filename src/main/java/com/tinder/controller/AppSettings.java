package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/AppSettings")
public class AppSettings {

	@RequestMapping(method = RequestMethod.GET)
	public String appSettings(HttpServletRequest request) {
		if( Home.checkValidSession(request) != null){
			return Home.checkValidSession(request);
		}
		return "app-settings";
	}

}
