package com.tinder.model.dao.chat;

import java.util.List;

import com.tinder.model.pojo.Chat;
import com.tinder.model.pojo.User;

public interface IChatDAO {

	void createChat(User one, User two);

	int createChat();

	void addUserToChat(Chat toAddIn, User toAdd);
	
	List<User> getUserChatFriends(User user);

}