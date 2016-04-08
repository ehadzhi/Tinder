package com.tinder.model.dao.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinder.model.pojo.UnconfirmedUser;

public class UnconfirmedUserMapper implements RowMapper<UnconfirmedUser>{

	@Override
	public UnconfirmedUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new UnconfirmedUser
				(rs.getString("uuid"), rs.getString("username"), rs.getString("password_hash"),
					rs.getInt("age"), rs.getBoolean("gender_is_male"),
					rs.getString("email"), rs.getString("full_name"));
	}

}
