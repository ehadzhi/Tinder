package com.tinder.info;

public interface UserViewParam {

	static final String USER = "user";
	static final String USER_CANDIDATES = "userCandidates";
	static final String DEFAULT_AVATAR = "avatar_default.jpg";
	static final String SEARCH_DISTANCE = "search-distance";
	static final String SHOW_WOMEN = "show-women";
	static final String SHOW_MEN = "show-men";
	static final String AGE_RANGE = "age-range";
	static final String EMAIL = "email";
	static final String USERNAME = "username";
	static final String AGE = "age";
	static final String PASSWORD = "password";
	static final String DESCRIPTION = "description";
	static final String GENDER = "gender";
	static final String FULL_NAME = "fullName";
	static final String S3ACCESSKEY = "AKIAJFLBQNS6WEO3LSUA";
	static final String S2SECRETKEY = "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";
	static final int FACEBOOK_PASS_LENGHT = 40;
	static final String FACEBOOK_ID = "facebookId";
	
	static boolean parseGender(String gender) {
		return gender.equals("male");
	}
}
