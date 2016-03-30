package com.tinder.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RestController;

import com.tinder.exceptions.DBException;
import com.tinder.exceptions.UnauthorizedException;
import com.tinder.model.dao.UserDAO;
import com.tinder.model.pojo.User;

@RestController
public class LikeDislikeService {

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession(false).getAttribute("user");
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) request.getSession().getAttribute("userCandidates");
		if (users.size() == 0) {
			addCandidates(request, users);
			// retrurnPhotosOfTheFirstUser(response, users);
		} else if (users.size() == 1) {
			// likeOrDislikeAndRemoveTheTopUser(request, user, users);
			addCandidates(request, users);
			// retrurnPhotosOfTheFirstUser(response, users);
		} else {
			// likeOrDislikeAndRemoveTheTopUser(request, user, users);
			// retrurnPhotosOfTheFirstUser(response, users);
		}
	}

	private void getPhotosOfTheFirstUser(List<User> users) throws DBException {
		if (users.size() == 0) {
			Arrays.asList("nousers.jpg");
		} else {
			UserDAO.getAllPhotosOfUser(users.get(0).getUsername());
		}
	}

	private void likeOrDislikeAndRemoveTheTopUser(HttpServletRequest request, User user, List<User> users)
			throws DBException {
		if (((String) request.getParameter("action")).equals("Like")) {
			UserDAO.likeUser(user.getId(), users.get(0).getId());
		}
		if (((String) request.getParameter("action")).equals("Dislike")) {
			UserDAO.dislikeUser(user.getId(), users.get(0).getId());
		}
		users.remove(0);
	}

	private void addCandidates(HttpServletRequest request, List<User> users) {
		List<User> newUsers = null;
		try {
			newUsers = UserDAO
					.getFirstThreeNearbyUsers(((User) request.getSession().getAttribute("user")).getUsername());
		} catch (DBException e) {
			e.printStackTrace();
		}
		for (User u : newUsers) {
			System.out.println(u.getUsername());
		}
		users.addAll(newUsers);
		request.getSession().setAttribute("userCandidates", users);
	}

}
