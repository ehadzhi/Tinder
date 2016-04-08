package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/Messages")
public class Messages {

	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(HttpServletRequest request){
		return "messages";
	}

}
