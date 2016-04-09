package com.tinder.controller.initial;

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
	public String confirmEmail(HttpServletRequest req, @RequestParam("UUID") String uuid) {
		if (uuid != null) {
			UnconfirmedUser user = userDAO.getUnconfirmedUser(uuid);
			if (user != null) {
				userDAO.deleteUnconfirmedUser(uuid);
				userDAO.registerUserWithHashedPassword(user.getUsername(), user.getPasswordHash(), user.getEmail(),
						user.isGenderIsMale(), user.getAge(), user.getFullName());
			}
		}
		return "forward:/login";
	}
}
