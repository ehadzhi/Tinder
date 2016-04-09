package com.tinder.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.model.dao.message.IMessageDAO;
import com.tinder.model.dao.notification.INotificationDAO;
import com.tinder.model.dao.picture.PictureDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping(value = "/UnmatchUser")
public class UnmatchUser {

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IMessageDAO messageDAO;
	
	@Autowired
	private INotificationDAO notificationDAO;

	@RequestMapping(method = RequestMethod.GET)
	public String unmatch(Principal principal, @RequestParam("user") String userToUnmatch) {
		User user1 = userDAO.getUser(principal.getName());
		User user2 = userDAO.getUser(userToUnmatch);
		if (user1 != null && user2 != null) {
			messageDAO.deletAllMessagesBetweenUsers(user1, user2);
			userDAO.removeLike(user1.getId(), user2.getId());
			notificationDAO.deleteAllMatchNotificationsForUser(user1, user2);
		}
		return "messages";
	}
}
