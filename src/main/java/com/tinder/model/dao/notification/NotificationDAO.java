package com.tinder.model.dao.notification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@Component
public class NotificationDAO implements INotificationDAO {

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void addMatch(int userOneId, int userTwoId) {
		final String ADD_MATCH_NOTIFICATION = "INSERT INTO `tinder`.`match-notification` "
				+ "VALUES (null, :user1_id, :user2_id);";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user1_id", userOneId);
		paramMap.put("user2_id", userTwoId);
		jdbcTemplate.update(ADD_MATCH_NOTIFICATION, paramMap);
	}
	
	@Override
	public boolean checkForMatch(int userOneId, int userTwoId) {
		final String ADD_MATCH_NOTIFICATION = "SELECT count(id) from `tinder`.`match-notification` "
				+ "where user_one = :user1_id and user_two = :user2_id ;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user1_id", userOneId);
		paramMap.put("user2_id", userTwoId);
		return jdbcTemplate.query(ADD_MATCH_NOTIFICATION, paramMap, resultSet -> resultSet.next() && resultSet.getInt(1) > 0);
	}

	@Override
	public boolean checkForLike(int likerId, int likedId) {
		final String CHECK_FOR_LIKE = "SELECT count(id) FROM tinder.likes "
				+ "WHERE liker_id = :liker_id and liked_id = :liked_id ;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("liker_id", likerId);
		paramMap.put("liked_id", likedId);
		return jdbcTemplate.query(CHECK_FOR_LIKE, paramMap, resultSet -> resultSet.next() && resultSet.getInt(1) > 0);
	}

	@Override
	public void deleteAllMatchNotificationsForUser(User user) {
		final String DELETE_ALL_USER_MATCH_NOTIFICATIONS = "DELETE FROM `tinder`.`match-notification` WHERE user_one=:user_id ;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", user.getId());
		jdbcTemplate.update(DELETE_ALL_USER_MATCH_NOTIFICATIONS, paramMap);
	}
	
	@Override
	public void deleteAllMatchNotificationsForUser(User user1,User user2) {
		final String DELETE_ALL_USER_MATCH_NOTIFICATIONS = "DELETE FROM `tinder`.`match-notification` WHERE user_one=:user_one and user_two=:user_two;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_one", user1.getId());
		paramMap.put("user_two", user2.getId());
		jdbcTemplate.update(DELETE_ALL_USER_MATCH_NOTIFICATIONS, paramMap);
	}

	@Override
	public List<User> getAllMatchNotificationsForUser(User user) {
		final String GET_ALL_MATCH_NOTIFICATIONS_FOR_USER = "select u.username from tinder.`match-notification` m"
				+ " join tinder.users u on u.id = m.user_two where m.user_one = :user_id;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", user.getId());
		return (List<User>) jdbcTemplate.query(GET_ALL_MATCH_NOTIFICATIONS_FOR_USER, paramMap,
				(resultSet, numRow) -> userDAO.getUser(resultSet.getString(1)));
	}

	@Override
	public List<User> getAllMessageNotificationsForUser(User user) {
		final String GET_ALL_MESSAGE_NOTIFICATIONS_FOR_USER = "select u.username from tinder.`message-notifications` m"
				+ " join tinder.users u on u.id = from_user_id" + " where to_user_id = :user_id;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", user.getId());
		return (List<User>) jdbcTemplate.query(GET_ALL_MESSAGE_NOTIFICATIONS_FOR_USER, paramMap,
				(resultSet, numRow) -> userDAO.getUser(resultSet.getString(1)));
	}

	@Override
	public void deleteAllMessageNotificationsForUser(User user, User withUser) {
		if (withUser == null) {
			final String DELETE_ALL_USER_MESSAGE_NOTIFICATIONS = "DELETE FROM `tinder`.`message-notifications`"
					+ " WHERE to_user_id=:user_id ;";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_id", user.getId());
			jdbcTemplate.update(DELETE_ALL_USER_MESSAGE_NOTIFICATIONS, paramMap);
		} else {
			final String DELETE_USER_MESSAGE_NOTIFICATIONS = "DELETE FROM `tinder`.`message-notifications`"
					+ " WHERE to_user_id=:user_id "
					+ "and from_user_id=:from_id;";
			Map<String, Object> paramMap2 = new HashMap<String, Object>();
			paramMap2.put("user_id", user.getId());
			paramMap2.put("from_id", withUser.getId());
			jdbcTemplate.update(DELETE_USER_MESSAGE_NOTIFICATIONS, paramMap2);
		}
	}
}
