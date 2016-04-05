package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.info.UserParam;
import com.tinder.model.dao.user.IUserDAO;

@Controller
@RequestMapping(value = "/SignUp")
public class SignUp {

	@Autowired
	private IUserDAO userDAO;

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request,
		@RequestParam(value = UserParam.USERNAME, required = false) String username,
		@RequestParam(value = UserParam.PASSWORD, required = false) String password,
		@RequestParam(value = UserParam.EMAIL, required = false) String email,
		@RequestParam(value = UserParam.GENDER, required = false) String gender,
		@RequestParam(value = UserParam.AGE, required = false) int age,
		@RequestParam(value = UserParam.FULL_NAME, required = false) String fullName) {

		userDAO.registerUser(username, password, email,
			UserParam.parseGender(gender), age, fullName);
		
		return "redirect:/Home";
	}

}
