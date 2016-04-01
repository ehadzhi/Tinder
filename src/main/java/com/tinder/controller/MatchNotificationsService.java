package com.tinder.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tinder.exceptions.DBException;
import com.tinder.model.dao.NotificationDAO;
import com.tinder.model.pojo.User;

@RestController
public class MatchNotificationsService {

	@RequestMapping(value = "/MatchNotificationsService", method = RequestMethod.POST)
	public Map<String, List<String>> doPost(HttpServletRequest request) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		if (Home.checkValidSession(request) == null) {
			try {
				result.put("matchNotifications", NotificationDAO
						.getAllMatchNotificationsForUser(((User) request.getSession().getAttribute("user")).getId()));

			} catch (DBException e) {
				e.printStackTrace();
				result.put("matchNotifications", new LinkedList<String>());
			}
		}
		return result;
	}

	@RequestMapping(value = "/MatchNotificationsService", method = RequestMethod.DELETE)
	public void doDelete(HttpServletRequest request) {
		if (Home.checkValidSession(request) == null) {
			try {
				NotificationDAO.deleteAllMatchNotificationsForUser(
						(((User) request.getSession().getAttribute("user")).getId()));

			} catch (DBException e) {
				e.printStackTrace();
			}
		}
	}
}
