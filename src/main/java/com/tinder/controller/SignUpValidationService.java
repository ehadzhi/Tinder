package com.tinder.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tinder.exceptions.DBException;
import com.tinder.model.dao.UserDAO;

@RestController
public class SignUpValidationService {
	
//	@RequestMapping(value = "/SignUpValidationService", method = RequestMethod.POST)
//	public String doPost(HttpServletRequest request, HttpServletResponse response){
//		try {
//			
//			String username = request.getParameter("username");
//			String email = request.getParameter("email");
//			String age = request.getParameter("age");
//			if (username != null) {
//				if (!UserDAO.isUsernameExisting(username) && username.length() <= 45) {
//					return "OK";
//				} else if (UserDAO.isUsernameExisting(username)) {
//					return "This username is already in use!";
//				} else {
//					return "Too long username!";
//				}
//			}
//			if (email != null) {
//				if (!UserDAO.isEmailExisting(username) && username.length() <= 45) {
//					resp.append("email", "OK");
//				} else {
//					resp.append("email", "This email is already in use!");
//				}
//			}
//			if(age!=null){
//				if(Integer.parseInt(age)>100){
//					resp.append("age","You can't be that old!");
//				}
//				else if(Integer.parseInt(age)<1){
//					resp.append("age","You can't be that young!");
//				}
//				else{
//					resp.append("age","OK");
//				}
//			}
//			PrintWriter pw = response.getWriter();
//			pw.print(resp);
//			pw.flush();
//			
//		} catch (DBException e) {
//			e.printStackTrace();
//		}
//	}

}
