package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.config.UserLoader;

@Controller
@RequestMapping(value = { "/EditProfile" })
public class EditProfile {
	
	@Autowired
	private UserLoader loader;

	@RequestMapping(method = RequestMethod.POST)
	public String doGet(HttpServletRequest request) {
		loader.loadUser(request);

		String newEmail = request.getParameter("newEmail");
		String newAge = request.getParameter("newAge");
		String newUsername = request.getParameter("newUsername");
		String newPassword = request.getParameter("newPassword");
		String newDescription = request.getParameter("newDescription");

		if (newEmail != null) {
			
		}
		if (newAge != null) {
			
		}
		if (newUsername != null) {
			
		}
		if (newPassword != null) {
			
		}
		if (newDescription != null) {
			
		}

		return "redirect:/Profile";
	}

}
