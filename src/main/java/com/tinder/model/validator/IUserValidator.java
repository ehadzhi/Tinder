package com.tinder.model.validator;

public interface IUserValidator {

	boolean validateSignupParam(String username, String password, String email);
}
