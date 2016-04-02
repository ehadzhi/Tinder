package com.tinder.model.dao.user;

import java.util.List;
import com.tinder.model.pojo.User;

public interface IUserDAO {

	boolean isUserAndPassExisting(String username, String password);

	boolean isUserExisting(String username, String password);

	void likeUser(int likerId, int likedId);

	void dislikeUser(int dislikerId, int dislikedId);

	void registerUser(String username, String password, String email, boolean gender, int age, String fullName);

	void setUserDiscoverySettings(int id, boolean wantsMale, boolean wantsFemale, int searchdistance, int minAge,
			int maxAge);

	void deleteUser(String username);

	String calculateHash(String password);

	boolean setLocation(String username, double latitude, double longitude);

	List<User> getFirstThreeNearbyUsers(String username);

	List<String> getAllPhotosOfUser(String username);

	User getUser(String username);

	boolean isUsernameExisting(String username);

	boolean isEmailExisting(String email);

}