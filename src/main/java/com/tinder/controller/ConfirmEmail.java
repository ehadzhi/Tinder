package com.tinder.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.model.dao.user.UserDAO;
import com.tinder.model.pojo.UnconfirmedUser;

@Controller
@RequestMapping("/ConfirmEmail")
public class ConfirmEmail {
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public String confirmEmail(HttpServletRequest req,@RequestParam("UUID")String uuid){
		UnconfirmedUser user = userDAO.getUnconfirmedUser(uuid);
		userDAO.deleteUnconfirmedUser(uuid);
		userDAO.registerUserWithHashedPassword(user.getUsername(),
				user.getPasswordHash(),user.getEmail(),user.isGenderIsMale(),
				user.getAge(), user.getFullName());
		req.setAttribute("error", "Please confirm your email!");
		return "forward:/login";
	}
}
