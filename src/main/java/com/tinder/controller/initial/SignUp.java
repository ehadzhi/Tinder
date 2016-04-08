package com.tinder.controller.initial;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.info.UserViewParam;
import com.tinder.model.dao.user.IUserDAO;

@Controller
@RequestMapping(value = "/SignUp")
public class SignUp {

	@Autowired
	private IUserDAO userDAO;

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request,
		@RequestParam(value = UserViewParam.USERNAME, required = false) String username,
		@RequestParam(value = UserViewParam.PASSWORD, required = false) String password,
		@RequestParam(value = UserViewParam.EMAIL, required = false) String email,
		@RequestParam(value = UserViewParam.GENDER, required = false) String gender,
		@RequestParam(value = UserViewParam.AGE, required = false) int age,
		@RequestParam(value = UserViewParam.FULL_NAME, required = false) String fullName) {

		userDAO.registerUser(username, password, email,
			UserViewParam.parseGender(gender), age, fullName);
		
		return "redirect:/Home";
	}

}
