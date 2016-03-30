package com.tinder.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionDispatcher {
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306" 
				+ "?autoReconnect=true&useSSL=false";
	private static final String USER = "root";
	private static final String PASS = "root";
	private static Connection conn = null;
	
	private ConnectionDispatcher() {
	}
	
	public static Connection getConnection() {
		if (conn == null) {
			synchronized (Connection.class) {
				if (conn == null) {
					try {
						Class.forName("com.mysql.jdbc.Driver");
						return  (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return conn;
	}
	
	public static void returnConnection(Connection conn){
		
	}
}
