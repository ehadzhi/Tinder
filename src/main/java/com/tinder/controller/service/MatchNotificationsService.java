package com.tinder.controller.service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<String, List<User>> doPost(Principal principal) {
		User user = userDAO.getUser(principal.getName());
		Map<String, List<User>> result = new HashMap<String, List<User>>();
		result.put("matchNotifications", notificationDAO
				.getAllMatchNotificationsForUser(user));

		return result;
	}

	@RequestMapping(value = "/MatchNotificationsService", method = RequestMethod.DELETE)
	public void doDelete(Principal principal) {
		User user = userDAO.getUser(principal.getName());
			notificationDAO
					.deleteAllMatchNotificationsForUser(user);
	}
}
