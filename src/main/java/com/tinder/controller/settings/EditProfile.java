package com.tinder.controller.settings;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
		@RequestParam("email")String newEmail,
		@RequestParam("username")String newUsername,
		@RequestParam("password")String newPassword,
		@RequestParam("description")String newDescription
		) 
	{
		boolean logout = false;
		
		User user = userDAO.getUser(principal.getName());
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
		
		userDAO.updateUser(user);
		if(!logout)
			request.getSession(false).setAttribute("user", user);
		
		return "redirect:/" + (logout?"Logout":"Profile");
	}

}
