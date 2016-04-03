package com.tinder.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tinder.exceptions.DBException;
import com.tinder.model.pojo.User;

public class PictureDAO {

	private static final String LAST_PHOTO_ID = "SELECT max(id) FROM tinder.pictures;";
	private static final String INSERT_PICTURE = 
			"INSERT INTO tinder.pictures VALUES (null,?,?,now());";
	private static final String DELETE_PICTURE = 
			"DELETE FROM tinder.pictures WHERE owner_id=? and name = ?;";
	private static final String SET_PROFILE_PICTURE = 
			"UPDATE tinder.users SET avatar_name=? WHERE id=?;";

	public static void addPicture(String pictureName,int ownerId) throws DBException{
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(INSERT_PICTURE);
			st.setInt(1, ownerId);
			st.setString(2, pictureName);
			st.execute();

		} catch (Exception e) {
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}
	
	public static void setProfilePicture(String pictureName, User toSet) throws DBException{
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(SET_PROFILE_PICTURE);
			st.setString(1, pictureName);
			st.setInt(2, toSet.getId());
			st.execute();

		} catch (Exception e) {
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}
	
	public static void deletePicture(String pictureName, User owner) throws DBException{
		if( pictureName.equals(owner.getAvatarName())){
			setProfilePicture(pictureName, owner);
		}
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(DELETE_PICTURE);
			st.setInt(1, owner.getId());
			st.setString(2, pictureName);
			st.execute();

		} catch (Exception e) {
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}
	
	public static int getLastPhotoId() throws SQLException {
		Connection conn = ConnectionDispatcher.getConnection();
		ResultSet res = conn.createStatement().executeQuery(LAST_PHOTO_ID);
		res.next();
		return res.getInt(1);
	}
}
