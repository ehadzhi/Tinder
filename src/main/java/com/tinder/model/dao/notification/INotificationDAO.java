package com.tinder.model.dao.notification;

import java.util.List;

import com.tinder.model.pojo.User;

public interface INotificationDAO {

	void addMatch(int userOneId, int userTwoId);

	 boolean checkForLike(int likerId, int likedId);

	void deleteAllMatchNotificationsForUser(User user);

	List<User> getAllMatchNotificationsForUser(User user);

	List<User> getAllMessageNotificationsForUser(User user);

	void deleteAllMessageNotificationsForUser(User user,User withUser);

}