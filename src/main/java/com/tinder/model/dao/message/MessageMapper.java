package com.tinder.model.dao.message;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinder.model.pojo.chat.Message;

public class MessageMapper implements RowMapper<Message>{

	@Override
	public Message mapRow(ResultSet res, int currentRow) throws SQLException {
		return new Message(res.getInt("id"),
				res.getInt("sender_id"),
				res.getInt("chat_id"),
				res.getString("message"),
				res.getString("username"),
				res.getTimestamp("created_at").toLocalDateTime());
	}

}
