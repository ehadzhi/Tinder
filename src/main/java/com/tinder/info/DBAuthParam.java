package com.tinder.info;

public interface DBAuthParam {

	final String USERS_BY_USERNAME = "SELECT username,password_hash as 'password',true"
			+ " FROM tinder.users where binary username = ?;";
	
	final String AUTHORITIES_BY_USERNAME = "select ? as 'username' ,'ROLE_USER' as 'ROLE_USER';";
}
