package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = { "/EditProfile" })
public class EditProfile {

	@RequestMapping(method = RequestMethod.POST)
	public String doGet(HttpServletRequest request) {
		if (Home.checkValidSession(request) != null) {
			return Home.checkValidSession(request);
		}

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
