package com.tinder.model.dao.notification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationDAO implements INotificationDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void addMatch(int userOneId, int userTwoId) {
		final String ADD_MATCH_NOTIFICATION = "INSERT INTO tinder.match-notification "
				+ "VALUES (null, :user1_id, user2_id);";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user1_id", userOneId);
		paramMap.put("user1_id", userTwoId);
		jdbcTemplate.update(ADD_MATCH_NOTIFICATION, paramMap);
	}

	@Override
	public boolean checkForLike(int likerId, int likedId) {
		final String CHECK_FOR_LIKE = "SELECT count(id) FROM tinder.likes "
				+ "WHERE liker_id = :liker_id and liked_id = :liked_id ;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("liker_id", likerId);
		paramMap.put("liked_id", likedId);
		return jdbcTemplate.query(CHECK_FOR_LIKE, paramMap,
				resultSet -> resultSet.next() && resultSet.getInt(1) > 0);
	}

	@Override
	public void deleteAllMatchNotificationsForUser(int userID) {
		final String DELETE_ALL_USER_MATCH_NOTIFICATIONS = 
				"DELETE FROM `tinder`.`match-notification` WHERE user_one=:user_id ;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", userID);
		jdbcTemplate.update(DELETE_ALL_USER_MATCH_NOTIFICATIONS, paramMap);
	}

	@Override
	public List<String> getAllMatchNotificationsForUser(int userID){
		final String GET_ALL_MATCH_NOTIFICATIONS_FOR_USER = 
				"select u.username from `tinder.match-notification` m"
				+ " join tinder.users u on u.id = m.user_two where m.user_one = :user_id;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", userID);
		return jdbcTemplate.query(GET_ALL_MATCH_NOTIFICATIONS_FOR_USER, paramMap, 
				 (resultSet, numRow) -> resultSet.getString(1));
	}

}
