package com.tinder.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tinder.exceptions.DBException;
import com.tinder.model.pojo.Chat;
import com.tinder.model.pojo.Message;
import com.tinder.model.pojo.User;

public class MessageDAO {
	private static final String FIND_CHAT_ID = "SELECT chat_id FROM" + " tinder.users_in_chats where user_id = ?"
			+ " and chat_id in(SELECT chat_id FROM " + "tinder.users_in_chats where user_id = ?);";
	private static final String SEND_MESSAGE = "insert into tinder.messages values(null,?,?,?);";
	private static final String GET_MESSAGES_BEFORE_GIVEN_TIME = "SELECT * FROM tinder.messages where chat_id = ? and created_at < ? limit ?;";

	public static List<Message> getLastMessagesFrom(int numMessages, User user1, User user2, LocalDateTime fromTime)
			throws DBException {
		List<Message> toReturn = new ArrayList<Message>(numMessages);
		Connection conn = null;
		PreparedStatement stmnt = null;
		Chat chat = null;
		ResultSet res = null;
		try {
			chat = new Chat(findChatId(user1, user2));
			conn = ConnectionDispatcher.getConnection();
			stmnt = conn.prepareStatement(GET_MESSAGES_BEFORE_GIVEN_TIME);
			stmnt.setInt(1, chat.getId());
			stmnt.setTimestamp(2, Timestamp.valueOf(fromTime));
			stmnt.setInt(3, numMessages);
			res = stmnt.executeQuery();
			while(res.next()){
				toReturn.add(new Message(res.getInt("id"), 
						res.getInt("sender_id"),
						res.getInt("chat_id"), 
						res.getString("message"),
						res.getTimestamp("created_at").toLocalDateTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
		return toReturn;
	}

	public static void sendMessage(String msg, User from, User to) throws DBException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		Chat chat = null;
		try {
			chat = new Chat(findChatId(from, to));
			conn = ConnectionDispatcher.getConnection();
			stmnt = conn.prepareStatement(SEND_MESSAGE);
			stmnt.setInt(1, from.getId());
			stmnt.setInt(2, chat.getId());
			stmnt.setString(3, msg);
			stmnt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}

	public static int findChatId(User firstUser, User secondUser) throws DBException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet res = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			stmnt = conn.prepareStatement(FIND_CHAT_ID);
			stmnt.setInt(1, firstUser.getId());
			stmnt.setInt(2, secondUser.getId());
			res = stmnt.executeQuery();
			if (res.next()) {
				return res.getInt(1);
			} else {
				throw new DBException("Failed to find chat between users user1:" + firstUser + " user2:" + secondUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}
}
