package com.tinder.model.dao.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.tinder.model.pojo.UnconfirmedUser;
import com.tinder.model.pojo.User;

@Component
public class UserDAO implements IUserDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public boolean isUserAndPassExisting(String username, String password) {
		final String IS_USER_AND_PASS_EXISTING = "select count(id) from tinder.users where "
				+ "username = :username and password_hash = :password_hash";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("password_hash", calculateHash(password));
		return jdbcTemplate.query(IS_USER_AND_PASS_EXISTING, paramMap,
				resultSet -> resultSet.next() && resultSet.getInt(1) > 0);
	}

	@Override
	public boolean isUserExisting(String username, String password) {
		final String IS_USER_EXISTING = "select count(id) from tinder.users where "
				+ "BINARY username = BINARY :username";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		return jdbcTemplate.query(IS_USER_EXISTING, paramMap, resultSet -> resultSet.next() && resultSet.getInt(1) > 0);
	}

	@Override
	public void likeUser(int likerId, int likedId) {
		final String LIKE_USER = "insert into tinder.likes values(null,:liker_id,:liked_id);";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("liker_id", likerId);
		paramMap.put("liked_id", likedId);
		jdbcTemplate.update(LIKE_USER, paramMap);
	}
	
	@Override
	public void removeLike(int likerId, int likedId) {
		final String REMOVE_LIKE = "delete from tinder.likes where liker_id = :liker_id"
				+ " and liked_id = :liked_id ;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("liker_id", likerId);
		paramMap.put("liked_id", likedId);
		jdbcTemplate.update(REMOVE_LIKE, paramMap);
	}

	@Override
	public void dislikeUser(int dislikerId, int dislikedId) {
		final String DISLIKE_USER = "insert into tinder.dislikes values(null,:disliker_id,:disliked_id);";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("disliker_id", dislikerId);
		paramMap.put("disliked_id", dislikedId);
		jdbcTemplate.update(DISLIKE_USER, paramMap);
	}

	@Override
	public void registerUser(String username, String password, String email, boolean gender, int age, String fullName) {
		final String REGISTER_USER = "INSERT INTO tinder.users "
				+ "values(null,:username,:password_hash,:age,:gender,'avatar_default.jpg'"
				+ ",:email,false,false,null,null,null,null,null,:full_name,NULL);";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("password_hash", calculateHash(password));
		paramMap.put("age", age);
		paramMap.put("gender", gender);
		paramMap.put("email", email);
		paramMap.put("full_name", fullName);
		jdbcTemplate.update(REGISTER_USER, paramMap);
	}
	
	@Override
	public void registerUserWithHashedPassword(String username, String password, String email, boolean gender, int age, String fullName) {
		final String REGISTER_USER = "INSERT INTO tinder.users "
				+ "values(null,:username,:password_hash,:age,:gender,'avatar_default.jpg'"
				+ ",:email,false,false,null,null,null,null,null,:full_name,NULL);";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("password_hash", password);
		paramMap.put("age", age);
		paramMap.put("gender", gender);
		paramMap.put("email", email);
		paramMap.put("full_name", fullName);
		jdbcTemplate.update(REGISTER_USER, paramMap);
	}

	@Override
	public void setUserDiscoverySettings(String username, boolean wantsMale, boolean wantsFemale, int searchdistance,
			int minAge, int maxAge) {
		int id = getUser(username).getId();
		final String SET_DISCOVERY_SETTINGS = "UPDATE tinder.users SET wants_male=:wants_male, wants_female=:wants_female,"
				+ " search_distance=:search_distance, min_desired_age=:min_desired_age,"
				+ " max_desired_age=:max_desired_age WHERE id=:user_id ;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("wants_male", wantsMale);
		paramMap.put("wants_female", wantsFemale);
		paramMap.put("search_distance", searchdistance);
		paramMap.put("min_desired_age", minAge);
		paramMap.put("max_desired_age", maxAge);
		paramMap.put("user_id", id);
		jdbcTemplate.update(SET_DISCOVERY_SETTINGS, paramMap);
	}

	@Override
	public void deleteUser(String username) {
		final String DELETE_USER = "delete from tinder.users WHERE username = :username;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		jdbcTemplate.update(DELETE_USER, paramMap);
	}

	@Override
	public String calculateHash(String password) {
		MessageDigest digest;
		try {
			digest = java.security.MessageDigest.getInstance("MD5");
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
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean setLocation(String username, double latitude, double longitude) {
		final String CHANGE_LOCATION = "UPDATE tinder.users " + "SET latitude = :latitude, longitude = :longitude "
				+ "WHERE id = :user_id;";
		User toChangeLocationOf = getUser(username);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("latitude", latitude);
		paramMap.put("longitude", longitude);
		paramMap.put("user_id", toChangeLocationOf.getId());
		return (jdbcTemplate.update(CHANGE_LOCATION, paramMap) == 1);
	}

	@Override
	public List<User> getFirstThreeNearbyUsers(String username) {
		final String FIND_CLOSE_USERS = "select username from tinder.users"
				+ " where age between :min_desired_age and :max_desired_age"
				+ " and 6371.009*sqrt(pow(radians(:latitude - latitude),2)"
				+ " + pow(cos((:latitude+latitude)/2)*(radians(:longitude - longitude)),2)) <= :search_distance"
				+ " and username not in(" + "select username from tinder.dislikes d"
				+ " right join tinder.users u on (d.disliked_id=u.id)" + " where d.disliker_id = :user_id)"
				+ " and username not in (" + " select username from tinder.likes l"
				+ " right join tinder.users u on (l.liked_id=u.id)" + " where l.liker_id = :user_id)"
				+ " and id != :user_id and ((:wants_male and gender_is_male) or (:wants_female and !gender_is_male)) limit 3;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		User user = getUser(username);
		paramMap.put("min_desired_age", user.getMinDesiredAge());
		paramMap.put("max_desired_age", user.getMaxDesiredAge());
		paramMap.put("latitude", user.getLatitude());
		paramMap.put("longitude", user.getLongitude());
		paramMap.put("search_distance", user.getSearchDistance());
		paramMap.put("user_id", user.getId());
		paramMap.put("wants_male", user.isWantsMale());
		paramMap.put("wants_female", user.isWantsFemale());
		return (List<User>) jdbcTemplate.query(FIND_CLOSE_USERS, paramMap,
				(resultSet, currentRow) -> getUser(resultSet.getString(1)));
	}

	@Override
	public List<String> getAllPhotosOfUser(String username) {
		final String FIND_PICTURES_OF_USER = "SELECT * FROM tinder.pictures where owner_id = :user_id;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", getUser(username).getId());
		return (List<String>) jdbcTemplate.query(FIND_PICTURES_OF_USER, paramMap,
				(resultSet, currentRow) -> resultSet.getString("name"));
	}

	@Override
	public User getUser(String username) {
		final String GET_USER = "select * from tinder.users "
				+ "where username = :username or email = :email";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("email", username);
		List<User> toReturn = jdbcTemplate.query(GET_USER, paramMap, new UserMapper());
		if (toReturn.size() > 0) {
			return toReturn.get(0);
		}
		return null;
	}

	@Override
	public boolean isUsernameExisting(String username) {
		final String CHECK_EXISTING_USERNAME = "select count(id) from tinder.users where BINARY username = BINARY :username";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		return jdbcTemplate.query(CHECK_EXISTING_USERNAME, paramMap,
				resultSet -> resultSet.next() && resultSet.getInt(1) > 0);
	}

	@Override
	public boolean isEmailExisting(String email) {
		final String CHECK_EXISTING_EMAIL = "select count(id) from tinder.users where BINARY email = BINARY :email";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("email", email);
		return jdbcTemplate.query(CHECK_EXISTING_EMAIL, paramMap,
				resultSet -> resultSet.next() && resultSet.getInt(1) > 0);
	}

	@Override
	public void updateUser(User user) {
		final String UPDATE_USER = "UPDATE `tinder`.`users` SET `username`=:username,"
				+ "`password_hash`=:password_hash, `age`=:age," + "`description`=:description WHERE `id`=:id;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", user.getUsername());
		paramMap.put("password_hash", user.getPasswordHash());
		paramMap.put("age", user.getAge());
		paramMap.put("email", user.getEmail());
		paramMap.put("description", user.getDescription());
		paramMap.put("id", user.getId());
		jdbcTemplate.update(UPDATE_USER, paramMap);
	}

	@Override
	public String getUsernameFromFacebookId(String facebookId) {
		final String GET_USERNAME = "SELECT u.username FROM tinder.facebook_users f"
				+ " join tinder.users u on (f.user_id=u.id) where f.id = :facebook_id;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("facebook_id", facebookId);
		return jdbcTemplate.query(GET_USERNAME, paramMap, resultSet -> {
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
		});
	}

	@Override
	public void addFacebookConnection(String username,String facebookId) {
		final String ADD_FACEBOOK_CONNECTION = "INSERT INTO `tinder`.`facebook_users`"
				+ " VALUES (:facebook_id, :user_id);";
		User user = getUser(username);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("facebook_id", facebookId);
		paramMap.put("user_id",user.getId());
		jdbcTemplate.update(ADD_FACEBOOK_CONNECTION, paramMap);
	}
	
	
	@Override
	public void registerUnconfirmedUser(String username, String password, String email, boolean gender, int age, String fullName, String UUID) {
		final String REGISTER_USER = "INSERT INTO tinder.`unconfirmed_users` "
				+ "values(:uuid,:username,:password_hash,:email,:gender,:age,:full_name) ;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uuid", UUID);
		paramMap.put("username", username);
		paramMap.put("password_hash", calculateHash(password));
		paramMap.put("age", age);
		paramMap.put("gender", gender);
		paramMap.put("email", email);
		paramMap.put("full_name", fullName);
		jdbcTemplate.update(REGISTER_USER, paramMap);
	}
	
	@Override
	public UnconfirmedUser getUnconfirmedUser(String UUID) {
		final String GET_USER = "select * from tinder.`unconfirmed_users` where uuid = :uuid";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uuid", UUID);
		List<UnconfirmedUser> toReturn = jdbcTemplate.query(GET_USER, paramMap, new UnconfirmedUserMapper());
		if (toReturn.size() > 0) {
			return toReturn.get(0);
		}
		return null;
	}
	
	@Override
	public void deleteUnconfirmedUser(String uuid) {
		final String DELETE_USER = "delete from tinder.`unconfirmed_users` WHERE uuid = :uuid;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uuid", uuid);
		jdbcTemplate.update(DELETE_USER, paramMap);
	}
}
