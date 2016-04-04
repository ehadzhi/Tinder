package com.tinder.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tinder.model.dao.notification.INotificationDAO;
import com.tinder.model.pojo.User;

@RestController
public class MatchNotificationsService {

	@Autowired
	private INotificationDAO notificationDAO;

	@RequestMapping(value = "/MatchNotificationsService", method = RequestMethod.POST)
	public Map<String, List<String>> doPost(HttpServletRequest request) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		if (Home.checkValidSession(request) == null) {
			result.put("matchNotifications", notificationDAO
					.getAllMatchNotificationsForUser(((User) request.getSession().getAttribute("user")).getId()));

		}
		return result;
	}

	@RequestMapping(value = "/MatchNotificationsService", method = RequestMethod.DELETE)
	public void doDelete(HttpServletRequest request) {
		if (Home.checkValidSession(request) == null) {
			notificationDAO
					.deleteAllMatchNotificationsForUser((((User) request.getSession().getAttribute("user")).getId()));

		}
	}
}
