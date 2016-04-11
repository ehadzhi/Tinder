package com.tinder.model.validator;

import org.springframework.stereotype.Component;

@Component
public class UserValidator implements IUserValidator{

	private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	private static final String USERNAME_REGEX = "^[0-9a-zA-Z]{1,100}";
	private static final String PASSWORD_REGEX = "[0-9a-zA-Z]{1,100}";
	
	@Override
	public boolean validateSignupParam(String username, String password, String email) {
		return 	validUsername(username) && 
				validEMail(email) && 
				validPassword(password);
	}
	
	public boolean validUsername(String username) {
		if (username != null && !username.equals("")) {
			return username.matches(USERNAME_REGEX);
		}
		return false;
	}
	
	public boolean validPassword(String password) {
		if (password != null && !password.equals("")) {
			return password.matches(PASSWORD_REGEX);
		}
		return false;
	}
	
	public boolean validEMail(String email) {
		if (email != null && !email.equals("")) {
			return email.matches(EMAIL_REGEX);
		}
		return false;
	}

}
