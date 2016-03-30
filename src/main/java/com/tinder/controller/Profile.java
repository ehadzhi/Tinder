package com.tinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.exceptions.DBException;
import com.tinder.model.dao.UserDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping(value = "/Profile")
public class Profile {
	
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(Model model,HttpServletRequest request) throws DBException {
		if( Home.checkValidSession(request) != null){
			return Home.checkValidSession(request);
		}
		User user = (User) request.getSession().getAttribute("user");
		List<String> pictures = UserDAO.getAllPhotosOfUser(user.getUsername());
		model.addAttribute("pictures",pictures);
		return "profile";
	}

}
