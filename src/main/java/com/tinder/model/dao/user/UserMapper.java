package com.tinder.model.dao.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinder.model.pojo.User;

public class UserMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int currentRow) throws SQLException {
		return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password_hash"),
				rs.getInt("age"), rs.getBoolean("gender_is_male"), rs.getString("avatar_name"),
				rs.getString("email"), rs.getBoolean("wants_male"), rs.getBoolean("wants_female"),
				rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getInt("search_distance"),
				rs.getInt("max_desired_age"), rs.getInt("min_desired_age"), rs.getString("full_name"),
				rs.getString("description"));
	}

}
