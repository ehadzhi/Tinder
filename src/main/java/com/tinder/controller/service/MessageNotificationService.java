package com.tinder.controller.service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value = "/MessageNotificationService", method = RequestMethod.POST)
	public Map<String, List<User>> getMessageNotifications(Principal principal){
		User user = userDAO.getUser(principal.getName());
		Map<String,List<User>> result = new HashMap<String,List<User>>();
		result.put("messageNotifications", notificationDAO.getAllMessageNotificationsForUser(user));
		return result;
	}
	
	@RequestMapping(value = "/MessageNotificationsService", method = RequestMethod.DELETE)
	public void deleteMessageNotifications(Principal principal,@RequestParam("withUser") String username){
		User withUser = null;
		if(!username.equals("none"))
			withUser = userDAO.getUser(username);
		
		User user = userDAO.getUser(principal.getName());
		notificationDAO.deleteAllMessageNotificationsForUser(user,withUser);
	}
	
}
