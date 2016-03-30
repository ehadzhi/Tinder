package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.exceptions.DBException;
import com.tinder.model.dao.UserDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping(value = "/DiscoverySettings")
public class DiscoverySettings {

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request) {
		if( Home.checkValidSession(request) != null){
			return Home.checkValidSession(request);
		}
		return "discovery-settings";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request) {
		if (Home.checkValidSession(request) != null) {
			return Home.checkValidSession(request);
		}
		HttpSession session = request.getSession(false);
		int dist = Integer.parseInt(request.getParameter("search-distance"));
		int minAge = Integer.parseInt(request.getParameter("age-range").trim().split(";")[0]);
		int maxAge = Integer.parseInt(request.getParameter("age-range").trim().split(";")[1]);
		boolean showFemale = request.getParameter("show-women") == null ? false
				: request.getParameter("show-women").equals("on");
		boolean showMale = request.getParameter("show-men") == null ? false
				: request.getParameter("show-men").equals("on");
		try {
			UserDAO.setUserDiscoverySettings(((User) session.getAttribute("user")).getId(), showMale, showFemale, dist,
					minAge, maxAge);
			User user = UserDAO.getUser(((User) session.getAttribute("user")).getUsername());
			session.setAttribute("user", user);

		} catch (DBException e) {
			e.printStackTrace();
		}

		return "redirect:DiscoverySettings";
	}

}