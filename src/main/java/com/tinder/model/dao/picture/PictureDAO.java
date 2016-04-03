package com.tinder.model.dao.picture;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.tinder.model.pojo.User;

@Component
public class PictureDAO implements IPictureDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void addPicture(String pictureName, int ownerId) {
		final String INSERT_PICTURE = "INSERT INTO tinder.pictures VALUES (null,:owner_id,:picture_name,now());";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("owner_id", ownerId);
		paramMap.put("picture_name", pictureName);
		jdbcTemplate.update(INSERT_PICTURE, paramMap);
	}

	@Override
	public void setProfilePicture(String pictureName, User toSet) {
		final String SET_PROFILE_PICTURE = "UPDATE tinder.users SET avatar_name=:profile_picture WHERE id=:user_id;";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("profile_picture", pictureName);
		paramMap.put("user_id", toSet.getId());
		jdbcTemplate.update(SET_PROFILE_PICTURE, paramMap);
	}

	@Override
	public void deletePicture(String pictureName, User owner) {
		final String DELETE_PICTURE = "DELETE FROM tinder.pictures WHERE owner_id=:owner_id and name = :picture_name;";
		if (pictureName.equals(owner.getAvatarName())) {
			setProfilePicture("avatar_default.jpg", owner);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("owner_id", owner.getId());
		paramMap.put("picture_name", pictureName);
		jdbcTemplate.update(DELETE_PICTURE, paramMap);
	}

	@Override
	public int getLastPhotoId() {
		final String LAST_PHOTO_ID = "SELECT max(id) FROM tinder.pictures;";
		return jdbcTemplate.query(LAST_PHOTO_ID, resultSet -> {
			resultSet.next();
			return resultSet.getInt(1);
		});
	}
}
