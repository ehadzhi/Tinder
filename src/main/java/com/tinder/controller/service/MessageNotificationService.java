package com.tinder.controller.service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tinder.model.dao.notification.INotificationDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@RestController
public class MessageNotificationService {
	
	@Autowired
	private INotificationDAO notificationDAO;
	
	@Autowired
	private IUserDAO userDAO;
	
	@RequestMapping(value = "/MessageNotificationsService", method = RequestMethod.POST)
	public Map<String, List<User>> getMessageNotifications(Principal principal){
		User user = userDAO.getUser(principal.getName());
		Map<String,List<User>> result = new HashMap<String,List<User>>();
		result.put("message-notifications", notificationDAO.getAllMessageNotificationsForUser(user));
		return result;
	}
	
	@RequestMapping(value = "/MessageNotificationsService", method = RequestMethod.DELETE)
	public void deleteMessageNotifications(Principal principal){
		User user = userDAO.getUser(principal.getName());
		notificationDAO.deleteAllMessageNotificationsForUser(user);
	}
	
}
