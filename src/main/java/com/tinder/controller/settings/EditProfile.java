package com.tinder.controller.settings;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.info.UserParam;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping("/EditProfile")
public class EditProfile {

	@Autowired
	private IUserDAO userDAO;
	
	@RequestMapping(method = RequestMethod.POST)
	public String doGet(
		Principal principal,
		@RequestParam(value=UserParam.EMAIL,required = false)String newEmail,
		@RequestParam(value=UserParam.AGE,required = false)String newAge,
		@RequestParam(value=UserParam.USERNAME,required = false)String newUsername,
		@RequestParam(value=UserParam.PASSWORD,required = false)String newPassword,
		@RequestParam(value=UserParam.DESCRIPTION,required = false)String newDescription
		) {

		User user = userDAO.getUser(principal.getName());
		if (newEmail != null) {
			user.setEmail(newEmail);
		}
		if (newAge != null) {
			user.setAge(Integer.parseInt(newAge));
		}
		if (newUsername != null) {
			user.setUsername(newUsername);
		}
		if (newPassword != null) {
			user.setPasswordHash(newPassword);
		}
		if (newDescription != null) {
			user.setDescription(newDescription);
		}
		
		userDAO.updateUser(user);
		
		return "redirect:/Profile";
	}

}
