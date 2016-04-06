package com.tinder.model.dao.chat;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.tinder.model.dao.message.IMessageDAO;
import com.tinder.model.dao.user.UserMapper;
import com.tinder.model.pojo.Chat;
import com.tinder.model.pojo.User;

@Component
public class ChatDAO implements IChatDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private IMessageDAO messageDAO;

	@Override
	public int createChat(User one, User two) {
		Chat chat = new Chat(createChat());
		addUserToChat(chat, one);
		addUserToChat(chat, two);
		return chat.getId();
	}

	@Override
	public int createChat() {
		final String CREATE_CHAT = "insert into tinder.chats values(null);";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(CREATE_CHAT, parameters, keyHolder, new String[] { "id" });
		return keyHolder.getKey().intValue();
	}

	@Override
	public void addUserToChat(Chat toAddIn, User toAdd) {
		final String ADD_USER_TO_CHAT = "insert into tinder.users_in_chats values(:user_id,:chat_id);";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("chat_id", toAddIn.getId());
		paramMap.put("user_id", toAdd.getId());
		jdbcTemplate.update(ADD_USER_TO_CHAT, paramMap);
	}

	@Override
	public List<User> getUserChatFriends(User user) {
		final String GET_USER_CHAT_FRIENDS = "SELECT u.* FROM tinder.users_in_chats c"
				+ " join tinder.users u on (c.user_id = u.id)" + " where c.chat_id in (SELECT chat_id "
				+ "FROM tinder.users_in_chats " + "where user_id = :user_id) and u.id != :user_id;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", user.getId());
		return jdbcTemplate.query(GET_USER_CHAT_FRIENDS, paramMap, new UserMapper());
	}

	@Override
	public void deleteChat(User user1, User user2) {
		final String DELETE_CHAT = "DELETE FROM tinder.chats WHERE id=:chat_id;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("chat_id", messageDAO.findChatId(user1, user2));
		jdbcTemplate.update(DELETE_CHAT, paramMap);

	}
}
