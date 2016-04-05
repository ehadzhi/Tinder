package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.config.UserLoader;

@Controller
@RequestMapping(value = { "/Home", "/" })
public class Home {
	
	@Autowired
	private UserLoader loader;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request) {
		loader.loadUser(request);
		return "index";
	}

}
