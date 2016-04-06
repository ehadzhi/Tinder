package com.tinder.controller.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tinder.info.UserParam;
import com.tinder.model.dao.chat.IChatDAO;
import com.tinder.model.dao.notification.INotificationDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@RestController
public class LikeDislikeService {

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IChatDAO chatDAO;

	@Autowired
	private INotificationDAO notificationDAO;

	@RequestMapping(value = "/LikeDislikeService", method = RequestMethod.POST)
	public Map<String, Object> doPost(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession(false).getAttribute(UserParam.USER);
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) request.getSession().getAttribute(UserParam.USER_CANDIDATES);
		if (users.size() == 0) {
			addCandidates(request, users);
		} else if (users.size() == 1) {
			likeOrDislikeAndRemoveTheTopUser(request, user, users);
			addCandidates(request, users);
		} else {
			likeOrDislikeAndRemoveTheTopUser(request, user, users);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("photos", returnPhotosOfTheFirstUser(response, users));
		if (users.size() > 0) {
			result.put("user", users.get(0));
		}

		return result;

	}

	private List<String> returnPhotosOfTheFirstUser(HttpServletResponse response, List<User> users) {
		if (users.size() == 0) {
			return Arrays.asList("nousers.jpg");
		} else {
			return userDAO.getAllPhotosOfUser(users.get(0).getUsername());
		}
	}

	private void likeOrDislikeAndRemoveTheTopUser(HttpServletRequest request, User user, List<User> users) {
		if (((String) request.getParameter("action")).equals("Like")) {
			userDAO.likeUser(user.getId(), users.get(0).getId());
			if (notificationDAO.checkForLike(users.get(0).getId(), user.getId())) {
				notificationDAO.addMatch(users.get(0).getId(), user.getId());
				notificationDAO.addMatch(user.getId(), users.get(0).getId());
				chatDAO.createChat(user, users.get(0));
			}
		}
		if (((String) request.getParameter("action")).equals("Dislike")) {
			userDAO.dislikeUser(user.getId(), users.get(0).getId());
		}
		users.remove(0);
	}

	private void addCandidates(HttpServletRequest request, List<User> users) {
		List<User> newUsers = userDAO
				.getFirstThreeNearbyUsers(((User) request.getSession().getAttribute("user")).getUsername());

		users.addAll(newUsers);
		request.getSession().setAttribute("userCandidates", users);
		System.out.println(users.toString());
	}

}
