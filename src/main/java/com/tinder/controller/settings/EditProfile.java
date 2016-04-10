package com.tinder.controller.settings;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.info.UserViewParam;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping("/EditProfile")
public class EditProfile {

	@Autowired
	private IUserDAO userDAO;
	
	@RequestMapping(method = RequestMethod.POST)
	public String doGet(
		HttpServletRequest request,
		Principal principal,
		@RequestParam(UserViewParam.EMAIL)String newEmail,
		@RequestParam(UserViewParam.USERNAME)String newUsername,
		@RequestParam(UserViewParam.PASSWORD)String newPassword,
		@RequestParam(UserViewParam.DESCRIPTION)String newDescription) {
		
		User user = userDAO.getUser(principal.getName());
		
		boolean logout = changeOnlyNecessarySettings(principal,
				newEmail, newUsername, user, newPassword, newDescription);
		
		userDAO.updateUser(user);
		if(!logout)
			request.getSession(false).setAttribute("user", user);
		
		return "redirect:/" + (logout?"Logout":"Profile");
	}

	private boolean changeOnlyNecessarySettings(Principal principal,
			String newEmail, String newUsername,User user,
			String newPassword, String newDescription) {
		boolean logout = false;
		
		if (newEmail != null && !newEmail.equals("")) {
			user.setEmail(newEmail);
		}
		if (newUsername != null && !newUsername.equals("")) {
			user.setUsername(newUsername);
			logout = true;
		}
		if (newPassword != null && !newPassword.equals("")) {
			user.setPasswordHash(userDAO.calculateHash(newPassword));
		}
		if (newDescription != null && !newDescription.equals("")) {
			newDescription=newDescription.trim();
			user.setDescription(newDescription);
		}
		return logout;
	}

}
