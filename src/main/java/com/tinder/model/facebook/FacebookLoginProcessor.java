package com.tinder.model.facebook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.Principal;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jets3t.service.S3ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.tinder.controller.picture.PictureUpload;
import com.tinder.info.UserViewParam;
import com.tinder.model.dao.picture.IPictureDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@Component
public class FacebookLoginProcessor {
	
	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IPictureDAO pictureDAO;

	@Autowired
	private PictureUpload pictureUpload;


	public void processFacebookLogin(String email, String gender, String fullName,
			String facebookId) throws IOException, S3ServiceException {
		String username = userDAO.getUsernameFromFacebookId(facebookId);
		User user = userDAO.getUser(email);
		if (username == null) {
			if (user == null) {
				username = registerWithUniqueUsername(email, gender, fullName);
			} else {
				username = user.getUsername();
			}
			connectWithFacebook(facebookId, username);
		}
		authenticateFacebookUser(facebookId);
	}

	private void authenticateFacebookUser(String facebookId) {
		String principalUsername = userDAO.getUsernameFromFacebookId(facebookId);
		Authentication authentication = new UsernamePasswordAuthenticationToken((Principal) () -> principalUsername,
				null, AuthorityUtils.createAuthorityList("ROLE_USER"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private void connectWithFacebook(String facebookId, String username) throws IOException, S3ServiceException {
		userDAO.addFacebookConnection(username, facebookId);
		uploadFacebookPicture(username, facebookId);
	}

	private String registerWithUniqueUsername(String email, String gender, String fullName) {
		String username;
		username = generateUsername(fullName);
		userDAO.registerUser(username, generatePassword(), email, UserViewParam.parseGender(gender), 18, fullName);
		return username;
	}

	private void uploadFacebookPicture(String newUsername, String facebookId) throws IOException, S3ServiceException {
			URL toGetFrom = new URL("https://graph.facebook.com/" + facebookId + "/picture?width=9999");
			File toUpload = new File(".jpg");
			FileUtils.copyURLToFile(toGetFrom, toUpload);
			FileInputStream input;
			input = new FileInputStream(toUpload);
			MultipartFile multipartFile = new MockMultipartFile("file", toUpload.getName(), "text/plain",
					IOUtils.toByteArray(input));
			pictureUpload.saveFile(multipartFile, userDAO.getUser(newUsername));
			pictureDAO.setProfilePicture(userDAO.getAllPhotosOfUser(newUsername).get(0), userDAO.getUser(newUsername));
			

	}

	private String generatePassword() {
		StringBuilder builder = new StringBuilder(UserViewParam.FACEBOOK_PASS_LENGHT);
		for (int index = 0; index < UserViewParam.FACEBOOK_PASS_LENGHT; index++) {
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
