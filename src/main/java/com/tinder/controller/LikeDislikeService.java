package com.tinder.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tinder.exceptions.DBException;
import com.tinder.model.dao.notification.INotificationDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@RestController
public class LikeDislikeService {

	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private INotificationDAO notificationDAO;
	
	@RequestMapping(value = "/LikeDislikeService", method = RequestMethod.POST)
	public Map<String,List<String>> doPost(HttpServletRequest request,
			HttpServletResponse response,Principal principal) {
		User user = userDAO.getUser(principal.getName());
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) request.getSession().getAttribute("userCandidates");
		if( users == null){
			request.getSession().setAttribute("userCandidates", new LinkedList<User>());
			users = (List<User>) request.getSession().getAttribute("userCandidates");
		}
		if (users.size() == 0) {
			addCandidates(request, principal, users);
		} else if (users.size() == 1) {
			likeOrDislikeAndRemoveTheTopUser(request, user, users);
			addCandidates(request, principal, users);
		} else {
			likeOrDislikeAndRemoveTheTopUser(request, user, users);
		}
		Map<String, List<String>> result =  new HashMap<String, List<String>>();
		 result.put("photos",
				retrurnPhotosOfTheFirstUser(response, users));
		 return result;
	}

	private List<String> retrurnPhotosOfTheFirstUser(HttpServletResponse response, List<User> users){
		if (users.size() == 0) {
			return Arrays.asList("nousers.jpg");
		} else {
			return userDAO.getAllPhotosOfUser(users.get(0).getUsername());
		}
	}

	private void likeOrDislikeAndRemoveTheTopUser(
			HttpServletRequest request, User user, List<User> users) {
		if (((String) request.getParameter("action")).equals("Like")) {
			userDAO.likeUser(user.getId(), users.get(0).getId());
			if(notificationDAO.checkForLike(users.get(0).getId(), user.getId())){
				notificationDAO.addMatch(users.get(0).getId(),  user.getId());
				notificationDAO.addMatch(user.getId(), users.get(0).getId());
			}
		}
		if (((String) request.getParameter("action")).equals("Dislike")) {
			userDAO.dislikeUser(user.getId(), users.get(0).getId());
		}
		users.remove(0);
	}

	private void addCandidates(HttpServletRequest request,Principal principal, List<User> users) {
		List<User> newUsers =  userDAO.getFirstThreeNearbyUsers(
				principal.getName());
		
		users.addAll(newUsers);
		request.getSession().setAttribute("userCandidates", users);
	}

}
