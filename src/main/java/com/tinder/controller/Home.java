package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.net.jsse.openssl.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.config.UserLoader;
import com.tinder.model.dao.user.IUserDAO;

@Controller
@RequestMapping(value = {"/index","/Home"})
public class Home {

	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private UserLoader loader;
	
	public String index(HttpServletRequest request) {
		loader.loadUser(request);
		return "login";
	}
	
	
}
