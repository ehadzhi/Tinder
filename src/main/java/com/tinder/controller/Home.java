package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = {"/Home","/index"})
public class Home {

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request) {
		if( Home.checkValidSession(request) != null){
			return Home.checkValidSession(request);
		}
			return "index";
	}

	public static String checkValidSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			return "login";
		}
		return null;
	}

}
