package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.model.dao.UserDAO;

@Controller
@RequestMapping(value = "/SignUp")
public class SignUp {

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request) {
		try {
			UserDAO.registerUser(request.getParameter("username"),
					request.getParameter("password"),
					request.getParameter("email"),
					parseGender(request.getParameter("gender")),
					Integer.parseInt(request.getParameter("age")),
					request.getParameter("fullName"));
			return "redirect:/Home";
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage());
			return "error";
		}
	}
	
	private boolean parseGender(String gender){
		if(gender.equals("male")){
			return true;
		}
		return false;
	}

}
