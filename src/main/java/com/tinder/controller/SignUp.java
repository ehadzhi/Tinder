package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.model.dao.user.IUserDAO;

@Controller
@RequestMapping(value = "/SignUp")
public class SignUp {
	
	@Autowired
	private IUserDAO userDAO;

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request) {
			userDAO.registerUser(request.getParameter("username"),
					request.getParameter("password"),
					request.getParameter("email"),
					parseGender(request.getParameter("gender")),
					Integer.parseInt(request.getParameter("age")),
					request.getParameter("fullName"));
			return "redirect:/Home";
	}
	
	private boolean parseGender(String gender){
		if(gender.equals("male")){
			return true;
		}
		return false;
	}

}
