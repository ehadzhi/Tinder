package com.tinder.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tinder.controller.picture.PictureUpload;
import com.tinder.info.UserParam;
import com.tinder.model.dao.user.IUserDAO;

@RestController
@RequestMapping(value = "/FacebookLogin")
public class FacebookLogin {

	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private PictureUpload pictureUpload;

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@RequestParam(value = UserParam.PASSWORD, required = false) String password,
			@RequestParam(value = UserParam.EMAIL, required = false) String email,
			@RequestParam(value = UserParam.GENDER, required = false) String gender,
			@RequestParam(value = UserParam.FULL_NAME, required = false) String fullName,
			@RequestParam(UserParam.FACEBOOK_ID) String facebookId) {
		String username = userDAO.getUsernameFromFacebookId(facebookId);

		if (username == null) {
			String newUsername = generateUsername(fullName);
			userDAO.registerUser(newUsername, generatePassword(), email, UserParam.parseGender(gender), 18, fullName);
			userDAO.addFacebookConnection(newUsername, facebookId);
			uploadPicture(newUsername, facebookId);

		}
		String principalUsername = userDAO.getUsernameFromFacebookId(facebookId);
		Authentication authentication = new 
				UsernamePasswordAuthenticationToken(
						(Principal) () -> principalUsername, null,
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "/LocationSetter";
	}

	private void uploadPicture(String newUsername, String facebookId) {
		try {
			URL toGetFrom = new URL("https://graph.facebook.com/" + facebookId + "/picture?width=9999");
			File toUpload = new File(".jpg");
			FileUtils.copyURLToFile(toGetFrom, toUpload);
			FileInputStream input;
			input = new FileInputStream(toUpload);
			MultipartFile multipartFile = new MockMultipartFile("file", toUpload.getName(), "text/plain",
					IOUtils.toByteArray(input));
			pictureUpload.saveFile(multipartFile, userDAO.getUser(newUsername));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (S3ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String generatePassword() {
		StringBuilder builder = new StringBuilder(UserParam.FACEBOOK_PASS_LENGHT);
		for (int index = 0; index < UserParam.FACEBOOK_PASS_LENGHT; index++) {
			builder.append((char) (Math.random() * Character.MAX_CODE_POINT));
		}
		return builder.toString();
	}

	private String generateUsername(String fullName) {
		fullName = fullName.trim().toLowerCase().replace(' ', '.');

		while (userDAO.isUsernameExisting(fullName)) {
			fullName = fullName.concat("" + Character.MAX_CODE_POINT);
		}
		return fullName;
	}
}
