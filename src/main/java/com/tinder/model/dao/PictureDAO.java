package com.tinder.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tinder.exceptions.DBException;

public class PictureDAO {

	private static final String NUM_PHOTOS = "SELECT count(id) FROM tinder.pictures;";
	private static final String INSERT_PICTURE = 
			"INSERT INTO tinder.pictures VALUES (null,?,?,now());";

	public static void addPicture(String name,int ownerId) throws DBException{
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ConnectionDispatcher.getConnection();
			st = conn.prepareStatement(INSERT_PICTURE);
			st.setInt(1, ownerId);
			st.setString(2, name);
			st.execute();

		} catch (Exception e) {
			throw new DBException("Something went wrong with the Database.", e);
		} finally {
			ConnectionDispatcher.returnConnection(conn);
		}
	}
	
	public static int getNumPhotos() throws SQLException {
		Connection conn = ConnectionDispatcher.getConnection();
		ResultSet res = conn.createStatement().executeQuery(NUM_PHOTOS);
		res.next();
		return res.getInt(1);
	}
}
