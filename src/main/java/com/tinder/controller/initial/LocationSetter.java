package com.tinder.controller.initial;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.model.dao.user.IUserDAO;

@Controller
@RequestMapping(value = "/LocationSetter")
public class LocationSetter {

	@Autowired
	private IUserDAO userDAO;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request) throws InterruptedException {
		return "set-location";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(
			@RequestParam("latitude")double latitude,
			@RequestParam("longitude")double longitude,
			Principal principal) {
		
		userDAO.setLocation(principal.getName(), latitude, longitude);

		return "redirect:/Home";
	}
}
