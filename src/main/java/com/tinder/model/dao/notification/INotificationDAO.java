package com.tinder.model.dao.notification;

import java.util.List;

public interface INotificationDAO {

	void addMatch(int userOneId, int userTwoId);

	 boolean checkForLike(int likerId, int likedId);

	void deleteAllMatchNotificationsForUser(int userID);

	List<String> getAllMatchNotificationsForUser(int userID);

}