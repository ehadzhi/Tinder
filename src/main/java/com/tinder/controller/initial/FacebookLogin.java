package com.tinder.controller.initial;

import java.io.IOException;

import org.jets3t.service.S3ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tinder.config.security.FacebookLoginProcessor;
import com.tinder.info.UserViewParam;

@RestController
@RequestMapping(value = "/FacebookLogin")
public class FacebookLogin {

	@Autowired
	private FacebookLoginProcessor loginProcesor;

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(
		@RequestParam(value = UserViewParam.PASSWORD, required = false) String password,
		@RequestParam(value = UserViewParam.EMAIL, required = false) String email,
		@RequestParam(value = UserViewParam.GENDER, required = false) String gender,
		@RequestParam(value = UserViewParam.FULL_NAME, required = false) String fullName,
		@RequestParam(UserViewParam.FACEBOOK_ID) String facebookId)
					throws IOException, S3ServiceException {
		
		loginProcesor.processFacebookLogin(email, gender, fullName, facebookId);
		
		return "/LocationSetter";
	}

}
