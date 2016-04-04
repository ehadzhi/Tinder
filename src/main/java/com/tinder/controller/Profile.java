package com.tinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.config.UserLoader;
import com.tinder.exceptions.DBException;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping(value = "/Profile")
public class Profile {
	
	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private UserLoader loader;
	
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(Model model,HttpServletRequest request) throws DBException {
		loader.loadUser(request);
		User user = (User) request.getSession().getAttribute("user");
		List<String> pictures = userDAO.getAllPhotosOfUser(user.getUsername());
		model.addAttribute("pictures",pictures);
		return "profile";
	}

}
