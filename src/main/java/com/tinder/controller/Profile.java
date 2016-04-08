package com.tinder.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.controller.initial.UserLoader;
import com.tinder.info.PictureViewParam;
import com.tinder.info.UserViewParam;
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
	protected String doGet(Model model,HttpServletRequest request) throws MessagingException {
		loader.loadUser(request);
		User user = (User) request.getSession().getAttribute(UserViewParam.USER);
		List<String> pictures = userDAO.getAllPhotosOfUser(user.getUsername());
		model.addAttribute(PictureViewParam.PICTURES,pictures);
		return "profile";
	}

}
