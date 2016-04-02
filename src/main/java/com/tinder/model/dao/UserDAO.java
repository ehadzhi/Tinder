package com.tinder.model.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.tinder.exceptions.DBException;
import com.tinder.model.pojo.User;

public class UserDAO {

	private static final String CHECK_EXISTING_EMAIL = "select count(id) from tinder.users where BINARY email = BINARY ?";
	private static final String CHECK_EXISTING_USERNAME = "select count(id) from tinder.users where BINARY username = BINARY ?";
	private static final String SET_DISCOVERY_SETTINGS = "UPDATE tinder.users SET wants_male=?, wants_female=?, search_distance=?, min_desired_age=?, max_desired_age=? WHERE id=? ;";
	private static final String DELETE_USER = "delete from tinder.users WHERE username = ? ;";
	private static final String REGISTER_USER = "INSERT INTO tinder.users "
			+ "values(null,?,?,?,?,'default',?,false,false,null,null,null,null,null,?,NULL);";
	private static final String IS_USER_AND_PASS_EXISTING = "select count(id) from tinder.users where "
			+ "username = ? and password_hash = ?";
	private static final String IS_USER_EXISTING = "select count(id) from tinder.users where "
			+ "BINARY username = BINARY ?";
	private static final String GET_USER = "select * from tinder.users where username = ?";
	private static final String CHANGE_LOCATION = "UPDATE tinder.users " + "SET latitude = ?, longitude = ? "
			+ "WHERE id = ?;";
	private static final String FIND_CLOSE_USERS = "select username from tinder.users" + " where age between ? and ?"
			+ " and 6371.009*sqrt(pow(radians(? - latitude),2) + pow(cos((?+latitude)/2)*(radians(? - longitude)),2)) <= ?"
			+ " and username not in(" + "select username from tinder.dislikes d"
			+ " right join tinder.users u on (d.disliked_id=u.id)" + " where d.disliker_id = ?)"
			+ " and username not in (" + " select username from tinder.likes l"
			+ " right join tinder.users u on (l.liked_id=u.id)" + " where l.liker_id = ?)" + " and id != ? limit 3;";
	private static final String FIND_PICTURES_OF_USER = "SELECT * FROM tinder.pictures where owner_id = ?;";
	private static final String LIKE_USER = "insert into tinder.likes values(null,?,?);";
	private static final String DISLIKE_USER = "insert into tinder.dislikes values(null,?,?);";
	private static final String PASSWORD_REGEX = "[0-9a-zA-Z]{3,100}";

	public static boolean isUserAndPassExisting(String username, String password) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		boolean result = false;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(IS_USER_AND_PASS_EXISTING);
			st.setString(1, username);
			st.setString(2, calculateHash(password));
			ResultSet rs = st.executeQuery();
			rs.next();
			if (rs.getInt(1) == 1) {
				result = true;
			}
		} catch (Exception e) {
			throw new DBException("Can't check if this user (" + username + ") exists .", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
		return result;
	}

	public static boolean isUserExisting(String username, String password) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		boolean result = false;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(IS_USER_EXISTING);
			st.setString(1, username);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			rs.next();
			if (rs.getInt(1) == 1) {
				result = true;
			}
		} catch (Exception e) {
			throw new DBException("Can't check if this user (" + username + ") exists .", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
		return result;
	}

	public static void likeUser(int likerId, int likedId) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(LIKE_USER);
			st.setInt(1, likerId);
			st.setInt(2, likedId);
			st.execute();

		} catch (Exception e) {
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}

	public static void dislikeUser(int dislikerId, int dislikedId) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(DISLIKE_USER);
			st.setInt(1, dislikerId);
			st.setInt(2, dislikedId);
			st.execute();

		} catch (Exception e) {
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}

	public static void registerUser(String username, String password, String email, boolean gender, int age)
			throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			calculateHash(password);
			conn = ConnectionDispatcher.getConnection();
			st = (PreparedStatement) conn.prepareStatement(REGISTER_USER);
			st.setString(1, username);
			st.setString(2, calculateHash(password));
			st.setInt(3, age);
			st.setBoolean(4, gender);
			st.setString(5, email);
			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Something went wrong with the Database.", e);
			// TODO
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}

	public static void setUserDiscoverySettings(int id, boolean wantsMale, boolean wantsFemale, int searchdistance,
			int min_age, int max_age) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = (PreparedStatement) conn.prepareStatement(SET_DISCOVERY_SETTINGS);
			st.setBoolean(1, wantsMale);
			st.setBoolean(2, wantsFemale);
			st.setInt(3, searchdistance);
			st.setInt(4, min_age);
			st.setInt(5, max_age);
			st.setInt(6, id);
			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Something went wrong with the Database.", e);
			// TODO
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}

	public static void deleteUser(String username) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			User user = UserDAO.getUser(username);
			if (user != null) {
				conn = ConnectionDispatcher.getConnection();
				st = conn.prepareStatement(DELETE_USER);
				st.setString(1, username);
				st.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Something went wrong with the Database.", e);
			// TODO
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}

	public static String calculateHash(String password) throws NoSuchAlgorithmException {
		MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
		digest.update(password.getBytes());
		byte[] hash = digest.digest();
		StringBuilder hexString = new StringBuilder(32);

		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			} else {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}
		return hexString.toString();
	}

	public static boolean setLocation(String username, double latitude, double longitude) throws DBException {
		User toChangeLocationOf = getUser(username);
		if (toChangeLocationOf == null) {
			return false;
		}
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(CHANGE_LOCATION);
			st.setDouble(1, latitude);
			st.setDouble(2, longitude);
			st.setInt(3, toChangeLocationOf.getId());
			st.execute();

		} catch (Exception e) {
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
		return true;
	}

	public static List<User> getFirstThreeNearbyUsers(String username) throws DBException {
		User toFindFor = getUser(username);
		List<User> toReturn = new LinkedList<User>();
		if (toFindFor != null) {
			Connection conn = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				conn = ConnectionDispatcher.getConnection();
				st = conn.prepareStatement(FIND_CLOSE_USERS);
				st.setInt(1, toFindFor.getMinDesiredAge());
				st.setInt(2, toFindFor.getMaxDesiredAge());
				st.setDouble(3, toFindFor.getLatitude());
				st.setDouble(4, toFindFor.getLatitude());
				st.setDouble(5, toFindFor.getLongitude());
				st.setInt(6, toFindFor.getSearchDistance());
				st.setInt(7, toFindFor.getId());
				st.setInt(8, toFindFor.getId());
				st.setInt(9, toFindFor.getId());
				rs = st.executeQuery();

				while (rs.next()) {
					toReturn.add(UserDAO.getUser(rs.getString("username")));
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new DBException("Something went wrong with the Database.", e);
				// TODO
			} finally {
				ConnectionDispatcher.returnConnection(conn);
			}
		}

		return toReturn;
	}

	public static List<String> getAllPhotosOfUser(String username) throws DBException {
		int idToSearchFor = UserDAO.getUser(username).getId();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<String> toReturn = new LinkedList<String>();
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(FIND_PICTURES_OF_USER);
			st.setInt(1, idToSearchFor);
			rs = st.executeQuery();
			while (rs.next()) {
				toReturn.add(rs.getString("name"));
			}
		} catch (Exception e) {
			throw new DBException("Something went wrong with the Database.", e);
			// TODO
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
		return toReturn;
	}

	public static User getUser(String username) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(GET_USER);
			st.setString(1, username);
			rs = st.executeQuery();
			if (rs.next()) {
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password_hash"),
						rs.getInt("age"), rs.getBoolean("gender_is_male"), rs.getString("avatar_name"),
						rs.getString("email"), rs.getBoolean("wants_male"), rs.getBoolean("wants_female"),
						rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getInt("search_distance"),
						rs.getInt("max_desired_age"), rs.getInt("min_desired_age"),
						rs.getString("full_name"),rs.getString("description"));
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Something went wrong with the Database.", e);
			// TODO
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}

	private static final String UPDATE_DISCOVERY_SETTINGS = "UPDATE tinder.users SET wants_male=?, wants_female=?, search_distance=?,"
			+ " max_desired_age=?, min_desired_age=? WHERE id=?;";

	public static boolean updateDiscovetySettings(String username, boolean wantsMale, boolean wantsFemale,
			int searchDistance, int maxDesiredAge, int minDesiredAge) throws DBException {
		User user = getUser(username);
		if (user == null) {
			return false;
		}
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(UPDATE_DISCOVERY_SETTINGS);
			st.setBoolean(1, wantsMale);
			st.setBoolean(2, wantsFemale);
			st.setInt(3, searchDistance);
			st.setInt(4, maxDesiredAge);
			st.setInt(5, minDesiredAge);
			st.setInt(6, user.getId());
			st.execute();

		} catch (Exception e) {
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
		return true;
	}

	public static boolean isUsernameExisting(String username) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(CHECK_EXISTING_USERNAME);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			rs.next();
			if (rs.getInt(1) == 0) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}

	public static boolean isEmailExisting(String email) throws DBException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(CHECK_EXISTING_EMAIL);
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			rs.next();
			if (rs.getInt(1) == 0) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}
}
