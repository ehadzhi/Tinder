package com.tinder.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.startup.UserDatabase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tinder.exceptions.DBException;
import com.tinder.model.dao.UserDAO;
import com.tinder.model.pojo.User;

@RestController
public class SignUpValidationService {

	@RequestMapping(value = "/SignUpValidationService", method = RequestMethod.POST)
	public Map<String, String> doPost(HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String age = request.getParameter("age");
			String password = request.getParameter("password");
			String oldPassword = request.getParameter("oldPassword");
			User user = (User)request.getSession().getAttribute("user");
			
			System.out.println(email);
			if (username != null) {
				if (!UserDAO.isUsernameExisting(username) && username.length() <= 45) {
					result.put("username", "OK");
				} else if (UserDAO.isUsernameExisting(username)) {
					result.put("username", "This username is already in use!");
				} else {
					result.put("username", "Too long username!");
				}
			}
			if (email != null) {
				if (!UserDAO.isEmailExisting(email) && email.length() <= 45 && isValidEmailAddress(email)) {
					result.put("email", "OK");
				} else if(!isValidEmailAddress(email)){
					result.put("email", "Email is not valid!");
				}else {
					result.put("email", "This email is already in use!");
				}
			}
			if (age != null) {
				if (Integer.parseInt(age) > 100) {
					result.put("age", "You can't be that old!");
				} else if (Integer.parseInt(age) < 1) {
					result.put("age", "You can't be that young!");
				} else {
					result.put("age", "OK");
				}
			}
			if (password != null) {
				result.put("password", checkPasswordStrength(password));
			}
			if (oldPassword != null) {
				if(UserDAO.isUserAndPassExisting(user.getUsername(), oldPassword)){
					result.put("oldPassword", "OK");
				}
				else{
					result.put("oldPassword", "Incorrect password");
				}
			}

		} catch (DBException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String checkPasswordStrength(String password) {
		int strengthPercentage = 0;
		String[] partialRegexChecks = { ".*[a-z]+.*", // lower
				".*[A-Z]+.*", // upper
				".*[\\d]+.*", // digits
				".*[@#$%]+.*" // symbols
		};

		if (password.matches(partialRegexChecks[0])) {
			strengthPercentage += 1;
		}
		if (password.matches(partialRegexChecks[1])) {
			strengthPercentage += 1;
		}
		if (password.matches(partialRegexChecks[2])) {
			strengthPercentage += 1;
		}
		if (password.matches(partialRegexChecks[3])) {
			strengthPercentage += 1;
		}

		if (strengthPercentage == 1)
			return "Poor";
		else if (strengthPercentage == 2)
			return "Good";
		else if (strengthPercentage == 3)
			return "Very Good";
		else if (strengthPercentage == 4)
			return "Strong";

		return "";
	}

	private boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

}
