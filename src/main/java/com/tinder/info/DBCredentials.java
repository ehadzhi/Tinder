package com.tinder.info;

public interface DBCredentials {
	static final String DB_USER = "root";
	static final String DB_PASS = "root";
	static final String DB_ADDRES = "jdbc:mysql://127.0.0.1:3306?autoReconnect=true&useSSL=false";
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final int INITIAL_POOL_SIZE = 5;
	static final int MAX_POOL_SIZE = 10;
}
