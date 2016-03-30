package com.tinder.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tinder.exceptions.DBException;
import com.tinder.model.pojo.Chat;
import com.tinder.model.pojo.User;

public class ChatDAO {
	private static final String CREATE_CHAT = "insert into tinder.chats values(null);";
	private static final String ADD_USER_TO_CHAT = "insert into tinder.users_in_chats values(?,?);";
	
	public static void createChat(User one, User two) throws DBException {
		Chat chat = new Chat(createChat());
		addUserToChat(chat, one);
		addUserToChat(chat, two);
	}

	public static int createChat() throws DBException {
		Connection conn = null;
		Statement stmnt = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			stmnt = conn.createStatement();
			stmnt.execute(CREATE_CHAT, Statement.RETURN_GENERATED_KEYS);
			ResultSet resultSet = stmnt.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DBException("Could not get generated chat id key");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}

	public static void addUserToChat(Chat toAddIn, User toAdd) throws DBException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			stmnt = conn.prepareStatement(ADD_USER_TO_CHAT);
			stmnt.setInt(1, toAdd.getId());
			stmnt.setInt(2, toAddIn.getId());
			stmnt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}
}
