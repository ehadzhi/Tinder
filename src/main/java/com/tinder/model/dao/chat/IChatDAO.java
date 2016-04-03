package com.tinder.model.dao.chat;

import com.tinder.model.pojo.Chat;
import com.tinder.model.pojo.User;

public interface IChatDAO {

	void createChat(User one, User two);

	int createChat();

	void addUserToChat(Chat toAddIn, User toAdd);

}