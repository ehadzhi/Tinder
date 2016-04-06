package com.tinder.model.dao.message;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.tinder.model.pojo.Chat;
import com.tinder.model.pojo.Message;
import com.tinder.model.pojo.User;

@Component
public class MessageDAO implements IMessageDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Message> getLastMessagesFrom(int numMessages, User user1, User user2, LocalDateTime fromTime) {
		final String GET_MESSAGES_BEFORE_GIVEN_TIME = 
				"SELECT * FROM tinder.messages m "
				+ "join tinder.users u on (m.sender_id=u.id) " 
				+ "where m.chat_id = :chat_id and "
				+ "m.created_at < :from_time "
				+ "order by created_at desc limit :num_messages;";

		Chat chat = new Chat(findChatId(user1, user2));
		System.out.println(chat + "ei tva e " + chat.getId());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("chat_id", chat.getId());
		paramMap.put("from_time", Timestamp.valueOf(fromTime));
		paramMap.put("num_messages", numMessages);
		return jdbcTemplate.query(GET_MESSAGES_BEFORE_GIVEN_TIME, paramMap, new MessageMapper());
	}

	@Override
	public void sendMessage(String msg, User from, User to){
		final String SEND_MESSAGE = 
				"insert into tinder.messages values(null,:user_id,:chat_id,:message,now());";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", from.getId());
		Chat chat = new Chat(findChatId(from, to));
		paramMap.put("chat_id", chat.getId());
		paramMap.put("message", msg);
		jdbcTemplate.update(SEND_MESSAGE, paramMap);
	}

	@Override
	public int findChatId(User firstUser, User secondUser) {
		final String FIND_CHAT_ID = "SELECT chat_id FROM" 
				+ " tinder.users_in_chats where user_id = :first_user_id"
				+ " and chat_id in(SELECT chat_id FROM " 
				+ "tinder.users_in_chats where user_id = :second_user_id);";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("first_user_id", firstUser.getId());
		paramMap.put("second_user_id", secondUser.getId());
		return jdbcTemplate.query(FIND_CHAT_ID, paramMap, resultSet -> {
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
			return -1;
		});
	}
}
