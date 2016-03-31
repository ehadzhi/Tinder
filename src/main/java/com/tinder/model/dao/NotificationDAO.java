package com.tinder.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tinder.exceptions.DBException;

public class NotificationDAO {
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
}
