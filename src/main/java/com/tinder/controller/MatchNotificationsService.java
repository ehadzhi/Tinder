package com.tinder.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tinder.model.dao.notification.INotificationDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@RestController
public class MatchNotificationsService {

	@Autowired
	private INotificationDAO notificationDAO;
	
	@Autowired
	private IUserDAO userDAO;

	@RequestMapping(value = "/MatchNotificationsService", method = RequestMethod.POST)
	public Map<String, List<String>> doPost(HttpServletRequest request,Principal principal) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		result.put("matchNotifications", notificationDAO
				.getAllMatchNotificationsForUser(userDAO.getUser(principal.getName()).getId()));

		return result;
	}

	@RequestMapping(value = "/MatchNotificationsService", method = RequestMethod.DELETE)
	public void doDelete(HttpServletRequest request,Principal principal) {
		if (Home.checkValidSession(request) == null) {
			notificationDAO
					.deleteAllMatchNotificationsForUser(userDAO.getUser(principal.getName()).getId());

		}
	}
}
