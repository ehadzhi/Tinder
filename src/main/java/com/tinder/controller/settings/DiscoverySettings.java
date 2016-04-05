package com.tinder.controller.settings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.info.UserParam;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping(value = "/DiscoverySettings")
public class DiscoverySettings {

	@Autowired
	private IUserDAO userDAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request) {
		return "discovery-settings";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request,
		@RequestParam(UserParam.SEARCH_DISTANCE)int dist,
		@RequestParam(value=UserParam.SHOW_WOMEN,required = false)boolean showFemale,
		@RequestParam(value=UserParam.SHOW_MEN,required = false)boolean showMale,
		@RequestParam(UserParam.AGE_RANGE)String ageRange) {
		
		HttpSession session = request.getSession(false);
		int minAge = Integer.parseInt(ageRange.split(";")[0]);
		int maxAge = Integer.parseInt(ageRange.split(";")[1]);
		User user = (User) session.getAttribute(UserParam.USER);
		userDAO.setUserDiscoverySettings(user.getId(),
				showMale, showFemale,dist,minAge,maxAge);
		
		User renewedUser = userDAO.getUser(user.getUsername());
			session.setAttribute(UserParam.USER, renewedUser);

		return "redirect:DiscoverySettings";
	}

}