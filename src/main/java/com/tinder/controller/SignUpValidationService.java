package com.tinder.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tinder.exceptions.DBException;
import com.tinder.model.dao.UserDAO;

@RestController
public class SignUpValidationService {

//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		try {
//			response.setContentType("aplication/json");
//			String username = request.getParameter("username");
//			String email = request.getParameter("email");
//			String age = request.getParameter("age");
//			JSONObject resp = new JSONObject();
//			if (username != null) {
//				if (!UserDAO.isUsernameExisting(username) && username.length() <= 45) {
//					resp.append("username", "OK");
//				} else if (UserDAO.isUsernameExisting(username)) {
//					resp.append("username", "This username is already in use!");
//				} else {
//					resp.append("username", "Too long username!");
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
