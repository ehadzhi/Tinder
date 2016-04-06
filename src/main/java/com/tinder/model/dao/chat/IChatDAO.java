package com.tinder.model.dao.chat;

import java.util.List;

import com.tinder.model.pojo.Chat;
import com.tinder.model.pojo.User;

public interface IChatDAO {

	int createChat(User user1, User user2);

	int createChat();

	void addUserToChat(Chat toAddIn, User toAdd);
	
	List<User> getUserChatFriends(User user);
	
	void deleteChat(User user1,User user2);

}