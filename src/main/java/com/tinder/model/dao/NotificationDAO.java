package com.tinder.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.tinder.exceptions.DBException;

public class NotificationDAO {
	private static final String GET_ALL_MATCH_NOTIFICATIONS_FOR_USER = "select u.username from `tinder`.`match-notification` m join tinder.users u on u.id = m.user_two where m.user_one = ?;";
	private static final String DELETE_ALL_USER_MATCH_NOTIFICATIONS = "DELETE FROM `tinder`.`match-notification` WHERE user_one=? ;";
	private static final String CHECK_FOR_LIKE = "SELECT count(id) FROM tinder.likes WHERE liker_id = ? and liked_id = ? ;";
	private static final String ADD_MATCH_NOTIFICATION = "INSERT INTO `tinder`.`match-notification` VALUES (null, ?, ?);";

	public static void addMatch(int user1_id, int user2_id) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(ADD_MATCH_NOTIFICATION);
			st.setInt(1, user1_id);
			st.setInt(2, user2_id);
			st.executeUpdate();
		} catch (Exception e) {
			throw new DBException("Can't insert a match!", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}
	
	public static boolean checkForLike(int user1_id, int user2_id) throws DBException{
		Connection conn = null;
		PreparedStatement st = null;
		boolean result = false;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(CHECK_FOR_LIKE);
			st.setInt(1, user1_id);
			st.setInt(2, user2_id);
			ResultSet rs = st.executeQuery();
			rs.next();
			if(rs.getInt(1)>0) 
				result = true;
			
		} catch (Exception e) {
			throw new DBException("Can't check for like!", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
		return result;
	}
	
	public static void deleteAllMatchNotificationsForUser(int userID) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(DELETE_ALL_USER_MATCH_NOTIFICATIONS);
			st.setInt(1, userID);
			st.executeUpdate();
		} catch (Exception e) {
			throw new DBException("Can't insert a match!", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}
	
	public static List<String> getAllMatchNotificationsForUser(int userID) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		List<String> result = new LinkedList<String>();
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(GET_ALL_MATCH_NOTIFICATIONS_FOR_USER);
			st.setInt(1, userID);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				result.add(rs.getString(1));
			}
			
		} catch (Exception e) {
			throw new DBException("Can't insert a match!", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
		return result;
	}
	
	
}
